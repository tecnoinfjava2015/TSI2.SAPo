package com.entities.mongo.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.entities.mongo.Alert;
import com.entities.mongo.Category;
import com.entities.mongo.GenericProduct;

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

	public long countAll(Class<GenericProduct> gProduct) {
		return dao.count(gProduct);
	}
	
	public GenericProduct getByName(String name){
		if ((name.isEmpty())) {
			return null;
		}	
		
		Query<GenericProduct> query = ds.createQuery(GenericProduct.class);
		query.and(query.criteria("name").equal(name));
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
	
	public List<GenericProduct> getGenericProductsByCategory(String categoryName){
		if ( (categoryName.isEmpty())) {
			return null;
		}
		
		Query<GenericProduct> query = ds.createQuery(GenericProduct.class);
		
		query.and(query.criteria("categories").containsIgnoreCase(categoryName));
		return query.asList();
	}

	public List<GenericProduct> getGenericProductsByCategories(List<Integer> categories){
		if ((categories.isEmpty())) {
			return null;
		}
		
		Query<GenericProduct> query = ds.createQuery(GenericProduct.class);
		for (int cat : categories) {
			query.and(query.criteria("categories.localId").equal(cat));
		}
		return query.asList();
	}	
}
