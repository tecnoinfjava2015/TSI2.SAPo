package com.entities.sql;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sun.istack.internal.Nullable;

@Entity
public class Document {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@Nullable
	private long virtualStorageId;
	private String name;
	private String description;
	private int stock;
	private int cash;
	
	public Document() {
		this.virtualStorageId=0;
		this.name=null;
		this.description=null;
		this.stock=0;
		this.cash=0;
	}
	
	public Document(long virtualStorageId, String name, String description, int stock, int cash) {
		this.virtualStorageId=virtualStorageId;
		this.name=name;
		this.description=description;
		this.stock=stock;
		this.cash=cash;
	}
	
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
