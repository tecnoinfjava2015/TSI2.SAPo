package com.entities.mongo.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.entities.mongo.CategoryOLD;
import com.entities.mongo.ProductTemplateOLD;
import com.mongo.utilities.MongoDB;
import com.mongo.utilities.GenericPersistence;

public class ProductTemplateDAOOLD {
	private final Datastore ds;
	private final GenericPersistence dao;

	public ProductTemplateDAOOLD() {
		ds = MongoDB.instance().getDatabase();
		dao = new GenericPersistence();
	}

	public ObjectId insert(ProductTemplateOLD pt) {
		return dao.persist(pt);
	}

	public long count(Class<ProductTemplateOLD> pt) {
		return dao.count(pt);
	}

	public ProductTemplateOLD getById(ObjectId id){
		return dao.get(ProductTemplateOLD.class, id);
	}

	public ProductTemplateOLD getByName(String name){// *ver*
		if ((name == null)) {
			return null;
		}

		Query<ProductTemplateOLD> query = ds.createQuery(ProductTemplateOLD.class);

		query.criteria("uniqueName").equal(name);

		return query.get();

	}

	public List<ProductTemplateOLD> getTemplatesOfCategory(CategoryOLD cat){ // *ver*
		if (cat==null){
			return null;
		}

		return ds.find(ProductTemplateOLD.class).field("category").equal(cat).asList(); // *ver* 27/09
	}

	public void deleteProductTemplate(String name){
		if (!(name!=null)){
			Query<ProductTemplateOLD> query = ds.createQuery(ProductTemplateOLD.class);
			ds.delete(query.criteria("uniqueName").equal(name)); // *ver* 27/09
		}
	}
}
