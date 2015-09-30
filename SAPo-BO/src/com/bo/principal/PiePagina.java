package com.bo.principal;



import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class PiePagina extends VerticalLayout{

	private static final long serialVersionUID = 1L;
	
	public PiePagina(){
		setSpacing(true);
		this.setStyleName("barraUsuario");
		Label version = new Label("SAPo - BackOffice - TSI2 JEE 2015", ContentMode.HTML);
		version.setSizeUndefined();//para que el texto quede centrado
		this.addComponent(version);
		this.setComponentAlignment(version, Alignment.BOTTOM_CENTER);
		this.setWidth("100%");
		this.setHeight("28px");

	}
}
