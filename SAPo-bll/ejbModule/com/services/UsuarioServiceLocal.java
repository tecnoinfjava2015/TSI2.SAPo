package com.services;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.entities.sql.Usuario;

@Local
public interface UsuarioServiceLocal {

	@GET
	@Path("/registro")
	@Consumes(MediaType.APPLICATION_JSON)
	public void registroUsuario ( @QueryParam("nombre") String nombre,
								  @QueryParam("tipo") String tipo,
								  @QueryParam("mail") String mail,
								   @QueryParam("nick") String nick,
								  @QueryParam("password") String password);
	
	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Usuario> getUsuarios();
		
	@GET
	@Path("/buscar")
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario getUsuario(String nick);
	
	@GET
	@Path("/baja")
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean borrarUsuario(String nick);
	
}
