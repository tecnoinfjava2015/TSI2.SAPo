package com.services;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class KeyValueBean implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    @XmlElement public Object data;
    
    public KeyValueBean(){};
    
    public KeyValueBean(Object data) {
    	this.data = data;
    }
}
