package com.services.interfaces;

import java.util.List;
import javax.ejb.Remote;
import com.entities.mongo.ProductOLD;

@Remote
public interface ProductBLRemoteOLD {
	public ProductOLD getByName(String tenant, String name);
	public List<ProductOLD> getProductsByCategory(String tenant, String name);
	public ProductOLD create(ProductOLD prodAux);
	
	public void delete(String name, String tenant);
}
