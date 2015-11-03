package com.bo.formularios.abm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJBException;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.bl.ProductBL;
import com.bo.principal.PanelDinamico;
import com.entities.mongo.Alert;
import com.entities.mongo.Category;
import com.entities.mongo.Product;
import com.entities.mongo.Spec;
import com.entities.sql.Unit;
import com.entities.sql.Usuario;
import com.entities.sql.VirtualStorage;
import com.services.Correo;
import com.services.UsuarioServiceLocal;
import com.services.VirtualStorageServiceLocal;
import com.services.interfaces.IProductBL;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table.ColumnResizeEvent;
import com.vaadin.ui.themes.ValoTheme;

public class FormAdminAV extends PanelDinamico{
	/**
	 * 
	 */
	private IProductBL servicioProducto = new ProductBL();
	
	private static final long serialVersionUID = 1L;
	private VerticalLayout panelArriba, panelAbajo;
	private HorizontalLayout rootLayout;
	private List<VirtualStorage>  listaVirtualStorage;
	private Window temaWindow;
	private List<Product>  listaProductos;
	private Table tableVirtualStorage, tableProductos;
	private Button bloquearAV, enviarMensaje, analizarFraude, imprimirReporte;
	private Button convertirGenerico, eliminarProucto, buscarProducto;
	private ComboBox comboAVs;
	private VirtualStorageServiceLocal servicioVS;
	private String mailDestino, mensaje;
	private TextField texMailDestino, texMensaje;
	private Button btnEnviarMensaje;
	
	private void lookup() {
		InitialContext context = null;
		try {
			context = new InitialContext();					
			servicioVS = (VirtualStorageServiceLocal) context.lookup("java:app/SAPo-BO/VirtualStorageServiceBean");
		} catch (NamingException e) {
			throw new EJBException(
					"It was not possible to get a reference to one of the required services",
					e);
		} finally {
			try {
				context.close();
			} catch (NamingException e) {
				
			}
		}
	}
	
