package com.entities.sql;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Unit {
	@Id
	@GeneratedValue
	private long id;
	private long virtualStorageId;
	private String name;
	
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
}
