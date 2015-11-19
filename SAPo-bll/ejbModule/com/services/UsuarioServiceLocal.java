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
								  @QueryParam("password") String password,
								  @QueryParam("enabled") Boolean enabled,
								  @QueryParam("aceptado") Boolean aceptado);
	
	@POST
	@Path("/registroTwitter")
	@Consumes(MediaType.APPLICATION_JSON)
	public void registroUsuarioTwitter ( final TwitterUserBean input);
//	public void registroUsuarioTwitter ( @QueryParam("nombre") String nombre,
//								  		  @QueryParam("twitterId") String twitterId);
	
	@GET
	@Path("/modificar")
	@Consumes(MediaType.APPLICATION_JSON)
	public void modificarUsuario ( @QueryParam("nombre") String nombre,
								  @QueryParam("tipo") String tipo,
								  @QueryParam("mail") String mail,
								  @QueryParam("nick") String nick,
								  @QueryParam("password") String password,
								  @QueryParam("enabled") Boolean enabled,
								  @QueryParam("aceptado") Boolean aceptado);
	
	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Usuario> getUsuarios();
	
	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Usuario> getUsuariosBorrados();
	
	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Usuario> getUsuariosPendientesAceptar();
	
	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Usuario> getUsuariosHabilitados();
		
	@GET
	@Path("/buscar")
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario getUsuario(String nick);
	
	@GET
	@Path("/baja")
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean borrarUsuario(String nick);
	
	@GET
	@Path("/aceptar")
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean aceptarUsuario(String nick);
	
	@GET
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario getLogin( @QueryParam("nick") String nick,
			  				 @QueryParam("password") String password);
	
	@POST
	@Path("/loginTwitter")
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario getLoginTwitter (final TwitterUserBean input);
	
	@POST
	@Path("/pasarAPremium")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario pasarAPremium ( PayPalUserBean input);
	//	public Boolean pasarAPremium (@QueryParam("nick") String nick,
//								  @QueryParam("paypalTransactionId") String paypalTransactionId);
	
	@POST
	@Path("/pasarAFreemium")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario pasarAFreemium ( TwitterUserBean input );

	@POST
	@Path("/geolocalizar")
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean geolocalizar (@QueryParam("nick") String nick,
								  @QueryParam("geolocation") String geolocation);
	//	public Usuario getLoginTwitter( @QueryParam("nick") String nick,
//			  				 @QueryParam("twitterId") String twitterId);

}
