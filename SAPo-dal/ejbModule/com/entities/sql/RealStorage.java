package com.entities.sql;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class RealStorage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3066123918990693668L;
	
	@Id @GeneratedValue 
	private int id;
	private String nombre;
	private String direccion;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

}
