package com.bo.principal;

import javax.servlet.annotation.WebServlet;

import com.bo.formularios.abm.FormABMUsuario;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
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
//import com.vaadin.cdi.CDIUI;


//@CDIUI("")
@SuppressWarnings("serial")
@Theme("sapo_bo")
public class SapoBackofficeUI extends UI {

	private BarraUsuario barraUsuario;
	private FormABMUsuario formABMUsuario;
	
	//private FormABMAutomatico formABMAutomatico;
	private VerticalLayout logo = new VerticalLayout();
	private VerticalLayout mainLayout = new VerticalLayout();
		

	@WebServlet(value = "/*", asyncSupported = true)
	    @VaadinServletConfiguration(productionMode = false, ui = SapoBackofficeUI.class)
		public static class Servlet extends VaadinServlet {}

		
	//String tenant2=  request.getContextPath();
	@Override
	protected void init(VaadinRequest request) {
		//String tenant= VaadinService.getCurrentRequest().getPathInfo().substring(1);
		getPage().setTitle("Sistema Soporte Elecciones" );
		Responsive.makeResponsive(this);
		
        cargarContenido();
        setSizeFull();
        
	}
	
	
public void cargarContenido(){
 
       mainLayout.setMargin(false);
       setContent(mainLayout);
       barraUsuario = new BarraUsuario();
       mainLayout.addComponent(barraUsuario);
       Resource imagenLogo = new ThemeResource("images/logoT.png");
       Image log = new Image(null, imagenLogo);
       log.setSizeFull();
       logo.addComponent(log);
       logo.setSizeFull();
       mainLayout.addComponent(logo);  
       
       
    	  TabSheet tabSheet = new TabSheet();
          tabSheet.setHeight(100.0f, Unit.PERCENTAGE);
          tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
          tabSheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
          tabSheet.setSizeFull();
          PanelDinamico layout1 = new PanelDinamico();
          formABMUsuario = new FormABMUsuario();
          layout1.addComponent(formABMUsuario);
          tabSheet.addTab(layout1, "Gestion Usuario"); 
          mainLayout.setExpandRatio(logo, 10);
          mainLayout.addComponent(tabSheet);
          mainLayout.setExpandRatio(tabSheet, 70);
          mainLayout.addComponent(new PiePagina());
          mainLayout.setSizeFull();
	      }
  
}


	