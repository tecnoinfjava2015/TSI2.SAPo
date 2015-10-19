/**
 * 
 */
package com.entities.mongo;

import java.util.List;

/**
 * @author jpmartinez
 *
 */
public class Product {
	private long virtualStorageId;
	private String virtualStorageName;
	private String barCode;
	private String name;
	private String description;
	private double salePrice;
	private double purchasePrice;
	private double stock;
	private List<Alert> alerts; 
	private List<byte[]> images;
	private List<Spec> specs;
//	private categories
	private boolean active;


}
