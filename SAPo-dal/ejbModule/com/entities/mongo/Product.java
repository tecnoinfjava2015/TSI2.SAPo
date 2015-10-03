package com.entities.mongo;

import java.util.List;

import org.mongodb.morphia.annotations.*;

@Entity(value="Product",noClassnameStored=false)
public class Product extends ProductTemplate{
	private boolean active;
	public String descName; //Nombre dado para el AV
	public String tenant;
	public long valueCuantity;
	public String valueType;
	public List<byte[]> images;
	
	public Product() {
		super();
	}
	
	public String getDescName() {
		return descName;
	}
	public void setDescName(String descName) {
		this.descName = descName;
	}
	public String getTenant() {
		return tenant;
	}
	public void setTenant(String tenant) {
		this.tenant = tenant;
	}
	public List<byte[]> getImages() {
		return images;
	}
	public void setImages(List<byte[]> images) {
		this.images = images;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public long getCoinCuantity() {
		return valueCuantity;
	}
	public void setCoinCuantity(long coinCuantity) {
		this.valueCuantity = coinCuantity;
	}
	public String getCoinType() {
		return valueType;
	}
	public void setCoinType(String coinType) {
		this.valueType = coinType;
	}
	public List<byte[]> getImagenes() {
		return images;
	}
	public void setImagenes(List<byte[]> imagenes) {
		this.images = imagenes;
	}
}
