package com.bo.formularios.abm;

import Reportes.ReporteUsuarios;

import com.bo.principal.PanelDinamico;
import com.ejt.vaadin.sizereporter.ComponentResizeEvent;
import com.ejt.vaadin.sizereporter.ComponentResizeListener;
import com.ejt.vaadin.sizereporter.SizeReporter;
import com.entities.mongo.Product;
import com.entities.sql.Usuario;
import com.services.UsuarioServiceLocal;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table.ColumnResizeEvent;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class FormReportes extends PanelDinamico{
	 /**
	 * 
	 */
	
	
	private static final long serialVersionUID1 = 1L;
	
	private Button reportar;
	private Window temaWindow;
	private Table table;
	private List<Usuario>  listaUsuarios;

private UsuarioServiceLocal servicio;
	
//	private void lookup() {
//			InitialContext context = null;
//			try {
//				context = new InitialContext();					
//				servicio = (UsuarioServiceLocal) context.lookup("java:app/SAPo-BO/UsuarioServiceBean");
//			} catch (NamingException e) {
//				throw new EJBException(
//						"It was not possible to get a reference to one of the required services",
//						e);
//			} finally {
//				try {
//					context.close();
//				} catch (NamingException e) {
//					
//				}
//			}
//	
//	}
//	
//	public FormReportes(){
//		lookup();
//		listaUsuarios = servicio.getUsuariosHabilitados();
//		reportar = new Button("reportar");
//		final Button print = new Button("Print This Page");
//		
//		
//		this.addComponent(reportar);
//		
//		reportar.addClickListener(new ClickListener() {
//	        private static final long serialVersionUID = 1L;
//	        @Override
//	        public void buttonClick(final ClickEvent event) {
//	        	
//	        	temaWindow = new Window("Estilo");
//	        	VerticalLayout windowCont = new VerticalLayout();
//	        	windowCont.setMargin(true);
//	        	temaWindow.setContent(windowCont);
//	            windowCont.addComponent(print);    
//	            windowCont.setWidth("100em");
//	            windowCont.setHeight("100em"); 
//	            temaWindow.center();
//	            UI.getCurrent().addWindow(temaWindow);
//	           
//	            
//	            table = new Table("Usuarios cargados");
//	    	    table.addContainerProperty("Nombre", String.class, null);
//	    	   
//	    	
//	    	   
//	    	        //para ordenar tabla por nombre
//	    		 table.addColumnResizeListener(new Table.ColumnResizeListener(){
//	    	    	  public void columnResize(ColumnResizeEvent event) {
//	    	    	        int width = event.getCurrentWidth();
//	    	    	        String column = (String) event.getPropertyId();
//	    	    	        table.setColumnFooter(column, String.valueOf(width) + "Nombre");
//	    	       	  }
//	    		 }); 
//	    		 table.setImmediate(true);
//	    		 table.setSelectable(true);
//	    		 table.select(1);
//	    		 table.setNullSelectionItemId(false);
//	    		 table.setPageLength(table.size());
//	    		 table.setWidth("80%");
//	    		 windowCont.setMargin(true);
//	    		 windowCont.addComponent(table);
//	    		 windowCont.setComponentAlignment(table, Alignment.MIDDLE_CENTER);
//	    		 
//	    		 table.addContainerProperty("Nombre", String.class, null);
//	    		 table.addContainerProperty("Tipo", String.class, null);
//	    		 table.addContainerProperty("Mail", String.class, null);
//	  	    		 
//	    		    for (Usuario u : listaUsuarios) {
//	    		    	Object newItemId = table.addItem();
//	    		    	Item row1 = table.getItem(newItemId);
//	    		    	row1.getItemProperty("Nombre").setValue(u.getName());
//	    		    	row1.getItemProperty("Tipo").setValue(u.getType());
//	    		    	row1.getItemProperty("Mail").setValue(u.getMail());
//	    		    	//row1.getItemProperty("Stock").setValue(listaP.getStock());   	
//	    		    }
//	    		    print.addClickListener(new ClickListener() {
//		                private static final long serialVersionUID = 1L;
//		                @Override
//		                public void buttonClick(final ClickEvent event) {
//		    		        
////		                	BrowserWindowOpener opener = new BrowserWindowOpener(ReporteUsuarios.class);
////		                	opener.setFeatures("height=200,width=400,resizable");
////		                	
////		                	Button print = new Button("Click to Print");
////		                	opener.extend(print);
//		    		    }
//		    		});	
//	       }
//	        
//			
//	    });
//	
//	}	
				public FormReportes(){
//					//BrowserWindowOpener opener = new BrowserWindowOpener(SapoBackofficeUI());
//	            	opener.setFeatures("height=200,width=400,resizable");
//	            	
//	            	Button print = new Button("Click to Print");
//	            	opener.extend(print);
//	            	this.addComponent(print);
				}
}