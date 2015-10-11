package com.bo.principal;

import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class BarraUsuario extends VerticalLayout{

	private static final long serialVersionUID = 1L;
	private Label titulo;
	
	public BarraUsuario(){
		this.setSpacing(true);
		this.setStyleName("barraUsuario");
		// Layout nombre de usuario
		AbsoluteLayout absLayout = new AbsoluteLayout();
		MenuUsuario menuUsuario = new MenuUsuario();
		menuUsuario.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
		menuUsuario.addStyleName(ValoTheme.MENUBAR_SMALL);
		absLayout.addComponent(menuUsuario, "bottom:0; right:0");
		absLayout.setWidth("100%");
		absLayout.setHeight("60px");
		
		titulo = new Label("Sapo");
		titulo.setStyleName("fondo-negro");
		Resource imagenLogo = new ThemeResource("images/logo.jpg");
		Image log = new Image(null, imagenLogo);
		log.setSizeUndefined();
		log.setHeight("60px");

		absLayout.addComponent(log);
		absLayout.addComponent(titulo, "bottom:2; left:110");
		addComponent(absLayout);
	}
}
