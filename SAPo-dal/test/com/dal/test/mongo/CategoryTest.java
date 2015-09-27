package com.dal.test.mongo;

import static org.junit.Assert.*;

import org.bson.types.ObjectId;
import org.junit.Test;

import com.entities.mongo.Category;
import com.mongo.utilities.GenericPersistence;
import com.mongo.utilities.MongoDB;

public class CategoryTest {

	 protected MongoDB persistence;
	  protected GenericPersistence genericPersistence = new GenericPersistence();
	  @Test
	  public void persist() {
	    Category category = new Category();
	    category.setName("Test category");
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
	    assertEquals("En una base vacía deberíamos tener 0 categorías.", 0,
			 genericPersistence.count(Category.class));
	    
	    Category category = new Category();
	    category.setName("Test category");
	    genericPersistence.persist(category);
	 
	    assertEquals("Después de haber agregado una categoría debería haber 1", 1,
			 genericPersistence.count(Category.class));
	  }
}
