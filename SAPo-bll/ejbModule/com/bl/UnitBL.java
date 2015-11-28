package com.bl;

import javax.ejb.EJB;

import com.entities.sql.Unit;
import com.entities.sql.dao.UnitDAO;

public class UnitBL {
	private UnitDAO dao = new UnitDAO();
	
	public UnitBL() {}
	
	public Unit createUnit(Unit unit) {
		return dao.createDocument(unit);
	}
	
	public Unit getUnitByVS(int vsId) {
		return dao.getUnitByVS(vsId);
	}
	
}
