package com.bo.formularios.abm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJBException;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.bl.CategoryBL;
import com.bl.GenericProductBL;
import com.bl.ProductBL;
import com.bo.principal.PanelDinamico;
import com.entities.mongo.Alert;
import com.entities.mongo.Category;
import com.entities.mongo.GenericProduct;
import com.entities.mongo.Product;
import com.entities.mongo.Spec;
import com.entities.sql.Unit;
import com.entities.sql.Usuario;
import com.entities.sql.VirtualStorage;
import com.services.Correo;
import com.services.UsuarioServiceLocal;
import com.services.VirtualStorageServiceLocal;
import com.services.interfaces.ICategoryBL;
import com.services.interfaces.IGenericProductBL;
import com.services.interfaces.IProductBL;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.combobox.FilteringMode;
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
	private ICategoryBL servicioCategoria = new CategoryBL();
	private IGenericProductBL servicioGenericProduct = new GenericProductBL();
	
	private static final long serialVersionUID = 1L;
	private VerticalLayout panelArriba, panelAbajo;
	private HorizontalLayout rootLayout;
	private List<VirtualStorage>  listaVirtualStorage;
	private List<Category>  listaCategorias;
	private List<Product>  listaProductos;
	private Window temaWindow;
	private Table tableVirtualStorage, tableProductos, tableCategorias;
	private Button bloquearAV, enviarMensaje, analizarFraude, imprimirReporte;
	private Button convertirGenerico, eliminarProducto, buscarProducto;
	private Button analizarProductos, analizarCategorias;
	private ComboBox porcentajeIgualdad;
	private VirtualStorageServiceLocal servicioVS;
	private String mailDestino, mensaje;
	private TextField texMailDestino, texMensaje;
	private Button btnEnviarMensaje;
	private int porcentajeSeleccionado;
	private String fraudeDe, repetidoEn; 
	
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
		repetidoEn = new String("");
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
					//listaVirtualStorage.get(((int)tableVirtualStorage.getValue()-1));
					listaProductos = servicioProducto.getAllProducts(listaVirtualStorage.get(((int)tableVirtualStorage.getValue()-1)).getId());
					listaCategorias = servicioCategoria.getAllCategories(listaVirtualStorage.get(((int)tableVirtualStorage.getValue()-1)).getId(),0,20);
				//	System.out.println(listaProductos);
					
					VerticalLayout nuevoPanDer = new VerticalLayout();
					nuevoPanDer = generarPanelAbajo();
			        rootLayout.replaceComponent (panelAbajo, nuevoPanDer);
			        panelAbajo = nuevoPanDer;
					//listaProductos = servicioProducto.getAllProducts(1);
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
                windowCont.setWidth("500px");
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
        
        analizarFraude.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void buttonClick(final ClickEvent event) {
            	
            	temaWindow = new Window("Estilo");
            	VerticalLayout windowCont = new VerticalLayout();
            	windowCont.setMargin(true);
            	temaWindow.setContent(windowCont);
            
            	analizarProductos = new Button("Analizar Productos");
            	analizarProductos.addStyleName(ValoTheme.BUTTON_PRIMARY);
            	analizarProductos.setWidth("70%");
            	
            	analizarCategorias = new Button("Analizar Categorias");
            	analizarCategorias.addStyleName(ValoTheme.BUTTON_PRIMARY);
            	analizarCategorias.setWidth("70%");

            	porcentajeIgualdad = new ComboBox("Seleccione porcentaje mínimo de igualdad");
            	porcentajeIgualdad.addItem("50");
            	porcentajeIgualdad.addItem("80");         	
            	porcentajeIgualdad.addItem("100");

            	porcentajeIgualdad.setFilteringMode(FilteringMode.CONTAINS);
            	porcentajeIgualdad.setWidth("90%");
            	porcentajeIgualdad.setNullSelectionAllowed(false);
            	porcentajeIgualdad.setTextInputAllowed(false);
            	porcentajeIgualdad.setValue(porcentajeIgualdad.getItemIds().iterator().next());
            	
            	
                windowCont.addComponent(analizarProductos);
                windowCont.addComponent(analizarCategorias);
                windowCont.addComponent(porcentajeIgualdad);
                windowCont.setWidth("500px");
                windowCont.setHeight("300px"); 
          
                temaWindow.center();
                UI.getCurrent().addWindow(temaWindow);
                analizarProductos.addClickListener(new ClickListener() {
                    private static final long serialVersionUID = 1L;
                    @Override
                    public void buttonClick(final ClickEvent event) {
                    	if (fraudeProductos()) {
                    		Notification sample = new Notification("Posible Fraude Detectado con el Almcen Virtual " + fraudeDe);
                    		sample.setDelayMsec(2000);
                        	sample.show(Page.getCurrent());
                    	}
                    	else{
                    		Notification sample = new Notification("No se ha Detectado Fraude");
                    		sample.setDelayMsec(2000);
                        	sample.show(Page.getCurrent());
                    	}
                    	//temaWindow.close();
               
                   }
                });	
                analizarCategorias.addClickListener(new ClickListener() {
                    private static final long serialVersionUID = 1L;
                    @Override
                    public void buttonClick(final ClickEvent event) {
                    	if (fraudeCategorias()) {
                    		Notification sample = new Notification("Posible Fraude Detectado con el Almcen Virtual " + fraudeDe);
                    		sample.setDelayMsec(2000);
                        	sample.show(Page.getCurrent());
                    	}
                    	else{
                    		
                    		Notification sample = new Notification("No se ha dDetectado Fraude");
                    		sample.setDelayMsec(2000);
                        	sample.show(Page.getCurrent());
                    	}
                    	//temaWindow.close();
                   }
                });
           }
        });
        
        eliminarProducto.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void buttonClick(final ClickEvent event) {
            	servicioProducto.deleteProduct(listaVirtualStorage.get(((int)tableVirtualStorage.getValue()-1)).getId(), listaProductos.get(((int)tableProductos.getValue()-1)).getBarCode());
            	Notification sample = new Notification("Producto Eliminado");
        		sample.setDelayMsec(2000);
            	sample.show(Page.getCurrent());
            	actualizar();
           }
			
        });
        
        convertirGenerico.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void buttonClick(final ClickEvent event) {
            	GenericProduct gp = new GenericProduct();
            	gp.setBarcode(listaProductos.get(((int)tableProductos.getValue()-1)).getBarCode());
            	gp.setDescription(listaProductos.get(((int)tableProductos.getValue()-1)).getDescription());
            	gp.setName(listaProductos.get(((int)tableProductos.getValue()-1)).getName());
            	servicioGenericProduct.createGenericProduct(gp);
            	Notification sample = new Notification("Producto Generico Creado");
        		sample.setDelayMsec(2000);
            	sample.show(Page.getCurrent());
           }
			
        });
        
        buscarProducto.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void buttonClick(final ClickEvent event) {
            	//listaProductos.get(((int)tableProductos.getValue()-1)).getBarCode();
            	//repetidoEn = "";
            	List<Product>  listaProductosTemp;
        		for (VirtualStorage vs : listaVirtualStorage){
        			if(listaVirtualStorage.get((int) tableVirtualStorage.getValue()-1).getId() != vs.getId()){
        				listaProductosTemp = servicioProducto.getAllProducts(vs.getId());
        				for (Product p : listaProductos){
        					if (servicioProducto.estaEnLista(p,listaProductosTemp)){
        						repetidoEn = " " + repetidoEn + vs.getName();
        					}
        				}
        			}		
        		}
            	Notification sample = new Notification("Producto Repetido en " + repetidoEn);
        		sample.setDelayMsec(2000);
            	sample.show(Page.getCurrent());
           }
			
        });
        
	}

	private VerticalLayout generarPanelAbajo() {
		VerticalLayout arribaDer = new VerticalLayout();
		VerticalLayout abajoDer = new VerticalLayout();
		//listaProductos = listaProductos((int)tableVirtualStorage.getValue()-1));	
		//listaProductos = servicioProducto.getAllProducts(1);
		if (listaVirtualStorage != null && listaVirtualStorage.size() > 0 ){
			listaProductos = servicioProducto.getAllProducts(listaVirtualStorage.get(((int)tableVirtualStorage.getValue()-1)).getId());
			listaCategorias = servicioCategoria.getAllCategories(listaVirtualStorage.get(((int)tableVirtualStorage.getValue()-1)).getId(),0,20);
		}
		
		
		VerticalLayout panDer = new VerticalLayout();
		tableProductos = new Table("Productos del Almacenes");
		tableProductos.addContainerProperty("Nombre", String.class, null);
		tableProductos.addContainerProperty("Código de Barras", String.class, null);
		tableProductos.addContainerProperty("Descripción", String.class, null);
		//tableProductos.addContainerProperty("Stock",  String.class, null);
		if (listaProductos != null && listaProductos.size() > 0 ){
		    for (Product listaP : listaProductos) {
		    	Object newItemId = tableProductos.addItem();
		    	Item row1 = tableProductos.getItem(newItemId);
		    	row1.getItemProperty("Nombre").setValue(listaP.getName());
		    	row1.getItemProperty("Código de Barras").setValue(listaP.getBarCode());
		    	row1.getItemProperty("Descripción").setValue(listaP.getDescription());
		    	//row1.getItemProperty("Stock").setValue(listaP.getStock());   	
		    }
		} 
	     
	    tableProductos.setImmediate(true);
	    tableProductos.setSelectable(true);
	    tableProductos.select(2);
	    tableProductos.setNullSelectionItemId(false);
	    //tableProductos.setPageLength(tableProductos.size());
	    tableProductos.setWidth("80%");
	    tableProductos.setHeight("18em");
	    arribaDer.setMargin(true);
	    arribaDer.addComponent(tableProductos);
	    arribaDer.setComponentAlignment(tableProductos, Alignment.MIDDLE_CENTER);
	    
	    tableCategorias = new Table("Categorias del Almacen");
	    tableCategorias.addContainerProperty("Nombre", String.class, null);
	    tableCategorias.addContainerProperty("Icono", String.class, null);
	    if (listaCategorias != null && listaCategorias.size() > 0 ){
		    for (Category listaC : listaCategorias) {
		    	Object newItemId = tableCategorias.addItem();
		    	Item row1 = tableCategorias.getItem(newItemId);
		    	row1.getItemProperty("Nombre").setValue(listaC.getName());
		    	row1.getItemProperty("Icono").setValue(listaC.getIcon());
		    }	   	
	    }
	     
	     
	    tableCategorias.setImmediate(true);
	    tableCategorias.setSelectable(true);
	    tableCategorias.select(1);
	    tableCategorias.setNullSelectionItemId(false);
	    tableCategorias.setPageLength(tableCategorias.size());
	    tableCategorias.setWidth("80%");
	    tableCategorias.setHeight("18em");
	    abajoDer.setMargin(true);
	    abajoDer.addComponent(tableCategorias);
	    abajoDer.setComponentAlignment(tableCategorias, Alignment.MIDDLE_CENTER);
	    
	    panDer.addComponent(arribaDer);
	    panDer.addComponent(abajoDer);
	    return panDer;
	}
	

	private VerticalLayout generarPanelArriba() {
		VerticalLayout panIzq = new VerticalLayout();
		VerticalLayout arribaIzq = new VerticalLayout();
		VerticalLayout abajoIzq = new VerticalLayout();
		tableVirtualStorage = new Table("Almacenes Virtuales");
		tableVirtualStorage.addContainerProperty("Nombre", String.class, null);
		tableVirtualStorage.addContainerProperty("Fecha Creación", String.class, null);
		tableVirtualStorage.addContainerProperty("Creador", String.class, null);
		tableVirtualStorage.addContainerProperty("URL", String.class, null);
		tableVirtualStorage.addContainerProperty("Bloqueado", Boolean.class, null);
	    ArrayList<String> nombreEncuestador = new ArrayList<String>();
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    for (VirtualStorage vs : listaVirtualStorage) {
	    	Object newItemId = tableVirtualStorage.addItem();
	    	Item row1 = tableVirtualStorage.getItem(newItemId);
	    	row1.getItemProperty("Nombre").setValue(vs.getName());
	    	String date = sdf.format(vs.getCreatedDate());
	    	row1.getItemProperty("Fecha Creación").setValue(date);
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
	    tableVirtualStorage.setHeight("18em");
	    
	    arribaIzq.setMargin(true);
	    arribaIzq.addComponent(tableVirtualStorage);
	    arribaIzq.setComponentAlignment(tableVirtualStorage, Alignment.TOP_CENTER);
	   
	    bloquearAV = new Button("Bloquear Almacen Virtual");
	    bloquearAV.addStyleName(ValoTheme.BUTTON_PRIMARY);
	    bloquearAV.setClickShortcut(KeyCode.ENTER);
	    bloquearAV.setWidth("70%");
	    abajoIzq.addComponent(bloquearAV);
	    
	    enviarMensaje = new Button("Enviar Mensaje a Administrador");
	    enviarMensaje.addStyleName(ValoTheme.BUTTON_PRIMARY);
	    enviarMensaje.setWidth("70%");
	    abajoIzq.addComponent(enviarMensaje);
	    enviarMensaje.setEnabled(true);
	    
	    analizarFraude = new Button("Analizar Fraude");
	    analizarFraude.addStyleName(ValoTheme.BUTTON_PRIMARY);
	    analizarFraude.setWidth("70%");
	    abajoIzq.addComponent(analizarFraude);
	    analizarFraude.setEnabled(true);
	    
	    imprimirReporte = new Button("Imprimir Reporte");
	    imprimirReporte.addStyleName(ValoTheme.BUTTON_PRIMARY);
	    imprimirReporte.setWidth("70%");
	    abajoIzq.addComponent(imprimirReporte);
	    
	    convertirGenerico = new Button("Convertir Producto en Genérico");
	    convertirGenerico.addStyleName(ValoTheme.BUTTON_PRIMARY);
	    convertirGenerico.setWidth("70%");
	    abajoIzq.addComponent(convertirGenerico);
	    
	    eliminarProducto = new Button("Eliminar Producto");
	    eliminarProducto.addStyleName(ValoTheme.BUTTON_PRIMARY);
	    eliminarProducto.setWidth("70%");
	    abajoIzq.addComponent(eliminarProducto);
	    eliminarProducto.setEnabled(true);
	    
	    buscarProducto = new Button(" Buscar Producto en otros AV");
	    buscarProducto.addStyleName(ValoTheme.BUTTON_PRIMARY);
	    buscarProducto.setWidth("70%");
	    abajoIzq.addComponent(buscarProducto);
		    
	    abajoIzq.setComponentAlignment(bloquearAV, Alignment.BOTTOM_CENTER);
	    abajoIzq.setComponentAlignment(enviarMensaje, Alignment.BOTTOM_CENTER);
	    abajoIzq.setComponentAlignment(analizarFraude, Alignment.BOTTOM_CENTER);
	    abajoIzq.setComponentAlignment(imprimirReporte, Alignment.BOTTOM_CENTER);
	    abajoIzq.setComponentAlignment(convertirGenerico, Alignment.BOTTOM_CENTER);
	    abajoIzq.setComponentAlignment(eliminarProducto, Alignment.BOTTOM_CENTER);
	    abajoIzq.setComponentAlignment(buscarProducto, Alignment.BOTTOM_CENTER);
	    abajoIzq.setSizeFull(); 
	    
	    panIzq.addComponent(arribaIzq);
	    panIzq.addComponent(abajoIzq);
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
		Correo c = new Correo();  			
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
	
	private Boolean fraudeProductos(){
		List<Product>  listaProductosTemp;
		int totalProd = listaProductos.size();
		int iguales = 0, igualesFinal=0;
		for (VirtualStorage vs : listaVirtualStorage){
			if(listaVirtualStorage.get((int) tableVirtualStorage.getValue()-1).getId() != vs.getId()){
				listaProductosTemp = servicioProducto.getAllProducts(vs.getId());
				for (Product p : listaProductos){
					if (servicioProducto.estaEnLista(p,listaProductosTemp)){
						iguales++;
					}
				}
				if (igualesFinal <= iguales ){
					igualesFinal = iguales;
					iguales = 0;
					fraudeDe = vs.getName();
				}
			}		
		}
		if(porcentajeIgualdad.getValue().equals("50"))porcentajeSeleccionado = 50;
		else if(porcentajeIgualdad.getValue().equals("80"))porcentajeSeleccionado = 80;
		else if(porcentajeIgualdad.getValue().equals("100"))porcentajeSeleccionado = 100;
		
		if(totalProd * porcentajeSeleccionado <= igualesFinal * 100){

			return true;
		}
		return false;
	}
	
	private Boolean fraudeCategorias(){
		List<Category>  listaCtegoriasTemp;
		int totalCat = listaCategorias.size();
		int iguales = 0, igualesFinal=0;
		for (VirtualStorage vs : listaVirtualStorage){
			if(listaVirtualStorage.get((int) tableVirtualStorage.getValue()-1).getId() != vs.getId()){
				listaCtegoriasTemp = servicioCategoria.getAllCategories(vs.getId());
				for (Category c : listaCategorias){
					if (servicioCategoria.estaEnLista(c,listaCtegoriasTemp)){
						iguales++;
					}
				}
				if (igualesFinal <= iguales ){
					igualesFinal = iguales;
					iguales = 0;
					fraudeDe = vs.getName();
				}
			}	
		}
		
		if(porcentajeIgualdad.getValue().equals("50"))porcentajeSeleccionado = 50;
		else if(porcentajeIgualdad.getValue().equals("80"))porcentajeSeleccionado = 80;
		else if(porcentajeIgualdad.getValue().equals("100"))porcentajeSeleccionado = 100;
		if(totalCat * porcentajeSeleccionado <= igualesFinal * 100){
			return true;
		}
		return false;
	}
}
