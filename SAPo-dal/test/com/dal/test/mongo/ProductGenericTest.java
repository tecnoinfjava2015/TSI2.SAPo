package com.dal.test.mongo;

import org.bson.types.ObjectId;
import org.junit.Test;

import com.entities.mongo.GenericProduct;
import com.entities.mongo.dao.GenericProductDAO;
import com.mongo.utilities.GenericPersistence;
import com.mongo.utilities.MongoDB;

public class ProductGenericTest {
	protected MongoDB persistence;
	protected GenericPersistence genericPersistence = new GenericPersistence();
	protected GenericProductDAO dao = new GenericProductDAO();
	@Test
	public void persist() {
//		if(dao.countVirtualStorage(1)>0)
//			dao.cleanProducts(1);
		
	//Prod 1
		GenericProduct Gproduct = new GenericProduct();
		Gproduct.setName("Test 1");
		Gproduct.setBarcode("11234567890");
		Gproduct.setDescription("description");
		ObjectId id1 = genericPersistence.persist(Gproduct);
		
		Gproduct = new GenericProduct();
		Gproduct.setName("Test 2");
		Gproduct.setBarcode("1123456789");
		Gproduct.setDescription("description2");
		id1 = genericPersistence.persist(Gproduct);
		
		Gproduct = new GenericProduct();
		Gproduct.setName("Test 3");
		Gproduct.setBarcode("112345678");
		Gproduct.setDescription("description3");
		id1 = genericPersistence.persist(Gproduct);
		
		Gproduct = new GenericProduct();
		Gproduct.setName("Test 4");
		Gproduct.setBarcode("11234567");
		Gproduct.setDescription("description4");
		id1 = genericPersistence.persist(Gproduct);
		
		Gproduct = new GenericProduct();
		Gproduct.setName("Test 5");
		Gproduct.setBarcode("1123456");
		Gproduct.setDescription("description5");
		id1 = genericPersistence.persist(Gproduct);
		
//		assertNotNull("Deber�a haberse generado un ObjectId cuando se persistio la entidad", id1);
//		assertEquals("El valor existente en la base y el generado deber�an ser el mismo",
//				product.getId(), id1);
//		
	}
	
}
