package com.bo.formularios.abm;

import java.util.ArrayList;
import java.util.List;

import com.bl.CategoryBL;


import com.bl.GenericCategoryBL;
import com.bo.principal.PanelDinamico;
import com.entities.mongo.Category;
import com.entities.mongo.GenericCategory;
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

public class FormABMCategoria  extends PanelDinamico{

		private static final long serialVersionUID = 1L;
		private List<GenericCategory>  listaCategorias;


		private VerticalLayout panelIzquierda, panelDerecha;
		private HorizontalLayout rootLayout;
		private Table table; 
		private Button modificar, alta, eliminar, recargar;
		private TextField icon, name, description;
		private IGenericCategoryBL servicioCategoria =   new GenericCategoryBL();
		
		public FormABMCategoria(){    
			//lookup();
			this.addStyleName("outlined");
	        this.setSizeFull();
	        listaCategorias = servicioCategoria.getAllGenericCategory();
	        System.out.println("lista de Categorias " + listaCategorias);    
	        
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
						name.setValue(listaCategorias.get((int) table.getValue()-1).getName());
						icon.setValue(listaCategorias.get((int) table.getValue()-1).getIcon());
						description.setValue(listaCategorias.get((int) table.getValue()-1).getDescription());
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
						GenericCategory cat = new GenericCategory();
						cat.setIcon(icon.getValue());
						cat.setName(name.getValue());
						cat.setDescription(description.getValue());
						servicioCategoria.createGenericCategory(cat);
			        	Notification notif = new Notification("Categoria cargada exitosamente");
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
	            	GenericCategory cat = new GenericCategory();
					cat.setIcon(icon.getValue());
					cat.setName(name.getValue());
					cat.setDescription(description.getValue());
					servicioCategoria.updateGenericCategory(cat);
			        Notification notif = new Notification("Categoria modificada con exito");
			        notif.setDelayMsec(2000);
			        notif.show(Page.getCurrent());   	
			    	actualizarTabla();
			    	reiniciarCampos();
	            }	
	        });
	        
	        eliminar.addClickListener(new ClickListener() {
	            private static final long serialVersionUID = 1L;
	            @Override
	            public void buttonClick(final ClickEvent event) {
	            	servicioCategoria.deleteGenericCategory(name.getValue());
		        	Notification sample = new Notification("Categori eliminada con exito");
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
		    table = new Table("Categorias Genericas cargados");
		    table.addContainerProperty("Nombre", String.class, null);
		    table.addContainerProperty("Icon", String.class, null);
		    table.addContainerProperty("Descripción", String.class, null);
		   		
		    for (GenericCategory gp : listaCategorias) {
		    	Object newItemId = table.addItem();
		    	Item row1 = table.getItem(newItemId);
		    	row1.getItemProperty("Nombre").setValue(gp.getName());
		    	row1.getItemProperty("Icon").setValue(gp.getIcon());
		    	row1.getItemProperty("Descripción").setValue(gp.getDescription());
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
			listaCategorias = servicioCategoria.getAllGenericCategory();
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
						name.setValue(listaCategorias.get((int) table.getValue()-1).getName());
						icon.setValue(listaCategorias.get((int) table.getValue()-1).getIcon());
						description.setValue(listaCategorias.get((int) table.getValue()-1).getDescription());
						alta.setEnabled(false);
					}
				}
			});
		    
		}
		
		private void reiniciarCampos(){
			
			name.setValue("");
	     	icon.setValue("");
	     	description.setValue("");
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
		
			name = new TextField("Nombre", "");
		    name.setWidth("70%");       
		    panIzq.addComponent(name);
		    name.setRequired (true);
		    
		    icon = new  TextField("Icono", "");
		    icon.setWidth("70%");
		    panIzq.addComponent(icon);
		    icon.setRequired (true);
		    
		    description = new  TextField("Descripción", "");
		    description.setWidth("70%");
		    panIzq.addComponent(description);
		    description.setRequired (true);
		    
		    
		    alta = new Button("Nueva Categoria Generico");
		    alta.addStyleName(ValoTheme.BUTTON_PRIMARY);
		    alta.setClickShortcut(KeyCode.ENTER);
		    alta.setWidth("70%");
		    panIzq.addComponent(alta);
		    
		    modificar = new Button("Modificar Datos Categoria");
		    modificar.addStyleName(ValoTheme.BUTTON_PRIMARY);
		    modificar.setWidth("70%");
		    panIzq.addComponent(modificar);
		    modificar.setEnabled(false);
		    
		    eliminar = new Button("Eliminar Cateogoria");
		    eliminar.addStyleName(ValoTheme.BUTTON_PRIMARY);
		    eliminar.setWidth("70%");
		    panIzq.addComponent(eliminar);
		    eliminar.setEnabled(false);
		    
		    recargar = new Button("Reiniciar Pantalla");
		    recargar.addStyleName(ValoTheme.BUTTON_TINY);
		    recargar.setWidth("50%");
		    panIzq.addComponent(recargar);
		
		    panIzq.setComponentAlignment(name, Alignment.TOP_CENTER);
		    panIzq.setComponentAlignment(icon, Alignment.BOTTOM_CENTER);
		    panIzq.setComponentAlignment(description, Alignment.TOP_CENTER);
		    
		    panIzq.setComponentAlignment(alta, Alignment.MIDDLE_CENTER);
		    panIzq.setComponentAlignment(modificar, Alignment.BOTTOM_CENTER);
		    panIzq.setComponentAlignment(eliminar, Alignment.MIDDLE_CENTER);
		    panIzq.setComponentAlignment(recargar, Alignment.BOTTOM_CENTER);
		    return panIzq;
		}
		

}