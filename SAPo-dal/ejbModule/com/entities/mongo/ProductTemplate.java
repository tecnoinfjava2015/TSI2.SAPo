package com.entities.mongo;

import java.util.AbstractMap;

import org.mongodb.morphia.annotations.*;

@Entity(value="ProductTemplate",noClassnameStored=false)
public abstract class ProductTemplate extends BaseEntity{
	@Indexed(unique = true)
	public String uniqueName;
	public String description;
	public AbstractMap.SimpleEntry<String,String> nameValueSpec;
	public Category category;
	
	public ProductTemplate() {
		super();
	}
	
	public String getDescName() {
		return uniqueName;
	}
	public void setDescName(String descName) {
		this.uniqueName = descName;
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
}
