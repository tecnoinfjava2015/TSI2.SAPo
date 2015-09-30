package com.services.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.entities.mongo.Category;
import com.entities.mongo.Product;

@Local
public interface ProductBLLocal {
	public List<Product> getProductsOfCategory(Category catAux);
	public Product create(Product prodAux);
	
	public void delete(String name, String tenant);
}
