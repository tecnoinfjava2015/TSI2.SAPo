package com.bl;

import java.util.List;

import org.bson.types.ObjectId;

import com.entities.mongo.Product;
import com.entities.mongo.dao.ProductDAO;
import com.services.interfaces.IProductBL;

public class ProductBL implements IProductBL {

	private final ProductDAO dao = new ProductDAO();

	public ProductBL() {
	}

	@Override
	public Product createProduct(Product product) {
		if (!(product == null)) {
			ObjectId productId = dao.insert(product);
			return dao.getById(productId);
		}
		return null;
	}

	@Override
	public Product updateProduct(Product product) {
		if (!(product == null)) {
			ObjectId productId = dao.insert(product);
			return dao.getById(productId);
		}
		return null;
	}

	@Override
	public Product getProductByBarCode(long virtualStorageId, String barcode) {
		if ((virtualStorageId > 0) && !(barcode.isEmpty())) {
			return dao.getByBarCode(virtualStorageId, barcode);
		}
		return null;
	}

	@Override
	public Product getProductByName(long virtualStorageId, String name) {
		if ((virtualStorageId>0)&&!(name.isEmpty())) {
			return dao.getByName(virtualStorageId, name);
		}
		return null;
	}

	@Override
	public List<Product> getAllProducts(long virtualStorageId) {
		if (virtualStorageId>0) {
			return dao.getByVirtualStorage(virtualStorageId,0,0);
		}
		return null;
	}

	@Override
	public List<Product> getProductsByCategory(long virtualStorageId,String category) {
		if (!(category.isEmpty())&&(virtualStorageId>0)) {
			return dao.getProductsByCategory(virtualStorageId, category,0,0);
		}
		return null;
	}

	@Override
	public void deleteProduct(long virtualStorageId, String barCode) {
		Product product = dao.getByBarCode(virtualStorageId, barCode);
		dao.deleteProduct(product.getId());
	}

	@Override
	public List<Product> getAllProductsPaginated(long virtualStorageId,
			int offset, int limit) {
		if (virtualStorageId>0) {
			return dao.getByVirtualStorage(virtualStorageId,offset,limit);
		}
		return null;
	}

	@Override
	public List<Product> getProductsByCategoryPaginated(long virtualStorageId,
			String category, int offset, int limit) {
		if (!(category.isEmpty())&&(virtualStorageId>0)) {
			return dao.getProductsByCategory(virtualStorageId, category,offset,limit);
		}
		return null;
	}

	@Override
	public List<Product> getProductsByCategories(long virtualStorageId,
			List<Integer> categories, int offset, int limit) {
		if ( !(virtualStorageId > 0) || (categories.isEmpty())) {
			return null;
		}
		return dao.getProductsByCategories(virtualStorageId, categories, offset, limit);
	}

	@Override
	public List<Product> getProductsByCategoriesOr(long virtualStorageId,
			List<Integer> categories, int offset, int limit) {
		if ( !(virtualStorageId > 0) || (categories.isEmpty())) {
			return null;
		}
		return dao.getProductsByCategoriesOr(virtualStorageId, categories, offset, limit);
	}

	@Override
	public boolean estaEnLista(Product p, List<Product> listaProductosTemp) {
		for(Product pr : listaProductosTemp){
			if (pr.getBarCode().equals(p.getBarCode()))return true;
		}
		return false;
	}

}
