package com.entities.mongo.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.entities.mongo.Category;
import com.entities.mongo.Product;
import com.mongo.utilities.MongoDB;
import com.mongo.utilities.GenericPersistence;

public class ProductDAO {
	private final Datastore ds;
	private final GenericPersistence dao;
	
	public ProductDAO() {
		ds = MongoDB.instance().getDatabase();
		dao = new GenericPersistence();
	}
	
	public ObjectId insert(Product pt) {
		return dao.persist(pt);
	}

	public long count(Class<Product> pt) {
		return dao.count(pt);
	}

	public Product getById(ObjectId id){
		return dao.get(Product.class, id);
	}
		
	public Product getByName(String tenant, String name){
		if ((name == null || tenant == null)) {
			return null;
		}	
		Query<Product> query = ds.createQuery(Product.class);
		query.and(query.criteria("descName").equal(name), 
				query.criteria("tenant").equal(tenant)
				);
		return query.get();
	}
	
	public Product getByTenant(String tenant){
		if ((tenant == null)) {
			return null;
		}	
		Query<Product> query = ds.createQuery(Product.class);
		query.criteria("tenant").equal(tenant);
		query.countAll();
		return query.get();
	}

	public List<Product> getProductsByCategory(String tenant, String name){
		if ((tenant == null) || (name == null)) {
			return null;
		}
		
		Query<Category> query = ds.createQuery(Category.class);
		
		query.and(query.criteria("name").equal(name), 
				query.criteria("tenant").equal(tenant)
				);
		
		Category auxCat = query.get();
		return ds.find(Product.class).field("categoryId").equal(auxCat.getId()).asList(); // *ver* 27/09
	}

	public void deleteProduct(String name, String tenant){
		if (!(name!=null)){
			Query<Product> query = ds.createQuery(Product.class);
			ds.delete(query.and(query.criteria("name").equal(name), 
					query.criteria("tenant").equal(tenant))); // *ver* 27/09
		}
	}
	
	public void cleanProducts(String tenant){
		if (!(tenant==null)){
			ds.delete(ds.createQuery(Product.class));
		}
	}
	
	public boolean isEmpty(String tenant){
		Query<Product> query = ds.createQuery(Product.class);
		query.criteria("tenant").equal(tenant);
		return (query.countAll() == 0);
	}
}
