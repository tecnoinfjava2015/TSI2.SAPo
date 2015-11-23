package com.bl;

import java.util.List;

import org.bson.types.ObjectId;

import com.entities.mongo.GenericProduct;
import com.entities.mongo.dao.GenericProductDAO;
import com.services.interfaces.IGenericProductBL;


public class GenericProductBL implements IGenericProductBL {

	private final GenericProductDAO dao = new GenericProductDAO();

	public GenericProductBL() {
	}	

	@Override
	public GenericProduct createGenericProduct(GenericProduct gProduct) {
		if (!(gProduct == null)) {
			ObjectId gProductId = dao.insert(gProduct);
			return dao.getById(gProductId);
		}
		return null;
	}

	@Override
	public GenericProduct updateGenericProduct(GenericProduct gProduct) {
		if (!(gProduct == null)) {
			ObjectId gProductId = dao.insert(gProduct);
			return dao.getById(gProductId);
		}
		return null;
	}

	@Override
	public GenericProduct getGenericProductByBarCode(String barcode) {
		if (!(barcode.isEmpty())) {
			return dao.getByBarCode(barcode);
		}
		return null;
	}

	@Override
	public GenericProduct getGenericProductByName(String name) {
		if (!(name.isEmpty())) {
			return dao.getByName(name);
		}
		return null;
	}

	@Override
	public List<GenericProduct> getAllGenericProducts() {
		return dao.getAllGenricProducts();
	}

	@Override
	public void deleteGenericProduct(String barcode) {
		GenericProduct gProduct = dao.getByBarCode(barcode);
		dao.deleteGenericProduct(gProduct.getId());		
	}
	
	@Override
	public List<GenericProduct> getGenericsBarcodeAndName(String search, int limit){
		return dao.getGenericsBarcodeAndName(search, limit);		
	}


}
