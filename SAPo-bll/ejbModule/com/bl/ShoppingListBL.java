package com.bl;

import java.util.List;

import com.entities.mongo.Product;
import com.entities.sql.ShoppingListItem;
import com.entities.sql.dao.ShoppingListDAO;

public class ShoppingListBL {

	private final ShoppingListDAO SListDAO = new ShoppingListDAO();
	
	public ShoppingListBL(){
		
	}

	public List<ShoppingListItem> returnShoppingList(long virtualStorageId) {
		return SListDAO.returnShoppingList(virtualStorageId);
	}

	public ShoppingListItem returnShoppingListItem(long virtualStorageId,
			String barcode) {
		return SListDAO.returnShoppingListItem(virtualStorageId, barcode);
	}

	public List<Product> getRecomendations(long virtualStorageId) {
		return SListDAO.getRecomendations(virtualStorageId);
	}

	public ShoppingListItem createShoppingListItem(ShoppingListItem item) {
		return SListDAO.createShoppingListItem( item );
	}

	public void updateShoppingListItem(ShoppingListItem item) {
		SListDAO.updateShoppingListItem( item );
	}

	public void deleteItem(long virtualStorageId, String barcode) {
		SListDAO.deleteItem(virtualStorageId, barcode);
	}
}
