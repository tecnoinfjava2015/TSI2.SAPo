package com.services;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Path;


import com.entities.sql.VirtualStorage;
import com.entities.sql.dao.VirtualStorageDAO;


@Stateless
@Path("/virtualStorage")
public class VirtualStorageServiceBean implements VirtualStorageServiceLocal{

	@EJB
	private VirtualStorageDAO vsdao;

	@Override
	public void registroVS(String nombre, String conexion, String url,Date fechaCreacion, String CSS, String loading, Boolean enabled, int idCreador, String logo) {	
		VirtualStorage vs = new VirtualStorage();
		vs.setName(nombre);
		vs.setConnection(conexion);
		vs.setUrl(url);
		vs.setCreatedDate(fechaCreacion);
		vs.setCSS(CSS);
		vs.setLoading(loading);
		vs.setLogo(logo);
		vs.setEnabled(enabled);
		vsdao.insertVS(vs, idCreador);
	}
	
	@Override
	public void modificarVS(String nombre, String conexion, String url,Date fechaCreacion, String CSS, String loading, Boolean enabled, int idCreador, String logo) {	
		VirtualStorage vs = new VirtualStorage();
		vs.setName(nombre);
		vs.setConnection(conexion);
		vs.setUrl(url);
		vs.setCreatedDate(fechaCreacion);
		vs.setCSS(CSS);
		vs.setLoading(loading);
		vs.setLogo(logo);
		vs.setEnabled(enabled);
		vsdao.updateVS(vs);
	}
	
	@Override
	public List<VirtualStorage> getVS() {
		return vsdao.getAllVS();
	}
	
	@Override
	public List<VirtualStorage> getVSBorrados() {
		return vsdao.getDisabledVS();
	}

	@Override
	public VirtualStorage getVSPorNombre(String nombre) {
		return vsdao.searchVSByName(nombre);
	}

	@Override
	public VirtualStorage getVSPorID(int id) {
		return vsdao.buscarVSporID(id);
	}

	@Override
	public Boolean borrarVSPorNombre(String nombre) {
		
		return vsdao.deleteVSByName(nombre);
	}
}
