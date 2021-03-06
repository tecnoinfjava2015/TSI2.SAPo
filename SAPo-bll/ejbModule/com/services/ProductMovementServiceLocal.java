package com.services;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.entities.sql.ProductMovement;
import com.utilities.IntervalDates;

@Local
@Path("/movement")
public interface ProductMovementServiceLocal{

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ProductMovement createMovement( ProductMovement productMovementAux );
	
	@GET
	@Path("/ByUserAndAV")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductMovement> getByUserAndAV( @QueryParam("VSId") long VSId,
			  @QueryParam("userID") int userID );
	
	@GET
	@Path("/Quantity")
	@Produces(MediaType.APPLICATION_JSON)
	public long getMovementQuantity( @QueryParam("VSId") long virtualStorageId );
	
	@GET
	@Path("/ByUser")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductMovement> getMovementByUser( @QueryParam("userID") int usrID);
	
	@POST
	@Path("/QuantityBetweenDates/{vsid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public long getMovementQuantityBetweenDates( @PathParam("vsid") long VSId,
			IntervalDates dates);
	
	@POST
	@Path("/ListBetweenDates/{vsid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductMovement> getMovementsBetweenDates( @PathParam("vsid") long VSId,
			IntervalDates dates);
	
	@GET
	@Path("/byProdAndAV")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductMovement> getByProductAndAV( @QueryParam("VSId") long VSId,
			@QueryParam("barcode") String barcode );
	
	@GET
	@Path("/byProd")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductMovement> getByAV( @QueryParam("VSId") long VSId );
	
	@POST
	@Path("/wherePriceChange/{VSId}/{barcode}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductMovement> getWherePriceChange( @PathParam("VSId") long VSId,
			@PathParam("barcode") String barcode );
	
	@POST
	@Path("/whereStockChangeByProduct/{VSId}/{barcode}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductMovement> getWhereStockChangeByProduct( @PathParam("VSId") long VSId,
			@PathParam("barcode") String barcode );
	
	@GET
	@Path("/whereStockChangeBetweenDates")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductMovement> getWhereStockChangeBetweenDates( @QueryParam("VSId") long VSId,
			@QueryParam("barcode") String barcode,  IntervalDates dates);
	
	@GET
	@Path("/movimientosStockProduto")
	@Produces(MediaType.APPLICATION_JSON)
	public long getovimientosStockProduto( @QueryParam("VSId") long VSId, @QueryParam("barcode") String barcode,  IntervalDates dates);
	
	@GET
	@Path("/getoFechaCreadoProduto")
	@Produces(MediaType.APPLICATION_JSON)
	public Calendar getoFechaCreadoProduto(@QueryParam("VSId") long virtualStorageId, @QueryParam("barcode") String barCode);
}