package com.entities.sql;

import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Bill {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private long virtualStorageId;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "document")
	private List<Document> document;
	private String origin;
	private String destination;
	@Temporal(TemporalType.DATE)
	private Calendar date;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "details")
	private List<BillDetail> details;
	private double amount;
	
	public Bill(){
		this.virtualStorageId=0;
		this.document=null;
		this.origin=null;
		this.destination=null;
		this.date=null;
		this.details=null;
		this.amount=0;
	}
	
	public Bill(long virtualStorageId, List<Document> document, String origin, String destination, Calendar date, List<BillDetail> details, double amount){
		this.virtualStorageId=virtualStorageId;
		this.document=document;
		this.origin=origin;
		this.destination=destination;
		this.date=date;
		this.details=details;
		this.amount=amount;
	}
	
	public long getVirtualStorage() {
		return virtualStorageId;
	}
	public void setVirtualStorage(long virtualStorage) {
		this.virtualStorageId = virtualStorage;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public List<Document> getDocument() {
		return document;
	}
	public void setDocument(List<Document> document) {
		this.document = document;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}
	public List<BillDetail> getDetails() {
		return details;
	}
	public void setDetails(List<BillDetail> details) {
		this.details = details;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}	
}
