package com.entities.sql;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class BillDetail {
	@Id
	@GeneratedValue
	private int id;
	private long virtualStorageId;
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "details")
	private Bill bill;
	private String productId;
	private String productName;
	private double quantity;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "unit")
	private Unit unit;
	private double unitPrice;
	private double price;
	
	public long getVirtualStorageId() {
		return virtualStorageId;
	}
	public void setVirtualStorageId(long virtualStorageId) {
		this.virtualStorageId = virtualStorageId;
	}
	public Bill getBill() {
		return bill;
	}
	public void setBill(Bill bill) {
		this.bill = bill;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}
