package com.entities.mongo;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexes;

@Entity(value="Categories",noClassnameStored=false)
@Indexes(@Index(fields = { @Field(value = "virtualStorageId"), @Field(value = "name") }, options = @IndexOptions(name="iCategoryUsuKey", unique=true)))
public class Category extends BaseEntity {
	private long virtualStorageId;
	private String virtualStorageName;
	private String name;
	private boolean starred;
	private String icon;
	
	public Category() {
		super();
	}

	public Category(long virtualStorageId, String virtualStorageName,
			String name, boolean starred, String icon) {
		super();
		this.virtualStorageId = virtualStorageId;
		this.virtualStorageName = virtualStorageName;
		this.name = name;
		this.starred = starred;
		this.icon = icon;
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

	public boolean isStarred() {
		return starred;
	}

	public void setStarred(boolean starred) {
		this.starred = starred;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((icon == null) ? 0 : icon.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (starred ? 1231 : 1237);
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
		Category other = (Category) obj;
		if (icon == null) {
			if (other.icon != null)
				return false;
		} else if (!icon.equals(other.icon))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (starred != other.starred)
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
