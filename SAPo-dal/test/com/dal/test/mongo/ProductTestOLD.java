package com.dal.test.mongo;

import static org.junit.Assert.*;

import org.bson.types.ObjectId;
import org.junit.Test;

import com.entities.mongo.ProductOLD;
import com.entities.mongo.dao.ProductDAOOLD;
import com.mongo.utilities.GenericPersistence;
import com.mongo.utilities.MongoDB;

public class ProductTestOLD {

	protected MongoDB persistence;
	protected GenericPersistence genericPersistence = new GenericPersistence();
	protected ProductDAOOLD proDAO = new ProductDAOOLD();
	@Test
	public void persist() {
		if(!proDAO.isEmpty("TePro"))
			proDAO.cleanProducts("TePro");
		
		ProductOLD product = new ProductOLD();
		product.setDescName("Test product");
		product.setUniqueName("TetemplatePro");
		product.setTenant("TePro");
		ObjectId id1 = genericPersistence.persist(product);
		assertNotNull("Deber�a haberse generado un ObjectId cuando se persistio la entidad", id1);
		assertEquals("El valor existente en la base y el generado deber�an ser el mismo",
				product.getId(), id1);
	}

	/**
	 * Check that all documents in a collection are counted correctly.
	 */
	@Test
	public void count() {
		proDAO.cleanProducts("TePro");
		assertEquals("En una base vac�a deber�amos tener 0 productos.", 0,
				genericPersistence.count(ProductOLD.class));

		ProductOLD product = new ProductOLD();
		product.setDescName("Test product");
		product.setUniqueName("TetemplatePro");
		product.setTenant("TePro");
		genericPersistence.persist(product);

		assertEquals("Despu�s de haber agregado un producto deber�a haber uno", 1,
				genericPersistence.count(ProductOLD.class));
	}
}
