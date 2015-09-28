package com.mongo.utilities;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import com.entities.mongo.BaseEntity;

public class GenericPersistence {
	private final Datastore mongoDatastore;

	public GenericPersistence() {
		mongoDatastore = MongoDB.instance().getDatabase();
	}

	public <E extends BaseEntity> ObjectId persist(E entity) {
		mongoDatastore.save(entity);
		return entity.getId();
	}

	public <E extends BaseEntity> long count(Class<E> clazz) {
		if (clazz == null) {
			return 0;
		}

		return mongoDatastore.find(clazz).countAll();
	}

	public <E extends BaseEntity> E get(Class<E> clazz, final ObjectId id) {
		if ((clazz == null) || (id == null)) {
			return null;
		}

		return mongoDatastore.find(clazz).field("id").equal(id).get();
	}
	
	public <E extends BaseEntity>  void remove(Class<E> clazz, final ObjectId id){
		if ((clazz != null) && (id != null)) {
			mongoDatastore.delete(mongoDatastore.find(clazz).field("id").equal(id));
			}
	}
}
