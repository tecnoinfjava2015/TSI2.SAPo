package com.entities.mongo;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.PrePersist;
import org.mongodb.morphia.annotations.Version;

public abstract class BaseEntity {
	@Id
	private ObjectId id;
	private Calendar creationDate;
	private Calendar lastChange;
	@Version
	private long version;

	public BaseEntity() {
		super();
	}

	public ObjectId getId() {
		return id;
	}
	
	public Calendar getCreationDate() {
		return creationDate;
	}

	public Calendar getLastChange() {
		return lastChange;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	@PrePersist
	public void prePersist() {
		this.creationDate = (creationDate == null) ? new GregorianCalendar() : creationDate;
		this.lastChange = (lastChange == null) ? creationDate : new GregorianCalendar();
	}
	
}
