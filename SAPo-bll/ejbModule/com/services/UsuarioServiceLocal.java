package com.services;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Local
public interface UsuarioServiceLocal {

	//http://localhost:8080/Sapo_Negocio/usuario/registro/?nombre=pepe&tipo=pepe&mail=pepe&nick=pepe&password=pepe
	@GET
	@Path("/registro")
	@Consumes(MediaType.APPLICATION_JSON)
	public void registroUsuario ( @QueryParam("nombre") String nombre,
								  @QueryParam("tipo") String tipo,
								  @QueryParam("mail") String mail,
								   @QueryParam("nick") String nick,
								  @QueryParam("password") String password);
}
