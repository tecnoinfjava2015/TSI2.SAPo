package com.bl;

import java.util.Calendar;
import java.util.List;

import com.entities.sql.ProductMovement;
import com.entities.sql.dao.ProductMovementDAO;
import com.services.interfaces.IProductMovementBL;

public class ProductMovementBL implements IProductMovementBL {

	private final ProductMovementDAO PMovDAO = new ProductMovementDAO();
	
	public ProductMovementBL(){
		
	}

	public ProductMovement createMovement(ProductMovement productMovementAux) {
		return PMovDAO.createMovement(productMovementAux);
	}
	
	public long getMovementQuantity(long VSId){
		return PMovDAO.getMovementQuantity(VSId);
	}
	
	public long getMovimentQuantityBetweenDates(long VSId, Calendar startD, Calendar endD){
		return PMovDAO.getMovimentQuantityBetweenDates(VSId, startD, endD);
	}

	public List<ProductMovement> getByProductAndAV(long virtualStorageId,
			String barcode) {
		return PMovDAO.getByProductAndAV(virtualStorageId, barcode);
	}

	public List<ProductMovement> getByAV(long virtualStorageId) {
		return PMovDAO.getByAV(virtualStorageId);
	}

	public List<ProductMovement> getWherePriceChange(long virtualStorageId,
			String barcode) {
		return PMovDAO.getWherePriceChange(virtualStorageId, barcode);
	}

	public List<ProductMovement> getByUserAndAV(long virtualStorageId, int usrID) {
		return PMovDAO.getByUserAndAV(virtualStorageId, usrID);
	}

	public List<ProductMovement> getWhereStockChangeByProduct(
			long virtualStorageId, String barcode) {
		return PMovDAO.getWhereStockChangeByProduct(virtualStorageId, barcode);
	}

	public List<ProductMovement> getWhereStockChangeBetweenDates(
			long virtualStorageId, String barcode, Calendar cal1, Calendar cal2) {
		return PMovDAO.getWhereStockChangeBetweenDates(virtualStorageId, barcode, cal1, cal2);
	}
	
	public List<ProductMovement> getMovementsByUser(long VSId, int usrID){
		return PMovDAO.getMovementsByUser(VSId, usrID);
	}
}
