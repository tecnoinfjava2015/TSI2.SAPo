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
import javax.servlet.http.HttpServletRequest;

import twitter4j.JSONException;
import twitter4j.JSONObject;

import com.sapo.paypal.*;

@ManagedBean(name = "paypal")
@SessionScoped
public class PayPalController {

	private PayPalResult result = new PayPalResult();

	public PayPalResult getResult() {
		return result;
	}

	public void setResult(PayPalResult result) {
		this.result = result;
	}

	public String success() throws IOException, JSONException {
		Properties props = new Properties();
		props.load(PayPalController.class.getResourceAsStream("sapo-config.properties"));
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		PayPalSucess ps = new PayPalSucess();
		this.result = ps.getPayPal(request);

		//Llamar al REST que ponga el transactionID en la DB
		JSONObject body = new JSONObject();
		//body.put("nick", result.getCustom());
		body.put("nick", sessionMap.get("twitterScreenName").toString());
		body.put("paypalTransactionId", result.getTxn_id());
		
		String paypalTxREST = props.getProperty("paypalTransactionUpdateREST");
		String sapoUser = postToRest(paypalTxREST, body);

		sessionMap.put("sapoUser", sapoUser);
		externalContext.addResponseCookie("sapoUser", sapoUser, null);
		
		String paypalFinalRedirectURL = props.getProperty("paypalFinalRedirectURL");

		externalContext.redirect(paypalFinalRedirectURL);
		
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
	
}
