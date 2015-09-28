package com.services.interfaces;

import java.util.List;

import javax.ejb.Remote;

import com.entities.mongo.Category;

@Remote
public interface CategoryBLRemote {
	public Category create(Category category);
	public void remove(String tenant, String name);
	public Category update(Category category);
	public Category getByName(String tenant, String name);
	public List<Category> getAll(String tenant);
}
