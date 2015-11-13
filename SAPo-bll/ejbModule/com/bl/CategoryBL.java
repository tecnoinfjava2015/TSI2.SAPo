package com.bl;

import java.util.List;

import org.bson.types.ObjectId;

import com.entities.mongo.Category;
import com.entities.mongo.Product;
import com.entities.mongo.dao.CategoryDAO;
import com.services.interfaces.ICategoryBL;

public class CategoryBL implements ICategoryBL {

	private final CategoryDAO dao = new CategoryDAO();

	public CategoryBL() {
	}	
	
	@Override
	public Category createCategory(Category category) {
		if(!(category==null)){
			ObjectId categoryId = dao.persist(category);
			return dao.getById(categoryId);
		}
		return null; 
	} 

	@Override
	public Category updateCategory(Category category) {
		if(!(category==null)){
			Category catAux = dao.getByName(category.getVirtualStorageId(), category.getName());
			category.setId(catAux.getId());
			ObjectId catId = dao.persist(category);
			return dao.getById(catId);
		}			
		return null;
	}

	@Override
	public Category getCategory(long virtualStorageId, int id) {
		if((virtualStorageId>0) && (id>0)){
			return dao.getByLocalId(virtualStorageId, id);
		}
		return null;
	}

	@Override
	public void deleteCategory(long virtualStorageId, int id) {
		if((virtualStorageId>0) && (id>0)){
			dao.remove(dao.getByLocalId(virtualStorageId, id).getId());
		}
	}

	@Override
	public List<Category> getAllCategories(long virtualStorageId,int offset, int limit) {
		if(virtualStorageId>0){
			return dao.getAllCategories(virtualStorageId,offset,limit);
		}
		return null;
	}
	
	@Override
	public List<Category> getAllStarredCategories(long virtualStorageId,int offset, int limit) {
		if(virtualStorageId>0){
			return dao.getAllStarredCategories(virtualStorageId,offset,limit);
		}
		return null;
	}

	@Override
	public List<Category> getAllCategories(long virtualStorageId) {
		if(virtualStorageId>0){
			return dao.getAllCategories(virtualStorageId);
		}
		return null;
	}
	
	@Override
	public boolean estaEnLista(Category c, List<Category> listaCtegoriasTemp) {
		for(Category ct : listaCtegoriasTemp){
			if (ct.getName().equals(c.getName()))return true;
		}
		return false;
	}

	@Override
	public List<Category> getAllCategories() {
			return dao.getAllCategories();
	}

	@Override
	public void deleteCategoryOfName(String name) {
		dao.remove(dao.getByName(name).getId());
	}
}
