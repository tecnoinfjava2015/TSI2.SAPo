package com.entities.sql.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.entities.mongo.Product;
import com.entities.sql.NotificationsParam;

@Stateless 
@TransactionManagement(TransactionManagementType.CONTAINER)
public class NotificationsParamDAO {

	@PersistenceContext(unitName="SAPo-dal")
	private EntityManager em;
	
	public NotificationsParam createNotification(NotificationsParam newNot){
		if(newNot.equals(null)) throw new NullPointerException("La notificacion esta vacia");		
		NotificationsParam notificationAux = getNotificationByProduct(newNot.getBarrcode(), newNot.getVSId());
		if(notificationAux.getBarrcode() == newNot.getBarrcode() && notificationAux.getVSId() == newNot.getVSId()){
			em.merge(newNot);
			em.flush();
			return newNot;
		}
		else{
			em.persist(newNot);
			em.flush();
			return newNot;
		}
	}
	
	public NotificationsParam getNotificationByProduct(String barcode, int VSId){
		if(exist(barcode, VSId)){
			Query query =  em.createQuery("SELECT n FROM NotificationsParam n WHERE n.VSId=:VSId AND n.barcode=:barcode")
					.setParameter("VSId", VSId).setParameter("barcode", barcode);
			NotificationsParam notification = (NotificationsParam) query.getResultList().get(0);
			return notification;
		}
		return null;
	}
	
	public boolean exist(String barcode, int VSId){
		TypedQuery<Long> query = em.createQuery("SELECT n FROM NotificationsParam n WHERE n.barcode=:barcode AND n.VSId=:VSId", long.class)
				.setParameter("barcode", barcode).setParameter("VSId", VSId);
		return (query.getSingleResult() > 0);
	}
	
	public void modificar(NotificationsParam notification){
		if(notification.equals(null)) throw new NullPointerException("La notificacion esta vacia");
		if(!exist(notification.getBarrcode(),notification.getVSId())) throw new IllegalArgumentException("No existe una notificacion para este producto");
		em.merge(notification);
	}
	
	public void enableOrDisable(String barcode, int VSId){
		NotificationsParam notification = getNotificationByProduct(barcode, VSId);
		if(notification.isActive())
			notification.setActive(false);
		else
			notification.setActive(true);
	}
	
	public void changeNotificationState(Product prod){
		if(exist(prod.getBarCode(), (int)prod.getVirtualStorageId())){
			NotificationsParam notification = getNotificationByProduct(prod.getBarCode(), (int)prod.getVirtualStorageId());
			if(!(notification == null) && notification.getMinStock() >= prod.getStock()){
				//ver
				notification.setActive(true);
			}else{
				notification.setActive(false);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<NotificationsParam> getNotificationByVSId(int VSId){
			Query query =  em.createQuery("SELECT n FROM NotificationsParam n WHERE n.VSId=:VSId")
					.setParameter("VSId", VSId);
			List<NotificationsParam> notification = (List<NotificationsParam>) query.getResultList();
			return notification;
	}
}