package com.services;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Path;

import com.entities.sql.User;
import com.entities.sql.dao.UserDAO;



@Stateless
@Path("/usuario")
public class UsuarioServiceBean implements UsuarioServiceLocal{

	@EJB
	private UserDAO udao;

	
	@Override
	public void registroUsuario(String nombre, String tipo, String mail,String nick, String password) {
		
		User u = new User();
		u.setName(nombre);
		u.setType(tipo);
		u.setMail(mail);
		u.setNick(nick);
		u.setPassword(password);
		udao.insert(u);

	}
	

//	//http://localhost:8080/com.see.rest/usuario/login/pepe?password=pepe
//	@GET
//	@Path("/login/{nick}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public boolean login(@PathParam("nick") String nick,
//                         @QueryParam("password") String password)
//	
//	{
//		//si se recibe la pass en texto plano se usan estas dos líneas:
//		//String md5 = new Encrypter().MD5(password);
//		//return pdao.login(nick, md5);
//		
//		//si se recibe el md5 del pass se usa esta:
//		return pdao.login(nick, password);
//		
//	}
//	
//	////////////////////////REGISTRAR UN NUEVO USUARIO/////////////////////
//	// http://localhost:8080/com.see.rest/usuario/registro/pepe?password=pepe
//	@GET
//	@Path("/registro/{nick}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public String registro(@PathParam("nick") String nick,
//                         @QueryParam("password") String password)
//	
//	{
//		
//		
//	
//				
//		EncuestadoraUser j = new EncuestadoraUser();
//		j.setNick("encustador-eluno");
//		String md5 = new Encrypter().MD5("123");
//	
//		
//		j.setPassword(md5);
//		j.setEnabled(true);
//		String retorno2=  pdao.insertar(j);
//		
//		
//		Administrador nuevoAdmin = new Administrador();
//		nuevoAdmin.setHabilitado(true);
//		nuevoAdmin.setEnabled(true);
//		nuevoAdmin.setNick("admin");
//		String md511 = new Encrypter().MD5("admin");
//		
//		nuevoAdmin.setPassword(md511);
//		String retorno2s=  pdao.insertar(nuevoAdmin);
//		
//		boolean ok = pdao.existe(nick); 
//		
//		if (!ok) {
//		EncuestadoraAdmin p = new EncuestadoraAdmin();
//		p.setEnabled(true);
//			p.setNick(nick);
//			String md51 = new Encrypter().MD5(password);
//		p.setPassword(md51);
//		p.setHabilitado(true);
//	 String retorno=  pdao.insertar(p);
//	 
//		//return udao.login(nick, password);
//		return ("usuario creado" + retorno);
//		}
//		else
//		{
//			return ("usuario duplicado");
//		}
//	
//		
//	}
//	
//	@GET
//	@Path ("/{nombre}")
//	@Produces(MediaType.APPLICATION_JSON)
//	
//	public  String getUsuario (@PathParam("nombre") String nombre){		
//			//Usuario per=udao.buscar(nombre);			
//			return  "hola "+ nombre;	
//	}
//	
//	
//	//http://localhost:8080/com.see.rest/usuario/registro/?nombre=pepe&apellido=pepe&mail=pepe&nick=pepe&password=pepe
//	@POST
//	@Path("/registro")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public void registroUsuario ( @QueryParam("nombre") String nombre,
//								  @QueryParam("apellido") String apellido,
//								  @QueryParam("mail") String mail,
//								   @QueryParam("nick") String nick,
//								  @QueryParam("password") String password)	{
//		
//		
//		
//	/*	Usuario u = new Usuario();
//		u.setNombre(nombre);
//		u.setApellido(apellido);
//		u.setMail(mail);
//		u.setNick(nick);
//		u.setPassword(password);
//		*/
//		//udao.insertar(u);
//		
//		
//	}
//	
//
//	
//	
//	
//	
//	
//	
//	
//	@PUT
//	@Path("/usuariomod")
//	public void modificarUsuario ( @QueryParam("nombre") String nombre,
//								  @QueryParam("apellido") String apellido,
//								  @QueryParam("mail") String mail,
//								  @QueryParam("mailnew") String mailnew,
//								  @QueryParam("fechaNac") String fechaNac,
//								  @QueryParam("sexo") String sexo)
//								 {
//		
//	
//		//falta conexion con los DAO
//				//falta ver de pasar la fecha
//		
//				
//		
//	}
//	
//	@PUT
//	@Path("/bajaUsuario")
//	public void bajaUsuario ( @QueryParam("mail") String mail
//								) {
//		
//	
//	
//	}
//	
//	@Path("/ecuestadora_user")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public void registroUsuarioEU ( @QueryParam("nombre") String nombre,
//								  @QueryParam("apellido") String apellido,
//								  @QueryParam("zona_asignada") String zona,
//								   @QueryParam("latitud") double latitud,
//								   @QueryParam("longitud") double longitud,
//								  @QueryParam("nick") String nick,
//								  @QueryParam("password") String password)	{
//		
//		
//		//falta conexion con los DAO
//		//falta ver de pasar la fecha
//		
//		
//	}
//	
//	//http://localhost:8080/com.see.rest/usuario/verificar/pepe?verificador=pepe
//		@GET
//		@Path("/verificar/{nick}")
//		@Produces(MediaType.APPLICATION_JSON)
//		public String verificar(@PathParam("nick") String nick,
//	                         @QueryParam("verificador") int verificador)
//		
//		{
//			
//			//
//			boolean ok = pdao.existe(nick); 
//	
//			if (ok) {
//				pejb.habilitarUsario(nick,verificador);
//				return "Habilitado con Exito";
//			}
//			else{
//				return "Error de datos: no existe usuario o no coincide el verificador";
//		
//			}
//			
//		}
//		
//		@GET
//		@Path("/verificarA/{nick}")
//		@Produces(MediaType.APPLICATION_JSON)
//		public String verificarA(@PathParam("nick") String nick,
//	                         @QueryParam("verificadorAdmin") int verificadorAdmin)
//		
//		{
//			
//			//
//			boolean ok = pdao.existe(nick); 
//	
//			if (ok) {
//				pejb.habilitarUsarioAdmin(nick,verificadorAdmin);
//				return "Habilitado con Exito";
//			}
//			else{
//				return "Error de datos: no existe usuario o no coincide el verificador";
//		
//			}
//			
//		}
//	
//	
//	
//	
}
