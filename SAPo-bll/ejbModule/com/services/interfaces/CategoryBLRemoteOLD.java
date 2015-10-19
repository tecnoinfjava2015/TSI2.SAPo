package com.services.interfaces;

import java.util.List;

import javax.ejb.Remote;

import com.entities.mongo.CategoryOLD;

@Remote
public interface CategoryBLRemoteOLD {
	public CategoryOLD create(CategoryOLD category);
	public void remove(String tenant, String name);
	public CategoryOLD update(CategoryOLD category);
	public CategoryOLD getByName(String tenant, String name);
	public List<CategoryOLD> getAll(String tenant);
}
