package com.entities.mongo.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.entities.mongo.Alert;
import com.entities.mongo.Category;
import com.entities.mongo.GenericCategory;
import com.entities.mongo.Spec;
import com.mongo.utilities.GenericPersistence;
import com.mongo.utilities.MongoDB;
import com.mongo.utilities.Utils;

public class GenericCategoryDAO {

	private final Datastore ds;
	private final GenericPersistence dao;
	
	public GenericCategoryDAO() {
		ds = MongoDB.instance().getDatabase();
		dao = new GenericPersistence();
	}
	
	public ObjectId insert(GenericCategory gCategory) {
		return dao.persist(gCategory);
	}

	public GenericCategory getById(ObjectId id){
		return dao.get(GenericCategory.class, id);
	}
	
	public long countAll(Class<GenericCategory> gCategory) {
		return dao.count(gCategory);
	}
	
	public GenericCategory getByName(String categoryName){
		if ((categoryName.isEmpty())) {
			return null;
		}	
		
		Query<GenericCategory> query = ds.createQuery(GenericCategory.class);
		query.and(query.criteria("name").equal(categoryName));
		return query.get();
	}
	
		
	
	public List<GenericCategory> getAllCategory(){
		Query<GenericCategory> query = ds.createQuery(GenericCategory.class);
		return query.asList();
	}

	public void deleteGenericCategory(ObjectId id) {
		dao.remove(GenericCategory.class, id);
	}

	public Boolean estaCategoria(long virtualStorageId, String name) {
		Query<Category> query = ds.createQuery(Category.class);
		query.and(query.criteria("name").equal(name), 
				query.criteria("virtualStorageId").equal(virtualStorageId));
		List<Category> auxList = query.asList();
		if (auxList.size() == 0) return false;
		return true;
	}
}