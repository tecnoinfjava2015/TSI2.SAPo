package com.entities.mongo;

import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.PrePersist;
import org.mongodb.morphia.annotations.Version;

public abstract class BaseEntity {
	@Id
	private ObjectId id;
	private Date creationDate;
	private Date lastChange;
	@Version
	private long version;

	public BaseEntity() {
		super();
	}

	public ObjectId getId() {
		return id;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastChange == null) ? 0 : lastChange.hashCode());
		result = prime * result + (int) (version ^ (version >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseEntity other = (BaseEntity) obj;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastChange == null) {
			if (other.lastChange != null)
				return false;
		} else if (!lastChange.equals(other.lastChange))
			return false;
		if (version != other.version)
			return false;
		return true;
	}
	
	
}
