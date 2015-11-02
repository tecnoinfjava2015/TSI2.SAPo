package com.bl;

import javax.ejb.EJB;

import com.entities.sql.VirtualStorage;
import com.entities.sql.dao.VirtualStorageDAO;

public class VirtualStorageBL {
	
	@EJB
	private VirtualStorageDAO dao;
	
//	private final VirtualStorageDAO dao = new VirtualStorageDAO();
	
	public VirtualStorageBL() {}
	
	public VirtualStorage createVS(VirtualStorage vs) {
		return dao.createVS(vs, vs.getOwner().getId());
	}
	
}
