package com.entities.mongo.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.entities.mongo.Category;
import com.entities.mongo.ProductTemplate;
import com.mongo.utilities.MongoDB;
import com.mongo.utilities.GenericPersistence;

public class ProductTemplateDAO {
	private final Datastore ds;
	private final GenericPersistence dao;

	public ProductTemplateDAO() {
		ds = MongoDB.instance().getDatabase();
		dao = new GenericPersistence();
	}

	public ObjectId insert(ProductTemplate pt) {
		return dao.persist(pt);
	}

	public long count(Class<ProductTemplate> pt) {
		return dao.count(pt);
	}

	public ProductTemplate getById(ObjectId id){
		return dao.get(ProductTemplate.class, id);
	}

	public ProductTemplate getByName(String name){// *ver*
		if ((name == null)) {
			return null;
		}

		Query<ProductTemplate> query = ds.createQuery(ProductTemplate.class);

		query.criteria("uniqueName").equal(name);

		return query.get();

	}

	public List<ProductTemplate> getTemplatesOfCategory(Category cat){ // *ver*
		if (cat==null){
			return null;
		}

		return ds.find(ProductTemplate.class).field("category").equal(cat).asList(); // *ver* 27/09
	}

	public void deleteProductTemplate(String name){
		if (!(name!=null)){
			Query<ProductTemplate> query = ds.createQuery(ProductTemplate.class);
			ds.delete(query.criteria("uniqueName").equal(name)); // *ver* 27/09
		}
	}
}
