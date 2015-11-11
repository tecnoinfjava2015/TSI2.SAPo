package com.entities.sql;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;

@Entity
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private String type;
	private String mail;
	private String nick;
	private String password;
	private Boolean enabled;
	private Boolean aceptado;
	private String geolocation;
	private String twitterId;
	private String paypalTransactionId;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JsonIdentityInfo(generator = PropertyGenerator.class, property = "id")
	@JsonIdentityReference(alwaysAsId = true) 
	private Set<VirtualStorage> tenantCreados;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			name = "seguidores_vs",
			joinColumns={@JoinColumn (name="user_id", referencedColumnName="id")},
			inverseJoinColumns={@JoinColumn (name="vs_id", referencedColumnName="id")})
	@JsonProperty(value = "vs_seguidos")
	@JsonIdentityInfo(generator = PropertyGenerator.class, property = "id")
	@JsonIdentityReference(alwaysAsId = true) 
	private Set<VirtualStorage> tenantSeguidor;
	

	public Usuario() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Set<VirtualStorage> getTenantSeguidor() {
		return tenantSeguidor;
	}

	public void setTenantSeguidor(Set<VirtualStorage> tenantSeguidor) {
		this.tenantSeguidor = tenantSeguidor;
	}

	public Set<VirtualStorage> getTenantCreados() {
		return tenantCreados;
	}

	public void setTenantCreados(Set<VirtualStorage> tenantCreados) {
		this.tenantCreados = tenantCreados;
	}

	public Boolean getAceptado() {
		return aceptado;
	}

	public void setAceptado(Boolean aceptado) {
		this.aceptado = aceptado;
	}

	public String getGeolocation() {
		return geolocation;
	}

	public void setGeolocation(String geolocation) {
		this.geolocation = geolocation;
	}

	public String getTwitterId() {
		return twitterId;
	}

	public void setTwitterId(String twitterId) {
		this.twitterId = twitterId;
	}

	public String getPaypalTransactionId() {
		return paypalTransactionId;
	}

	public void setPaypalTransactionId(String paypalTransactionId) {
		this.paypalTransactionId = paypalTransactionId;
	}
	
	
	
}
