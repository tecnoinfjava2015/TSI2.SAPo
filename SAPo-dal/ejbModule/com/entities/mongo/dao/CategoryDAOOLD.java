package com.entities.mongo.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.entities.mongo.CategoryOLD;
import com.mongo.utilities.MongoDB;
import com.mongo.utilities.GenericPersistence;

public class CategoryDAOOLD {
	private final Datastore ds;
	private final GenericPersistence dao;

	public CategoryDAOOLD() {
		ds = MongoDB.instance().getDatabase();
		dao = new GenericPersistence();
	}

	public ObjectId persist(CategoryOLD cat) {
		return dao.persist(cat);
	}

	public long count(Class<CategoryOLD> cat) {
		return dao.count(cat);
	}
	
	public CategoryOLD getById(ObjectId id){
		return dao.get(CategoryOLD.class, id);
	}
	
	public CategoryOLD getByName(String tenant, String name){
		if ((tenant == null) || (name == null)) {
			return null;
		}
		
		Query<CategoryOLD> query = ds.createQuery(CategoryOLD.class);
		
		query.and(query.criteria("name").equal(name), 
				query.criteria("tenant").equal(tenant)
				);
		
		return query.get();
		
	}
	
	public List<CategoryOLD> getAllCategories(String tenant){
		if (tenant==null){
			return null;
		}
		
		return ds.find(CategoryOLD.class).field("tenant").equal(tenant).asList();
		}
	
	public void remove(String tenant, String name){
		Query<CategoryOLD> q = ds.createQuery(CategoryOLD.class);
		q.and(q.criteria("tenant").equal(tenant),
				q.or(q.criteria("name").equal(name),
						q.criteria("ancestors").contains(name)));
		ds.delete(q);
	}

	public void cleanCategories(String tenant){
		if (!(tenant==null)){
			ds.delete(ds.createQuery(CategoryOLD.class));
		}
	}	
}

