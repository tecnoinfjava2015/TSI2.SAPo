package com.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.entities.sql.VirtualStorage;
import com.entities.sql.dao.VirtualStorageDAO;

@Path("/VirtualStorage/")
public class VirtualStorageResource {
	
	@EJB
	private VirtualStorageDAO dao;
	
	//VirtualStorageBL bl = new VirtualStorageBL();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createVS(VirtualStorage vs) {
		//validar que el getOwner no sea null y que el id exista
		return dao.createVS(vs, vs.getOwner().getId());
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateVS(VirtualStorage vs) {
		return dao.updateVS(vs);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getByOwner")
	public List<VirtualStorage> getVirtualStorageByOwner(@QueryParam("ownerId") int ownerId) {
		return dao.getVirtualStorageByOwner(ownerId);
		
	}
	
}
