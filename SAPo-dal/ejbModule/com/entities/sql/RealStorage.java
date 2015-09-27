package com.entities.sql;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class RealStorage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3066123918990693668L;
	
	@Id @GeneratedValue 
	private int id;
}
