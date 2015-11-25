package com.bl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.entities.mongo.Product;
import com.entities.sql.NotificationsParam;
import com.entities.sql.dao.NotificationsParamDAO;
import com.services.interfaces.INotificationsParamBL;

@Stateless
public class NotificationsParamBL implements INotificationsParamBL{
	@EJB
	NotificationsParamDAO noDAO;
	
	@Override
	public void changeNotificationState(Product prod){
		if(!(prod == null)){
			noDAO.changeNotificationState(prod);
		}
	}

	@Override
	public List<NotificationsParam> getNotifications(int VSId) {
		return noDAO.getNotificationByVSId(VSId);
	}
	
	@Override
	public NotificationsParam createNotification(NotificationsParam newNot){
		return noDAO.createNotification(newNot);
	}

	@Override
	public NotificationsParam getOneByVSIdAndProduct(int VSId, String barcode) {
		return noDAO.getNotificationByProduct(barcode, VSId);
	}
}
