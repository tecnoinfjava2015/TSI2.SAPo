package com.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

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
	public void registroUsuarioTwitter(TwitterUserBean input) { //String nombre, String twitterId) {
		Usuario u = new Usuario();
		u.setName(input.nombre);
		u.setType("Free");
		u.setMail("");
		u.setNick(input.nombre);
		u.setPassword("");
		u.setEnabled(true);
		u.setAceptado(true);
		u.setTwitterId(input.twitterId);
		u.setGeolocation(input.geoLocation);
		udao.insert(u);
	}
	
	@Override
	public Usuario emailUpdate(UserBean input) {
		Usuario u = udao.buscar(input.nick);
		u.setMail(input.mail);
		udao.modificar(u);
		return u;
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

	@Override
	public Usuario getLoginTwitter(TwitterUserBean input) {//(String nick, String twitterId) {
		//en el dao revisa el nick, que es igual al nombre
		Usuario result = udao.getLoginTwitter(input.nombre, input.twitterId);
		
		//en caso que devuelva null, es el primer ingreso, llamo a registro y luego a login nuevamente
		if(result == null) {
			registroUsuarioTwitter(input);
			result = udao.getLoginTwitter(input.nombre, input.twitterId);
		}
		return result;
		//return udao.getLoginTwitter(input.nombre, input.twitterId);
	}

	@Override
	public Usuario pasarAPremium(PayPalUserBean input){//String nick, String paypalTransactionId){
		return udao.pasarAPremium(input.nick, input.paypalTransactionId);
	}

	@Override
	public Usuario pasarAFreemium(TwitterUserBean input){//String nick, String paypalTransactionId){
		return udao.pasarAFreemium(input.nick);
	}

	@Override
	public Boolean geolocalizar(String nick, String geolocation) {
		return udao.geolocalizar(nick, geolocation);
	}

	@Override
	public Usuario getUsuario(int id) {
		return udao.buscar(id);
	}

	
}
