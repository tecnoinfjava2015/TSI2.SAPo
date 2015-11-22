package com.services.interfaces;

import java.util.List;

import com.entities.mongo.GenericCategory;


public interface IGenericCategoryBL {

	public GenericCategory createGenericCategory(GenericCategory gCategory);
	public GenericCategory updateGenericCategory(GenericCategory gCategory);
	public GenericCategory getGenericCategoryByName(String name);
	public List<GenericCategory> getAllGenericCategory();
	public void deleteGenericCategory(String name);
	public Boolean estaCategoria(long virtualStorageId, String name);
}