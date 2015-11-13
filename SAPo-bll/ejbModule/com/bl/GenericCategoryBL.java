package com.bl;
import java.util.List;

import org.bson.types.ObjectId;

import com.entities.mongo.GenericCategory;
import com.entities.mongo.dao.GenericCategoryDAO;
import com.services.interfaces.IGenericCategoryBL;


public class GenericCategoryBL implements IGenericCategoryBL{

	private final GenericCategoryDAO dao = new GenericCategoryDAO();

	public GenericCategoryBL() {
	}	

	@Override
	public GenericCategory createGenericCategory(GenericCategory gCategory) {
		if (!(gCategory == null)) {
			ObjectId gCategoryId = dao.insert(gCategory);
			return dao.getById(gCategoryId);
		}
		return null;
	}

	@Override
	public GenericCategory updateGenericCategory(GenericCategory gCategory) {
		if (!(gCategory == null)) {
			ObjectId gCategoryId = dao.insert(gCategory);
			return dao.getById(gCategoryId);
		}
		return null;
	}

	@Override
	public GenericCategory getGenericCategoryByName(String name) {
		if (!(name.isEmpty())) {
			return dao.getByName(name);
		}
		return null;
	}

	@Override
	public List<GenericCategory> getAllGenericCategory() {
		return dao.getAllCategory();
	}

	@Override
	public void deleteGenericCategory(String name) {
		GenericCategory gCategory = dao.getByName(name);
		dao.deleteGenericCategory(gCategory.getId());		
	}
}