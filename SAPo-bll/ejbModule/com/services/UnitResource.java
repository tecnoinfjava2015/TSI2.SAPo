package com.services;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.bl.UnitBL;
import com.entities.sql.Unit;
import com.entities.sql.VirtualStorage;
import com.entities.sql.dao.UnitDAO;

@Path("/Unit")
public class UnitResource {
	@EJB
	private UnitDAO dao = new UnitDAO();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Unit createUnit(Unit unit) {
		//validar que el getOwner no sea null y que el id exista
		return dao.createDocument(unit);
	} 
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{vsId}")
	public Unit getUnitByVS(@PathParam("vsId") int vsId) {
		return dao.getUnitByVS(vsId);
	}
}
