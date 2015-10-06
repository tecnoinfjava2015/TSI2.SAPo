package com.dal.test.mongo;

import static org.junit.Assert.*;

import org.bson.types.ObjectId;
import org.junit.Test;

import com.entities.mongo.Product;
import com.entities.mongo.dao.ProductDAO;
import com.mongo.utilities.GenericPersistence;
import com.mongo.utilities.MongoDB;

public class ProductTest {

	protected MongoDB persistence;
	protected GenericPersistence genericPersistence = new GenericPersistence();
	protected ProductDAO proDAO = new ProductDAO();
	@Test
	public void persist() {
		if(!proDAO.isEmpty("TePro"))
			proDAO.cleanProducts("TePro");
		
		Product product = new Product();
		product.setDescName("Test product");
		product.setUniqueName("TetemplatePro");
		product.setTenant("TePro");
		ObjectId id1 = genericPersistence.persist(product);
		assertNotNull("Debería haberse generado un ObjectId cuando se persistio la entidad", id1);
		assertEquals("El valor existente en la base y el generado deberían ser el mismo",
				product.getId(), id1);
	}

	/**
	 * Check that all documents in a collection are counted correctly.
	 */
	@Test
	public void count() {
		proDAO.cleanProducts("TePro");
		assertEquals("En una base vacía deberíamos tener 0 productos.", 0,
				genericPersistence.count(Product.class));

		Product product = new Product();
		product.setDescName("Test product");
		product.setUniqueName("TetemplatePro");
		product.setTenant("TePro");
		genericPersistence.persist(product);

		assertEquals("Después de haber agregado un producto debería haber uno", 1,
				genericPersistence.count(Product.class));
	}
}
