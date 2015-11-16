/**
 * 
 */
package com.entities.mongo.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.entities.mongo.Alert;
import com.entities.mongo.Category;
import com.entities.mongo.Product;
import com.entities.mongo.Spec;
import com.mongo.utilities.GenericPersistence;
import com.mongo.utilities.MongoDB;
import com.mongo.utilities.Utils;

/**
 * @author jpmartinez
 *
 */
public class ProductDAO {
	private final Datastore ds;
	private final GenericPersistence dao;
	
	public ProductDAO() {
		ds = MongoDB.instance().getDatabase();
		dao = new GenericPersistence();
	}
	
	public ObjectId insert(Product product) {
		return dao.persist(product);
	}

	public long countAll(Class<Product> product) {
		return dao.count(product);
	}
	
	public long countVirtualStorage (long virtualStorageId){
		Query<Product> query = ds.createQuery(Product.class);
		query.criteria("virtualStorageId").equal(virtualStorageId);
		return query.countAll();
	}

	public Product getById(ObjectId id){
		return dao.get(Product.class, id);
	}
		
	public Product getByName(long virtualStorageId, String name){
		if ((name.isEmpty()) || (!(virtualStorageId > 0 ))) {
			return null;
		}	
		
		Query<Product> query = ds.createQuery(Product.class);
		query.and(query.criteria("name").equal(name), 
				query.criteria("virtualStorageId").equal(virtualStorageId)
				);
		return query.get();
	}
	
	public Product getByBarCode(long virtualStorageId, String barCode){
		if ((barCode.isEmpty()) || (!(virtualStorageId > 0 ))) {
			return null;
		}	
		
		Query<Product> query = ds.createQuery(Product.class);
		query.and(query.criteria("barCode").equal(barCode), 
				query.criteria("virtualStorageId").equal(virtualStorageId)
				);
		return query.get();
	}	
	
	public List<Product> getByVirtualStorage(long virtualStorageId, int offset, int limit){
		if (!(virtualStorageId > 0)) {
			return null;
		}	

		Query<Product> query = ds.createQuery(Product.class);
		query.criteria("virtualStorageId").equal(virtualStorageId);
		query.offset(offset).limit(limit);
		return query.asList();
		
	}

	public List<Product> getProductsByCategory(long virtualStorageId, String categoryName, int offset, int limit){
		if ( !(virtualStorageId > 0) || (categoryName.isEmpty())) {
			return null;
		}
		
		Query<Product> query = ds.createQuery(Product.class);
		
		query.and(query.criteria("categories").containsIgnoreCase(categoryName), 
				query.criteria("virtualStorageId").equal(virtualStorageId)
				);
		query.offset(offset).limit(limit);
		return query.asList();
	}

	public List<Product> getProductsByCategories(long virtualStorageId, List<Integer> categories, int offset, int limit){
		if ( !(virtualStorageId > 0) || (categories.isEmpty())) {
			return null;
		}
		
		Query<Product> query = ds.createQuery(Product.class);
		for (int cat : categories) {
			query.and(query.criteria("categories.localId").equal(cat));
		}
		query.and(query.criteria("virtualStorageId").equal(virtualStorageId));
		query.offset(offset).limit(limit);
		return query.asList();
	}	
	
	public List<Product> getProductsByCategoriesOr(long virtualStorageId, List<Integer> categories, int offset, int limit){
		if ( !(virtualStorageId > 0) || (categories.isEmpty())) {
			return null;
		}
		
		Query<Product> query = ds.createQuery(Product.class);
		query.and(query.criteria("virtualStorageId").equal(virtualStorageId));
		
		for (int cat : categories) {
			query.or(query.criteria("categories.localId").equal(cat));
		}
		
		query.offset(offset).limit(limit);
		return query.asList();
	}	

	public void deleteProduct(ObjectId id){
		dao.remove(Product.class, id);
	}
	
	public void cleanProducts(long virtualStorageId){
		if (virtualStorageId>0){
			ds.delete(ds.createQuery(Product.class));
		}
	}
	
	public ObjectId removeImage(Product product, int position){
		if (position>0 && !(product==null)){
			List <String> images = product.getImages();
			images.remove(position);
			product.setImages(images);
			return dao.persist(product);
		}
		return null;
	}
	
	public ObjectId removeSpec(Product product, String name){
		if (!(product == null) && !(name.isEmpty())){
			List<Spec> specs = product.getSpecs();
			int position =new Utils().getSpecPosition(specs, name);
			if(position>0){
				specs.remove(position);
				product.setSpecs(specs);
				return dao.persist(product);		
			}
			return null;
		}
		return null;
	}
	public ObjectId removeAlert(Product product, String name){
		if (!(product == null) && !(name.isEmpty())){
			List<Alert> alerts = product.getAlerts();
			int position =new Utils().getAlertPosition(alerts, name);
			if(position>0){
				alerts.remove(position);
				product.setAlerts(alerts);
				return dao.persist(product);		
			}
			return null;
		}
		return null;
	}
	
	public ObjectId removeCategory(Product product, Category category){
		if (!(product==null)&&!(category==null)){
			List<Category> categories = product.getCategories();
			categories.remove(category);
			product.setCategories(categories);
			return dao.persist(product);
		}
		return  null;
	}
	
	public List<Product> stockLessOrEqualThan(double stockMax, long virtualStorageId, int limit){
		Query<Product> query = ds.createQuery(Product.class);
		query.and(query.criteria("stock").lessThanOrEq(stockMax), query.criteria("virtualStorageId").equal(virtualStorageId));
		query.limit(limit);
		return query.asList();
	}
	
	public void updateStock(double change, long virtualStorageId, String barCode){
		if(change != 0 && virtualStorageId > 0 && barCode != null){
		Query<Product> query = ds.createQuery(Product.class);
		query.and(query.criteria("barCode").equal(barCode), 
				query.criteria("virtualStorageId").equal(virtualStorageId));
		if(change<0) change = 0;
		UpdateOperations<Product> updateOp = ds.createUpdateOperations(Product.class).set("stock", change);
		ds.update(query, updateOp);
		}
	}
	
	public double getVSValue(long virtualStorageId){
		Query<Product> query = ds.createQuery(Product.class);
		query.criteria("virtualStorageId").equal(virtualStorageId).criteria("stock").greaterThan(0);
		List<Product> auxList = query.asList();
		double value = 0;
		for( Product prodAux : auxList ){
			value = value + (prodAux.getSalePrice() * prodAux.getStock());
		}
		return value;
	}
}
