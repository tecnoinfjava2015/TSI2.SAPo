package com.services.interfaces;

import java.util.List;
import javax.ejb.Local;
import com.entities.mongo.ProductOLD;

@Local
public interface ProductBLLocalOLD {
	public ProductOLD getByName(String tenant, String name);
	public List<ProductOLD> getProductsByCategory(String tenant, String name);
	public ProductOLD create(ProductOLD prodAux);
	
	public void delete(String name, String tenant);
}
