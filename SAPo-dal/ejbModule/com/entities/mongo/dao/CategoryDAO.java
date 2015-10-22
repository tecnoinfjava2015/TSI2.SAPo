package com.entities.mongo.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.entities.mongo.Category;
import com.mongo.utilities.GenericPersistence;
import com.mongo.utilities.MongoDB;

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

	public long countAll() {
		return dao.count(Category.class);
	}
	
	public long countVirtualStorage(long virtualStorageId) {
		Query<Category> query = ds.createQuery(Category.class);
		query.criteria("virtualStorageId").equal(virtualStorageId);
		return query.countAll();
	}	

	public Category getById(ObjectId id) {
		return dao.get(Category.class, id);
	}

	public Category getByName(long virtualStorageId, String name) {
		if (!(virtualStorageId > 0) || (name.isEmpty())) {
			return null;
		}

		Query<Category> query = ds.createQuery(Category.class);

		query.and(query.criteria("name").equal(name),
				query.criteria("virtualStorageId").equal(virtualStorageId));

		return query.get();

	}

	public List<Category> getAllCategories(long virtualStorageId) {
		if (!(virtualStorageId < 0)) {
			return null;
		}

		return ds.find(Category.class).field("virtualStorageId")
				.equal(virtualStorageId).asList();
	}

	public void remove(ObjectId id) {
		dao.remove(Category.class, id);
	}

	public void cleanCategories(long virtualStorageId) {
		if (!(virtualStorageId < 0)) {
			ds.delete(ds.createQuery(Category.class));
		}
	}
}
