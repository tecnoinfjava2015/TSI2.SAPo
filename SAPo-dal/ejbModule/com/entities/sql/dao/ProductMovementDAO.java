package com.entities.sql.dao;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import com.entities.sql.ProductMovement;

@Stateless 
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ProductMovementDAO {
	@PersistenceContext(unitName="SAPo-dal")
	private EntityManager em;
	
	public ProductMovement createBill(ProductMovement productMovementAux){
		em.persist(productMovementAux);
		em.flush();
		return productMovementAux;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductMovement> getByProductAndAV(long VSId, String bCode){
		Query query =  em.createQuery("SELECT m FROM ProductMovement m WHERE m.virtualStorageId=:VSId AND m.barCode=:bCode")
				.setParameter("VSId", VSId).setParameter("bCode", bCode);
		List<ProductMovement> PMList = (List<ProductMovement>) query.getResultList();
		return PMList;		
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductMovement> getByAV(long VSId){
		Query query =  em.createQuery("SELECT m FROM ProductMovement m WHERE m.virtualStorageId=:VSId")
				.setParameter("VSId", VSId);
		List<ProductMovement> PMList = (List<ProductMovement>) query.getResultList();
		return PMList;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductMovement> getWherePriceChange(long VSId, String bCode){
		Query query =  em.createQuery("SELECT m FROM ProductMovement m WHERE m.virtualStorageId=:VSId AND m.barCode=:bCode AND m.initialPrice <> m.finalPrice")
				.setParameter("VSId", VSId).setParameter("bCode", bCode);
		List<ProductMovement> PMList = (List<ProductMovement>) query.getResultList();
		return PMList;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductMovement> getByUserAndAV(long VSId, int UsrId){
		Query query =  em.createQuery("SELECT m FROM ProductMovement m WHERE m.virtualStorageId=:VSId AND m.userID=:UsrId")
				.setParameter("VSId", VSId).setParameter("UsrId", UsrId);
		List<ProductMovement> PMList = (List<ProductMovement>) query.getResultList();
		return PMList;		
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductMovement> getWhereStockChangeByProduct(long VSId, String bCode){
		Query query =  em.createQuery("SELECT m FROM ProductMovement m WHERE m.virtualStorageId=:VSId AND m.barCode=:bCode AND m.stock <> 0")
				.setParameter("VSId", VSId).setParameter("bCode", bCode);
		List<ProductMovement> PMList = (List<ProductMovement>) query.getResultList();
		return PMList;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductMovement> getWhereStockChangeBetweenDates(Calendar startD, Calendar endD, long VSId, String bCode){
		Query query =  em.createQuery("SELECT m FROM ProductMovement m WHERE m.dateMov BETWEEN :startD AND :endD AND m.virtualStorageId=:VSId AND m.barCode=:bCode AND m.stock <> 0")
				.setParameter("startD", startD, TemporalType.DATE).setParameter("endD", endD, TemporalType.DATE).setParameter("VSId", VSId).setParameter("bCode", bCode);
		List<ProductMovement> PMList = (List<ProductMovement>) query.getResultList();
		return PMList;
	}
}
