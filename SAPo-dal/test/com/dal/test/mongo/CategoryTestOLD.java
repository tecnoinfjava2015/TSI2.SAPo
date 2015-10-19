package com.dal.test.mongo;

import static org.junit.Assert.*;

import org.bson.types.ObjectId;
import org.junit.Test;

import com.entities.mongo.CategoryOLD;
import com.entities.mongo.dao.CategoryDAOOLD;
import com.mongo.utilities.GenericPersistence;
import com.mongo.utilities.MongoDB;

public class CategoryTestOLD {

	 protected MongoDB persistence;
	  protected GenericPersistence genericPersistence = new GenericPersistence();
	  protected CategoryDAOOLD catDAO = new CategoryDAOOLD();
	  @Test
	  public void persist() {
		catDAO.cleanCategories("T1");
	    CategoryOLD category = new CategoryOLD();
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
			 genericPersistence.count(CategoryOLD.class));
	    
	    CategoryOLD category = new CategoryOLD();
	    category.setName("Test category");
	    category.setTenant("T1");
	    genericPersistence.persist(category);
	 
	    assertEquals("Despu�s de haber agregado una categor�a deber�a haber 1", 1,
			 genericPersistence.count(CategoryOLD.class));
	  }
}
