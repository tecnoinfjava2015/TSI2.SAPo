package com.services;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Path;





import com.bl.ProductBL;
import com.entities.mongo.Product;
import com.entities.sql.VirtualStorage;
import com.entities.sql.dao.VirtualStorageDAO;
import com.services.interfaces.IProductBL;


@Stateless
@Path("/virtualStorage")
public class VirtualStorageServiceBean implements VirtualStorageServiceLocal{

	@EJB
	private VirtualStorageDAO vsdao;

	@Override
	public void registroVS(String nombre, String conexion, String url,Date fechaCreacion, String CSS, String loading, Boolean enabled, Boolean blocked, int idCreador, String logo) {	
		VirtualStorage vs = new VirtualStorage();
		vs.setName(nombre);
		vs.setConnection(conexion);
		vs.setUrl(url);
		vs.setCreatedDate(fechaCreacion);
		vs.setCSS(CSS);
		vs.setLoading(loading);
		vs.setLogo(logo);
		vs.setEnabled(enabled);
		vs.setBlocked(blocked);
		vsdao.insertVS(vs, idCreador);
	}
	
	@Override
	public void modificarVS(String nombre, String conexion, String url,Date fechaCreacion, String CSS, String loading, Boolean enabled,  Boolean blocked, int idCreador, String logo) {	
		VirtualStorage vs = new VirtualStorage();
		vs.setName(nombre);
		vs.setConnection(conexion);
		vs.setUrl(url);
		vs.setCreatedDate(fechaCreacion);
		vs.setCSS(CSS);
		vs.setLoading(loading);
		vs.setLogo(logo);
		vs.setEnabled(enabled);
		vs.setBlocked(blocked);
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

	@Override
	public Boolean cambiarBloqueoVS(int id) {
		if(vsdao.buscarVSporID(id).getBlocked()){
			vsdao.buscarVSporID(id).setBlocked(false);
		}else vsdao.buscarVSporID(id).setBlocked(true);
		return vsdao.buscarVSporID(id).getBlocked();
	}

	@Override
	public double valorarAV(int id) {
		IProductBL servicioProducto = new ProductBL();
		List<Product>  listaProductos;
		listaProductos = servicioProducto.getAllProducts(id);
		double resultado = 0;
		for(Product p : listaProductos){
			resultado += p.getSalePrice();
		}
		return resultado;
	}
}
