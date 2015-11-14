package com.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Path;

import com.entities.sql.LimitCount;
import com.entities.sql.dao.LimitCountDAO;



@Stateless
@Path("/limitCount")
public class LimitCountServiceBean implements LimitCountServiceLocal {

	@EJB
	private LimitCountDAO lcdao;

	@Override
	public void registroLimitCount(String type, int limit, int avisarFaltando) {
		LimitCount lc = new LimitCount();
		lc.setLimit(limit);
		lc.setType(type);
		lc.setAvisarFaltando(avisarFaltando);
		lcdao.insert(lc);
		
	}

	@Override
	public void modificarLimitCount(String type, int limit, int avisarFaltando) {
		LimitCount lc = new LimitCount();
		lc.setLimit(limit);
		lc.setType(type);
		lc.setAvisarFaltando(avisarFaltando);
		lcdao.modificar(lc);
		
	}

	@Override
	public List<LimitCount> listarLimitCount() {
		return lcdao.listarLimitCount();
	}

	@Override
	public LimitCount buscarType(String type) {
		return lcdao.buscarType(type);
	}

	@Override
	public int limitePorTipo(String type) {
		return lcdao.limitePorTipo(type);
	}

	@Override
	public int AvisarPorTipo(String type) {
		return lcdao.avisarPorTipo(type);
	}
	
}
