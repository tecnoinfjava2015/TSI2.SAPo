package com.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.bl.ProductMovementBL;
import com.entities.sql.ProductMovement;

@Path("/movement")
public class ProductMovementResource {

	ProductMovementBL PMBL = new ProductMovementBL();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ProductMovement createMovement( ProductMovement productMovementAux ){
		return PMBL.createMovement(productMovementAux);
	}
	
	@GET
	@Path("/{virtualStorageId}/byProdAndAV")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductMovement> getByProductAndAV( @PathParam("virtualStorageId") long virtualStorageId,
			@QueryParam("barcode") String barcode ){
		return PMBL.getByProductAndAV(virtualStorageId, barcode);
	}
	
	@GET
	@Path("/{virtualStorageId}/byProd")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductMovement> getByAV( @PathParam("virtualStorageId") long virtualStorageId ){
		return PMBL.getByAV(virtualStorageId);
	}
	
	@GET
	@Path("/{virtualStorageId}/wherePriceChange")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductMovement> getWherePriceChange( @PathParam("virtualStorageId") long virtualStorageId,
			@QueryParam("barcode") String barcode ){
		return PMBL.getWherePriceChange(virtualStorageId, barcode);
	}
	
	@GET
	@Path("/{virtualStorageId}/ByUserAndAV")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductMovement> getByUserAndAV( @PathParam("virtualStorageId") long virtualStorageId,
			@QueryParam("usrID") int usrID ){
		return PMBL.getByUserAndAV(virtualStorageId, usrID);
	}
	
	@GET
	@Path("/{virtualStorageId}/whereStockChangeByProduct")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductMovement> getWhereStockChangeByProduct( @PathParam("virtualStorageId") long virtualStorageId,
			@QueryParam("barcode") String barcode ){
		return PMBL.getWhereStockChangeByProduct(virtualStorageId, barcode);
	}
	
	@GET
	@Path("/{virtualStorageId}/whereStockChangeBetweenDates")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductMovement> getWhereStockChangeBetweenDates( @PathParam("virtualStorageId") long virtualStorageId,
			@QueryParam("barcode") String barcode,  @QueryParam("fecha1") String fecha1,  @QueryParam("fecha2") String fecha2){
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		try {
			cal1.setTime(sdf.parse("2015-11-09T03:00:00.000Z"));
			cal2.setTime(sdf.parse("2014-11-09T03:00:00.000Z"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// all done
		return PMBL.getWhereStockChangeBetweenDates(virtualStorageId, barcode, cal1, cal2);
	}
}
