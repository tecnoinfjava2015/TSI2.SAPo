package com.services;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.entities.mongo.Product;
import com.entities.sql.ShoppingListItem;

@Local
@Path("/shoppingList")
public interface ShoppingListServiceLocal {
	
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ShoppingListItem> getShoppingList( @QueryParam("VSId") long VSId);
	
	@GET
	@Path("/listItem")
	@Produces(MediaType.APPLICATION_JSON)
	public ShoppingListItem getShoppingListItem( @QueryParam("VSId") long VSId, 
			@QueryParam("barcode") String barcode );
	
	@GET
	@Path("/recomendations")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getShoppingListRecomendations( @QueryParam("VSId") long VSId);
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ShoppingListItem createShoppingListItem( ShoppingListItem item );
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void updateShoppingListItem( ShoppingListItem item );
	
	@DELETE
	@Path("/listItem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteShoppingListItem( @QueryParam("VSId") long VSId,
			@QueryParam("barcode") String barcode );
}
