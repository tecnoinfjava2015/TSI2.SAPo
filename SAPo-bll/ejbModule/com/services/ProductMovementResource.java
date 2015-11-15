//package com.services;
//
//import java.util.List;
//
//import javax.ejb.EJB;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
//import javax.ws.rs.core.MediaType;
//
//import com.bl.ProductMovementBL;
//import com.entities.sql.dao.ProductMovementDAO;
//import com.entities.sql.ProductMovement;
//import com.utilities.IntervalDates;
//
//@Path("/movement")
//public class ProductMovementResource {
//
//	@EJB
//	private ProductMovementDAO dao;
//	
//	ProductMovementBL PMBL = new ProductMovementBL();
//	
//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public ProductMovement createMovement( ProductMovement productMovementAux ){
//		return PMBL.createMovement(productMovementAux);
//	}
//	
//	@GET
//	@Path("/{virtualStorageId}/movementQuantity")
//	@Produces(MediaType.APPLICATION_JSON)
//	public long getMovementQuantity( @PathParam("virtualStorageId") long virtualStorageId ){
//		return PMBL.getMovementQuantity(virtualStorageId);
//	}
//	
//	@GET
//	@Path("/{virtualStorageId}/movementByUser")
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<ProductMovement> getMovementByUser( @PathParam("virtualStorageId") long virtualStorageId,
//			@QueryParam("userID") int usrID){
//		return PMBL.getMovementsByUser(virtualStorageId, usrID);
//	}
//	
//	@GET
//	@Path("/{virtualStorageId}/movementQuantityBetweenDates")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public long getMovementQuantityBetweenDates( @PathParam("virtualStorageId") long virtualStorageId,
//			IntervalDates dates){
////		Calendar cal1 = Calendar.getInstance();
////		Calendar cal2 = Calendar.getInstance();
////		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"); //ejemplo "2015-11-09T03:00:00.000Z"
////		try {
////			cal1.setTime(sdf.parse(fecha1));
////			cal2.setTime(sdf.parse(fecha2));
////		} catch (ParseException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//		return PMBL.getMovimentQuantityBetweenDates(virtualStorageId, dates.getDate1(), dates.getDate2());
//	}
//	
//	@GET
//	@Path("/{virtualStorageId}/byProdAndAV")
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<ProductMovement> getByProductAndAV( @PathParam("virtualStorageId") long virtualStorageId,
//			@QueryParam("barcode") String barcode ){
//		return dao.getByProductAndAV(virtualStorageId, barcode);
//		//return PMBL.getByProductAndAV(virtualStorageId, barcode);
//	}
//	
//	@GET
//	@Path("/{virtualStorageId}/byProd")
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<ProductMovement> getByAV( @PathParam("virtualStorageId") long virtualStorageId ){
//		return PMBL.getByAV(virtualStorageId);
//	}
//	
//	@GET
//	@Path("/{virtualStorageId}/wherePriceChange")
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<ProductMovement> getWherePriceChange( @PathParam("virtualStorageId") long virtualStorageId,
//			@QueryParam("barcode") String barcode ){
//		return PMBL.getWherePriceChange(virtualStorageId, barcode);
//	}
//	
//	@GET
//	@Path("/ByUserAndAV")
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<ProductMovement> getByUserAndAV( @QueryParam("virtualStorageId") long virtualStorageId,
//			@QueryParam("usrID") int usrID ){
//		return PMBL.getByUserAndAV(virtualStorageId, usrID);
//	}
//	
//	@GET
//	@Path("/{virtualStorageId}/whereStockChangeByProduct")
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<ProductMovement> getWhereStockChangeByProduct( @PathParam("virtualStorageId") long virtualStorageId,
//			@QueryParam("barcode") String barcode ){
//		return PMBL.getWhereStockChangeByProduct(virtualStorageId, barcode);
//	}
//	
//	@GET
//	@Path("/{virtualStorageId}/whereStockChangeBetweenDates")
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<ProductMovement> getWhereStockChangeBetweenDates( @PathParam("virtualStorageId") long virtualStorageId,
//			@QueryParam("barcode") String barcode,  IntervalDates dates){
////		Calendar cal1 = Calendar.getInstance();
////		Calendar cal2 = Calendar.getInstance();
////		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
////		try {
////			cal1.setTime(sdf.parse("2015-11-09T03:00:00.000Z"));
////			cal2.setTime(sdf.parse("2014-11-09T03:00:00.000Z"));
////		} catch (ParseException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}// all done
//		return PMBL.getWhereStockChangeBetweenDates(virtualStorageId, barcode, dates.getDate1(), dates.getDate2());
//	}
//}
