package com.services.interfaces;

import java.util.List;

import com.entities.mongo.GenericProduct;

public interface IGenericProductBL {
	public GenericProduct createGenericProduct(GenericProduct gProduct);
	public GenericProduct updateGenericProduct(GenericProduct gProduct);
	public GenericProduct getGenericProductByBarCode(String barcode);
	public GenericProduct getGenericProductByName(String name);
	public List<GenericProduct> getAllGenericProducts();
	public void deleteGenericProduct(String barcode);
	public List<GenericProduct> getGenericsBarcodeAndName(String search, int limit);
}
