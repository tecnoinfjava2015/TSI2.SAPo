package com.bo.formularios.abm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.bl.ProductBL;
import com.bo.principal.PanelDinamico;
import com.entities.mongo.Alert;
import com.entities.mongo.Category;
import com.entities.mongo.Product;
import com.entities.mongo.Spec;
import com.entities.sql.Unit;
import com.entities.sql.Usuario;
import com.entities.sql.VirtualStorage;
import com.services.UsuarioServiceLocal;
import com.services.VirtualStorageServiceLocal;
import com.services.interfaces.IProductBL;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Table.ColumnResizeEvent;

public class FormAdminAV extends PanelDinamico{
	/**
	 * 
	 */
	private IProductBL servicioProducto = new ProductBL();
	
	private static final long serialVersionUID = 1L;
	private VerticalLayout panelArriba, panelAbajo;
	private HorizontalLayout rootLayout;
	private List<VirtualStorage>  listaVirtualStorage;
	private List<Product>  listaProductos;
	private Table tableVirtualStorage, tableProductos;
	private ComboBox comboAVs;
	private VirtualStorageServiceLocal servicioVS;
	
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
		listaVirtualStorage = servicioVS.getVS();
		panelArriba = new VerticalLayout();
        panelAbajo = new VerticalLayout();
        rootLayout = new HorizontalLayout(panelArriba,panelAbajo);
        rootLayout.setSizeFull();
        panelArriba = generarPanelArriba();
        panelAbajo = generarPanelAbajo();
        rootLayout.addComponent(panelArriba);
        rootLayout.addComponent(panelAbajo);
        rootLayout.setExpandRatio(panelArriba, 1);
        rootLayout.setExpandRatio(panelAbajo, 1);
        this.addComponent(rootLayout);
        tableVirtualStorage.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
				if(event.getProperty().getValue() != null){
					//listaProductos = listaProductos((int)tableVirtualStorage.getValue()-1));	
					listaProductos = servicioProducto.getAllProducts(1);
				}
			}
		});
	}

	private VerticalLayout generarPanelAbajo() {
		//listaProductos = listaProductos((int)tableVirtualStorage.getValue()-1));	
		listaProductos = servicioProducto.getAllProducts(1);
		VerticalLayout panDown = new VerticalLayout();
		tableProductos = new Table("Productos del Almacenes");
		tableProductos.addContainerProperty("Nombre", String.class, null);
		tableProductos.addContainerProperty("Código de Barras", String.class, null);
		tableProductos.addContainerProperty("Descripción", String.class, null);
		//tableProductos.addContainerProperty("Stock",  String.class, null);
	    ArrayList<String> nombreEncuestador = new ArrayList<String>();
	
	    for (Product listaP : listaProductos) {
	    	Object newItemId = tableProductos.addItem();
	    	Item row1 = tableProductos.getItem(newItemId);
	    	row1.getItemProperty("Nombre").setValue(listaP.getName());
	    	row1.getItemProperty("Código de Barras").setValue(listaP.getBarCode());
	    	row1.getItemProperty("Descripción").setValue(listaP.getDescription());
	    	//row1.getItemProperty("Stock").setValue(listaP.getStock());   	
	    }
	     
	     
	    tableProductos.setImmediate(true);
	    tableProductos.setSelectable(true);
	    tableProductos.select(1);
	    tableProductos.setNullSelectionItemId(false);
	    tableProductos.setPageLength(tableProductos.size());
	    tableProductos.setWidth("80%");
	    panDown.setMargin(true);
	    panDown.addComponent(tableProductos);
	    panDown.setComponentAlignment(tableProductos, Alignment.MIDDLE_CENTER);
	     
	    return panDown;
	}
	

	private VerticalLayout generarPanelArriba() {
		VerticalLayout panArriba = new VerticalLayout();
		tableVirtualStorage = new Table("Almacenes Virtuales");
		tableVirtualStorage.addContainerProperty("Nombre", String.class, null);
		tableVirtualStorage.addContainerProperty("Fecha Creación", Date.class, null);
		tableVirtualStorage.addContainerProperty("Creador", String.class, null);
		tableVirtualStorage.addContainerProperty("URL", String.class, null);
	    ArrayList<String> nombreEncuestador = new ArrayList<String>();
	
	    for (VirtualStorage vs : listaVirtualStorage) {
	    	Object newItemId = tableVirtualStorage.addItem();
	    	Item row1 = tableVirtualStorage.getItem(newItemId);
	    	row1.getItemProperty("Nombre").setValue(vs.getName());
	    	row1.getItemProperty("Fecha Creación").setValue(vs.getCreatedDate());
	    	row1.getItemProperty("Creador").setValue(vs.getOwner().getName());
	    	row1.getItemProperty("URL").setValue(vs.getUrl());   	
	    }
	     
	     
	    tableVirtualStorage.setImmediate(true);
	    tableVirtualStorage.setSelectable(true);
	    tableVirtualStorage.select(1);
	    tableVirtualStorage.setNullSelectionItemId(false);
	    tableVirtualStorage.setPageLength(tableVirtualStorage.size());
	    tableVirtualStorage.setWidth("80%");
	    panArriba.setMargin(true);
	    panArriba.addComponent(tableVirtualStorage);
	    panArriba.setComponentAlignment(tableVirtualStorage, Alignment.MIDDLE_CENTER);
	     
	    return panArriba;
	}	
	
}
