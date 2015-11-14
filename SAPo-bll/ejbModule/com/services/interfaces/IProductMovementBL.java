package com.services.interfaces;

import java.util.Calendar;
import java.util.List;

import com.entities.sql.ProductMovement;

public interface IProductMovementBL {

	public ProductMovement createMovement(ProductMovement productMovementAux);
	
	public long getMovementQuantity(long VSId);
	
	public long getMovimentQuantityBetweenDates(long VSId, Calendar startD, Calendar endD);

	public List<ProductMovement> getByProductAndAV(long virtualStorageId,
			String barcode);

	public List<ProductMovement> getByAV(long virtualStorageId);

	public List<ProductMovement> getWherePriceChange(long virtualStorageId,
			String barcode);

	public List<ProductMovement> getByUserAndAV(long virtualStorageId, int usrID);

	public List<ProductMovement> getWhereStockChangeByProduct(
			long virtualStorageId, String barcode);

	public List<ProductMovement> getWhereStockChangeBetweenDates(
			long virtualStorageId, String barcode, Calendar cal1, Calendar cal2);
	
	public List<ProductMovement> getMovementsByUser(long VSId, int usrID);
}
