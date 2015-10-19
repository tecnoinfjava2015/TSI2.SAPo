package com.entities.mongo;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexes;

@Entity(value="Categories",noClassnameStored=false)
@Indexes(@Index(fields = { @Field(value = "virtualStorageId"), @Field(value = "localId") }, options = @IndexOptions(name="iCategoryUsuKey", unique=true)))
public class Category extends BaseEntity {
	private int localId;
	private long virtualStorageId;
	private String virtualStorageName;
	private String name;
	private boolean starred;
	private String icon;
	
	public Category() {
		super();
	}

	public Category(int localId, long virtualStorageId, String virtualStorageName,
			String name, boolean starred, String icon) {
		super();
		this.localId = localId;
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

	public int getLocalId() {
		return localId;
	}

	public void setLocalId(int localId) {
		this.localId = localId;
	}
	
	

}
