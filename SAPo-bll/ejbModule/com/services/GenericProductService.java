package com.services;


import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.bl.GenericProductBL;
import com.entities.mongo.GenericProduct;
import com.entities.sql.Usuario;


//@Stateless
@Path("/genericProduct")
public class GenericProductService {
	GenericProductBL gpbl = new GenericProductBL();

	@GET
	@Path("{barcode}")
	@Produces(MediaType.APPLICATION_JSON)
	public GenericProduct getByBarCode(
			@PathParam("barcode") String barcode) {
		return gpbl.getGenericProductByBarCode(barcode);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public GenericProduct createGenericProduct(GenericProduct gProduct) {
		return gpbl.createGenericProduct(gProduct);
	}

	@PUT
	@Path("{update}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public GenericProduct updateProduct(
			@PathParam("update") String barcode, GenericProduct gProduct) {
		GenericProduct prodAux = gpbl.getGenericProductByBarCode(barcode);
		gProduct.setId(prodAux.getId());
		return gpbl.updateGenericProduct(gProduct);
	}

	@DELETE
	@Path("{delete}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteProduct(
			@PathParam("delete") String barcode) {
		gpbl.deleteGenericProduct(barcode);
	}
	
	@GET
	@Path("{all}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<GenericProduct> getAllGenericProduct(){
		return gpbl.getAllGenericProducts();
	}
	
}
