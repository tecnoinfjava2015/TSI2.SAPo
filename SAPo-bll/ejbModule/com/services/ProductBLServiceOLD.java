/**
 * 
 */
package com.services;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import java.util.List;

import com.bl.ProductOLDBL;
import com.entities.mongo.ProductOLD;
import com.services.interfaces.ProductBLRemoteOLD;

/**
 * @author Lufasoch
 *
 */

@RequestScoped
@Path("{tenant}/product")
public class ProductBLServiceOLD {	
	@EJB
	ProductBLRemoteOLD prodEJB;
	
	@PathParam("tenant")
	
	@GET
	@Path("/byCat")
	@Produces("application/JSON")
	public List<ProductOLD> getProductByCategory(@PathParam("tenant") String tenant, String nameCategory){
		ProductBLRemoteOLD pb = new ProductOLDBL();
		return pb.getProductsByCategory(tenant, nameCategory);
	}
	
	@GET
	@Path("/byName")
	@Produces("application/JSON")
	public ProductOLD getByName(@PathParam("tenant") String tenant, String name){
		ProductBLRemoteOLD pb = new ProductOLDBL();
		return pb.getByName(tenant, name);
	}
	
	@POST
	@Path("/create")
	@Consumes("application/JSON")
	@Produces("application/JSON")
	public ProductOLD createProduct(@PathParam("tenant") String tenant, ProductOLD prodAux){
		ProductBLRemoteOLD pb = new ProductOLDBL();
		return pb.create(prodAux);
	}
	
	@DELETE
	@Path("/delete")
	@Consumes("application/JSON")
	public void deleteProduct(@PathParam("tenant") String tenant, String arg[]){ //El argumento 1 debe ser el nombre y el 2 el tenant
		ProductBLRemoteOLD pb = new ProductOLDBL();
		pb.delete(arg[1], arg[2]);
	}
}
