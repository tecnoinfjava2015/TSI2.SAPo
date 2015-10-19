package com.bl;

import java.util.List;

import com.entities.mongo.ProductOLD;
import com.entities.mongo.dao.ProductDAOOLD;
import com.services.interfaces.ProductBLLocalOLD;
import com.services.interfaces.ProductBLRemoteOLD;

import javax.ejb.Stateless;

/**
 * Session Bean implementation class ProductBL
 */
@Stateless
public class ProductOLDBL implements ProductBLRemoteOLD, ProductBLLocalOLD {

	protected ProductDAOOLD dao = new ProductDAOOLD();
    /**
     * Default constructor. 
     */
    public ProductOLDBL() {
        // TODO Auto-generated constructor stub
    }
    
	@Override
	public ProductOLD getByName(String tenant, String name) {
		return dao.getByName(tenant, name);
	}

	@Override
	public List<ProductOLD> getProductsByCategory(String tenant, String name) {
		return dao.getProductsByCategory(tenant, name);
	}
	
	@Override
	public ProductOLD create(ProductOLD prodAux) {		
		return dao.getById(dao.insert(prodAux));
	}

	
	
	@Override
	public void delete(String name, String tenant) {
		dao.deleteProduct(name, tenant);		
	}
}
