package com.services.interfaces;

import java.util.List;

import com.entities.mongo.Product;

public interface IProductBL {
	public Product createProduct(Product product);
	public Product updateProduct(Product product);
	public Product getProductByBarCode(long virtualStorageId, String barcode);
	public Product getProductByName(long virtualStorageId, String name);
	public List<Product> getAllProducts(long virtualStorageId);
	public List<Product> getAllProductsPaginated(long virtualStorageId,int offset, int limit);
	public List<Product> getProductsByCategory(long virtualStorageId, String category);
	public List<Product> getProductsByCategoryPaginated(long virtualStorageId, String category, int offset, int limit);
	public List<Product> getProductsByCategories(long virtualStorageId, List<Integer> categories, int offset, int limit);
	public List<Product> getProductsByCategoriesOr(long virtualStorageId, List<Integer> categories, int offset, int limit);
	public void deleteProduct(long virtualStorageId, String barcode);
	public boolean estaEnLista(Product p, List<Product> listaProductosTemp);
	public double getVSVaue(long virtualStorageId);
	public List<Product> getProductsBarCodeAndName(long virtualStorageId,String search, int limit); 
}
