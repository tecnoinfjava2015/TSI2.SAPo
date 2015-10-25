package com.entities.sql.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.entities.sql.Usuario;
import com.entities.sql.VirtualStorage;

@Stateless 
@TransactionManagement(TransactionManagementType.CONTAINER)
public class VirtualStorageDAO {	
	@PersistenceContext(unitName="SAPo-dal")
	EntityManager em;
	
	public void modificarVS(VirtualStorage vs){
		em.merge(vs);
	}

	public Boolean bajaVSPorNombre(String nombre){
		Query query =  em.createQuery("SELECT v FROM VirtualStorage v WHERE v.nombre=:nombre ")
		.setParameter("nombre", nombre);
		VirtualStorage virtualStorage = (VirtualStorage) query.getResultList().get(0);
		virtualStorage.setEnabled(false);
		em.merge(virtualStorage);
		return true;
	}
	
	
	public VirtualStorage buscarVSporNombre(String nombre){
		return em.find(VirtualStorage.class, nombre);		
	}
	
	public VirtualStorage buscarVSporID(int id){
		return em.find(VirtualStorage.class, id);		
	}
	
	
	public String insertVS(VirtualStorage vs, int idCreador){
		UserDAO udao = null;
		Usuario creador = udao.buscarID(idCreador);
		vs.setCreador(creador);
		em.persist(vs);
		em.flush(); 
		return vs.getNombre();
	}

	public List<VirtualStorage> listarVS() {
		Query query =  em.createQuery("SELECT vs FROM VirtualStorage vs ");
		List<VirtualStorage>  retorno = query.getResultList();
		return retorno;
	}
	
	public List<VirtualStorage> listarVSBorrados() {
		Query query =  em.createQuery("SELECT vs FROM VirtualStorage vs WHERE vs.enabled=:enabled ")
		.setParameter("enabled", false);
		List<VirtualStorage>  retorno = query.getResultList();
		return retorno;
	}

	public List<VirtualStorage> listarVSHabilitados() {
		Query query =  em.createQuery("SELECT vs FROM VirtualStorage vs WHERE vs.enabled=:enabled ")
		.setParameter("enabled", true);
		List<VirtualStorage>  retorno = query.getResultList();
		return retorno;
	}	
}