package com.services;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.entities.sql.NotificationsParam;

@Local
@Path("/{virtualStorageId}/notification")
public interface NotificationParamServiceLocal {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public NotificationsParam ceateNotification(NotificationsParam newNot);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<NotificationsParam> getNotification(@PathParam("virtualStorageId") int VSId);
	
	@GET
	@Path("/one")
	@Produces(MediaType.APPLICATION_JSON)
	public NotificationsParam getNotificationByProduct(@PathParam("virtualStorageId") int VSId, @QueryParam("barcode") String barcode);
}
