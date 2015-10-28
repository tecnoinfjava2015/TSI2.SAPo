package com.services;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;



import com.entities.sql.VirtualStorage;

@Local
public interface VirtualStorageServiceLocal {

		@GET
		@Path("/registro")
		@Consumes(MediaType.APPLICATION_JSON)
		public void registroVS ( 	@QueryParam("nombre") String nombre,
									@QueryParam("conexion") String conexion,
									@QueryParam("url") String url,
									@QueryParam("fechaCreacion") Date fechaCreacion,
									@QueryParam("CSS") String CSS,
									@QueryParam("loading") String loading,
									@QueryParam("enabled") Boolean enabled,
									@QueryParam("idCreador") int idCreador,
									@QueryParam("logo") String logo); 
		
		
		@GET
		@Path("/modificar")
		@Consumes(MediaType.APPLICATION_JSON)
		public void modificarVS (@QueryParam("nombre") String nombre,
										@QueryParam("conexion") String conexion,
										@QueryParam("url") String url,
										@QueryParam("fechaCreacion") Date fechaCreacion,
										@QueryParam("CSS") String CSS,
										@QueryParam("loading") String loading,
										@QueryParam("enabled") Boolean enabled,
										@QueryParam("idCreador") int idCreador,
										@QueryParam("logo") String logo);
		
		@GET
		@Path("/listar")
		@Produces(MediaType.APPLICATION_JSON)
		public List<VirtualStorage> getVS();
		
		@GET
		@Path("/listar")
		@Produces(MediaType.APPLICATION_JSON)
		public List<VirtualStorage> getVSBorrados();
		
	
		@GET
		@Path("/buscar")
		@Produces(MediaType.APPLICATION_JSON)
		public VirtualStorage getVSPorNombre(String nombre);
		
		@GET
		@Path("/buscar")
		@Produces(MediaType.APPLICATION_JSON)
		public VirtualStorage getVSPorID(int id);
		
		@GET
		@Path("/baja")
		@Produces(MediaType.APPLICATION_JSON)
		public Boolean borrarVSPorNombre(String nombre);
		
}
