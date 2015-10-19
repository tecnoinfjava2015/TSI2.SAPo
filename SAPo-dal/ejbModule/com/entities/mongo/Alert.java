package com.entities.mongo;

import org.mongodb.morphia.annotations.Embedded;

/**
 * @author jpmartinez
 *
 */

@Embedded
public class Alert {
	private String name;
	private String msg;
	private String condition;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
}
