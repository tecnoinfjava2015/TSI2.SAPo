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
	EntityManager em;
	
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
	
	
	public Usuario buscar(String nombre){
		return em.find(Usuario.class, nombre);		
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

}
