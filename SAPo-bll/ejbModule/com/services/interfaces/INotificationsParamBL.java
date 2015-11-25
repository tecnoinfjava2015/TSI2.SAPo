package com.services.interfaces;

import java.util.List;

import com.entities.mongo.Product;
import com.entities.sql.NotificationsParam;

public interface INotificationsParamBL {

	public void changeNotificationState(Product prod);
	public List<NotificationsParam> getNotifications(int VSId);
	public NotificationsParam createNotification(NotificationsParam not);
	public NotificationsParam getOneByVSIdAndProduct(int VSId, String barcode);
}
