package com.entities.mongo;

import java.util.AbstractMap;

import org.mongodb.morphia.annotations.*;

@Entity(value="ProductTemplate",noClassnameStored=false)
public abstract class ProductTemplate extends BaseEntity{
	@Indexed(unique = true)
	public String uniqueName;
	@Indexed(unique = true)
	public String barCode;
	public String description;
	public AbstractMap.SimpleEntry<String,String> nameValueSpec;
	public Category category;
	
	public ProductTemplate() {
		super();
	}

	public String getUniqueName() {
		return uniqueName;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AbstractMap.SimpleEntry<String, String> getNameValueSpec() {
		return nameValueSpec;
	}

	public void setNameValueSpec(
			AbstractMap.SimpleEntry<String, String> nameValueSpec) {
		this.nameValueSpec = nameValueSpec;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}	
}
