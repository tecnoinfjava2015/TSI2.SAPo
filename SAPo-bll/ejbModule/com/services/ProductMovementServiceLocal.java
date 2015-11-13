//package com.services;
//
//import java.util.List;
//
//import javax.ws.rs.Consumes;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
//import javax.ws.rs.core.MediaType;
//
//import com.entities.sql.ProductMovement;
//
//@Path("/movement")
//public interface ProductMovementServiceLocal {
//	
//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public ProductMovement createMovement( ProductMovement productMovementAux );
//	
//	@GET
//	@Path("/{virtualStorageId}/byProdAndAV")
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<ProductMovement> getByProductAndAV( @PathParam("virtualStorageId") long virtualStorageId,
//			@QueryParam("barcode") String barcode );
//	
//	@GET
//	@Path("/{virtualStorageId}/byProd")
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<ProductMovement> getByAV( @PathParam("virtualStorageId") long virtualStorageId );
//	
//	@GET
//	@Path("/{virtualStorageId}/wherePriceChange")
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<ProductMovement> getWherePriceChange( @PathParam("virtualStorageId") long virtualStorageId,
//			@QueryParam("barcode") String barcode );
//	
//	@GET
//	@Path("/{virtualStorageId}/ByUserAndAV")
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<ProductMovement> getByUserAndAV( @PathParam("virtualStorageId") long virtualStorageId,
//			@QueryParam("usrID") int usrID );
//	
//	@GET
//	@Path("/{virtualStorageId}/whereStockChangeByProduct")
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<ProductMovement> getWhereStockChangeByProduct( @PathParam("virtualStorageId") long virtualStorageId,
//			@QueryParam("barcode") String barcode );
//	
//	@GET
//	@Path("/{virtualStorageId}/whereStockChangeBetweenDates")
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<ProductMovement> getWhereStockChangeBetweenDates( @PathParam("virtualStorageId") long virtualStorageId,
//			@QueryParam("barcode") String barcode,  @QueryParam("fecha1") String fecha1,  @QueryParam("fecha2") String fecha2);
//}
