package com.entities.mongo;

import java.util.List;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.utils.IndexDirection;

@Entity(value="Categories",noClassnameStored=false)
@Indexes(@Index(fields = { @Field(value = "tenant"), @Field(value = "name") }, options = @IndexOptions(name="iCategoryKey", unique=true)))
public class CategoryOLD extends BaseEntity{
	private String tenant;
	private String	name;
	private String parent;
	@Indexed(value=IndexDirection.ASC,name="iCategoryAncestors")
	private List<String> ancestors;
	
	public CategoryOLD() {
		super();
	}
	
	public CategoryOLD(String tenant, String name, String parent,
			List<String> ancestors) {
		super();
		this.tenant = tenant;
		this.name = name;
		this.parent = parent;
		this.ancestors = ancestors;
	}
	/**
	 * @return the tenant
	 */
	public String getTenant() {
		return tenant;
	}
	/**
	 * @param tenant the tenant to set
	 */
	public void setTenant(String tenant) {
		this.tenant = tenant;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the parent
	 */
	public String getParent() {
		return parent;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(String parent) {
		this.parent = parent;
	}
	/**
	 * @return the ancestors
	 */
	public List<String> getAncestors() {
		return ancestors;
	}
	/**
	 * @param ancestors the ancestors to set
	 */
	public void setAncestors(List<String> ancestors) {
		this.ancestors = ancestors;
	}
	
	
}
