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

import com.entities.sql.Bill;
import com.entities.sql.Document;

@Stateless 
@TransactionManagement(TransactionManagementType.CONTAINER)
public class BillDAO {
	@PersistenceContext(unitName="SAPo-dal")
	EntityManager em;
	
	public Bill createBill(Bill billAux){
		em.persist(billAux);
		em.flush();
		return billAux;
	}
	
	public Bill getOneById(long billId){
		Query query =  em.createQuery("SELECT b FROM Bill b WHERE b.id=:billId")
		.setParameter("billId", billId);
		Bill bill = (Bill) query.getResultList().get(0);
		return bill;
	}
	
	@SuppressWarnings("unchecked")
	public List<Bill> getByVirtualStorageId(long VSId){
		Query query =  em.createQuery("SELECT b FROM Bill b WHERE b.virtualStorageId=:VSId")
		.setParameter("VSId", VSId);
		List<Bill> billList = ((List<Bill>) query.getResultList());
		return billList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Bill> getByDocument(Document docAux){
		Query query =  em.createQuery("SELECT b FROM Bill b WHERE b.document=:docAux")// *ver*
		.setParameter("docAux", docAux);
		List<Bill> billList = ((List<Bill>) query.getResultList());
		return billList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Bill> getByOrigin(String ori, long VSId){
		Query query =  em.createQuery("SELECT b FROM Bill b WHERE b.origin=:ori AND b.virtualStorageId=:VSId")
		.setParameter("ori", ori).setParameter("VSId", VSId);
		List<Bill> billList = ((List<Bill>) query.getResultList());
		return billList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Bill> getByDestination(String des, long VSId){
		Query query =  em.createQuery("SELECT b FROM Bill b WHERE b.destination=:des AND b.virtualStorageId=:VSId")
		.setParameter("des", des).setParameter("VSId", VSId);
		List<Bill> billList = ((List<Bill>) query.getResultList());
		return billList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Bill> getBetweenDates(Calendar startD, Calendar endD, long VSId){
		Query query =  em.createQuery("SELECT b FROM Bill b WHERE b.date BETWEEN :startD AND :endD AND b.virtualStorageId=:VSId")
		.setParameter("startD", startD, TemporalType.DATE).setParameter("endD", endD, TemporalType.DATE).setParameter("VSId", VSId);
		List<Bill> billList = ((List<Bill>) query.getResultList());
		return billList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Bill> getBetweenAmounts(double startA, double endA, long VSId){
		Query query =  em.createQuery("SELECT b FROM Bill b WHERE b.amount BETWEEN :startA AND :endA AND b.virtualStorageId=:VSId")
		.setParameter("startA", startA).setParameter("endA", endA).setParameter("VSId", VSId);
		List<Bill> billList = ((List<Bill>) query.getResultList());
		return billList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Bill> getGeaterAmount(double GoshujinSama, long VSId){
		Query query =  em.createQuery("SELECT b FROM Bill b WHERE b.amount>=:GoshujinSama AND b.virtualStorageId=:VSId")
		.setParameter("GoshujinSama", GoshujinSama).setParameter("VSId", VSId);
		List<Bill> billList = ((List<Bill>) query.getResultList());
		return billList;
	}
	
//	public Bill updateBill(Bill billAux){
//		em.merge(billAux);
//		em.flush();
//		return billAux;
//	}
}
