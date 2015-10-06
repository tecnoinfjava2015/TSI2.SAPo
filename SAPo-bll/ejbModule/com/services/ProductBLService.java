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
import com.entities.mongo.Category;
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
	@Path("{tenant}/byCat")
	@Produces("application/JSON")
	public List<Product> getProductByCategory(Category catAux){
		ProductBLRemote pb = new ProductBL();
		return pb.getProductsByCategory(catAux);
	}
	
	@GET
	@Path("{tenant}/byName")
	@Produces("application/JSON")
	public Product getByName(String name){
		ProductBLRemote pb = new ProductBL();
		return pb.getByName(name);
	}
	
	@POST
	@Path("{tenant}/create")
	@Consumes("application/JSON")
	@Produces("application/JSON")
	public Product createProduct(Product prodAux){
		ProductBLRemote pb = new ProductBL();
		return pb.create(prodAux);
	}
	
	@DELETE
	@Path("{tenant}/delete")
	@Consumes("application/JSON")
	public void deleteProduct(String arg[]){ //El argumento 1 debe ser el nombre y el 2 el tenant
		ProductBLRemote pb = new ProductBL();
		pb.delete(arg[1], arg[2]);
	}
}
