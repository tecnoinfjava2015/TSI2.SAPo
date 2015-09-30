package com.services.interfaces;

import java.util.List;

import javax.ejb.Remote;

import com.entities.mongo.Category;
import com.entities.mongo.Product;

@Remote
public interface ProductBLRemote {
	public List<Product> getProductsOfCategory(Category catAux);
	public Product create(Product prodAux);
	
	public void delete(String name, String tenant);
}
