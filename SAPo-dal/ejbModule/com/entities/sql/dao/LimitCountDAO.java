package com.entities.sql.dao;


import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.entities.sql.LimitCount;


@Stateless 
@TransactionManagement(TransactionManagementType.CONTAINER)
public class LimitCountDAO {

	@PersistenceContext(unitName="SAPo-dal")
	private EntityManager em;
	
	public void modificar(LimitCount l){
		em.merge(l);
	}

	public LimitCount buscarType(String type) {
		Query query =  em.createQuery("SELECT u FROM LimitCount u where u.type =:type");
		query.setParameter("type", type);
		List<LimitCount> list = query.getResultList();
		return list.isEmpty() ? null : list.get(0);
	}	
	
	public String insert(LimitCount l){
		em.persist(l);
		em.flush(); 
		return l.getType();
	}

	public List<LimitCount> listarLimitCount() {
		Query query =  em.createQuery("SELECT u FROM LimitCount u ");
		List<LimitCount>  retorno = query.getResultList();
		return retorno;
	}

	public Boolean baja(String type) {
		
		return true;
	}
}
