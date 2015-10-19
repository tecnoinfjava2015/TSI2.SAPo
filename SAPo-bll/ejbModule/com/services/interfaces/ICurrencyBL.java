package com.services.interfaces;

import java.util.List;

import com.entities.mongo.Currency;

public interface ICurrencyBL {
	public Currency createCurrency(Currency currency);

	public Currency updateCurrency(Currency currency);

	public Currency getCurrency(long virtualStorageId, int localId);	
	
	public void deleteCurrency(long virtualStorageId, int localId);

	public List<Currency> getAllCurrencies(long virtualStorageId, int offset, int limit);


}
