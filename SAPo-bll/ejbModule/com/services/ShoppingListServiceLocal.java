package com.services;

import java.util.List;

import javax.ejb.Local;
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

import com.entities.mongo.Product;
import com.entities.sql.ShoppingListItem;

@Local
public interface ShoppingListServiceLocal {
	
	@GET
	@Path("/{virtualStorageId}/list")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<ShoppingListItem> getShoppingList( @PathParam("virtualStorageId") long virtualStorageId);
	
	@GET
	@Path("/{virtualStorageId}/listItem")
	@Consumes(MediaType.APPLICATION_JSON)
	public ShoppingListItem getShoppingListItem( @PathParam("virtualStorageId") long virtualStorageId, 
			@QueryParam("nombre") String barcode );
	
	@GET
	@Path("/{virtualStorageId}/listItem")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Product> getShoppingListRecomendations( @PathParam("virtualStorageId") long virtualStorageId );
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ShoppingListItem createShoppingListItem( ShoppingListItem item );
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void updateShoppingListItem( ShoppingListItem item );
	
	@DELETE
	@Path("/{virtualStorageId}/listItem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteShoppingListItem( @PathParam("virtualStorageId") long virtualStorageId,
			@QueryParam("nombre") String barcode );
}
