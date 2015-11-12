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

import com.bl.ShoppingListBL;
import com.entities.mongo.Product;
import com.entities.sql.ShoppingListItem;

@Path("/shoppingList")
public class ShoppingListResource {

	ShoppingListBL SLBL = new ShoppingListBL();
	
	@GET
	@Path("/{virtualStorageId}/list")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ShoppingListItem> getShoppingList( @PathParam("virtualStorageId") long virtualStorageId){
		return SLBL.returnShoppingList(virtualStorageId);
	}
	
	@GET
	@Path("/{virtualStorageId}/listItem")
	@Produces(MediaType.APPLICATION_JSON)
	public ShoppingListItem getShoppingListItem( @PathParam("virtualStorageId") long virtualStorageId, 
			@QueryParam("barcode") String barcode ){
		return SLBL.returnShoppingListItem(virtualStorageId, barcode);
	}
	
	@GET
	@Path("/{virtualStorageId}/recomendations")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getShoppingListRecomendations( @PathParam("virtualStorageId") long virtualStorageId ){
		return SLBL.getRecomendations(virtualStorageId);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ShoppingListItem createShoppingListItem( ShoppingListItem item ){
		return SLBL.createShoppingListItem( item );
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void updateShoppingListItem( ShoppingListItem item ){
		SLBL.updateShoppingListItem( item );
	}
	
	@DELETE
	@Path("/{virtualStorageId}/listItem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteShoppingListItem( @PathParam("virtualStorageId") long virtualStorageId,
			@QueryParam("barcode") String barcode ){
		SLBL.deleteItem(virtualStorageId, barcode);
	}
}
