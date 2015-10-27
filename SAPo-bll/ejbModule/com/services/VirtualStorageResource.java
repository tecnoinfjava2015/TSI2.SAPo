package com.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.bl.VirtualStorageBL;
import com.entities.sql.VirtualStorage;

@Path("/VirtualStorage/")
public class VirtualStorageResource {
	VirtualStorageBL bl = new VirtualStorageBL();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public VirtualStorage createVS(VirtualStorage vs) {
		return bl.createVS(vs);
	}
	
}
