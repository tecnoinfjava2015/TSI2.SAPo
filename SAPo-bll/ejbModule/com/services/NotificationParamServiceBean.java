package com.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.entities.sql.NotificationsParam;
import com.entities.sql.dao.NotificationsParamDAO;

@Stateless
public class NotificationParamServiceBean implements NotificationParamServiceLocal{

	@EJB
	private NotificationsParamDAO noDAO;

	@Override
	public NotificationsParam ceateNotification(NotificationsParam newNot) {
		return noDAO.createNotification(newNot);
	}

	@Override
	public List<NotificationsParam> getNotificationList(int VSId) {
		return noDAO.getNotificationByVSId(VSId);
	}

	@Override
	public NotificationsParam getNotificationByProduct(int VSId,
			String barcode) {
		return noDAO.getNotificationByProduct(barcode, VSId);
	}
}
