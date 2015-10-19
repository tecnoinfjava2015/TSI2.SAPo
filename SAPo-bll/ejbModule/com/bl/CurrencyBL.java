package com.bl;

import java.util.List;

import org.bson.types.ObjectId;

import com.entities.mongo.Category;
import com.entities.mongo.Currency;
import com.entities.mongo.dao.CurrencyDAO;
import com.services.interfaces.ICurrencyBL;

public class CurrencyBL implements ICurrencyBL {
	
	CurrencyDAO dao = new CurrencyDAO();

	@Override
	public Currency createCurrency(Currency currency) {
		if(!(currency==null)){
			ObjectId currencyId = dao.persist(currency);
			return dao.getById(currencyId);
		}		
		return null;
	}

	@Override
	public Currency updateCurrency(Currency currency) {
		if(!(currency==null)){
			Currency aux = dao.getByLocalId(currency.getVirtualStorageId(), currency.getLocalId());
			currency.setId(aux.getId());
			ObjectId auxId = dao.persist(currency);
			return dao.getById(auxId);
		}			
		return null;
	}

	@Override
	public Currency getCurrency(long virtualStorageId, int localId) {
		if((virtualStorageId>0) && (localId>0)){
			return dao.getByLocalId(virtualStorageId, localId);
		}
		return null;
	}


	@Override
	public void deleteCurrency(long virtualStorageId, int localId) {
		if((virtualStorageId>0) && (localId>0)){
			dao.remove(virtualStorageId, localId);
		}		
	}

	@Override
	public List<Currency> getAllCurrencies(long virtualStorageId, int offset,
			int limit) {
		if(virtualStorageId>0){
			return dao.getAllCurrencies(virtualStorageId,offset,limit);
		}		
		return null;
	}

}
