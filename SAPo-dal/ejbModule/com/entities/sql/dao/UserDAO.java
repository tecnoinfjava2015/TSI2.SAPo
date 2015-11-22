package com.entities.sql.dao;



import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.entities.sql.Usuario;


@Stateless 
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UserDAO {
	
	@PersistenceContext(unitName="SAPo-dal")
	private EntityManager em;
	
	public void modificar(Usuario u){
		em.merge(u);
	}

	public Boolean baja(String nick){
		Query query =  em.createQuery("SELECT u FROM Usuario u WHERE u.nick=:nick ")
		.setParameter("nick", nick);
		Usuario usuario = (Usuario) query.getResultList().get(0);
		usuario.setEnabled(false);
		em.merge(usuario);
		return true;
	}
	
	
	public Usuario buscarID(int id) {
		Query query =  em.createQuery("SELECT u FROM Usuario u where u.id =:id");
		query.setParameter("id", id);
		List<Usuario> list = query.getResultList();
		return list.isEmpty() ? null : list.get(0);
	}	
	
	public Usuario buscar(String nombre){
		Usuario result = (Usuario) em.createQuery("SELECT u FROM Usuario u WHERE u.name = :nombre")
                .setParameter("nombre", nombre).getSingleResult();
		//return em.find(Usuario.class, nombre);
		return result;
	}
	
	public String insert(Usuario u){
		em.persist(u);
		em.flush(); 
		return u.getNick();
	}

	public List<Usuario> listarUsuarios() {
		Query query =  em.createQuery("SELECT u FROM Usuario u ");
		List<Usuario>  retorno = query.getResultList();
		 return retorno;
	}
	
	public Boolean aceptar(String nick){
		Query query =  em.createQuery("SELECT u FROM Usuario u WHERE u.nick=:nick ")
		.setParameter("nick", nick);
		Usuario usuario = (Usuario) query.getResultList().get(0);
		usuario.setAceptado(true);
		em.merge(usuario);
		return true; 
	}

	public List<Usuario> listarUsuariosBorrados() {
		Query query =  em.createQuery("SELECT u FROM Usuario u WHERE u.enabled=:enabled ")
		.setParameter("enabled", false);
		List<Usuario>  retorno = query.getResultList();
		return retorno;
	}

	public List<Usuario> listarUsuariosPendientesAceptar() {
		Query query =  em.createQuery("SELECT u FROM Usuario u WHERE u.aceptado=:aceptado ")
		.setParameter("aceptado", false);
		List<Usuario>  retorno = query.getResultList();
		return retorno;
	}

	public List<Usuario> listarUsuariosHabilitados() {
		Query query =  em.createQuery("SELECT u FROM Usuario u WHERE u.enabled=:enabled ")
		.setParameter("enabled", true);
		List<Usuario>  retorno = query.getResultList();
		return retorno;
	}	
	
	public Usuario getLogin(String nick, String password) {
		Query query =  em.createQuery("SELECT l FROM Usuario l WHERE l.nick=:nick and l.password=:password ")
		.setParameter("nick", nick)
		.setParameter("password", password);
		List<Usuario> results = query.getResultList();
		Usuario foundEntity = null;
		if(!results.isEmpty()){
		// ignores multiple results
			foundEntity = results.get(0);
		}	
		 return foundEntity;
	}
	
	public Usuario getLoginTwitter(String nick, String twitterId) {
		Query query =  em.createQuery("SELECT l FROM Usuario l WHERE l.nick=:nick and l.twitterId=:twitterId ")
		.setParameter("nick", nick)
		.setParameter("twitterId", twitterId);
		List<Usuario> results = query.getResultList();
		Usuario foundEntity = null;
		if(!results.isEmpty()){
		// ignores multiple results
			foundEntity = results.get(0);
		}	
		 return foundEntity;
	}

	public Usuario pasarAPremium(String nick, String paypalTransactionId) {
		Query query =  em.createQuery("SELECT u FROM Usuario u WHERE u.nick=:nick ")
		.setParameter("nick", nick);
		Usuario usuario = (Usuario) query.getResultList().get(0);
		usuario.setType("PREMIUM");
		usuario.setPaypalTransactionId(paypalTransactionId);
		em.merge(usuario);
		return usuario;
	}

	public Usuario pasarAFreemium(String nick) {
		Query query =  em.createQuery("SELECT u FROM Usuario u WHERE u.nick=:nick ")
		.setParameter("nick", nick);
		Usuario usuario = (Usuario) query.getResultList().get(0);
		if(!usuario.getType().equals("PREMIUM")){
			usuario.setType("FREEMIUM");			
			em.merge(usuario);
		}
		return usuario;
	}

	public Boolean geolocalizar(String nick, String geolocation) {
		Query query =  em.createQuery("SELECT u FROM Usuario u WHERE u.nick=:nick ")
		.setParameter("nick", nick);
		Usuario usuario = (Usuario) query.getResultList().get(0);
		usuario.setGeolocation(geolocation);
		em.merge(usuario);
		return true;
	}

	public Usuario buscar(int id) {
		Usuario result = (Usuario) em.createQuery("SELECT u FROM Usuario u WHERE u.id = :id")
                .setParameter("id", id).getSingleResult();
		//return em.find(Usuario.class, nombre);
		return result;
	}
}
