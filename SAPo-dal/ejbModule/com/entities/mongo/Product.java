/**
 * 
 */
package com.entities.mongo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexes;

import com.entities.sql.Unit;

/**
 * @author jpmartinez
 *
 */
@XmlRootElement
@Entity(value="Products",noClassnameStored=false)
@Indexes(@Index(fields = { @Field(value = "virtualStorageId"), @Field(value = "barCode") }, options = @IndexOptions(name="iProductUsuKey", unique=true)))
public class Product extends BaseEntity{
	private long virtualStorageId;
	private String virtualStorageName;
	private String barCode;
	private String name;
	private String description;
	private Unit unit;
	private double salePrice;
	private double purchasePrice;
	private double stock;
	private List<Alert> alerts; 
	private List<String> images;
	private List<Spec> specs;
	private List<Category> categories;
	private boolean active;
	private List<String> chips;
	
	
	public Product() {
		super();
	}


	public Product(long virtualStorageId, String virtualStorageName,
			String barCode, String name, String description, Unit unit,
			double salePrice, double purchasePrice, double stock,
			List<Alert> alerts, List<String> images, List<Spec> specs,
			List<Category> categories, boolean active, List<String> chips) {
		super();
		this.virtualStorageId = virtualStorageId;
		this.virtualStorageName = virtualStorageName;
		this.barCode = barCode;
		this.name = name;
		this.description = description;
		this.unit = unit;
		this.salePrice = salePrice;
		this.purchasePrice = purchasePrice;
		this.stock = stock;
		this.alerts = alerts;
		this.images = images;
		this.specs = specs;
		this.categories = categories;
		this.active = active;
		this.chips = chips;
	}


	public long getVirtualStorageId() {
		return virtualStorageId;
	}


	public void setVirtualStorageId(long virtualStorageId) {
		this.virtualStorageId = virtualStorageId;
	}


	public String getVirtualStorageName() {
		return virtualStorageName;
	}


	public void setVirtualStorageName(String virtualStorageName) {
		this.virtualStorageName = virtualStorageName;
	}


	public String getBarCode() {
		return barCode;
	}


	public void setBarCode(String barCode) {
		this.barCode = barCode;
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


	public Unit getUnit() {
		return unit;
	}


	public void setUnit(Unit unit) {
		this.unit = unit;
	}


	public double getSalePrice() {
		return salePrice;
	}


	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}


	public double getPurchasePrice() {
		return purchasePrice;
	}


	public void setPurchasePrice(double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}


	public double getStock() {
		return stock;
	}


	public void setStock(double stock) {
		this.stock = stock;
	}


	public List<Alert> getAlerts() {
		return alerts;
	}


	public void setAlerts(List<Alert> alerts) {
		this.alerts = alerts;
	}


	public List<String> getImages() {
		return images;
	}


	public void setImages(List<String> images) {
		this.images = images;
	}


	public List<Spec> getSpecs() {
		return specs;
	}


	public void setSpecs(List<Spec> specs) {
		this.specs = specs;
	}


	public List<Category> getCategories() {
		return categories;
	}


	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}

	public List<String> getChips() {
		return chips;
	}


	public void setChips(List<String> chips) {
		this.chips = chips;
	}


}