	public FormAdminAV(){    
		lookup();
		listaVirtualStorage = servicioVS.getVS();
		panelArriba = new VerticalLayout();
        panelAbajo = new VerticalLayout();
        rootLayout = new HorizontalLayout(panelArriba,panelAbajo);
        rootLayout.setSizeFull();
        panelArriba = generarPanelArriba();
        panelArriba.setSizeFull();
        panelAbajo.setSizeFull();
        panelAbajo = generarPanelAbajo();
        rootLayout.addComponent(panelArriba);
        rootLayout.addComponent(panelAbajo);
        rootLayout.setExpandRatio(panelArriba, 1);
        rootLayout.setExpandRatio(panelAbajo, 1);
        this.addComponent(rootLayout);
        rootLayout.setSizeFull();
        tableVirtualStorage.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
				if(event.getProperty().getValue() != null){
					//listaProductos = ((int)tableVirtualStorage.getValue()-1));	
					listaProductos = servicioProducto.getAllProducts(1);
				}
			}
		});
        bloquearAV.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void buttonClick(final ClickEvent event) {
				 bloquearAV();
           }
			
        });
        
        enviarMensaje.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void buttonClick(final ClickEvent event) {
            	
            	temaWindow = new Window("Estilo");
            	VerticalLayout windowCont = new VerticalLayout();
            	windowCont.setMargin(true);
            	temaWindow.setContent(windowCont);
   
            	texMailDestino = new TextField("Destino", ((listaVirtualStorage.get((int) tableVirtualStorage.getValue()-1).getOwner().getMail())));
            	texMailDestino.setWidth("70%");       
            	windowCont.addComponent(texMailDestino);
        	    texMailDestino.setRequired (true);
        	    
        	    texMensaje = new TextField("Mensaje", "");
        	    texMensaje.setWidth("70%");           
            	windowCont.addComponent(texMensaje);
            	texMensaje.setRequired (true);
            	
            	btnEnviarMensaje = new Button("Enviar Mensaje");
            	btnEnviarMensaje.addStyleName(ValoTheme.BUTTON_PRIMARY);
            	btnEnviarMensaje.setWidth("70%");

                windowCont.addComponent(texMailDestino);
                windowCont.addComponent(texMensaje);
                windowCont.addComponent(btnEnviarMensaje);
                windowCont.setWidth("600px");
                windowCont.setHeight("300px"); 
                temaWindow.center();
                UI.getCurrent().addWindow(temaWindow);
                btnEnviarMensaje.addClickListener(new ClickListener() {
                    private static final long serialVersionUID = 1L;
                    @Override
                    public void buttonClick(final ClickEvent event) {
                    	enviarCorreo(texMailDestino.getValue(),texMensaje.getValue()); 
                    	temaWindow.close();
                   }
        			
                });
            	
           }
			
        });
	}

	private VerticalLayout generarPanelAbajo() {
		//listaProductos = listaProductos((int)tableVirtualStorage.getValue()-1));	
		listaProductos = servicioProducto.getAllProducts(1);
		VerticalLayout panDer = new VerticalLayout();
		tableProductos = new Table("Productos del Almacenes");
		tableProductos.addContainerProperty("Nombre", String.class, null);
		tableProductos.addContainerProperty("Código de Barras", String.class, null);
		tableProductos.addContainerProperty("Descripción", String.class, null);
		//tableProductos.addContainerProperty("Stock",  String.class, null);
	    ArrayList<String> nombreEncuestador = new ArrayList<String>();
	
	    for (Product listaP : listaProductos) {
	    	Object newItemId = tableProductos.addItem();
	    	Item row1 = tableProductos.getItem(newItemId);
	    	row1.getItemProperty("Nombre").setValue(listaP.getName());
	    	row1.getItemProperty("Código de Barras").setValue(listaP.getBarCode());
	    	row1.getItemProperty("Descripción").setValue(listaP.getDescription());
	    	//row1.getItemProperty("Stock").setValue(listaP.getStock());   	
	    }
	     
	     
	    tableProductos.setImmediate(true);
	    tableProductos.setSelectable(true);
	    tableProductos.select(1);
	    tableProductos.setNullSelectionItemId(false);
	    tableProductos.setPageLength(tableProductos.size());
	    tableProductos.setWidth("80%");
	    panDer.setMargin(true);
	    panDer.addComponent(tableProductos);
	    panDer.setComponentAlignment(tableProductos, Alignment.MIDDLE_CENTER);
	    
	    //convertirGenerico, eliminarProucto, buscarProducto
	    convertirGenerico = new Button("Convertir Producto en Genérico");
	    convertirGenerico.addStyleName(ValoTheme.BUTTON_PRIMARY);
	    
	    convertirGenerico.setWidth("70%");
	    panDer.addComponent(convertirGenerico);
	    
	    eliminarProucto = new Button("Eliminar Producto");
	    eliminarProucto.addStyleName(ValoTheme.BUTTON_PRIMARY);
	    eliminarProucto.setWidth("70%");
	    panDer.addComponent(eliminarProucto);
	    eliminarProucto.setEnabled(true);
	    
	    buscarProducto = new Button(" Buscar Producto en otros AV");
	    buscarProducto.addStyleName(ValoTheme.BUTTON_PRIMARY);
	    buscarProducto.setWidth("70%");
	    panDer.addComponent(buscarProducto);
	
	    
	    panDer.setComponentAlignment(convertirGenerico, Alignment.MIDDLE_CENTER);
	    panDer.setComponentAlignment(eliminarProucto, Alignment.MIDDLE_CENTER);
	    panDer.setComponentAlignment(buscarProducto, Alignment.MIDDLE_CENTER);
	    
	     
	    return panDer;
	}
	

	private VerticalLayout generarPanelArriba() {
		VerticalLayout panIzq = new VerticalLayout();
		tableVirtualStorage = new Table("Almacenes Virtuales");
		tableVirtualStorage.addContainerProperty("Nombre", String.class, null);
		tableVirtualStorage.addContainerProperty("Fecha Creación", Date.class, null);
		tableVirtualStorage.addContainerProperty("Creador", String.class, null);
		tableVirtualStorage.addContainerProperty("URL", String.class, null);
		tableVirtualStorage.addContainerProperty("Bloqueado", Boolean.class, null);
	    ArrayList<String> nombreEncuestador = new ArrayList<String>();
	
	    for (VirtualStorage vs : listaVirtualStorage) {
	    	Object newItemId = tableVirtualStorage.addItem();
	    	Item row1 = tableVirtualStorage.getItem(newItemId);
	    	row1.getItemProperty("Nombre").setValue(vs.getName());
	    	row1.getItemProperty("Fecha Creación").setValue(vs.getCreatedDate());
	    	row1.getItemProperty("Creador").setValue(vs.getOwner().getName());
	    	row1.getItemProperty("URL").setValue(vs.getUrl());   
	    	row1.getItemProperty("Bloqueado").setValue(vs.getBlocked());
	    }
	     
	     
	    tableVirtualStorage.setImmediate(true);
	    tableVirtualStorage.setSelectable(true);
	    tableVirtualStorage.select(1);
	    tableVirtualStorage.setNullSelectionItemId(false);
	    tableVirtualStorage.setPageLength(tableVirtualStorage.size());
	    tableVirtualStorage.setWidth("80%");
	    panIzq.setMargin(true);
	    panIzq.addComponent(tableVirtualStorage);
	    panIzq.setComponentAlignment(tableVirtualStorage, Alignment.MIDDLE_CENTER);
	   
	    bloquearAV = new Button("Bloquear Almacen Virtual");
	    bloquearAV.addStyleName(ValoTheme.BUTTON_PRIMARY);
	    bloquearAV.setClickShortcut(KeyCode.ENTER);
	    bloquearAV.setWidth("70%");
	    panIzq.addComponent(bloquearAV);
	    
	    enviarMensaje = new Button("Enviar Mensaje a Administrador");
	    enviarMensaje.addStyleName(ValoTheme.BUTTON_PRIMARY);
	    enviarMensaje.setWidth("70%");
	    panIzq.addComponent(enviarMensaje);
	    enviarMensaje.setEnabled(true);
	    
	    analizarFraude = new Button("Analizar Fraude");
	    analizarFraude.addStyleName(ValoTheme.BUTTON_PRIMARY);
	    analizarFraude.setWidth("70%");
	    panIzq.addComponent(analizarFraude);
	    analizarFraude.setEnabled(true);
	    
	    imprimirReporte = new Button("Imprimir Reporte");
	    imprimirReporte.addStyleName(ValoTheme.BUTTON_PRIMARY);
	    imprimirReporte.setWidth("70%");
	    panIzq.addComponent(imprimirReporte);
	
	    
	    panIzq.setComponentAlignment(bloquearAV, Alignment.BOTTOM_CENTER);
	    panIzq.setComponentAlignment(enviarMensaje, Alignment.BOTTOM_CENTER);
	    panIzq.setComponentAlignment(analizarFraude, Alignment.BOTTOM_CENTER);
	    panIzq.setComponentAlignment(imprimirReporte, Alignment.BOTTOM_CENTER);
	     
	    return panIzq;
	}	
	
	private void bloquearAV() {
		
		if (servicioVS.cambiarBloqueoVS((listaVirtualStorage.get((int) tableVirtualStorage.getValue()-1).getId()))){
			
			Notification sample = new Notification("Almacen Virtual Bloqueado");
			sample.show(Page.getCurrent());
		}
		Notification sample = new Notification("Almcen Virtual Desbloqueado");
    	sample.show(Page.getCurrent());
    	actualizar();
	}
	
	private void actualizar(){
		listaVirtualStorage = servicioVS.getVS();
		tableVirtualStorage.removeAllItems();
		for (VirtualStorage vs : listaVirtualStorage) {
	    	Object newItemId = tableVirtualStorage.addItem();
	    	Item row1 = tableVirtualStorage.getItem(newItemId);
	    	row1.getItemProperty("Nombre").setValue(vs.getName());
	    	row1.getItemProperty("Fecha Creación").setValue(vs.getCreatedDate());
	    	row1.getItemProperty("Creador").setValue(vs.getOwner().getName());
	    	row1.getItemProperty("URL").setValue(vs.getUrl());   
	    	row1.getItemProperty("Bloqueado").setValue(vs.getBlocked());
	    }
		//listaProductos = servicioProducto.getAllProducts(1);
	}
	
	private void enviarCorreo(String destino, String mensaje){
		Correo c = new Correo();  //pw: 2015sseadmin			
			try {
				c.enviarMensajeConAuth("smtp.gmail.com", 587,"sapoTSI2@gmail.com", destino,"sapoTSI2pass", "Mail enviado desde sistema SAPo", 
						mensaje);
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
}
