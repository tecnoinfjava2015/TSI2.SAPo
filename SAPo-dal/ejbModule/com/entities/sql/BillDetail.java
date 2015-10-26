package com.entities.sql;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class BillDetail {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
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
	
	public BillDetail() {
		this.virtualStorageId=0;
		this.bill=null;
		this.productId=null;
		this.productName=null;
		this.quantity=0;
		this.unit=null;
		this.unitPrice=0;
		this.price=0;
	}
	
	public BillDetail(long virtualStorageId, Bill bill, String productId, String productName, double quantity, Unit unit, double unitPrice, double price) {
		this.virtualStorageId=virtualStorageId;
		this.bill=bill;
		this.productId=productId;
		this.productName=productName;
		this.quantity=quantity;
		this.unit=unit;
		this.unitPrice=unitPrice;
		this.price=price;
	}
	
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
