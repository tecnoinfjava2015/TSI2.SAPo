package com.bo.formularios.abm;



import java.util.ArrayList;
import java.util.List;

import utils.Encrypter;

import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.entities.sql.Usuario;


import com.bo.principal.PanelDinamico;
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
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table.ColumnResizeEvent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class FormABMUsuario extends PanelDinamico{
	//private AbsoluteLayout panelDinamico = new AbsoluteLayout();

	
	
	private static final long serialVersionUID = 1L;
	private List<Usuario>  listaUsuarios;


	private VerticalLayout panelIzquierda, panelDerecha;
	private HorizontalLayout rootLayout;
	private Table table; 
	private Button modificar, alta, eliminar, recargar;
	private TextField nick, mail;
	private ComboBox tipo;
	private PasswordField password, passwordConfirm;
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
	
	public FormABMUsuario(){    
		lookup();
		this.addStyleName("outlined");
        this.setSizeFull();
        listaUsuarios = servicio.getUsuariosHabilitados();
        for(Usuario u : listaUsuarios){
        	System.out.println("usuario " + u.getNick());
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
					modificar.setEnabled(true);
					eliminar.setEnabled(true);
					nick.setValue(listaUsuarios.get((int) table.getValue()-1).getNick());
					password.setValue(listaUsuarios.get((int) table.getValue()-1).getPassword());
					passwordConfirm.setValue(listaUsuarios.get((int) table.getValue()-1).getPassword());
					alta.setEnabled(false);
					nick.setReadOnly(true);
				}
			}
		});
    	
        alta.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void buttonClick(final ClickEvent event) {
                alta();
            }
			private void alta() {	
				if (nick.isEmpty()){ 
		        	Notification notif = new Notification("Debe cargar un nick");
		        	notif.setDelayMsec(2000);
		        	notif.show(Page.getCurrent());
				}
				else
		        if (password.isEmpty() || !password.getValue().equals(passwordConfirm.getValue())){
		        	Notification notif = new Notification("Debe cargar un password distinto de vacio y que coincida con la confirmación");
		        	notif.setDelayMsec(2000);
		        	notif.show(Page.getCurrent());
		        }
		        else{
		        	String md5 = new Encrypter().MD5(password.getValue());
		        	servicio.registroUsuario(nick.getValue(), (String) tipo.getValue(), mail.getValue(), nick.getValue(), md5, true, true);
		        	Notification notif = new Notification("Usuario cargado con éxito");
		        	notif.setDelayMsec(2000);
		        	notif.show(Page.getCurrent());
		        	reiniciarCampos();
		        }
		        actualizarTabla();
			}			   
        });
        
        modificar.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void buttonClick(final ClickEvent event) {
            	if (nick.isEmpty()){ 
		        	Notification notif = new Notification("Debe cargar un nick");
		        	notif.setDelayMsec(2000);
		        	notif.show(Page.getCurrent());
				}
				else
		        if (password.isEmpty() || !password.getValue().equals(passwordConfirm.getValue())){
		        	Notification notif = new Notification("Debe cargar un password distinto de vacio y que coincida con la confirmación");
		        	notif.setDelayMsec(2000);
		        	notif.show(Page.getCurrent());
		        }
		        else{
		        	String md5 = new Encrypter().MD5(password.getValue());
		        	servicio.modificarUsuario(nick.getValue(), (String) tipo.getValue(), mail.getValue(), nick.getValue(), md5, true, true);
		        	Notification notif = new Notification("Usuario modificado con exito");
		        	notif.setDelayMsec(2000);
		        	notif.show(Page.getCurrent());   	
		    	    actualizarTabla();
		    	    reiniciarCampos();
		        }
            }
        });
        
        eliminar.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void buttonClick(final ClickEvent event) {
            	servicio.borrarUsuario(nick.getValue());
	        	Notification sample = new Notification("Usuario eliminado con exito");
	        	sample.show(Page.getCurrent());
	            actualizarTabla();
	    	    reiniciarCampos();  
            }
        });
            
        recargar.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void buttonClick(final ClickEvent event) {
				actualizarTabla();
				reiniciarCampos(); 
           }
        });
        
        
        
	}
	
	public VerticalLayout generarPanelDerecha() {
		VerticalLayout panDer = new VerticalLayout();
	    table = new Table("Usuarios cargados");
	    table.addContainerProperty("Nombre", String.class, null);
	    table.addContainerProperty("Tipo", String.class, null);
	    table.addContainerProperty("Mail", String.class, null);
	    ArrayList<String> nombreEncuestador = new ArrayList<String>();
	
	    for (Usuario usuario : listaUsuarios) {
	    	Object newItemId = table.addItem();
	    	Item row1 = table.getItem(newItemId);
	    	row1.getItemProperty("Nombre").setValue(usuario.getNick());
	    	row1.getItemProperty("Tipo").setValue(usuario.getType());
	    	row1.getItemProperty("Mail").setValue(usuario.getMail());
	    }
	     
	        //para ordenar tabla por nombre
		 table.addColumnResizeListener(new Table.ColumnResizeListener(){
	    	  public void columnResize(ColumnResizeEvent event) {
	    	        int width = event.getCurrentWidth();
	    	        String column = (String) event.getPropertyId();
	    	        table.setColumnFooter(column, String.valueOf(width) + "Nombre");
	    	        modificar.setEnabled(true);
	    	        eliminar.setEnabled(true);
	    	        alta.setEnabled(false);
	    	       
	    	  }
		 }); 
		 table.setImmediate(true);
		 table.setSelectable(true);
		 table.select(1);
		 table.setNullSelectionItemId(false);
		 table.setPageLength(table.size());
		 table.setWidth("80%");
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
					modificar.setEnabled(true);
					eliminar.setEnabled(true);
					nick.setValue(listaUsuarios.get((int) table.getValue()-1).getNick());
					password.setValue(listaUsuarios.get((int) table.getValue()-1).getPassword());
					passwordConfirm.setValue(listaUsuarios.get((int) table.getValue()-1).getPassword());
					alta.setEnabled(false);
					nick.setReadOnly(true);
				}
			}
		});
	    
	}
	
	private void reiniciarCampos(){
		nick.setReadOnly(false);
		nick.setValue("");
     	password.setValue("");
     	passwordConfirm.setValue("");
        modificar.setEnabled(false);
 	    eliminar.setEnabled(false);
 	    alta.setEnabled(true);	
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
	    
	    password = new  PasswordField("Password", "");
	    password.setWidth("70%");
	    panIzq.addComponent(password);
	    password.setRequired (true);
	    
	    passwordConfirm = new  PasswordField("Confirme Password", "");
	    passwordConfirm.setWidth("70%");
	    panIzq.addComponent(passwordConfirm);
	    passwordConfirm.setRequired (true);
	    
	    mail = new  TextField("Mail", "");
	    mail.setWidth("70%");
	    panIzq.addComponent(mail);
	    mail.setRequired (true);
	    
	    List<String> listaTipos = new ArrayList<String>();
	    listaTipos.add("Administrador");  listaTipos.add("Free");  listaTipos.add("Premium");
	    tipo = new ComboBox("Seleccione un Tipo",listaTipos);  
	    tipo.setValue(tipo.getItemIds().iterator().next());
	    tipo.setNullSelectionAllowed(false);
	    tipo.setTextInputAllowed(false);
	    tipo.setWidth("70%");
	    tipo.setFilteringMode(FilteringMode.CONTAINS);
	    //temaCombo.setValue(temaCombo.getItemIds().iterator().next());
	    panIzq.addComponent(tipo);
	    
	    alta = new Button("Nuevo Usuario");
	    alta.addStyleName(ValoTheme.BUTTON_PRIMARY);
	    alta.setClickShortcut(KeyCode.ENTER);
	    alta.setWidth("70%");
	    panIzq.addComponent(alta);
	    
	    modificar = new Button("Modificar Datos Usuario");
	    modificar.addStyleName(ValoTheme.BUTTON_PRIMARY);
	    modificar.setWidth("70%");
	    panIzq.addComponent(modificar);
	    modificar.setEnabled(false);
	    
	    eliminar = new Button("Eliminar Usuario");
	    eliminar.addStyleName(ValoTheme.BUTTON_PRIMARY);
	    eliminar.setWidth("70%");
	    panIzq.addComponent(eliminar);
	    eliminar.setEnabled(false);
	    
	    recargar = new Button("Reiniciar Pantalla");
	    recargar.addStyleName(ValoTheme.BUTTON_PRIMARY);
	    recargar.setWidth("70%");
	    panIzq.addComponent(recargar);
	
	    panIzq.setComponentAlignment(nick, Alignment.BOTTOM_CENTER);
	    panIzq.setComponentAlignment(password, Alignment.BOTTOM_CENTER);
	    panIzq.setComponentAlignment(passwordConfirm, Alignment.BOTTOM_CENTER);
	    panIzq.setComponentAlignment(mail, Alignment.BOTTOM_CENTER);
	    panIzq.setComponentAlignment(tipo, Alignment.BOTTOM_CENTER);
	    
	    panIzq.setComponentAlignment(alta, Alignment.BOTTOM_CENTER);
	    panIzq.setComponentAlignment(modificar, Alignment.BOTTOM_CENTER);
	    panIzq.setComponentAlignment(eliminar, Alignment.BOTTOM_CENTER);
	    panIzq.setComponentAlignment(recargar, Alignment.BOTTOM_CENTER);
	
	    return panIzq;
	   
	}
	
	
}
