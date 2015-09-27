package com.dal.test.mongo;

import static org.junit.Assert.*;

import org.bson.types.ObjectId;
import org.junit.Test;

import com.entities.mongo.Category;
import com.entities.mongo.dao.CategoryDAO;
import com.mongo.utilities.GenericPersistence;
import com.mongo.utilities.MongoDB;

public class CategoryTest {

	 protected MongoDB persistence;
	  protected GenericPersistence genericPersistence = new GenericPersistence();
	  protected CategoryDAO catDAO = new CategoryDAO();
	  @Test
	  public void persist() {
		catDAO.cleanCategories("T1");
	    Category category = new Category();
	    category.setName("Test category");
	    category.setTenant("T1");
	    ObjectId id1 = genericPersistence.persist(category);
	    assertNotNull("Debería haberse generado un ObjectId cuando se persistio la entidad", id1);
	    assertEquals("El valor existente en la base y el generado deberían ser el mismo",
	    		category.getId(), id1);
	    }

	  /**
	   * Check that all documents in a collection are counted correctly.
	   */
	  @Test
	  public void count() {
		  catDAO.cleanCategories("T1");
	    assertEquals("En una base vacía deberíamos tener 0 categorías.", 0,
			 genericPersistence.count(Category.class));
	    
	    Category category = new Category();
	    category.setName("Test category");
	    category.setTenant("T1");
	    genericPersistence.persist(category);
	 
	    assertEquals("Después de haber agregado una categoría debería haber 1", 1,
			 genericPersistence.count(Category.class));
	  }
}
