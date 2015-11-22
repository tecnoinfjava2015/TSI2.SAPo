package com.bo.formularios.abm;

import java.util.List;

import javax.ejb.EJBException;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.bo.principal.PanelDinamico;
import com.entities.sql.LimitCount;
import com.entities.sql.Usuario;
import com.entities.sql.VirtualStorage;
import com.services.Correo;
import com.services.LimitCountServiceLocal;
import com.services.UsuarioServiceLocal;
import com.services.VirtualStorageServiceLocal;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Table.ColumnResizeEvent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class FormUsuarioAV extends PanelDinamico{
	
		private static final long serialVersionUID = 1L;
		private List<LimitCount>  listaLimtCounts;
		private List<Usuario>  listaUsuarios;
		private List<VirtualStorage>  listaVirtualStorage;
		private VerticalLayout panelIzquierda, panelDerecha;
		private HorizontalLayout rootLayout;
		private Table table; 
		private Button enviarNotificacion;
		private Usuario usuarioSeleccionado;
		private Window temaWindow;	
		private LimitCountServiceLocal servicioLC;
		private UsuarioServiceLocal servicioU;
		private VirtualStorageServiceLocal servicioVS;
		private String mailDestino, mensaje;
		private TextField texMailDestino, texMensaje;
		private Button btnEnviarMensaje;
		
		private void lookup() {
				InitialContext context = null;
				try {
					context = new InitialContext();					
					servicioLC = (LimitCountServiceLocal) context.lookup("java:app/SAPo-BO/LimitCountServiceBean");
					servicioU = (UsuarioServiceLocal) context.lookup("java:app/SAPo-BO/UsuarioServiceBean");
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
		
		public FormUsuarioAV(){    
			lookup();
			this.addStyleName("outlined");
	        this.setSizeFull();
	        listaLimtCounts = servicioLC.listarLimitCount();
	        listaUsuarios = servicioU.getUsuarios();
	        listaVirtualStorage = servicioVS.getVS();
	        
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
	        
	        //avisar usuarios
	        
	        for (Usuario lu : listaUsuarios) {
	        	
		    	if(servicioLC.limitePorTipo(lu.getType()) <= servicioVS.AVPorUsuario(lu.getNick()) + servicioLC.AvisarPorTipo(lu.getType())) {
		    		enviarCorreo(lu.getMail(),"Aviso automático, usted está llegano al límite de AV que puede generar"); 
		    	}
		    	
		    }
	        
	        
	    	table.addValueChangeListener(new Property.ValueChangeListener() {
				@Override
				public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
					if(event.getProperty().getValue() != null){
						enviarNotificacion.setEnabled(true);
					}
				}
			});
	    	
	        enviarNotificacion.addClickListener(new ClickListener() {
	        	
	            private static final long serialVersionUID = 1L;
	            @Override
	            public void buttonClick(final ClickEvent event){
	            	usuarioSeleccionado = (listaUsuarios.get((int) table.getValue()-1));
	            	temaWindow = new Window("Estilo");
	            	VerticalLayout windowCont = new VerticalLayout();
	            	windowCont.setMargin(true);
	            	temaWindow.setContent(windowCont);
	   
	            	texMailDestino = new TextField("Destino", usuarioSeleccionado.getMail());
	            	texMailDestino.setWidth("70%");       
	            	windowCont.addComponent(texMailDestino);
	        	    texMailDestino.setRequired (true);
	        	    
	        	    if(usuarioSeleccionado.getType().equals("Free")){
	        	    	texMensaje = new TextField("Mensaje", "Estimado usuario " + usuarioSeleccionado.getNick() 
	        	    											+ " usted posee " 
	        	    											+ servicioVS.AVPorUsuario(usuarioSeleccionado.getNick()) + 
	        	    											" almacenes virtuales creados. Su límite de almacenes es " +
	        	    											servicioLC.limitePorTipo("Free") + 
	        	    											" ,le recomendamos pasarse a usaurio Premium, y usted podrá tener " + 
	        	    											servicioLC.limitePorTipo("Premium"));
		        	    texMensaje.setWidth("70%");
	        	    }
	        	    else{
	        	    	texMensaje = new TextField("Mensaje", "Estimado usuario " + usuarioSeleccionado.getNick() 
								+ " usted posee " 
								+ servicioVS.AVPorUsuario(usuarioSeleccionado.getNick()) + 
								" almacenes virtuales creados. Su límite de almacenes es " +
								servicioLC.limitePorTipo(usuarioSeleccionado.getType()));
		        	    texMensaje.setWidth("70%");  
	        	    }     
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
	    }
		
		public VerticalLayout generarPanelDerecha() {
			VerticalLayout panDer = new VerticalLayout();
		    
		     panDer.setMargin(true);
		     
		     
		     return panDer;
		}	

		public void actualizarTabla(){
			listaLimtCounts = servicioLC.listarLimitCount();
			VerticalLayout nuevaTabla = new VerticalLayout();
		    nuevaTabla = generarPanelDerecha();
		    rootLayout.replaceComponent(panelDerecha, nuevaTabla);
		    panelDerecha = nuevaTabla;
		    
		    table.addValueChangeListener(new Property.ValueChangeListener() {
				@Override
				public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
					if(event.getProperty().getValue() != null){
						enviarNotificacion.setEnabled(true);
						usuarioSeleccionado = listaUsuarios.get((int) table.getValue()-1);
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
			
			
			table = new Table("Estado de Cuentas de Usuarios");
		    table.addContainerProperty("Usuario", String.class, null);
		    table.addContainerProperty("Tipo", String.class, null);
		    table.addContainerProperty("Límite", Integer.class, null);
		    table.addContainerProperty("# de Almacenes Virtuales", Integer.class, null);
		   		
		    for (Usuario lu : listaUsuarios) {
		    	Object newItemId = table.addItem();
		    	Item row1 = table.getItem(newItemId);
		    	row1.getItemProperty("Usuario").setValue(lu.getNick());
		    	row1.getItemProperty("Tipo").setValue(lu.getType());
		    	System.out.println("servicio Limit UI limite: " + servicioLC.limitePorTipo(lu.getType()));
		    	row1.getItemProperty("Límite").setValue(servicioLC.limitePorTipo(lu.getType()));
		    	row1.getItemProperty("# de Almacenes Virtuales").setValue(servicioVS.AVPorUsuario(lu.getNick()));
		    }
		     
		        //para ordenar tabla por nombre
			 table.addColumnResizeListener(new Table.ColumnResizeListener(){
		    	  public void columnResize(ColumnResizeEvent event) {
		    	        int width = event.getCurrentWidth();
		    	        String column = (String) event.getPropertyId();
		    	        table.setColumnFooter(column, String.valueOf(width) + "Usuario");
		    	        enviarNotificacion.setEnabled(true);
		       	  }
			 }); 
			 table.setImmediate(true);
			 table.setSelectable(true);
			 table.select(1);
			 table.setNullSelectionItemId(false);
			 table.setPageLength(table.size());
			 table.setWidth("80%");
			 table.setHeight("18em");
			 panIzq.setMargin(true);
			 panIzq.addComponent(table);
			 panIzq.setComponentAlignment(table, Alignment.MIDDLE_CENTER);
			
			enviarNotificacion = new Button("Enviar Notificación");
			enviarNotificacion.addStyleName(ValoTheme.BUTTON_PRIMARY);
			enviarNotificacion.setClickShortcut(KeyCode.ENTER);
			enviarNotificacion.setWidth("70%");
			enviarNotificacion.setEnabled(false);
		    panIzq.addComponent(enviarNotificacion);
		
		    panIzq.setComponentAlignment(enviarNotificacion, Alignment.BOTTOM_CENTER);
		    return panIzq;
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
