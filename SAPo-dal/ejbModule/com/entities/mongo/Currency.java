package com.entities.mongo;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexes;

import com.mongo.utilities.DataType;

@Entity(value="Currencies",noClassnameStored=false)
@Indexes(@Index(fields = { @Field(value = "virtualStorageId"), @Field(value = "name") }, options = @IndexOptions(name="iCurrencyUsuKey", unique=true)))
public class Currency extends BaseEntity{
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Currency other = (Currency) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		if (type != other.type)
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
