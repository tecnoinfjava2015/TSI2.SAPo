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

import com.bl.ProductBL;
import com.entities.mongo.Product;
import com.services.interfaces.ProductBLRemote;

/**
 * @author Lufasoch
 *
 */

@RequestScoped
@Path("{tenant}/product")
public class ProductBLService {	
	@EJB
	ProductBLRemote prodEJB;
	
	@PathParam("tenant")
	
	@GET
	@Path("/byCat")
	@Produces("application/JSON")
	public List<Product> getProductByCategory(@PathParam("tenant") String tenant, String nameCategory){
		ProductBLRemote pb = new ProductBL();
		return pb.getProductsByCategory(tenant, nameCategory);
	}
	
	@GET
	@Path("/byName")
	@Produces("application/JSON")
	public Product getByName(@PathParam("tenant") String tenant, String name){
		ProductBLRemote pb = new ProductBL();
		return pb.getByName(tenant, name);
	}
	
	@POST
	@Path("/create")
	@Consumes("application/JSON")
	@Produces("application/JSON")
	public Product createProduct(@PathParam("tenant") String tenant, Product prodAux){
		ProductBLRemote pb = new ProductBL();
		return pb.create(prodAux);
	}
	
	@DELETE
	@Path("/delete")
	@Consumes("application/JSON")
	public void deleteProduct(@PathParam("tenant") String tenant, String arg[]){ //El argumento 1 debe ser el nombre y el 2 el tenant
		ProductBLRemote pb = new ProductBL();
		pb.delete(arg[1], arg[2]);
	}
}
