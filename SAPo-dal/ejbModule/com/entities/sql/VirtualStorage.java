package com.entities.sql;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class VirtualStorage implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private int id;
	private String connection;
	private String url;
	private Date createdDate;
	private String name;
	
	/*STYLES*/
	private String theme;
	private String sidenavTop;
	private String sidenavBottom;
	private Boolean progressLinear;
	
	@Column(length=10485760)
	private String logo;
	private Boolean enabled;
	private Boolean blocked; 
	
	@ManyToOne (optional = true)
    @JoinColumn(name = "tenantCreados")
	private Usuario owner;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getConnection() {
		return connection;
	}
	public void setConnection(String connection) {
		this.connection = connection;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getSidenavTop() {
		return sidenavTop;
	}
	public void setSidenavTop(String sidenavTop) {
		this.theme = sidenavTop;
	}
	public String getSidenavBottom() {
		return sidenavBottom;
	}
	public void setSidenavBottom(String sidenavBottom) {
		this.sidenavBottom = sidenavBottom;
	}
	public Boolean getProgressLinear() {
		return progressLinear;
	}
	public void setProgressLinear(Boolean progressLinear) {
		this.progressLinear = progressLinear;
	}
	public Usuario getOwner() {
		return owner;
	}
	public void setOwner(Usuario owner) {
		this.owner = owner;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public Boolean getBlocked() {
		return blocked;
	}
	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}
}
