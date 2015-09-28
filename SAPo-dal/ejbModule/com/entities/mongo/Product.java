package com.entities.mongo;

import java.util.List;

import org.mongodb.morphia.annotations.*;

@Entity(value="Product",noClassnameStored=false)
public class Product extends ProductTemplate{
	private boolean active;
	public String descName; //Nombre dado para el AV
	public long coinCuantity;
	public String coinType;
	public List<byte[]> images;
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public long getCoinCuantity() {
		return coinCuantity;
	}
	public void setCoinCuantity(long coinCuantity) {
		this.coinCuantity = coinCuantity;
	}
	public String getCoinType() {
		return coinType;
	}
	public void setCoinType(String coinType) {
		this.coinType = coinType;
	}
	public List<byte[]> getImagenes() {
		return images;
	}
	public void setImagenes(List<byte[]> imagenes) {
		this.images = imagenes;
	}
}
