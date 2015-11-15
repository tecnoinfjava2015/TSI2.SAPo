package com.services;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.entities.sql.ProductMovement;

@Local
@Path("/movemen")
public interface ProductMovementServiceLocal{
	
	@GET
	@Path("/getByUserAndAV")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductMovement> getByUserAndAV( @QueryParam("VSId") long VSId,
			  @QueryParam("userID") int userID );
}