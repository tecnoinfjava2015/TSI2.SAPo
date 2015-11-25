package com.services;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class UserBean {
    @XmlElement public String nick;
    @XmlElement public String mail;
}
