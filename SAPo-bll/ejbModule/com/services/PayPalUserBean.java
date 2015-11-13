package com.services;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class PayPalUserBean {
    @XmlElement public String nick;
    @XmlElement public String paypalTransactionId;
}
