package com.entities.mongo.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.entities.mongo.CategoryOLD;
import com.entities.mongo.ProductOLD;
import com.mongo.utilities.MongoDB;
import com.mongo.utilities.GenericPersistence;

public class ProductDAOOLD {
	private final Datastore ds;
	private final GenericPersistence dao;
	
	public ProductDAOOLD() {
		ds = MongoDB.instance().getDatabase();
		dao = new GenericPersistence();
	}
	
	public ObjectId insert(ProductOLD pt) {
		return dao.persist(pt);
	}

	public long count(Class<ProductOLD> pt) {
		return dao.count(pt);
	}

	public ProductOLD getById(ObjectId id){
		return dao.get(ProductOLD.class, id);
	}
		
	public ProductOLD getByName(String tenant, String name){
		if ((name == null || tenant == null)) {
			return null;
		}	
		Query<ProductOLD> query = ds.createQuery(ProductOLD.class);
		query.and(query.criteria("descName").equal(name), 
				query.criteria("tenant").equal(tenant)
				);
		return query.get();
	}
	
	public ProductOLD getByTenant(String tenant){
		if ((tenant == null)) {
			return null;
		}	
		Query<ProductOLD> query = ds.createQuery(ProductOLD.class);
		query.criteria("tenant").equal(tenant);
		query.countAll();
		return query.get();
	}

	public List<ProductOLD> getProductsByCategory(String tenant, String name){
		if ((tenant == null) || (name == null)) {
			return null;
		}
		
		Query<CategoryOLD> query = ds.createQuery(CategoryOLD.class);
		
		query.and(query.criteria("name").equal(name), 
				query.criteria("tenant").equal(tenant)
				);
		
		CategoryOLD auxCat = query.get();
		return ds.find(ProductOLD.class).field("categoryId").equal(auxCat.getId()).asList(); // *ver* 27/09
	}

	public void deleteProduct(String name, String tenant){
		if (!(name!=null)){
			Query<ProductOLD> query = ds.createQuery(ProductOLD.class);
			ds.delete(query.and(query.criteria("name").equal(name), 
					query.criteria("tenant").equal(tenant))); // *ver* 27/09
		}
	}
	
	public void cleanProducts(String tenant){
		if (!(tenant==null)){
			ds.delete(ds.createQuery(ProductOLD.class));
		}
	}
	
	public boolean isEmpty(String tenant){
		Query<ProductOLD> query = ds.createQuery(ProductOLD.class);
		query.criteria("tenant").equal(tenant);
		return (query.countAll() == 0);
	}
}
