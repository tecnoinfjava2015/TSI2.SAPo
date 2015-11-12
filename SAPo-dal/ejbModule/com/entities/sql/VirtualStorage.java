package com.entities.sql;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;

@Entity
@XmlRootElement
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
	
	@ManyToOne (optional = true,fetch = FetchType.EAGER)
	@JsonIdentityInfo(generator = PropertyGenerator.class, property = "id")
	@JsonIdentityReference(alwaysAsId = true) 
	private Usuario owner;
	
	@ManyToMany (mappedBy = "tenantSeguidor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonProperty(value = "id_seguidores")
	@JsonIdentityInfo(generator = PropertyGenerator.class, property = "id")
	@JsonIdentityReference(alwaysAsId = true) 
	private Set<Usuario> seguidores;
	
	public Set<Usuario> getSeguidores() {
		return seguidores;
	}
	public void setSeguidores(Set<Usuario> seguidores) {
		this.seguidores = seguidores;
	}
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
		this.sidenavTop = sidenavTop;
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
	public VirtualStorage(int id, String connection, String url,
			Date createdDate, String name, String theme, String sidenavTop,
			String sidenavBottom, Boolean progressLinear, String logo,
			Boolean enabled, Boolean blocked, Usuario owner,
			Set<Usuario> seguidores) {
		super();
		this.id = id;
		this.connection = connection;
		this.url = url;
		this.createdDate = createdDate;
		this.name = name;
		this.theme = theme;
		this.sidenavTop = sidenavTop;
		this.sidenavBottom = sidenavBottom;
		this.progressLinear = progressLinear;
		this.logo = logo;
		this.enabled = enabled;
		this.blocked = blocked;
		this.owner = owner;
		this.seguidores = seguidores;
	}
	
	public VirtualStorage(){}
	
}
