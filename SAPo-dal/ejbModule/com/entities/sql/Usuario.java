package com.entities.sql;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


@Entity
public class Usuario implements Serializable {

	/**
	 * 
	 */
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
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "tenatCreados")
	private List <Tenant> tenantCreados;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Tenant>tenantSeguidor;
	

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

	public List<Tenant> getTenantSeguidor() {
		return tenantSeguidor;
	}

	public void setTenantSeguidor(List<Tenant> tenantSeguidor) {
		this.tenantSeguidor = tenantSeguidor;
	}

	public List<Tenant> getTenantCreados() {
		return tenantCreados;
	}

	public void setTenantCreados(List<Tenant> tenantCreados) {
		this.tenantCreados = tenantCreados;
	}

	public Boolean getAceptado() {
		return aceptado;
	}

	public void setAceptado(Boolean aceptado) {
		this.aceptado = aceptado;
	}
	
	
}
