package com.bo.formularios.abm;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.bl.CategoryBL;


import com.bl.GenericCategoryBL;
import com.bo.principal.PanelDinamico;
import com.entities.mongo.Category;
import com.entities.mongo.GenericCategory;
import com.entities.sql.LimitCount;
import com.services.LimitCountServiceLocal;
import com.services.UsuarioServiceLocal;
import com.services.VirtualStorageServiceLocal;
import com.services.interfaces.ICategoryBL;
import com.services.interfaces.IGenericCategoryBL;
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
import com.vaadin.ui.Table.ColumnResizeEvent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class FormABMLimitCount extends PanelDinamico{

		private static final long serialVersionUID = 1L;
		private List<LimitCount>  listaLimtCounts;
		

		private VerticalLayout panelIzquierda, panelDerecha;
		private HorizontalLayout rootLayout;
		private Table table; 
		private Button modificar, alta, recargar;
		private TextField type, limit, avisarLimite;
		
		private LimitCountServiceLocal servicioLC;
		
		private void lookup() {
				InitialContext context = null;
				try {
					context = new InitialContext();					
					servicioLC = (LimitCountServiceLocal) context.lookup("java:app/SAPo-BO/LimitCountServiceBean");
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
		
		public FormABMLimitCount(){    
			lookup();
			this.addStyleName("outlined");
	        this.setSizeFull();
	        listaLimtCounts = servicioLC.listarLimitCount();
	                
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
						String cargar = String.valueOf(listaLimtCounts.get((int) table.getValue()-1).getLimit());
						limit.setValue(cargar);
						type.setValue(listaLimtCounts.get((int) table.getValue()-1).getType());
						cargar = String.valueOf(listaLimtCounts.get((int) table.getValue()-1).getAvisarFaltando());
						avisarLimite.setValue(cargar);
						alta.setEnabled(false);
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
						servicioLC.registroLimitCount(type.getValue(),  Integer.parseInt(limit.getValue()), Integer.parseInt(avisarLimite.getValue()));
			        	Notification notif = new Notification("Nuevo tipo de cuenta cargado exitosamente");
			        	notif.setDelayMsec(2000);
			        	notif.show(Page.getCurrent());
			        	reiniciarCampos();
			            actualizarTabla();
				}			   
	        });
	        
	        modificar.addClickListener(new ClickListener() {
	            private static final long serialVersionUID = 1L;
	            @Override
	            public void buttonClick(final ClickEvent event) {
	            	servicioLC.modificarLimitCount(type.getValue(),  Integer.parseInt(limit.getValue()), Integer.parseInt(avisarLimite.getValue()));
			        Notification notif = new Notification("Tipo de cuenta modificada con exito");
			        notif.setDelayMsec(2000);
			        notif.show(Page.getCurrent());   	
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
		    table = new Table("Tipos de Usuarios cargados");
		    table.addContainerProperty("Tipo", String.class, null);
		    table.addContainerProperty("Limite de AV", Integer.class, null);
		    table.addContainerProperty("Avisar cuando falten", Integer.class, null);
		   		
		    for (LimitCount gp : listaLimtCounts) {
		    	Object newItemId = table.addItem();
		    	Item row1 = table.getItem(newItemId);
		    	row1.getItemProperty("Tipo").setValue(gp.getType());
		    	row1.getItemProperty("Limite de AV").setValue(gp.getLimit());
		    	row1.getItemProperty("Avisar cuando falten").setValue(gp.getAvisarFaltando());
		    }
		     
		        //para ordenar tabla por nombre
			 table.addColumnResizeListener(new Table.ColumnResizeListener(){
		    	  public void columnResize(ColumnResizeEvent event) {
		    	        int width = event.getCurrentWidth();
		    	        String column = (String) event.getPropertyId();
		    	        table.setColumnFooter(column, String.valueOf(width) + "Tipo");
		    	        modificar.setEnabled(true);
		    	        alta.setEnabled(false);
		    	       
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
			listaLimtCounts = servicioLC.listarLimitCount();
			VerticalLayout nuevaTabla = new VerticalLayout();
		    nuevaTabla = generarPanelDerecha();
		    rootLayout.replaceComponent(panelDerecha, nuevaTabla);
		    panelDerecha = nuevaTabla;
		    
		    table.addValueChangeListener(new Property.ValueChangeListener() {
				@Override
				public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
					if(event.getProperty().getValue() != null){
						modificar.setEnabled(true);
						String cargar = String.valueOf(listaLimtCounts.get((int) table.getValue()-1).getLimit());
						limit.setValue(cargar);
						type.setValue(listaLimtCounts.get((int) table.getValue()-1).getType());
						cargar = String.valueOf(listaLimtCounts.get((int) table.getValue()-1).getAvisarFaltando());
						avisarLimite.setValue(cargar);
						alta.setEnabled(false);
					}
				}
			});
		    
		}
		
		private void reiniciarCampos(){
			
			limit.setValue("");
	     	type.setValue("");
	     	avisarLimite.setValue("");
	        modificar.setEnabled(false);
	 	    alta.setEnabled(true);	
		}


		private VerticalLayout generarPanelIzquierda(){
			
			VerticalLayout panIzq = new VerticalLayout();
		    panIzq.setSizeFull();
		    panIzq.setMargin(true);
		    MarginInfo mi = new MarginInfo(false, true, false, true);
		    panIzq.setMargin(mi);
		
			limit = new TextField("Límite de cuentas", "");
		    limit.setWidth("70%");       
		    panIzq.addComponent(limit);
		    limit.setRequired (true);
		    
		    type = new  TextField("Tipo de cuenta", "");
		    type.setWidth("70%");
		    panIzq.addComponent(type);
		    type.setRequired (true);
		    
		    avisarLimite = new  TextField("Avisar cuando falten X cuentas", "");
		    avisarLimite.setWidth("70%");
		    panIzq.addComponent(avisarLimite);
		    avisarLimite.setRequired (true);
		    
		    
		    alta = new Button("Nueva Tipo de Cuenta");
		    alta.addStyleName(ValoTheme.BUTTON_PRIMARY);
		    alta.setClickShortcut(KeyCode.ENTER);
		    alta.setWidth("70%");
		    panIzq.addComponent(alta);
		    
		    modificar = new Button("Modificar Tipo de Cuenta");
		    modificar.addStyleName(ValoTheme.BUTTON_PRIMARY);
		    modificar.setWidth("70%");
		    panIzq.addComponent(modificar);
		    modificar.setEnabled(false);
		    
		    recargar = new Button("Reiniciar Pantalla");
		    recargar.addStyleName(ValoTheme.BUTTON_PRIMARY);
		    recargar.setWidth("70%");
		    panIzq.addComponent(recargar);
		
		    panIzq.setComponentAlignment(limit, Alignment.BOTTOM_CENTER);
		    panIzq.setComponentAlignment(type, Alignment.BOTTOM_CENTER);
		    panIzq.setComponentAlignment(avisarLimite, Alignment.BOTTOM_CENTER);
		    
		    panIzq.setComponentAlignment(alta, Alignment.BOTTOM_CENTER);
		    panIzq.setComponentAlignment(modificar, Alignment.BOTTOM_CENTER);
		    panIzq.setComponentAlignment(recargar, Alignment.BOTTOM_CENTER);
		    return panIzq;
		}
		

}