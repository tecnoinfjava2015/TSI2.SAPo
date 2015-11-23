package com.entities.sql.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import com.entities.sql.ProductMovement;
import com.entities.mongo.Product;
import com.entities.mongo.dao.ProductDAO;

@Stateless 
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ProductMovementDAO {
	@PersistenceContext(unitName="SAPo-dal")
	private EntityManager em;
	
	public ProductMovement createMovement(ProductMovement productMovementAux){
		ProductDAO PDAO = new ProductDAO();
		Product Paux = PDAO.getByBarCode(productMovementAux.getVirtualStorageId(), productMovementAux.getBarCode());
		double doAux = Paux.getStock() + productMovementAux.getStock();
		productMovementAux.setUnit(Paux.getUnit());
		productMovementAux.setProductName(Paux.getName());
		Calendar cal = productMovementAux.getDateMov();
		if(cal == null) { //desde Android la fecha vine vacía
			Calendar now = Calendar.getInstance();
			productMovementAux.setDateMov(now);
		}
		
		if(doAux < 0) throw new IllegalArgumentException("The resultant stock cannot be negative.");
		
		PDAO.updateStock(doAux, productMovementAux.getVirtualStorageId(), productMovementAux.getBarCode());
		
		if( productMovementAux.getFinalPrice() != 0 ){
			PDAO.updatePrice(productMovementAux.getVirtualStorageId(),
							productMovementAux.getBarCode(),
							productMovementAux.getFinalPrice());			
		}
		
		em.persist(productMovementAux);
		em.flush();
		return productMovementAux;
	}
	
	public long getMovementQuantity(long VSId){
		TypedQuery<Long> query =  em.createQuery("SELECT COUNT(m) FROM ProductMovement m WHERE m.virtualStorageId=:VSId", Long.class)
				.setParameter("VSId", VSId);
		return query.getSingleResult();
	}
	
//	public long getMovimentQuantityBetweenDates(long VSId, Calendar startD, Calendar endD){
//		TypedQuery<Long> query =  em.createQuery("SELECT COUNT(m) FROM ProductMovement m WHERE m.dateMov BETWEEN :startD AND :endD AND m.virtualStorageId=:VSId AND m.stock <> 0", Long.class)
//				.setParameter("startD", startD, TemporalType.DATE).setParameter("endD", endD, TemporalType.DATE).setParameter("VSId", VSId);
//		return query.getSingleResult();
//	}
	
	public long getMovimentQuantityBetweenDates(long VSId, Date startD, Date endD){
		TypedQuery<Long> query =  em.createQuery("SELECT COUNT(m) FROM ProductMovement m WHERE m.dateMov BETWEEN :startD AND :endD AND m.virtualStorageId=:VSId AND m.stock <> 0", Long.class)
				.setParameter("startD", startD, TemporalType.DATE).setParameter("endD", endD, TemporalType.DATE).setParameter("VSId", VSId);
		return query.getSingleResult();
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
	public List<ProductMovement> getWhereStockChangeBetweenDates(long VSId, String bCode, Calendar startD, Calendar endD){
		Query query =  em.createQuery("SELECT m FROM ProductMovement m WHERE m.dateMov BETWEEN :startD AND :endD AND m.virtualStorageId=:VSId AND m.barCode=:bCode AND m.stock <> 0")
				.setParameter("startD", startD, TemporalType.DATE).setParameter("endD", endD, TemporalType.DATE).setParameter("VSId", VSId).setParameter("bCode", bCode);
		List<ProductMovement> PMList = (List<ProductMovement>) query.getResultList();
		return PMList;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductMovement> getBetweenDates(long VSId, Calendar startD, Calendar endD){
		Query query =  em.createQuery("SELECT m FROM ProductMovement m WHERE m.dateMov BETWEEN :startD AND :endD AND m.virtualStorageId=:VSId AND m.stock > 0")
				.setParameter("startD", startD, TemporalType.DATE).setParameter("endD", endD, TemporalType.DATE).setParameter("VSId", VSId);
		List<ProductMovement> PMList = (List<ProductMovement>) query.getResultList();
		return PMList;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductMovement> getMovementsByUser(int usrID){
		Query query =  em.createQuery("SELECT m FROM ProductMovement m WHERE m.userID=:usrID")
				.setParameter("usrID", usrID);
		List<ProductMovement> PMList = (List<ProductMovement>) query.getResultList();
		return PMList;
	}

	public long getMovimientosStockProduto(long vSId, String barcode, Calendar startD, Calendar endD) {
		Query query =  em.createQuery("SELECT m FROM ProductMovement m WHERE m.dateMov BETWEEN :startD AND :endD AND m.virtualStorageId=:VSId AND m.barCode=:bCode")
				.setParameter("startD", startD, TemporalType.DATE)
				.setParameter("endD", endD, TemporalType.DATE)
				.setParameter("VSId", vSId)
				.setParameter("bCode", barcode);
		int resultado = 0;
		List<ProductMovement> PMList = (List<ProductMovement>) query.getResultList();
		for (ProductMovement pm : PMList){
			resultado+= pm.getStock();
		}
		return resultado;
	}

	public Calendar getoFechaCreadoProduto(long virtualStorageId, String barCode) {
		Query query =  em.createQuery("SELECT m FROM ProductMovement m WHERE m.virtualStorageId=:VSId AND m.barCode=:bCode")
				.setParameter("VSId", virtualStorageId)
				.setParameter("bCode", barCode);
		int resultado = 0;
		List<ProductMovement> PMList = (List<ProductMovement>) query.getResultList();
		return PMList.get(0).getDateMov();
	}
}
