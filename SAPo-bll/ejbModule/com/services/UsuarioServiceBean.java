package com.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Path;

import com.entities.sql.Usuario;
import com.entities.sql.dao.UserDAO;



@Stateless
@Path("/usuario")
public class UsuarioServiceBean implements UsuarioServiceLocal{

	@EJB
	private UserDAO udao;

	
	@Override
	public void registroUsuario(String nombre, String tipo, String mail,String nick, String password) {
		
		Usuario u = new Usuario();
		u.setName(nombre);
		u.setType(tipo);
		u.setMail(mail);
		u.setNick(nick);
		u.setPassword(password);
		u.setEnabled(true);
		udao.insert(u);
	}

	@Override
	public List<Usuario> getUsuarios() {
		return udao.listarUsuarios();
	}

	@Override
	public Usuario getUsuario(String nick) {
		return udao.buscarUsuario(nick);
	}

	@Override
	public Boolean borrarUsuario(String nick) {
		
		return udao.baja(nick);
	}
	



}
