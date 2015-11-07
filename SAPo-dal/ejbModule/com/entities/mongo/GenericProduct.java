package com.entities.mongo;

import java.util.AbstractMap;

public class GenericProduct extends BaseEntity {
	private String barCode;
	private String name;
	private String description;
	private Byte[] imagen;
	
	//BARCODE
	public String getBarcode() {
		return barCode;
	}
	public void setBarcode(String barcode) {
		this.barCode = barcode;
	}
	
	//NAME
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	//DESCRIPTION
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	//IMAGEN
	public Byte[] getImagen() {
		return imagen;
	}
	public void setImagen(Byte[] imagen) {
		this.imagen = imagen;
	}
}
