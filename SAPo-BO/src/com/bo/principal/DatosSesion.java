package com.bo.principal;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

import com.entities.sql.Usuario;


@SessionScoped
public class DatosSesion implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
