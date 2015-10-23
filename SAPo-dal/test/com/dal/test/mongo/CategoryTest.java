package com.dal.test.mongo;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Test;

import com.entities.mongo.Category;
import com.entities.mongo.Product;
import com.entities.mongo.dao.CategoryDAO;
import com.entities.mongo.dao.ProductDAO;
import com.mongo.utilities.GenericPersistence;
import com.mongo.utilities.MongoDB;

public class CategoryTest {
	protected MongoDB persistence;
	protected GenericPersistence genericPersistence = new GenericPersistence();
	protected CategoryDAO dao = new CategoryDAO();
	@Test
	public void persist() {
		if(dao.countVirtualStorage(1)<=0)
			dao.cleanCategories(1);
		
		Category cat1 = new Category(1, 1, null, "Cat 1", true, "stars");
		dao.persist(cat1); 
		Category cat2 = new Category(2, 1, null, "Cat 2", true, "accessibility");
		dao.persist(cat2); 
		Category cat3 = new Category(3, 1, null, "Cat 3", false, "account_balance");
		dao.persist(cat3); 
		Category cat4 = new Category(4, 1, null, "Cat 4", false, "backup");
		dao.persist(cat4); 
		Category cat5 = new Category(5, 1, null, "Cat 5", false, "android");
		dao.persist(cat5); 
		Category cat6 = new Category(6, 1, null, "Cat 6", false, "build");
		dao.persist(cat6); 
		Category cat7 = new Category(7, 1, null, "Cat 7", true, "alarm");
		dao.persist(cat7); 
		
		
	}
}
