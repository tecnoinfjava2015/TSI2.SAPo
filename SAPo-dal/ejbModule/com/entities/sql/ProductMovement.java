package com.entities.sql;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.entities.sql.Unit;

@Entity
public class ProductMovement {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long movimentID; 
	private String barCode;
	private String productName;
	private long virtualStorageId;
	private int userID;
	@Temporal(TemporalType.DATE)
	private Calendar dateMov;
	private double initialPrice;
	private double finalPrice;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "unit")
	private Unit unit;
	private boolean toAV;
	private long origin;
	private long destination;
	private long stock;
	
	public long getMovimentID() {
		return movimentID;
	}
	public void setMovimentID(long movimentID) {
		this.movimentID = movimentID;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public long getVirtualStorageId() {
		return virtualStorageId;
	}
	public void setStorageId(long virtualStorageId) {
		this.virtualStorageId = virtualStorageId;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public Calendar getDateMov() {
		return dateMov;
	}
	public void setDateMov(Calendar dateMov) {
		this.dateMov = dateMov;
	}
	public double getInitialPrice() {
		return initialPrice;
	}
	public void setInitialPrice(double initialPrice) {
		this.initialPrice = initialPrice;
	}
	public double getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(double finalPrice) {
		this.finalPrice = finalPrice;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	public boolean isToAV() {
		return toAV;
	}
	public void setToAV(boolean toAV) {
		this.toAV = toAV;
	}
	public long getOrigin() {
		return origin;
	}
	public void setOrigin(long origin) {
		this.origin = origin;
	}
	public long getDestination() {
		return destination;
	}
	public void setDestination(long destination) {
		this.destination = destination;
	}
	public long getStock() {
		return stock;
	}
	public void setStock(long stock) {
		this.stock = stock;
	}
}
