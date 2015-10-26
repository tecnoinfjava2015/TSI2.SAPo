package com.entities.sql;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sun.istack.internal.Nullable;

@Entity
public class Unit {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@Nullable
	private long virtualStorageId;
	private String name;
	private String abbreviation;
	
	public Unit(){
		this.virtualStorageId=0;
		this.name=null;
		this.abbreviation=null;
	}
	
	public Unit(long virtualStorageId, String name, String abbreviation){
		this.virtualStorageId=virtualStorageId;
		this.name=name;
		this.abbreviation=abbreviation;
	}
	
	public long getVirtualStorageId() {
		return virtualStorageId;
	}
	public void setVirtualStorageId(long virtualStorageId) {
		this.virtualStorageId = virtualStorageId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
}
