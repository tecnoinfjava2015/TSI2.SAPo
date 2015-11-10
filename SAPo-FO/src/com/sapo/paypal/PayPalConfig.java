package com.sapo.paypal;

import java.util.Properties;

import javax.servlet.http.*;

import com.sapo.controllers.PayPalController;

public class PayPalConfig {

    private String authToken;
    private String posturl;
    private String business;
    private String returnurl;
    private String cancelurl;
    private String cmd;

    public String getAuthToken() {
        return authToken;
    }

    public String getPosturl() {
        return posturl;
    }

    public String getBusiness() {
        return business;
    }

    public String getReturnurl() {
        return returnurl;
    }

    public String getCancelurl() {
        return cancelurl;
    }

    public String getCmd() {
        return cmd;
    }

    public PayPalConfig getConfig(HttpServletRequest request) {
        PayPalConfig pc = new PayPalConfig();
        try {
        	Properties props = new Properties();
    		props.load(PayPalController.class.getResourceAsStream("sapo-config.properties"));
    		//props.getProperty("paypal");
            pc.authToken = props.getProperty("paypalAuthToken");//request.getServletContext().getInitParameter("authtoken");
            pc.posturl = props.getProperty("paypalPostURL");//request.getServletContext().getInitParameter("posturl");
            pc.business = props.getProperty("paypalBusiness");//request.getServletContext().getInitParameter("business");
            pc.returnurl = props.getProperty("paypalReturnURL");//request.getServletContext().getInitParameter("returnurl");
            
        } catch (Exception ex) {
            pc = null;
        }
        return pc;
    }
}
