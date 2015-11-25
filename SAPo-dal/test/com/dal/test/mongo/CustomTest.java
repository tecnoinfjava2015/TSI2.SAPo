package com.dal.test.mongo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import com.entities.mongo.Category;
import com.entities.mongo.Product;
import com.entities.mongo.dao.CategoryDAO;
import com.entities.mongo.dao.ProductDAO;
import com.mongo.utilities.GenericPersistence;
import com.mongo.utilities.MongoDB;

public class CustomTest {

	protected MongoDB persistence;
	protected GenericPersistence genericPersistence = new GenericPersistence();
	protected ProductDAO prodDAO = new ProductDAO();
	protected CategoryDAO catDAO = new CategoryDAO();
	@Test
	public void persist() {

		Category cat10 = new Category(667, 665, "BigBox", "Audio", true, "stars");
		catDAO.persist(cat10);
//		Category cat10 = catDAO.getByLocalId(665, 667);
		
		
		List<Category> listCat14 = new ArrayList<>();//Audio
		listCat14.add(cat10);
		
		//Prod 1
		Product product = new Product();
		product.setName("Test 1");
		product.setBarCode("00000001");
		product.setVirtualStorageId(665);
		product.setCategories(listCat14);
		product.setStock(5);
		ObjectId id1 = prodDAO.insert(product);
		
		//prodDAO.deleteProduct(id1);
	}
}