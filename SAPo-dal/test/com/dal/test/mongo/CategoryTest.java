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
	    assertNotNull("Deber�a haberse generado un ObjectId cuando se persistio la entidad", id1);
	    assertEquals("El valor existente en la base y el generado deber�an ser el mismo",
	    		category.getId(), id1);
	    }

	  /**
	   * Check that all documents in a collection are counted correctly.
	   */
	  @Test
	  public void count() {
		  catDAO.cleanCategories("T1");
	    assertEquals("En una base vac�a deber�amos tener 0 categor�as.", 0,
			 genericPersistence.count(Category.class));
	    
	    Category category = new Category();
	    category.setName("Test category");
	    category.setTenant("T1");
	    genericPersistence.persist(category);
	 
	    assertEquals("Despu�s de haber agregado una categor�a deber�a haber 1", 1,
			 genericPersistence.count(Category.class));
	  }
}
