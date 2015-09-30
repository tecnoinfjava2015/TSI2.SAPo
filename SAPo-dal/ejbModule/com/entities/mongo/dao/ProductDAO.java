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
		
	public Product getByName(String name){
		if ((name == null)) {
			return null;
		}
			
		Query<Product> query = ds.createQuery(Product.class);
			
		query.criteria("descName").equal(name);
			
		return query.get();
			
	}

	public List<Product> getProductsOfCategory(Category cat){
		if (cat==null){
			return null;			
		}

		return ds.find(Product.class).field("category").equal(cat).asList(); // *ver* 27/09
	}

	public void deleteProduct(String name, String tenant){
		if (!(name!=null)){
			Query<Product> query = ds.createQuery(Product.class);
			ds.delete(query.and(query.criteria("name").equal(name), 
					query.criteria("tenant").equal(tenant))); // *ver* 27/09
		}
	}
}
