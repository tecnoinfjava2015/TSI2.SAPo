package com.bl;

import com.entities.sql.VirtualStorage;
import com.entities.sql.dao.VirtualStorageDAO;

public class VirtualStorageBL {
	private final VirtualStorageDAO dao = new VirtualStorageDAO();
	
	public VirtualStorageBL() {}
	
	public VirtualStorage createVS(VirtualStorage vs) {
		return dao.createVS(vs, vs.getOwner().getId());
	}
	
}
