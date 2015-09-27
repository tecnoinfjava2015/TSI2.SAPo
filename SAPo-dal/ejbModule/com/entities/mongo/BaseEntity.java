package com.entities.mongo;

import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.PrePersist;
import org.mongodb.morphia.annotations.Version;

public abstract class BaseEntity {
	@Id
	protected ObjectId _id;
	/**
	 * @return the _id
	 */
	public ObjectId getId() {
		return _id;
	}

	protected Date creationDate;
	protected Date lastChange;
	@Version
	private long version;

	public BaseEntity() {
		super();
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getLastChange() {
		return lastChange;
	}

	@PrePersist
	public void prePersist() {
		this.creationDate = (creationDate == null) ? new Date() : creationDate;
		this.lastChange = (lastChange == null) ? creationDate : new Date();
	}
}
