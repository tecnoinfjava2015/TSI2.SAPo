package com.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.entities.sql.ProductMovement;
import com.entities.sql.dao.ProductMovementDAO;
import com.utilities.IntervalDates;

@Stateless
public class ProductMovementServiceBean implements ProductMovementServiceLocal{

	@EJB
	private ProductMovementDAO pmdao;

	@Override
	public ProductMovement createMovement(ProductMovement productMovementAux) {
		return pmdao.createMovement(productMovementAux);
	}
	
	@Override
	public List<ProductMovement> getByUserAndAV(long VSId, int userID) {
		return pmdao.getByUserAndAV(VSId, userID);
	}

	@Override
	public long getMovementQuantity(long VSId) {
		return pmdao.getMovementQuantity(VSId);
	}

	@Override
	public List<ProductMovement> getMovementByUser(int usrID) {
		return pmdao.getMovementsByUser(usrID);
	}

	@Override
	public long getMovementQuantityBetweenDates(long VSId, IntervalDates dates) {
		return pmdao.getMovimentQuantityBetweenDates(VSId, dates.getDate1(), dates.getDate2());
	}

	@Override
	public List<ProductMovement> getByProductAndAV(long VSId, String barcode) {
		return pmdao.getByProductAndAV(VSId, barcode);
	}

	@Override
	public List<ProductMovement> getByAV(long VSId) {
		return pmdao.getByAV(VSId);
	}

	@Override
	public List<ProductMovement> getWherePriceChange(long VSId, String barcode) {
		return pmdao.getWherePriceChange(VSId, barcode);
	}

	@Override
	public List<ProductMovement> getWhereStockChangeByProduct(long VSId,
			String barcode) {
		return pmdao.getWhereStockChangeByProduct(VSId, barcode);
	}

	@Override
	public List<ProductMovement> getWhereStockChangeBetweenDates(long VSId,
			String barcode, IntervalDates dates) {
		return pmdao.getWhereStockChangeBetweenDates(VSId, barcode, dates.getDate1(), dates.getDate2());
	}
}