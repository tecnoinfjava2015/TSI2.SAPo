//package com.services;
//
//import java.util.List;
//
//import javax.ejb.EJB;
//import javax.ejb.Stateless;
//
//import com.entities.sql.ShoppingListItem;
//import com.entities.mongo.Product;
//import com.entities.sql.dao.ShoppingListDAO;;
//
//@Stateless
//public class ShoppingListServiceBean implements ShoppingListServiceLocal {
//
//	@EJB
//	private final ShoppingListDAO SListDAO = new ShoppingListDAO();
//	
//	@Override
//	public List<ShoppingListItem> getShoppingList(long virtualStorageId) {
//		return SListDAO.returnShoppingList(virtualStorageId);
//	}
//
//	@Override
//	public ShoppingListItem getShoppingListItem(long virtualStorageId,
//			String barcode) {
//		return SListDAO.returnShoppingListItem(virtualStorageId, barcode);
//	}
//
//	@Override
//	public List<Product> getShoppingListRecomendations(long virtualStorageId) {
//		return SListDAO.getRecomendations(virtualStorageId);
//	}
//
//	@Override
//	public ShoppingListItem createShoppingListItem(ShoppingListItem item) {
//		return SListDAO.createShoppingListItem( item );
//	}
//
//	@Override
//	public void updateShoppingListItem(ShoppingListItem item) {
//		SListDAO.updateShoppingListItem( item );
//	}
//
//	@Override
//	public void deleteShoppingListItem(long virtualStorageId, String barcode) {
//		SListDAO.deleteItem(virtualStorageId, barcode);
//	}
//
//}
