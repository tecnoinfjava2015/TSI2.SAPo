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

import com.bl.NotificationsParamBL;
import com.bl.ProductBL;
import com.entities.mongo.Product;

@Path("/{virtualStorageId}/products")
public class ProductResource {
	ProductBL pbl = new ProductBL();
	NotificationsParamBL nobl = new NotificationsParamBL();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getAllByTenant(
			@PathParam("virtualStorageId") long virtualStorageId,
			@QueryParam("criteria") String criteria,
			@QueryParam("category") List<Integer> categories,
			@QueryParam("offset") int offset,
			@QueryParam("limit") int limit,
			@QueryParam("minSearch") boolean minSearch,
			@QueryParam("search") String search,
			@QueryParam("searching") boolean searching) {
		
		if(searching){
			return pbl.searchProducts(virtualStorageId, categories, search, offset, limit);
		}
		if (!(categories==null || categories.isEmpty())) {
			if (!(criteria==null || criteria.isEmpty())){
				return pbl.getProductsByCategoriesOr(virtualStorageId, categories, offset, limit);
			}
			return pbl.searchProducts(virtualStorageId, categories, search, offset, limit);
		}
		if(minSearch){
			return pbl.getProductsBarCodeAndName(virtualStorageId, search, limit);
		}
		return pbl.getAllProductsPaginated(virtualStorageId, offset, limit);//(virtualStorageId);
	}

	@GET
	@Path("{barcode}")
	@Produces(MediaType.APPLICATION_JSON)
	public Product getByBarCode(
			@PathParam("virtualStorageId") long virtualStorageId,
			@PathParam("barcode") String barcode) {
		return pbl.getProductByBarCode(virtualStorageId, barcode);
	}

//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public double getVSVaue(@PathParam("virtualStorageId") long virtualStorageId){
//		return pbl.getVSVaue(virtualStorageId);
//	}
//	@GET
//	@Path("{barcode}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Boolean estaProducto(@PathParam("virtualStorageId") long virtualStorageId, @PathParam("barcode") String barcode){
//		return pbl.estaProducto(virtualStorageId,barcode);
//	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Product createProduct(Product product) {
		return pbl.createProduct(product);
	}

	@PUT
	@Path("{barcode}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Product updateProduct(
			@PathParam("virtualStorageId") long virtualStorageId,
			@PathParam("barcode") String barcode, Product product) {
		Product prodAux = pbl.getProductByBarCode(virtualStorageId, barcode);
		product.setId(prodAux.getId());
		nobl.changeNotificationState(product);
		return pbl.updateProduct(product);
	}

	@DELETE
	@Path("{barcode}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteProduct(
			@PathParam("virtualStorageId") long virtualStorageId,
			@PathParam("barcode") String barcode) {
		pbl.deleteProduct(virtualStorageId, barcode);
	}
}
