package com.services;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class TwitterUserBean {
	@XmlElement public String nick;
    @XmlElement public String nombre;
    @XmlElement public String twitterId;
    @XmlElement public String geoLocation;
    @XmlElement public String latitud;
    @XmlElement public String longitud;
    
}
