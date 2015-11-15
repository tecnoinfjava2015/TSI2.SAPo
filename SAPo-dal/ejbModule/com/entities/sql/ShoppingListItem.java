package com.entities.sql;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ShoppingListItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private long virtualStorageId;
	private String productBarcode;
	private String productname;
	private long quantity;
	@Temporal(TemporalType.DATE)
	private Calendar creationDate;
	
	public ShoppingListItem() {
		this.virtualStorageId=0;
		this.productBarcode=null;
		this.productname=null;
		this.quantity=0;
		this.creationDate=Calendar.getInstance();
	}
	
	public ShoppingListItem(long virtualStorageId, String productBarcode, String productname, long quantity, Calendar creationDate) {
		this.virtualStorageId=virtualStorageId;
		this.productBarcode=productBarcode;
		this.productname=productname;
		this.quantity=quantity;
		this.creationDate=creationDate;
	}
	
	public Calendar getDate() {
		return creationDate;
	}

	public void setDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getVirtualStorageId() {
		return virtualStorageId;
	}
	public void setVirtualStorageId(long virtualStorageId) {
		this.virtualStorageId = virtualStorageId;
	}
	public String getProductBarcode() {
		return productBarcode;
	}
	public void setProductBarcode(String productBarcode) {
		this.productBarcode = productBarcode;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	
}
