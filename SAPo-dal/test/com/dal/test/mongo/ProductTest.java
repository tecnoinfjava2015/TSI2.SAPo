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
import com.entities.mongo.dao.ProductDAO;
import com.mongo.utilities.GenericPersistence;
import com.mongo.utilities.MongoDB;

public class ProductTest {

	protected MongoDB persistence;
	protected GenericPersistence genericPersistence = new GenericPersistence();
	protected ProductDAO dao = new ProductDAO();
	@Test
	public void persist() {
		if(dao.countVirtualStorage(1)>0)
			dao.cleanProducts(1);
		
		Category cat1 = new Category(1, 1, null, "Cat 1", false, null);
		Category cat2 = new Category(2, 1, null, "Cat 2", false, null);
		Category cat3 = new Category(3, 1, null, "Cat 3", false, null);
		Category cat4 = new Category(4, 1, null, "Cat 4", false, null);
		Category cat5 = new Category(5, 1, null, "Cat 5", false, null);
		Category cat6 = new Category(6, 1, null, "Cat 6", false, null);
		Category cat7 = new Category(7, 1, null, "Cat 7", false, null);
		
		List<Category> listCat1 = new ArrayList<>();
		List<Category> listCat2 = new ArrayList<>(); 

		listCat1.add(cat1);
		listCat1.add(cat2);
		

		listCat2.add(cat3);
		listCat2.add(cat5);
		listCat2.add(cat7);
		
		
		//Prod 1
		Product product = new Product();
		product.setName("Test 1");
		product.setBarCode("1.1234567890");
		product.setVirtualStorageId(1);
		product.setCategories(listCat1);
		ObjectId id1 = genericPersistence.persist(product);
		
		product = new Product();
		product.setName("Test 2");
		product.setBarCode("2.0987654321");
		product.setVirtualStorageId(1);
		product.setCategories(listCat2);
		id1 = genericPersistence.persist(product);
		
		product = new Product();
		product.setName("Test 3");
		product.setBarCode("3.0987654321");
		product.setVirtualStorageId(1);
		product.setCategories(listCat2);
		id1 = genericPersistence.persist(product);

		product = new Product();
		product.setName("Test 4");
		product.setBarCode("4.0987654321");
		product.setVirtualStorageId(1);
		product.setCategories(listCat2);
		id1 = genericPersistence.persist(product);
		
		product = new Product();
		product.setName("Test 5");
		product.setBarCode("5.0987654321");
		product.setVirtualStorageId(1);
		id1 = genericPersistence.persist(product);		
			
	}
	@Test 
	public void search(){
		dao.getProductsBarCodeAndName(1, "test", 5);
	}
	
}
