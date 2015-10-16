package com.entities.mongo;

import java.util.AbstractMap;

public class GenericProduct extends BaseEntity {
	private String barcode;
	private String name;
	private String description;
	private AbstractMap.SimpleEntry<String,String> nameValueSpec;
	private Byte[] imagen;
	
	//BARCODE
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
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
	
	//SPECS
	public AbstractMap.SimpleEntry<String, String> getNameValueSpec() {
		return nameValueSpec;
	}
	public void setNameValueSpec(
			AbstractMap.SimpleEntry<String, String> nameValueSpec) {
		this.nameValueSpec = nameValueSpec;
	}
	
	//IMAGEN
	public Byte[] getImagen() {
		return imagen;
	}
	public void setImagen(Byte[] imagen) {
		this.imagen = imagen;
	}
}
