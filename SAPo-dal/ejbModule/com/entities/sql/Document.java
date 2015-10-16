package com.entities.sql;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Document {
	@Id
	@GeneratedValue
	private long id;
	private long virtualStorageId;
	private String name;
	private String description;
	private int stock;
	private int cash;
	
	public long getVirtualStorage() {
		return virtualStorageId;
	}
	public void setVirtualStorage(long virtualStorageId) {
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getCash() {
		return cash;
	}
	public void setCash(int cash) {
		this.cash = cash;
	}
}
