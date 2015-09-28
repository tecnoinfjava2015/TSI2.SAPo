package com.services;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.entities.mongo.Category;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.services.interfaces.CategoryBLRemote;

@RequestScoped
@Path("{tenant}/category")
public class CategoryBLService {
	@EJB
	CategoryBLRemote catEJB;
	
	@GET
	@Path("/all")
	@Produces("text/plain")
	public Response getAllCategories(@PathParam("tenant") String tenant){
		return Response.ok("PRUEBA OK").build();
	}

}
