package com.entities.mongo;

import java.util.List;

import org.mongodb.morphia.annotations.*;

@Entity(value="Product",noClassnameStored=false)
public class Product extends ProductTemplate{
	private boolean active;
	public String descName; //Nombre dado para el AV
	public String tenant;
	public long valueQuantity;
	public String valueType;
	public List<byte[]> images;
	public long Quantity;
	
	public Product() {
		super();
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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

	public long getValueQuantity() {
		return valueQuantity;
	}

	public void setValueQuantity(long valueQuantity) {
		this.valueQuantity = valueQuantity;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public List<byte[]> getImages() {
		return images;
	}

	public void setImages(List<byte[]> images) {
		this.images = images;
	}

	public long getQuantity() {
		return Quantity;
	}

	public void setQuantity(long quantity) {
		Quantity = quantity;
	}
}
