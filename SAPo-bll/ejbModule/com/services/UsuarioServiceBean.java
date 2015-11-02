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
	public void registroUsuario(String nombre, String tipo, String mail,String nick, String password, Boolean enabled, Boolean aceptado) {	
		Usuario u = new Usuario();
		u.setName(nombre);
		u.setType(tipo);
		u.setMail(mail);
		u.setNick(nick);
		u.setPassword(password);
		u.setEnabled(enabled);
		u.setAceptado(aceptado);
		udao.insert(u);
	}

	@Override
	public List<Usuario> getUsuarios() {
		return udao.listarUsuarios();
	}

	@Override
	public Usuario getUsuario(String nick) {
		return udao.buscar(nick);
	}

	@Override
	public Boolean borrarUsuario(String nick) {
		
		return udao.baja(nick);
	}

	@Override
	public void modificarUsuario(String nombre, String tipo, String mail,String nick, String password, Boolean enabled, Boolean aceptado) {
		Usuario u = new Usuario();
		u.setName(nombre);
		u.setType(tipo);
		u.setMail(mail);
		u.setNick(nick);
		u.setPassword(password);
		u.setEnabled(enabled);
		u.setAceptado(aceptado);
		udao.modificar(u);
	}

	@Override
	public Boolean aceptarUsuario(String nick) {
		return udao.aceptar(nick);
	}

	@Override
	public List<Usuario> getUsuariosBorrados() {
		return udao.listarUsuariosBorrados();
	}

	@Override
	public List<Usuario> getUsuariosPendientesAceptar() {
		return udao.listarUsuariosPendientesAceptar();
	}

	@Override
	public List<Usuario> getUsuariosHabilitados() {
		return udao.listarUsuariosHabilitados();
	}

	@Override
	public Usuario getLogin(String nick, String password) {
		return udao.getLogin(nick, password);
	}
}
