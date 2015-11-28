package com.bo.formularios.abm;

import java.util.Date;
import java.util.List;

import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.bl.ProductBL;
import com.bo.principal.PanelDinamico;
import com.entities.mongo.Product;
import com.entities.sql.ProductMovement;
import com.entities.sql.Usuario;
import com.entities.sql.VirtualStorage;
import com.services.LimitCountServiceLocal;
import com.services.ProductMovementServiceLocal;
import com.services.UsuarioServiceLocal;
import com.services.VirtualStorageServiceLocal;
import com.services.interfaces.IProductBL;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

public class FormABMHARD extends PanelDinamico{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Button usuarios, virtualStorage, genericProduct, limteCuenta, moviemientoProuctos;
	
	private UsuarioServiceLocal servicioUsuario;
	private VirtualStorageServiceLocal servicioVS;
	private LimitCountServiceLocal servicioLC;
	private ProductMovementServiceLocal servicioPM;
	private IProductBL servicioProducto = new ProductBL();
	
	private void lookup() {
			InitialContext context = null;
			try {
				context = new InitialContext();					
				servicioUsuario = (UsuarioServiceLocal) context.lookup("java:app/SAPo-BO/UsuarioServiceBean");
				servicioVS = (VirtualStorageServiceLocal) context.lookup("java:app/SAPo-BO/VirtualStorageServiceBean");
				servicioLC = (LimitCountServiceLocal) context.lookup("java:app/SAPo-BO/LimitCountServiceBean");
				servicioPM = (ProductMovementServiceLocal) context.lookup("java:app/SAPo-BO/ProductMovementServiceBean");
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
		VerticalLayout panIzq = new VerticalLayout();
	    panIzq.setSizeFull();
		this.addComponent(panIzq);
		usuarios = new Button("Cargar Usuarios");
		usuarios.addStyleName(ValoTheme.BUTTON_PRIMARY);
		usuarios.setClickShortcut(KeyCode.ENTER);
		usuarios.setWidth("70%");
		panIzq.addComponent(usuarios);
		
		limteCuenta = new Button("Cargar tipos de cuentas y sus límites");
		limteCuenta.addStyleName(ValoTheme.BUTTON_PRIMARY);
		limteCuenta.setClickShortcut(KeyCode.ENTER);
		limteCuenta.setWidth("70%");
		panIzq.addComponent(limteCuenta);
	    
	    virtualStorage = new Button("Cargar Virtual Storage");
	    virtualStorage.addStyleName(ValoTheme.BUTTON_PRIMARY);
	    virtualStorage.setWidth("70%");
	    panIzq.addComponent(virtualStorage);
	    
	    moviemientoProuctos = new Button("Cargar Movimiento de productos");
	    moviemientoProuctos.addStyleName(ValoTheme.BUTTON_PRIMARY);
	    moviemientoProuctos.setWidth("70%");
	    panIzq.addComponent(moviemientoProuctos);
	    	
	    
	    panIzq.setComponentAlignment(usuarios, Alignment.MIDDLE_CENTER);
	    panIzq.setComponentAlignment(virtualStorage, Alignment.MIDDLE_CENTER);
	    panIzq.setComponentAlignment(limteCuenta, Alignment.MIDDLE_CENTER);
	    panIzq.setComponentAlignment(moviemientoProuctos, Alignment.MIDDLE_CENTER);
	    
	    usuarios.addClickListener(new ClickListener() {
	        private static final long serialVersionUID = 1L;
	        @Override
	        public void buttonClick(final ClickEvent event) {
	        	servicioUsuario.registroUsuario("Andrés", "Administrador", "gonsc7@gmail.com", "Andrés", "1a1dc91c907325c69271ddf0c944bc72", true, true);
	        	servicioUsuario.registroUsuario("Lucía", "Free", "gonsc7@gmail.com", "Lucía", "1a1dc91c907325c69271ddf0c944bc72", true, true);
	        	servicioUsuario.registroUsuario("Juan Pablo", "Premium", "gonsc7@gmail.com", "Juan Pablo", "1a1dc91c907325c69271ddf0c944bc72", true, true);
	        	servicioUsuario.registroUsuario("Fabricio", "Freemium", "gonsc7@gmail.com", "Fabricio", "1a1dc91c907325c69271ddf0c944bc72", true, true);
	        	servicioUsuario.registroUsuario("Gonzalo", "Premium", "gonsc7@gmail.com", "Gonzalo", "1a1dc91c907325c69271ddf0c944bc72", true, true);
	        	
	        	Notification notif = new Notification("Usuarios Creados");
		        notif.setDelayMsec(2000);
		        notif.show(Page.getCurrent());
	       }
	    });
	    
	    virtualStorage.addClickListener(new ClickListener() {
	        private static final long serialVersionUID = 1L;
	        @Override
	        public void buttonClick(final ClickEvent event) {
	        	Date utilDate = new Date(); 
	        	List<Usuario> us = servicioUsuario.getUsuarios();
	        	System.out.println("usuraio 1" + us.get(0).getName()+ " usuraio 2" + us.get(1).getName() +" usuraio 3" + us.get(2).getName());
	        	servicioVS.registroVS("Heladera", "con", "URL", utilDate, "CSS", "loading", true,true, us.get(0).getId(), "logo");
	        	servicioVS.registroVS("Ropero", "con", "URL", utilDate, "CSS", "loading", true,true,  us.get(0).getId(), "logo");
	        	servicioVS.registroVS("Lista de Compras", "con", "URL", utilDate, "CSS", "loading", true,true,  us.get(1).getId(), "logo");
	        	servicioVS.registroVS("Super Cacho", "con", "URL", utilDate, "CSS", "loading", true,true,  us.get(1).getId(), "logo");
	        	
	        	Notification notif = new Notification("Almacenes Virtuales Creados");
		        notif.setDelayMsec(2000);
		        notif.show(Page.getCurrent());
	       }
	    });
	    
	    limteCuenta.addClickListener(new ClickListener() {
	        private static final long serialVersionUID = 1L;
	        @Override
	        public void buttonClick(final ClickEvent event) { 
	        	servicioLC.registroLimitCount("Administrador", 1000, 10);
	        	servicioLC.registroLimitCount("Premium", 100, 5);
	        	servicioLC.registroLimitCount("Freemium", 5, 2);
	        	servicioLC.registroLimitCount("Free", 1, 0);
	        	
	        	Notification notif = new Notification("Tipos de cuentas Creados");
		        notif.setDelayMsec(2000);
		        notif.show(Page.getCurrent());
	       }
	    });
	    
	    moviemientoProuctos.addClickListener(new ClickListener() {
	        private static final long serialVersionUID = 1L;
	        @Override
	        public void buttonClick(final ClickEvent event) { 
	        	ProductMovement pma = new ProductMovement();
	        	VirtualStorage vs = servicioVS.getVS().get(0);
	        	Usuario us = servicioUsuario.getUsuarios().get(0);
	        	Product pr = servicioProducto.getAllProductsPaginated(vs.getId(), 0, 1).get(0);
	        	Product pr2 = servicioProducto.getAllProductsPaginated(vs.getId(), 0, 2).get(1);
	        	Product pr3 = servicioProducto.getAllProductsPaginated(vs.getId(), 0, 2).get(2);
	        	Date date = new Date();
	        	servicioPM.createMovement(vs.getId(), us.getId(), pr.getBarCode(), date, 0, vs.getId(), vs.getId(), 10, 10, false);
	        	servicioPM.createMovement(vs.getId(), us.getId(), pr2.getBarCode(), date, 2, vs.getId(), vs.getId(), 10, 10, false);
	        	servicioPM.createMovement(vs.getId(), us.getId(), pr3.getBarCode(), date, -1, vs.getId(), vs.getId(), 10, 10, false);
	        	servicioPM.createMovement(vs.getId(), us.getId(), pr.getBarCode(), date, 5, vs.getId(), vs.getId(), 10, 10, false);
	        	
	        	Notification notif = new Notification("Movimientos Creados");
		        notif.setDelayMsec(2000);
		        notif.show(Page.getCurrent());
	       }
	    });
	}	

}
