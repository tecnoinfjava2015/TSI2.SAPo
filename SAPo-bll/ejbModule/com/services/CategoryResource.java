package com.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.bl.CategoryBL;
import com.entities.mongo.Category;

@Path("/{virtualStorageId}/categories")
public class CategoryResource {
	
	CategoryBL bl = new CategoryBL();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Category> getAllByTenant(
			@PathParam("virtualStorageId") long virtualStorageId,
			@QueryParam("offset") int offset,
			@QueryParam("limit") int limit) {
		return bl.getAllCategories(virtualStorageId, offset, limit);
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Category getCategory(
			@PathParam("virtualStorageId") long virtualStorageId,
			@PathParam("id") int id) {
		return bl.getCategory(virtualStorageId, id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Category createCategory(Category category) {
		return bl.createCategory(category);
	}

	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Category updateCategory(
			@PathParam("virtualStorageId") long virtualStorageId,
			@PathParam("id") int id, Category category) {
		return bl.updateCategory(category);
	}

	@DELETE
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteCategory(
			@PathParam("virtualStorageId") long virtualStorageId,
			@PathParam("id") int id) {
		bl.deleteCategory(virtualStorageId, id);
	}
}
