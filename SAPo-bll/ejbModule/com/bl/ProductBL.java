package com.bl;

import java.util.List;

import com.entities.mongo.Category;
import com.entities.mongo.Product;
import com.entities.mongo.dao.ProductDAO;
import com.services.interfaces.ProductBLLocal;
import com.services.interfaces.ProductBLRemote;

import javax.ejb.Stateless;

/**
 * Session Bean implementation class ProductBL
 */
@Stateless
public class ProductBL implements ProductBLRemote, ProductBLLocal {

	protected ProductDAO dao = new ProductDAO();
    /**
     * Default constructor. 
     */
    public ProductBL() {
        // TODO Auto-generated constructor stub
    }
    
	@Override
	public Product getByName(String name) {
		return dao.getByName(name);
	}

	@Override
	public List<Product> getProductsByCategory(Category catAux) {
		return dao.getProductsByCategory(catAux);
	}
	
	@Override
	public Product create(Product prodAux) {		
		return dao.getById(dao.insert(prodAux));
	}

	
	
	@Override
	public void delete(String name, String tenant) {
		dao.deleteProduct(name, tenant);		
	}
}