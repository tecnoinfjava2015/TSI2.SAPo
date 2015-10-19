package com.bl;

import java.util.List;

import com.entities.mongo.CategoryOLD;
import com.entities.mongo.dao.CategoryDAOOLD;
import com.services.interfaces.CategoryBLLocalOLD;
import com.services.interfaces.CategoryBLRemoteOLD;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class Category
 */
@Stateless
public class CategoryOLDBL implements CategoryBLRemoteOLD, CategoryBLLocalOLD {

    /**
     * Default constructor. 
     */
	protected CategoryDAOOLD dao = new CategoryDAOOLD();
	
    public CategoryOLDBL() {
    }

	@Override
	public CategoryOLD create(CategoryOLD category) {
		return dao.getById(dao.persist(category));
	}

	@Override
	public void remove(String tenant, String name) {
		dao.remove(tenant, name);		
	}

	@Override
	public CategoryOLD update(CategoryOLD category) {
		CategoryOLD aux = dao.getById(category.getId());
		if (aux.getName()==category.getName()) {
			return dao.getById(dao.persist(category));			
		}
		return null;
	}

	@Override
	public CategoryOLD getByName(String tenant, String name) {	
		return dao.getByName(tenant, name);
	}

	@Override
	public List<CategoryOLD> getAll(String tenant) {
		return dao.getAllCategories(tenant);
	}

}
