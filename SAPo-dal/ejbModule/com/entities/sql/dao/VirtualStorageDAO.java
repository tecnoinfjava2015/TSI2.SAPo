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
	
	public void updateVS(VirtualStorage vs){
		em.merge(vs);
	}

	public Boolean deleteVSByName(String name){
		Query query =  em.createQuery("SELECT v FROM VirtualStorage v WHERE v.nombre=:name ")
		.setParameter("nombre", name);
		VirtualStorage virtualStorage = (VirtualStorage) query.getResultList().get(0);
		virtualStorage.setEnabled(false);
		em.merge(virtualStorage);
		return true;
	}
	
	
	public VirtualStorage searchVSByName(String name){
		return em.find(VirtualStorage.class, name);		
	}
	
	public VirtualStorage buscarVSporID(int id){
		return em.find(VirtualStorage.class, id);		
	}
	
	
	public VirtualStorage createVS(VirtualStorage vs, int ownerId){
		UserDAO udao = new UserDAO();
		Usuario owner = udao.buscarID(ownerId);
		vs.setOwner(owner);
		em.persist(vs);
		em.flush(); 
		return vs;
	}

	public List<VirtualStorage> getAllVS() {
		Query query =  em.createQuery("SELECT vs FROM VirtualStorage vs ");
		List<VirtualStorage>  retorno = query.getResultList();
		return retorno;
	}
	
	public List<VirtualStorage> getDisabledVS() {
		Query query =  em.createQuery("SELECT vs FROM VirtualStorage vs WHERE vs.enabled=:enabled ")
		.setParameter("enabled", false);
		List<VirtualStorage>  retorno = query.getResultList();
		return retorno;
	}

	public List<VirtualStorage> getEnabledVS() {
		Query query =  em.createQuery("SELECT vs FROM VirtualStorage vs WHERE vs.enabled=:enabled ")
		.setParameter("enabled", true);
		List<VirtualStorage>  retorno = query.getResultList();
		return retorno;
	}	
}