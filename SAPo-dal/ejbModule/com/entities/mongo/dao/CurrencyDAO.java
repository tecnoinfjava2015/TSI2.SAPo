package com.entities.mongo.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.entities.mongo.Currency;
import com.mongo.utilities.GenericPersistence;
import com.mongo.utilities.MongoDB;

public class CurrencyDAO {
	private final Datastore ds;
	private final GenericPersistence dao;

	public CurrencyDAO() {
		ds = MongoDB.instance().getDatabase();
		dao = new GenericPersistence();
	}

	public ObjectId persist(Currency currency) {
		return dao.persist(currency);
	}

	public long countAll() {
		return dao.count(Currency.class);
	}
	
	public long countVirtualStorage(long virtualStorageId) {
		Query<Currency> query = ds.createQuery(Currency.class);
		query.criteria("virtualStorageId").equal(virtualStorageId);
		return query.countAll();
	}	

	public Currency getById(ObjectId id) {
		return dao.get(Currency.class, id);
	}

	public Currency getByName(long virtualStorageId, String name) {
		if (!(virtualStorageId > 0) || (name.isEmpty())) {
			return null;
		}

		Query<Currency> query = ds.createQuery(Currency.class);

		query.and(query.criteria("name").equal(name),
				query.criteria("virtualStorageId").equal(virtualStorageId));

		return query.get();

	}
	
	public Currency getByLocalId(long virtualStorageId, int localId) {
		if (!(virtualStorageId > 0) || !(localId>0)) {
			return null;
		}

		Query<Currency> query = ds.createQuery(Currency.class);

		query.and(query.criteria("localId").equal(localId),
				query.criteria("virtualStorageId").equal(virtualStorageId));

		return query.get();

	}	

	public List<Currency> getAllCurrencies(long virtualStorageId,int offset, int limit) {
		if (!(virtualStorageId < 0)) {
			return null;
		}

		return ds.find(Currency.class).field("virtualStorageId")
				.equal(virtualStorageId).offset(offset).limit(limit).asList();
	}	
	
	public void remove(long virtualStorageId, int localId){
		dao.remove(Currency.class, getByLocalId(virtualStorageId, localId).getId());
	}
}
