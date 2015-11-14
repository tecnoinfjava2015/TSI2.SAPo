package com.entities.sql;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class LimitCount implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int id;
	private String tipo;
	private int limite;
	private int avisarFaltando;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return tipo;
	}
	public void setType(String type) {
		this.tipo = type;
	}
	public int getLimit() {
		return limite;
	}
	public void setLimit(int limit) {
		this.limite = limit;
	}
	public int getAvisarFaltando() {
		return avisarFaltando;
	}
	public void setAvisarFaltando(int avisarFaltando) {
		this.avisarFaltando = avisarFaltando;
	}

}
