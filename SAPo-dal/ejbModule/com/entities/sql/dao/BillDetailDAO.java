package com.entities.sql.dao;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.entities.sql.BillDetail;

@Stateless 
@TransactionManagement(TransactionManagementType.CONTAINER)
public class BillDetailDAO {
	@PersistenceContext(unitName="SAPo-dal")
	EntityManager em;
	
	public BillDetail createBill(BillDetail billDetailAux){
		em.persist(billDetailAux);
		em.flush();
		return billDetailAux;
	}
}
