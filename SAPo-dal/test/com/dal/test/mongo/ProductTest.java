package com.dal.test.mongo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.bson.types.ObjectId;
import org.junit.Test;

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
		if(dao.countVirtualStorage(1)<=0)
			dao.cleanProducts(1);
		
		Product product = new Product();
		product.setName("Prueba");
		product.setVirtualStorageId(1);
		ObjectId id1 = genericPersistence.persist(product);
		assertNotNull("Deber�a haberse generado un ObjectId cuando se persistio la entidad", id1);
		assertEquals("El valor existente en la base y el generado deber�an ser el mismo",
				product.getId(), id1);
		
	}
	
}
