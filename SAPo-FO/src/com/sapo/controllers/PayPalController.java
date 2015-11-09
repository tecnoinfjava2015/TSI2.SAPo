package com.sapo.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Properties;

import javax.faces.bean.*;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import twitter4j.JSONException;
import twitter4j.JSONObject;

import com.sapo.paypal.*;

@ManagedBean(name = "paypal")
@SessionScoped
public class PayPalController {

	private PayPalResult result = new PayPalResult();
	private String PayPalTxId;

	public PayPalResult getResult() {
		return result;
	}
	
	public String getUserName() {
		String userId; 
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> cookies = externalContext.getRequestCookieMap();
		Cookie cookie = (Cookie) cookies.get("sapoUserName");
		userId = cookie.getValue().toString();
		
		return userId;
	}

	public void setResult(PayPalResult result) {
		this.result = result;
	}

	public String success() throws IOException, JSONException {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		PayPalSucess ps = new PayPalSucess();
		this.result = ps.getPayPal(request);
		this.PayPalTxId = result.getTxn_id();

		//postear al rest el transaction ID
		//Tomo datos del properties
		Properties props = new Properties();
		props.load(PayPalController.class.getResourceAsStream("sapo-config.properties"));
		String restURL = props.getProperty("paypalRestURL");
		
		//Armo el json para mandar al rest
		JSONObject body = new JSONObject();
		body.put("nick", this.result.getCm());
		body.put("paypalTransactionId", this.result.getTxn_id());

		//llamo al rest que devuelve un json con el usuario (ahora conteniendo el transaction id)
		String sapoUser = postToRest(restURL, body);
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		sessionMap.put("sapoUser", sapoUser);
		
		return "success";
	}

	private String postToRest (String restURL, JSONObject body) throws IOException {
		//Conecto al rest para el login
		URL url = new URL(restURL);
		URLConnection connection = url.openConnection();
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setConnectTimeout(25000);
		connection.setReadTimeout(25000);
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
		out.write(body.toString());
		out.close();

		//Envío datos y leo respuesta
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		StringBuilder sb = new StringBuilder();
		while ((line = in.readLine()) != null) {
	        sb.append(line);
	    }
	    in.close();
	    
	    String sbStr = sb.toString();
	    
	    return sbStr;
	}

	public String getPayPalTxId() {
		return PayPalTxId;
	}

	public void setPayPalTxId(String payPalTxId) {
		PayPalTxId = payPalTxId;
	}
}
