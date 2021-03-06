package com.entities.sql.dao;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.entities.mongo.Product;
import com.entities.mongo.dao.ProductDAO;
import com.entities.sql.ShoppingListItem;

@Stateless 
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ShoppingListDAO {
	@PersistenceContext(unitName="SAPo-dal")
	private EntityManager em;
	
	public boolean existShoppingListItemForVS(String barcode, long VSId){
		Query query =  em.createQuery("SELECT s FROM ShoppingListItem s WHERE s.productBarcode=:barcode AND s.virtualStorageId=:VSId")
		.setParameter("barcode", barcode).setParameter("VSId", VSId);
		return !(query.getResultList().isEmpty());
	}
	
	//Esta funcion se fija si ya se registro la necesidad de comprar un producto, si se registro devuelve null
	public ShoppingListItem createShoppingListItem(ShoppingListItem itemAux){
		if(!existShoppingListItemForVS(itemAux.getProductBarcode(), itemAux.getVirtualStorageId())){
			em.persist(itemAux);
			em.flush();
			return itemAux;
		}
		else
		{
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ShoppingListItem> returnShoppingList(long VSId){
		Query query =  em.createQuery("SELECT s FROM ShoppingListItem s WHERE s.virtualStorageId=:VSId")
		.setParameter("VSId", VSId);
		List<ShoppingListItem> shoppingList = (List<ShoppingListItem>) query.getResultList();
		return shoppingList;
	}
	
	public ShoppingListItem returnShoppingListItem(long VSId, String barcode){
		Query query =  em.createQuery("SELECT s FROM ShoppingListItem s WHERE s.virtualStorageId=:VSId AND s.productBarcode=:barcode")
		.setParameter("VSId", VSId).setParameter("barcode", barcode);
		ShoppingListItem shoppingListItem = (ShoppingListItem) query.getResultList().get(0);
		return shoppingListItem;
	}
	
	public void updateShoppingListItem(ShoppingListItem newItem){
		Query query =  em.createQuery("SELECT s FROM ShoppingListItem s WHERE s.virtualStorageId=:VSId AND s.productBarcode=:barcode")
				.setParameter("VSId", newItem.getVirtualStorageId()).setParameter("barcode", newItem.getProductBarcode());
		ShoppingListItem shoppingListItem = (ShoppingListItem) query.getResultList().get(0);
		shoppingListItem.setQuantity(newItem.getQuantity());
		em.merge(shoppingListItem);
	}

	@SuppressWarnings("unchecked")
	public List<ShoppingListItem> getOldersThan(Calendar auxDate){
		Query query = em.createQuery("SELECT s FROM ShoppingListItem s WHERE s.creationDate < :auxDate")
		.setParameter("auxDate", auxDate);
		List<ShoppingListItem> shoppingList = (List<ShoppingListItem>) query.getResultList();
		return shoppingList;
	}
	
	public void deleteItem(long VSId, String barcode){
        try {
        	if(existShoppingListItemForVS(barcode, VSId)){
	        	ShoppingListItem item = returnShoppingListItem(VSId, barcode);
	            em.remove(item);
        	}
        }catch (Exception e){
            e.printStackTrace();
        }
    }
	
	public List<Product> getRecomendations(long VSId){
		ProductDAO PDAO = new ProductDAO();
		int stockMin = 3;
		int limit = 5;
		return PDAO.stockLessOrEqualThan(stockMin, VSId, limit);
	}
}
