package com.services.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.entities.mongo.Category;

@Local
public interface CategoryBLLocal {
	public Category create(Category category);
	public void remove(String tenant, String name);
	public Category update(Category category);
	public Category getByName(String tenant, String name);
	public List<Category> getAll(String tenant);
}
