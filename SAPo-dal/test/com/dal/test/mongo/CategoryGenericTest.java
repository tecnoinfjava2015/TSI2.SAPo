package com.dal.test.mongo;


import org.bson.types.ObjectId;
import org.junit.Test;

import com.entities.mongo.GenericCategory;

import com.entities.mongo.dao.GenericCategoryDAO;
import com.mongo.utilities.GenericPersistence;
import com.mongo.utilities.MongoDB;

public class CategoryGenericTest {

	
		protected MongoDB persistence;
		protected GenericPersistence genericPersistence = new GenericPersistence();
		protected GenericCategoryDAO dao = new GenericCategoryDAO();
		@Test
		public void persist() {
//			if(dao.countVirtualStorage(1)>0)
//				dao.cleanProducts(1);
			
		//Prod 1
			GenericCategory Gcategory = new GenericCategory();
			Gcategory.setName("Test 1");
			Gcategory.setIcon("Icon 1");
			Gcategory.setDescription("description");
			ObjectId id1 = genericPersistence.persist(Gcategory);
			
			Gcategory = new GenericCategory();
			Gcategory.setName("Test 2");
			Gcategory.setIcon("Icon 1");
			Gcategory.setDescription("description2");
			id1 = genericPersistence.persist(Gcategory);
			
			Gcategory = new GenericCategory();
			Gcategory.setName("Test 3");
			Gcategory.setIcon("Icon 1");
			Gcategory.setDescription("description3");
			id1 = genericPersistence.persist(Gcategory);
			
			Gcategory = new GenericCategory();
			Gcategory.setName("Test 4");
			Gcategory.setIcon("Icon 1");
			Gcategory.setDescription("description4");
			id1 = genericPersistence.persist(Gcategory);
			
			Gcategory = new GenericCategory();
			Gcategory.setName("Test 5");
			Gcategory.setIcon("Icon 1");
			Gcategory.setDescription("description5");
			id1 = genericPersistence.persist(Gcategory);
			
//			assertNotNull("Deber�a haberse generado un ObjectId cuando se persistio la entidad", id1);
//			assertEquals("El valor existente en la base y el generado deber�an ser el mismo",
//					product.getId(), id1);
//			
		}
		
	}
