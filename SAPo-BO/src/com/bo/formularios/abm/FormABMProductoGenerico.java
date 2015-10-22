package com.bo.formularios.abm;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import utils.Encrypter;

import com.bo.principal.PanelDinamico;
import com.entities.mongo.GenericProduct;
import com.entities.sql.Usuario;
import com.services.GenericProductService;
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

public class FormABMProductoGenerico extends PanelDinamico{
		//private AbsoluteLayout panelDinamico = new AbsoluteLayout();
		
		private static final long serialVersionUID = 1L;
		private List<GenericProduct>  listaProductosGenericos;


		private VerticalLayout panelIzquierda, panelDerecha;
		private HorizontalLayout rootLayout;
		private Table table; 
		private Button modificar, alta, eliminar, recargar;
		private TextField barCode, name, description;
		private GenericProductService servicio;	
		
		private void lookup() {
				InitialContext context = null;
				try {
					context = new InitialContext();					
					servicio = (GenericProductService) context.lookup("java:app/SAPo-bll/GenericProductService");
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
		
		public FormABMProductoGenerico(){    
			//lookup();
			this.addStyleName("outlined");
	        this.setSizeFull();
	        listaProductosGenericos = servicio.getAllGenericProduct();
	        for(GenericProduct u : listaProductosGenericos){
	        	System.out.println("Producto " + u.getName());
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
						barCode.setValue(listaProductosGenericos.get((int) table.getValue()-1).getBarcode());
						name.setValue(listaProductosGenericos.get((int) table.getValue()-1).getName());
						description.setValue(listaProductosGenericos.get((int) table.getValue()-1).getDescription());
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
						GenericProduct gp = new GenericProduct();
						gp.setBarcode(barCode.getValue());
						gp.setName(name.getValue());
						gp.setDescription(description.getValue());
			        	servicio.createGenericProduct(gp);
			        	Notification notif = new Notification("Producto Genérico cargado exitosamente");
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
	            	GenericProduct gp = new GenericProduct();
					gp.setBarcode(barCode.getValue());
					gp.setName(name.getValue());
					gp.setDescription(description.getValue());
			        servicio.updateProduct(barCode.getValue(), gp);
			        Notification notif = new Notification("Modificado con exito");
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
	            	servicio.deleteProduct(barCode.getValue());
		        	Notification sample = new Notification("Producto eliminado con exito");
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
		    ArrayList<String> nombreEncuestador = new ArrayList<String>();
		
		    for (GenericProduct gp : listaProductosGenericos) {
		    	Object newItemId = table.addItem();
		    	Item row1 = table.getItem(newItemId);
		    	row1.getItemProperty("Nombre").setValue(gp.getName());
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
						name.setValue(listaProductosGenericos.get((int) table.getValue()-1).getName());
						barCode.setValue(listaProductosGenericos.get((int) table.getValue()-1).getBarcode());
						description.setValue(listaProductosGenericos.get((int) table.getValue()-1).getDescription());
						alta.setEnabled(false);
					}
				}
			});
		    
		}
		
		private void reiniciarCampos(){
			
			name.setValue("");
	     	barCode.setValue("");
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
		    
		    barCode = new  TextField("Codigo de Barras", "");
		    barCode.setWidth("70%");
		    panIzq.addComponent(barCode);
		    barCode.setRequired (true);
		    
		    description = new  TextField("Descripción", "");
		    description.setWidth("70%");
		    panIzq.addComponent(description);
		    description.setRequired (true);
		    
		    
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
		    recargar.addStyleName(ValoTheme.BUTTON_TINY);
		    recargar.setWidth("50%");
		    panIzq.addComponent(recargar);
		
		    panIzq.setComponentAlignment(name, Alignment.TOP_CENTER);
		    panIzq.setComponentAlignment(barCode, Alignment.BOTTOM_CENTER);
		    panIzq.setComponentAlignment(description, Alignment.TOP_CENTER);
		    
		    panIzq.setComponentAlignment(alta, Alignment.MIDDLE_CENTER);
		    panIzq.setComponentAlignment(modificar, Alignment.BOTTOM_CENTER);
		    panIzq.setComponentAlignment(eliminar, Alignment.MIDDLE_CENTER);
		    panIzq.setComponentAlignment(recargar, Alignment.BOTTOM_CENTER);
		
		    return panIzq;
		   
		}
}
