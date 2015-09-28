package com.entities.mongo.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.entities.mongo.Category;
import com.mongo.utilities.MongoDB;
import com.mongo.utilities.GenericPersistence;

public class CategoryDAO {
	private final Datastore ds;
	private final GenericPersistence dao;

	public CategoryDAO() {
		ds = MongoDB.instance().getDatabase();
		dao = new GenericPersistence();
	}

	public ObjectId persist(Category cat) {
		return dao.persist(cat);
	}

	public long count(Class<Category> cat) {
		return dao.count(cat);
	}
	
	public Category getById(ObjectId id){
		return dao.get(Category.class, id);
	}
	
	public Category getByName(String tenant, String name){
		if ((tenant == null) || (name == null)) {
			return null;
		}
		
		Query<Category> query = ds.createQuery(Category.class);
		
		query.and(query.criteria("name").equal(name), 
				query.criteria("tenant").equal(tenant)
				);
		
		return query.get();
		
	}
	
	public List<Category> getAllCategories(String tenant){
		if (tenant==null){
			return null;
		}
		
		return ds.find(Category.class).field("tenant").equal(tenant).asList();
		}
	
	public void remove(String tenant, String name){
		Query<Category> q = ds.createQuery(Category.class);
		q.and(q.criteria("tenant").equal(tenant),
				q.or(q.criteria("name").equal(name),
						q.criteria("ancestors").contains(name)));
		ds.delete(q);
	}

	public void cleanCategories(String tenant){
		if (!(tenant==null)){
			ds.delete(ds.createQuery(Category.class));
		}
	}	
}

