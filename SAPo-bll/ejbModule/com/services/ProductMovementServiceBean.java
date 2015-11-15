package com.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Path;
import com.entities.sql.ProductMovement;
import com.entities.sql.dao.ProductMovementDAO;

@Stateless
public class ProductMovementServiceBean implements ProductMovementServiceLocal{

	@EJB
	private ProductMovementDAO pmdao;

	@Override
	public List<ProductMovement> getByUserAndAV(long VSId, int userID) {
		return pmdao.getByUserAndAV(VSId, userID);
	}
}