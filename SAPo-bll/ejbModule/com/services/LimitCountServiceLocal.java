package com.services;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.entities.sql.LimitCount;


@Local
public interface LimitCountServiceLocal {

	@GET
	@Path("/registro")
	@Consumes(MediaType.APPLICATION_JSON)
	public void registroLimitCount ( @QueryParam("type") String type,
								  @QueryParam("limit") int limit,
								  @QueryParam("avisarFaltando") int avisarFaltando);
	
	@GET
	@Path("/modificar")
	@Consumes(MediaType.APPLICATION_JSON)
	public void modificarLimitCount ( @QueryParam("type") String type,
								  @QueryParam("limit") int limit,
								  @QueryParam("avisarFaltando") int avisarFaltando);
	

	
	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public List<LimitCount> listarLimitCount();
	
	@GET
	@Path("/buscar")
	@Produces(MediaType.APPLICATION_JSON)
	public LimitCount buscarType(String type);

}
