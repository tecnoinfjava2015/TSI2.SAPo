package com.bo.formularios.abm;

import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.bo.principal.PanelDinamico;
import com.services.UsuarioServiceLocal;
import com.services.VirtualStorageServiceLocal;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

public class FormABMHARD extends PanelDinamico{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Button usuarios, virtualStorage, genericProduct;
	
	private UsuarioServiceLocal servicioUsuario;
	private VirtualStorageServiceLocal servicioVS;
	
	private void lookup() {
			InitialContext context = null;
			try {
				context = new InitialContext();					
				servicioUsuario = (UsuarioServiceLocal) context.lookup("java:app/SAPo-BO/UsuarioServiceBean");
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
	
	private void lookup2() {
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
	
	
	
	public FormABMHARD(){    
		lookup();
		lookup2();
		VerticalLayout panIzq = new VerticalLayout();
	    panIzq.setSizeFull();
		this.addComponent(panIzq);
		usuarios = new Button("Cargar Usuarios");
		usuarios.addStyleName(ValoTheme.BUTTON_PRIMARY);
		usuarios.setClickShortcut(KeyCode.ENTER);
		usuarios.setWidth("70%");
		panIzq.addComponent(usuarios);
	    
	    virtualStorage = new Button("Cargar Virtual Storage");
	    virtualStorage.addStyleName(ValoTheme.BUTTON_PRIMARY);
	    virtualStorage.setWidth("70%");
	    panIzq.addComponent(virtualStorage);
	    
	    
	    genericProduct = new Button("Cargar Productos Genericos");
	    genericProduct.addStyleName(ValoTheme.BUTTON_PRIMARY);
	    genericProduct.setWidth("70%");
	    panIzq.addComponent(genericProduct); 	
	    
	    panIzq.setComponentAlignment(usuarios, Alignment.TOP_CENTER);
	    panIzq.setComponentAlignment(virtualStorage, Alignment.TOP_CENTER);
	    panIzq.setComponentAlignment(genericProduct, Alignment.TOP_CENTER);
	    
	    usuarios.addClickListener(new ClickListener() {
	        private static final long serialVersionUID = 1L;
	        @Override
	        public void buttonClick(final ClickEvent event) {
	        	servicioUsuario.registroUsuario("usuario2", "Administrador", "lalal@mail", "usuario2", "1a1dc91c907325c69271ddf0c944bc72", true, true);
	        	servicioUsuario.registroUsuario("usuario3", "Free", "lalal@mail", "usuario3", "1a1dc91c907325c69271ddf0c944bc72", true, true);
	        	servicioUsuario.registroUsuario("usuario4", "Premium", "lalal@mail", "usuario4", "1a1dc91c907325c69271ddf0c944bc72", true, true);
	       }
	    });
	    
	    virtualStorage.addClickListener(new ClickListener() {
	        private static final long serialVersionUID = 1L;
	        @Override
	        public void buttonClick(final ClickEvent event) {
	        	java.util.Date utilDate = new java.util.Date(); 
	        	servicioVS.registroVS("AV1", "con", "URL", utilDate, "CSS", "loading", true,true, 3, "logo");
	        	servicioVS.registroVS("AV2", "con", "URL", utilDate, "CSS", "loading", true,true, 3, "logo");
	        	servicioVS.registroVS("AV3", "con", "URL", utilDate, "CSS", "loading", true,true, 3, "logo");
	       }
	    });
	}	

}
