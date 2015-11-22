package com.services.interfaces;

import java.util.List;

import com.entities.mongo.Category;
import com.entities.mongo.Product;

public interface ICategoryBL {
	public Category createCategory(Category category);

	public Category updateCategory(Category category);

	public Category getCategory(long virtualStorageId, int id);

	public void deleteCategory(long virtualStorageId, int id);

	public List<Category> getAllCategories(long virtualStorageId, int offset, int limit);
	
	public List<Category> getAllCategories(long virtualStorageId);
	
	public List<Category> getAllStarredCategories(long virtualStorageId, int offset, int limit);

	public boolean estaEnLista(Category ct, List<Category> listaCategoriasTemp);
	
	public List<Category> getAllCategories();

	public void deleteCategoryOfName(String value);
	
	public Boolean estaCategoria(long virtualStorageId, String name);

}
