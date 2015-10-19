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

import com.bl.CurrencyBL;
import com.entities.mongo.Currency;

@Path("/{virtualStorageId}/categories")
public class CurrencyResource {
	
	CurrencyBL bl = new CurrencyBL();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Currency> getAllByTenant(
			@PathParam("virtualStorageId") long virtualStorageId,
			@QueryParam("offset") int offset,
			@QueryParam("limit") int limit) {
		return bl.getAllCurrencies(virtualStorageId, offset, limit);
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Currency getCurrency(
			@PathParam("virtualStorageId") long virtualStorageId,
			@PathParam("id") int id) {
		return bl.getCurrency(virtualStorageId, id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Currency createCurrency(Currency currency) {
		return bl.createCurrency(currency);
	}

	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Currency updateCurrency(
			@PathParam("virtualStorageId") long virtualStorageId,
			@PathParam("id") int id, Currency currency) {
		return bl.updateCurrency(currency);
	}

	@DELETE
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteCurrency(
			@PathParam("virtualStorageId") long virtualStorageId,
			@PathParam("id") int id) {
		bl.deleteCurrency(virtualStorageId, id);
	}
}
