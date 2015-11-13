package com.entities.mongo;
import java.util.AbstractMap;

public class GenericCategory extends BaseEntity {

	private String name;
	private String description;
	private String icon;
	
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
	public String getIcon() {
		return icon;
	}
	public void setIcon(String imagen) {
		this.icon = imagen;
	}
}
