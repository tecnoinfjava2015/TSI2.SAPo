package com.bo.formularios.abm;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import utils.Encrypter;

import com.bo.principal.PanelDinamico;
import com.entities.sql.Usuario;
import com.services.UsuarioServiceLocal;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table.ColumnResizeEvent;
import com.vaadin.ui.themes.ValoTheme;

public class FormAceptarUsuarios extends PanelDinamico{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Usuario>  listaUsuarios;


	private VerticalLayout panelIzquierda, panelDerecha;
	private HorizontalLayout rootLayout;
	private Table table; 
	private Button aceptar;
	private TextField nick;
	private UsuarioServiceLocal servicio;
	
	private void lookup() {
			InitialContext context = null;
			try {
				context = new InitialContext();					
				servicio = (UsuarioServiceLocal) context.lookup("java:app/SAPo-BO/UsuarioServiceBean");
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
	
	public FormAceptarUsuarios(){    
		lookup();
		this.addStyleName("outlined");
        this.setSizeFull();
        listaUsuarios = servicio.getUsuariosPendientesAceptar();
        for(Usuario u : listaUsuarios){
        	//System.out.println("usuario " + u.getNick());
        }
        
        panelIzquierda = new VerticalLayout();
        panelDerecha = new VerticalLayout();
        rootLayout = new HorizontalLayout(panelIzquierda,panelDerecha);
        rootLayout.setSizeFull();
        panelIzquierda = generarPanelIzquierda();
        panelDerecha = generarPanelDerecha();
        rootLayout.addComponent(panelIzquierda);
        rootLayout.addComponent(panelDerecha);
        rootLayout.setExpandRatio(panelIzquierda, 1);
        rootLayout.setExpandRatio(panelDerecha, 1);
        this.addComponent(rootLayout);
    	table.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
				if(event.getProperty().getValue() != null){
					nick.setValue(listaUsuarios.get((int) table.getValue()-1).getNick());
					aceptar.setEnabled(true);
					nick.setReadOnly(true);
				}
			}
		});
    	
        aceptar.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void buttonClick(final ClickEvent event) {
            	aceptar();
            }
			private void aceptar() {	
				servicio.aceptarUsuario(nick.getValue());
		        Notification notif = new Notification("Usuario aceptado");
		        notif.setDelayMsec(2000);
		        notif.show(Page.getCurrent());
		        actualizarTabla();
			}			   
        });

	}
	
	public VerticalLayout generarPanelDerecha() {
		VerticalLayout panDer = new VerticalLayout();
	    table = new Table("Usuarios pendientes de aceptación");
	    table.addContainerProperty("Nombre", String.class, null);
	    ArrayList<String> nombreEncuestador = new ArrayList<String>();
	
	    for (Usuario usuario : listaUsuarios) {
	    	Object newItemId = table.addItem();
	    	Item row1 = table.getItem(newItemId);
	    	row1.getItemProperty("Nombre").setValue(usuario.getNick());
	    }
	     
	        //para ordenar tabla por nombre
		 table.addColumnResizeListener(new Table.ColumnResizeListener(){
	    	  public void columnResize(ColumnResizeEvent event) {
	    	        int width = event.getCurrentWidth();
	    	        String column = (String) event.getPropertyId();
	    	        table.setColumnFooter(column, String.valueOf(width) + "Nombre");
	    	        aceptar.setEnabled(true);   
	    	  }
		 }); 
		 table.setImmediate(true);
		 table.setSelectable(true);
		 table.select(1);
		 table.setNullSelectionItemId(false);
		 table.setPageLength(table.size());
		 table.setWidth("80%");
		 table.setHeight("18em");
	     panDer.setMargin(true);
	     panDer.addComponent(table);
	     panDer.setComponentAlignment(table, Alignment.MIDDLE_CENTER);
	     
	     return panDer;
	}	

	public void actualizarTabla(){
		
		VerticalLayout nuevaTabla = new VerticalLayout();
	    nuevaTabla = generarPanelDerecha();
	    rootLayout.replaceComponent(panelDerecha, nuevaTabla);
	    panelDerecha = nuevaTabla;
	    
	    table.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
				if(event.getProperty().getValue() != null){
					nick.setValue(listaUsuarios.get((int) table.getValue()-1).getNick());
					aceptar.setEnabled(true);
					nick.setReadOnly(true);
				}
			}
		});
	    
	}
	
	private VerticalLayout generarPanelIzquierda(){
		
		VerticalLayout panIzq = new VerticalLayout();
	    panIzq.setSizeFull();
	    panIzq.setMargin(true);
	    MarginInfo mi = new MarginInfo(false, true, false, true);
	    panIzq.setMargin(mi);
	
		nick = new TextField("Nick", "");
	    nick.setWidth("70%");       
	    panIzq.addComponent(nick);
	    nick.setRequired (true);
	    
	    aceptar = new Button("Aceptar Usuario");
	    aceptar.addStyleName(ValoTheme.BUTTON_PRIMARY);
	    aceptar.setClickShortcut(KeyCode.ENTER);
	    aceptar.setWidth("70%");
	    aceptar.setEnabled(false);
	    panIzq.addComponent(aceptar);
	
	    panIzq.setComponentAlignment(nick, Alignment.TOP_CENTER);
	    panIzq.setComponentAlignment(aceptar, Alignment.MIDDLE_CENTER);
	   	
	    return panIzq;
	   
	}

}
