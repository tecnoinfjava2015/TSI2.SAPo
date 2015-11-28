package com.bo.formularios.abm;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.bo.principal.PanelDinamico;
import com.entities.sql.Usuario;
import com.services.UsuarioServiceLocal;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table.ColumnResizeEvent;
import com.vaadin.ui.themes.ValoTheme;

public class FormCalculoGanancias extends PanelDinamico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Usuario>  listaUsuarios;
	private List<Usuario>  listaUsuariosPremium;
	private VerticalLayout panelIzquierda, panelDerecha;
	private HorizontalLayout rootLayout;
	private Table table; 
	private Button evaluar;
	private ComboBox costo, tiempo;
	private UsuarioServiceLocal servicio;
	private int costoInt = 0, tiempoInt = 0;
	
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
	
	public FormCalculoGanancias(){    
		lookup();
		this.addStyleName("outlined");
        this.setSizeFull();
        listaUsuariosPremium = new ArrayList<Usuario>();
        listaUsuarios = servicio.getUsuarios();
        if(listaUsuarios != null && listaUsuarios.size() > 0 ){
        	for (Usuario u : listaUsuarios){
            	if( u.getType().equals("Premium")){
            		listaUsuariosPremium.add(u);
            		System.out.println("entro");
            	}
            }
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
					costo.setValue(listaUsuarios.get((int) table.getValue()-1).getNick());
					evaluar.setEnabled(true);
					costo.setReadOnly(true);
				}
			}
		});
    	
        evaluar.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void buttonClick(final ClickEvent event) {
            	Evaluar();
            }
			private void Evaluar() {	
				double ganancia;
				if(costo.getValue().equals("50"))costoInt = 50;
				else if(costo.getValue().equals("100"))costoInt = 100;
				else if(costo.getValue().equals("150"))costoInt = 150;
				else if(costo.getValue().equals("200"))costoInt = 200;
				else if(costo.getValue().equals("250"))costoInt = 250;
				else if(costo.getValue().equals("500"))costoInt = 500;
				
				if(tiempo.getValue().equals("1"))tiempoInt = 1;
				else if(tiempo.getValue().equals("6"))tiempoInt = 6;
				else if(tiempo.getValue().equals("12"))tiempoInt = 12;
				else if(tiempo.getValue().equals("18"))tiempoInt = 18;
				else if(tiempo.getValue().equals("24"))tiempoInt = 24;
				else if(tiempo.getValue().equals("36"))tiempoInt = 36;

				ganancia = listaUsuariosPremium.size() * costoInt *  tiempoInt;
				
		        Notification notif = new Notification("Ganancia Calculada: " + ganancia);
		        notif.setDelayMsec(2000);
		        notif.show(Page.getCurrent());
		        actualizarTabla();
			}			   
        });

	}
	
	public VerticalLayout generarPanelDerecha() {
		VerticalLayout panDer = new VerticalLayout();
	    table = new Table("Usuarios Premium");
	    table.addContainerProperty("Nombre", String.class, null);
	    System.out.println("largo de la lista " + listaUsuariosPremium.size());
	    if(listaUsuariosPremium != null && listaUsuariosPremium.size() > 0 ){
		    for (Usuario usuario : listaUsuariosPremium) {
		    	Object newItemId = table.addItem();
		    	Item row1 = table.getItem(newItemId);
		    	row1.getItemProperty("Nombre").setValue(usuario.getNick());
		    }
	    }
		     
	        //para ordenar tabla por nombre
		 table.addColumnResizeListener(new Table.ColumnResizeListener(){
	    	  public void columnResize(ColumnResizeEvent event) {
	    	        int width = event.getCurrentWidth();
	    	        String column = (String) event.getPropertyId();
	    	        table.setColumnFooter(column, String.valueOf(width) + "Nombre");
	    	        evaluar.setEnabled(true);   
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
					costo.setValue(listaUsuarios.get((int) table.getValue()-1).getNick());
					evaluar.setEnabled(true);
					costo.setReadOnly(true);
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
	
	    costo = new ComboBox("Precio Usuario Premium");
	    costo.addItem("50");
	    costo.addItem("100");         	
	    costo.addItem("150");
	    costo.addItem("200");
	    costo.addItem("250");
	    costo.addItem("500");
	    costo.setWidth("70%");       
	    panIzq.addComponent(costo);
	    costo.setRequired (true);
	    
	    tiempo = new ComboBox("Meses Para Evaluar Ganancia");
	    tiempo.addItem("1");
	    tiempo.addItem("6");         	
	    tiempo.addItem("12");
	    tiempo.addItem("18");
	    tiempo.addItem("24");
	    tiempo.addItem("36");
	    tiempo.setWidth("70%");       
	    panIzq.addComponent(tiempo);
	    tiempo.setRequired (true);
	    
	    evaluar = new Button("Evaluar ganancias");
	    evaluar.addStyleName(ValoTheme.BUTTON_PRIMARY);
	    evaluar.setClickShortcut(KeyCode.ENTER);
	    evaluar.setWidth("70%");
	    evaluar.setEnabled(true);
	    panIzq.addComponent(evaluar);
	
	    panIzq.setComponentAlignment(tiempo, Alignment.MIDDLE_CENTER);
	    panIzq.setComponentAlignment(costo, Alignment.MIDDLE_CENTER);
	    panIzq.setComponentAlignment(evaluar, Alignment.MIDDLE_CENTER);
	   	
	    return panIzq;
	   
	}

}

