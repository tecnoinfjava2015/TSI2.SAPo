package com.bo.principal;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class MenuUsuario extends MenuBar{

	private static final long serialVersionUID = 1L;
	private Window window;
	
	
	public MenuUsuario(){
		this.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		MenuItem padre = addItem("Nombre", FontAwesome.GEAR, null);
		padre.setStyleName("v.menu");
		
		MenuItem menu = null;
/*
		menu = padre.addItem("Ver perfil", FontAwesome.USER, new Command() {
			 
 			@Override
 			public void menuSelected(MenuItem selectedItem) {
			//	falta la ventana para editar el usuario
 			//	abrirPopup();
 			
 			}
 		});*/
		menu = padre.addItem("Cerrar sesion", FontAwesome.SIGN_OUT, new Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {		
				((SapoBackofficeUI) UI.getCurrent()).logout();
			}
		});
	}

	public MenuUsuario(String usuario) {
		this.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		MenuItem padre = addItem(usuario, FontAwesome.GEAR, null);
		padre.setStyleName("v.menu");
		
		MenuItem menu = null;
/*
		menu = padre.addItem("Ver perfil", FontAwesome.USER, new Command() {
			 
 			@Override
 			public void menuSelected(MenuItem selectedItem) {
			//	falta la ventana para editar el usuario
 			//	abrirPopup();
 			
 			}
 		});*/
		menu = padre.addItem("Cerrar sesion", FontAwesome.SIGN_OUT, new Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {		
				((SapoBackofficeUI) UI.getCurrent()).logout();
			}
		});
	}

	protected void abrirPopup() {
		if (window.getParent() != null) {
            Notification not = new Notification("Window is already open");
            not.show(Page.getCurrent());
            
        } else {
        	UI.getCurrent().addWindow(window);
        }
	}
}
