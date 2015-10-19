package com.entities.sql;

import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Bill {
	@Id
	@GeneratedValue
	private long id;
	private long virtualStorage;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "document")
	private Document document;
	private String origin;
	private String destination;
	@Temporal(TemporalType.DATE)
	private Calendar date;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "details")
	private List<BillDetail> details;
	private double amount;
	
	public long getVirtualStorage() {
		return virtualStorage;
	}
	public void setVirtualStorage(long virtualStorage) {
		this.virtualStorage = virtualStorage;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Document getDocument() {
		return document;
	}
	public void setDocument(Document document) {
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
