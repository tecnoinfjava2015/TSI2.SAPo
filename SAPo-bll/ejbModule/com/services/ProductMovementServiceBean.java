//package com.services;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.List;
//
//import javax.ejb.EJB;
//import javax.ejb.Stateless;
//
//import com.entities.sql.ProductMovement;
//import com.entities.sql.dao.ProductMovementDAO;
//
//@Stateless
//public class ProductMovementServiceBean implements ProductMovementServiceLocal{
//
//	@EJB
//	private final ProductMovementDAO PMovDAO = new ProductMovementDAO();
//
//	@Override
//	public ProductMovement createMovement(ProductMovement productMovementAux) {
//		return PMovDAO.createMovement(productMovementAux);
//	}
//
//	@Override
//	public List<ProductMovement> getByProductAndAV(long virtualStorageId,
//			String barcode) {
//		return PMovDAO.getByProductAndAV(virtualStorageId, barcode);
//	}
//
//	@Override
//	public List<ProductMovement> getByAV(long virtualStorageId) {
//		return PMovDAO.getByAV(virtualStorageId);
//	}
//
//	@Override
//	public List<ProductMovement> getWherePriceChange(long virtualStorageId,
//			String barcode) {
//		return PMovDAO.getWherePriceChange(virtualStorageId, barcode);
//	}
//
//	@Override
//	public List<ProductMovement> getByUserAndAV(long virtualStorageId,
//			int usrID) {
//		return PMovDAO.getByUserAndAV(virtualStorageId, usrID);
//	}
//
//	@Override
//	public List<ProductMovement> getWhereStockChangeByProduct(
//			long virtualStorageId, String barcode ) {
//		return PMovDAO.getWhereStockChangeByProduct(virtualStorageId, barcode);
//	}
//
//	@Override
//	public List<ProductMovement> getWhereStockChangeBetweenDates(
//		long virtualStorageId, String barcode, String fecha1, String fecha2) {
//		
//		Calendar cal1 = Calendar.getInstance();
//		Calendar cal2 = Calendar.getInstance();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//		try {
//			cal1.setTime(sdf.parse("2015-11-09T03:00:00.000Z"));
//			cal2.setTime(sdf.parse("2014-11-09T03:00:00.000Z"));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}// all done
//		return PMovDAO.getWhereStockChangeBetweenDates(virtualStorageId, barcode, cal1, cal2);
//	}
//}
