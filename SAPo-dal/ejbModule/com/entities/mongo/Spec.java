package com.entities.mongo;

import org.mongodb.morphia.annotations.Embedded;

import com.mongo.utilities.DataType;

/**
 * @author jpmartinez
 *
 */
@Embedded
public class Spec {
	private	String name;
	private	String value;
	private	DataType type;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public DataType getType() {
		return type;
	}
	public void setType(DataType type) {
		this.type = type;
	}
	
	

}
