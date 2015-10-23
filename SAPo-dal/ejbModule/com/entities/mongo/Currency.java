package com.entities.mongo;

import javax.xml.bind.annotation.XmlRootElement;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexes;

import com.mongo.utilities.DataType;
@XmlRootElement
@Entity(value="Currencies",noClassnameStored=false)
@Indexes(@Index(fields = { @Field(value = "virtualStorageId"), @Field(value = "localId") }, options = @IndexOptions(name="iCurrencyUsuKey", unique=true)))
public class Currency extends BaseEntity{
	private int localId;
	private long virtualStorageId;
	private String virtualStorageName;
	private String name;
	private String symbol;
	private DataType type;
	
	public Currency() {
		super();
	}
	
	public Currency(long virtualStorageId, String virtualStorageName,
			String name, String symbol, DataType type) {
		super();
		this.virtualStorageId = virtualStorageId;
		this.virtualStorageName = virtualStorageName;
		this.name = name;
		this.symbol = symbol;
		this.type = type;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public DataType getType() {
		return type;
	}
	public void setType(DataType type) {
		this.type = type;
	}

	public int getLocalId() {
		return localId;
	}

	public void setLocalId(int localId) {
		this.localId = localId;
	}


}
