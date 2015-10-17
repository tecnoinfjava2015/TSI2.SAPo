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
	private double salePrice;
	private double purchasePrice;
	private double stock;
	private List<Alert> alerts; 
	private List<byte[]> images;
	private List<Spec> specs;
	private List<Category> categories;
	private boolean active;
	
	
	public Product() {
		super();
	}


	public Product(long virtualStorageId, String virtualStorageName,
			String barCode, String name, String description, double salePrice,
			double purchasePrice, double stock, List<Alert> alerts,
			List<byte[]> images, List<Spec> specs, List<Category> categories,
			boolean active) {
		super();
		this.virtualStorageId = virtualStorageId;
		this.virtualStorageName = virtualStorageName;
		this.barCode = barCode;
		this.name = name;
		this.description = description;
		this.salePrice = salePrice;
		this.purchasePrice = purchasePrice;
		this.stock = stock;
		this.alerts = alerts;
		this.images = images;
		this.specs = specs;
		this.categories = categories;
		this.active = active;
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


	public List<byte[]> getImages() {
		return images;
	}


	public void setImages(List<byte[]> images) {
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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (active ? 1231 : 1237);
		result = prime * result + ((alerts == null) ? 0 : alerts.hashCode());
		result = prime * result + ((barCode == null) ? 0 : barCode.hashCode());
		result = prime * result
				+ ((categories == null) ? 0 : categories.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((images == null) ? 0 : images.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(purchasePrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(salePrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((specs == null) ? 0 : specs.hashCode());
		temp = Double.doubleToLongBits(stock);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ (int) (virtualStorageId ^ (virtualStorageId >>> 32));
		result = prime
				* result
				+ ((virtualStorageName == null) ? 0 : virtualStorageName
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (active != other.active)
			return false;
		if (alerts == null) {
			if (other.alerts != null)
				return false;
		} else if (!alerts.equals(other.alerts))
			return false;
		if (barCode == null) {
			if (other.barCode != null)
				return false;
		} else if (!barCode.equals(other.barCode))
			return false;
		if (categories == null) {
			if (other.categories != null)
				return false;
		} else if (!categories.equals(other.categories))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (images == null) {
			if (other.images != null)
				return false;
		} else if (!images.equals(other.images))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(purchasePrice) != Double
				.doubleToLongBits(other.purchasePrice))
			return false;
		if (Double.doubleToLongBits(salePrice) != Double
				.doubleToLongBits(other.salePrice))
			return false;
		if (specs == null) {
			if (other.specs != null)
				return false;
		} else if (!specs.equals(other.specs))
			return false;
		if (Double.doubleToLongBits(stock) != Double
				.doubleToLongBits(other.stock))
			return false;
		if (virtualStorageId != other.virtualStorageId)
			return false;
		if (virtualStorageName == null) {
			if (other.virtualStorageName != null)
				return false;
		} else if (!virtualStorageName.equals(other.virtualStorageName))
			return false;
		return true;
	}

	

}
