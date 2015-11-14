package com.bo.principal;

import javax.inject.Inject;

import com.bo.formularios.abm.FormABMCategoria;
import com.bo.formularios.abm.FormABMHARD;
import com.bo.formularios.abm.FormABMLimitCount;
import com.bo.formularios.abm.FormABMProductoGenerico;
import com.bo.formularios.abm.FormABMUsuario;
import com.bo.formularios.abm.FormReportes;
import com.bo.formularios.abm.FormAdminAV;
import com.bo.formularios.abm.FormUsuarioAV;
import com.bo.formularios.abm.Login;
import com.bo.formularios.abm.prueba;
import com.entities.sql.Usuario;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Image;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;


@CDIUI("")
@SuppressWarnings("serial")
@Theme("sapo_bo")
public class SapoBackofficeUI extends UI {

	private BarraUsuario barraUsuario;
	private FormABMUsuario formABMUsuario;
	private FormABMProductoGenerico formABMProductoGenerico;
	private FormABMCategoria formABMCategoriaGenerico;
	private FormAdminAV formAdminAV;
	private FormABMHARD formABMHard;
	private FormReportes formReportes;
	private FormABMLimitCount formLimitCount;
	private FormUsuarioAV formUsuarioAV;
	
	@Inject 
	private DatosSesion datosSesion;	
	
	//private FormABMAutomatico formABMAutomatico;
	private VerticalLayout logo = new VerticalLayout();
	private VerticalLayout mainLayout = new VerticalLayout();
		

//	@WebServlet(value = "/*", asyncSupported = true)
//	    @VaadinServletConfiguration(productionMode = false, ui = SapoBackofficeUI.class)
//		public static class Servlet extends VaadinServlet {}
	//@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = SapoBackofficeUI.class)
	public static class Servlet extends VaadinServlet {	}
		
	//String tenant2=  request.getContextPath();
	@Override
	protected void init(VaadinRequest request) {
		//String tenant= VaadinService.getCurrentRequest().getPathInfo().substring(1);
		getPage().setTitle("SAPo" );
		Responsive.makeResponsive(this);
		
        cargarContenido();
        setSizeFull();
        
	}

	
public void login(Usuario usuario){
		this.datosSesion.setUsuario(usuario);
		this.cargarContenido();
}	

public void logout(){
	this.datosSesion.setUsuario(null);
	this.close();
	Page.getCurrent().reload();
}
   
public DatosSesion getDatosSesion() {
	return datosSesion;
}

public void cargarContenido(){
	//System.out.println("el nombre loguedo es: " + datosSesion.getUsuario().getName());
	if (datosSesion.getUsuario() == null){
	     Login login = new Login();
	     setContent(login);
	   }

	else{
       mainLayout.setMargin(false);
       setContent(mainLayout);
       barraUsuario = new BarraUsuario(datosSesion.getUsuario().getName());
       mainLayout.addComponent(barraUsuario);

    	  TabSheet tabSheet = new TabSheet();
          tabSheet.setHeight(100.0f, Unit.PERCENTAGE);
          tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
          tabSheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
          tabSheet.setSizeFull();
          
          PanelDinamico layout1 = new PanelDinamico();
          formABMUsuario = new FormABMUsuario();
          layout1.addComponent(formABMUsuario);
          tabSheet.addTab(layout1, "Gestion Usuario"); 
          
          PanelDinamico layout2 = new PanelDinamico();
          formABMProductoGenerico = new FormABMProductoGenerico();
          layout2.addComponent(formABMProductoGenerico);
          tabSheet.addTab(layout2, "Gestion Producto Generico"); 
          
          PanelDinamico layout3 = new PanelDinamico();
          formABMCategoriaGenerico = new FormABMCategoria();
          layout3.addComponent(formABMCategoriaGenerico);
          tabSheet.addTab(layout3, "Gestion Categoria Generico"); 
          
          PanelDinamico layout4 = new PanelDinamico();
          formAdminAV = new FormAdminAV();
          layout4.addComponent(formAdminAV);
          tabSheet.addTab(layout4, "Gestion Almacen Virtual");
          
          PanelDinamico layout5 = new PanelDinamico();
          formABMHard = new FormABMHARD();
          layout5.addComponent(formABMHard);              
          tabSheet.addTab(layout5, "Cargas Automáticas");
          
          PanelDinamico layout6 = new PanelDinamico();
          formReportes = new FormReportes();
          layout6.addComponent(formReportes);              
          tabSheet.addTab(layout6, "Reportes");
          
          PanelDinamico layout7 = new PanelDinamico();
          formLimitCount = new FormABMLimitCount();
          layout7.addComponent(formLimitCount);              
          tabSheet.addTab(layout7, "Gestión Tipos de Cuentas");
          
          PanelDinamico layout8 = new PanelDinamico();
          formUsuarioAV = new FormUsuarioAV();
          layout8.addComponent(formUsuarioAV);              
          tabSheet.addTab(layout8, "Gestión límites de Cuentas");
          
          mainLayout.addComponent(tabSheet);
          mainLayout.setExpandRatio(tabSheet, 70);
          mainLayout.addComponent(new PiePagina());
          mainLayout.setSizeFull();
	      }
	}
}


	