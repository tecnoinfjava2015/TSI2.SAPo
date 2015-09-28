package com.bl;

import java.util.List;

import com.entities.mongo.Category;
import com.entities.mongo.dao.CategoryDAO;
import com.services.interfaces.CategoryBLLocal;
import com.services.interfaces.CategoryBLRemote;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class Category
 */
@Stateless
public class CategoryBL implements CategoryBLRemote, CategoryBLLocal {

    /**
     * Default constructor. 
     */
	protected CategoryDAO dao = new CategoryDAO();
	
    public CategoryBL() {
    }

	@Override
	public Category create(Category category) {
		return dao.getById(dao.persist(category));
	}

	@Override
	public void remove(String tenant, String name) {
		dao.remove(tenant, name);		
	}

	@Override
	public Category update(Category category) {
		Category aux = dao.getById(category.getId());
		if (aux.getName()==category.getName()) {
			return dao.getById(dao.persist(category));			
		}
		return null;
	}

	@Override
	public Category getByName(String tenant, String name) {	
		return dao.getByName(tenant, name);
	}

	@Override
	public List<Category> getAll(String tenant) {
		return dao.getAllCategories(tenant);
	}

}
