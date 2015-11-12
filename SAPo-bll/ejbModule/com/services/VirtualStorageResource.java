package com.services;

import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.bl.ProductBL;
import com.entities.mongo.Product;
import com.entities.sql.VirtualStorage;
import com.entities.sql.dao.VirtualStorageDAO;
import com.services.interfaces.IProductBL;

@Path("/VirtualStorage/")
public class VirtualStorageResource {
	
	@EJB
	private VirtualStorageDAO dao;
	
	//VirtualStorageBL bl = new VirtualStorageBL();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
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
	@Path("owner/{ownerId}")
	public List<VirtualStorage> getVirtualStorageByOwner(@PathParam("ownerId") int ownerId) {
		List<VirtualStorage> virtualStorages = dao.getVirtualStorageByOwner(ownerId);
		
		return virtualStorages;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("myVSs/{userId}")
	public HashMap<String, List<VirtualStorage>> getMyVirtualStorages(@PathParam("userId") int userId) { //owned + following
		HashMap<String, List<VirtualStorage>> virtualStorages = dao.getMyVirtualStorages(userId);
		
		return virtualStorages;
	}
	
	@POST
	@Path("/valorar")
	@Produces(MediaType.APPLICATION_JSON)
	public double valorarAV(@QueryParam("id") int id){
		IProductBL servicioProducto = new ProductBL();
		List<Product>  listaProductos;
		listaProductos = servicioProducto.getAllProducts(id);
		double resultado = 0;
		for(Product p : listaProductos){
			resultado += p.getSalePrice();
		}
		return resultado;
	}

}
