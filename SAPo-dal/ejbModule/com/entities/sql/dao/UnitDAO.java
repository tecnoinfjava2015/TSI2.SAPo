package com.entities.sql.dao;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.entities.sql.Unit;

@Stateless 
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UnitDAO {
	@PersistenceContext(unitName="SAPo-dal")
	EntityManager em;
	
	public Unit createDocument(Unit unitAux){
		em.persist(unitAux);
		em.flush();
		return unitAux;
	}
	
	public Unit getOneById(long unitId){
		Query query =  em.createQuery("SELECT u FROM Unit u WHERE u.id=:unitId")
		.setParameter("unitId", unitId);
		Unit unit = (Unit) query.getResultList().get(0);
		return unit;
	}
	
	public Unit getOneByNameAndVS(String name, long VSId){
		Query query =  em.createQuery("SELECT u FROM Unit u WHERE u.name=:name AND d.virtualStorageId=:VSId")
		.setParameter("name", name).setParameter("VSId", VSId);
		Unit unit = (Unit) query.getResultList().get(0);
		return unit;
	}
	
	public Unit getOneByName(String name){
		Query query =  em.createQuery("SELECT u FROM Unit u WHERE u.name=:name AND d.virtualStorageId is NULL")
		.setParameter("name", name);
		Unit unit = (Unit) query.getResultList().get(0);
		return unit;
	}
	
	public Unit getOneByAbbreviationAndVS(String abbreviation, long VSId){
		Query query =  em.createQuery("SELECT u FROM Unit u WHERE u.abbreviation=:abbreviation AND d.virtualStorageId=:VSId")
		.setParameter("abbreviation", abbreviation).setParameter("VSId", VSId);
		Unit unit = (Unit) query.getResultList().get(0);
		return unit;
	}
	
	public Unit getOneByAbbreviation(String abbreviation){
		Query query =  em.createQuery("SELECT u FROM Unit u WHERE u.abbreviation=:abbreviation AND d.virtualStorageId is NULL")
		.setParameter("abbreviation", abbreviation);
		Unit unit = (Unit) query.getResultList().get(0);
		return unit;
	}
}
