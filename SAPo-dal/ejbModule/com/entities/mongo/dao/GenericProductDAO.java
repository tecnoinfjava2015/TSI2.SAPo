package com.entities.mongo.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.entities.mongo.Alert;
import com.entities.mongo.Category;
import com.entities.mongo.GenericProduct;
import com.entities.mongo.Product;
import com.entities.mongo.Spec;
import com.mongo.utilities.GenericPersistence;
import com.mongo.utilities.MongoDB;
import com.mongo.utilities.Utils;

public class GenericProductDAO {
	
	private final Datastore ds;
	private final GenericPersistence dao;
	
	public GenericProductDAO() {
		ds = MongoDB.instance().getDatabase();
		dao = new GenericPersistence();
	}
	
	public ObjectId insert(GenericProduct gProduct) {
		return dao.persist(gProduct);
	}

	public GenericProduct getById(ObjectId id){
		return dao.get(GenericProduct.class, id);
	}
	
	public long countAll(Class<GenericProduct> gProduct) {
		return dao.count(gProduct);
	}
	
	public GenericProduct getByName(String productName){
		if ((productName.isEmpty())) {
			return null;
		}	
		
		Query<GenericProduct> query = ds.createQuery(GenericProduct.class);
		query.and(query.criteria("name").equal(productName));
		return query.get();
	}
	
	public GenericProduct getByBarCode(String barCode){
		if ((barCode.isEmpty())) {
			return null;
		}	
		Query<GenericProduct> query = ds.createQuery(GenericProduct.class);
		query.and(query.criteria("barCode").equal(barCode));
		return query.get();
	}	
	
	public List<GenericProduct> getAllGenricProducts(){
		Query<GenericProduct> query = ds.createQuery(GenericProduct.class);
		return query.asList();
	}

	public void deleteGenericProduct(ObjectId id) {
		dao.remove(GenericProduct.class, id);
	}
	
	public List<GenericProduct> getGenericsBarcodeAndName(String search, int limit){
		Query<GenericProduct> query = ds.createQuery(GenericProduct.class);
		query.criteria("name").contains(search);
		query.limit(limit).retrievedFields(true, "barCode","name");
		return query.asList();
	}

	
}
