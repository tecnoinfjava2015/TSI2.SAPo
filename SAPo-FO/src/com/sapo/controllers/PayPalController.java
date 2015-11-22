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
		body.put("nick", result.getCustom());
		//body.put("nick", sessionMap.get("twitterScreenName").toString());
		body.put("paypalTransactionId", result.getTxn_id());
		
		String serverURL = getServerURL(externalContext);
		String paypalTxREST = props.getProperty("paypalTransactionUpdateREST");
		String sapoUser = postToRest(serverURL+paypalTxREST, body);

		sessionMap.put("sapoUser", sapoUser);
		externalContext.addResponseCookie("sapoUser", sapoUser, null);
		
		String paypalFinalRedirectURL = props.getProperty("paypalFinalRedirectURL");

		externalContext.redirect(serverURL+paypalFinalRedirectURL);
		
		return "success";
	}

	private String getServerURL(ExternalContext ec) {
		if(ec.getRequestScheme().equals("http") && ec.getRequestServerPort() == 80) {
			return ec.getRequestScheme()+"://"+ec.getRequestServerName();
		}
		else if(ec.getRequestScheme().equals("https") && ec.getRequestServerPort() == 443) {
			return ec.getRequestScheme()+"://"+ec.getRequestServerName();
		}
		else {
			return ec.getRequestScheme()+"://"+ec.getRequestServerName()+":"+ec.getRequestServerPort();
		}
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
	
	public String returnURL() throws IOException{
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		String serverURL = getServerURL(externalContext);
		
		Properties props = new Properties();
		props.load(PayPalController.class.getResourceAsStream("sapo-config.properties"));
		String restURL = props.getProperty("paypalReturnURL");
		
		return serverURL+restURL;
	}
	
	public String userID() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		return sessionMap.get("twitterScreenName").toString();
	}
	
	public String hideFreemium() throws JSONException{
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Cookie sapoUser = (Cookie) externalContext.getRequestCookieMap().get("sapoUser");
		//Map<String, Object> sessionMap = externalContext.getSessionMap();
		//JSONObject sapoUserJSON = new JSONObject(sessionMap.get("sapoUser"));
		JSONObject sapoUserJSON = new JSONObject(sapoUser.getValue());
		String userType = sapoUserJSON.get("type").toString(); 
		
		if(userType.equals("FREEMIUM") || userType.equals("PREMIUM")){
			return "none";
		}
		return "";
	}
	
	public String hidePremium() throws JSONException{
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Cookie sapoUser = (Cookie) externalContext.getRequestCookieMap().get("sapoUser");
		//Map<String, Object> sessionMap = externalContext.getSessionMap();
		//JSONObject sapoUserJSON = new JSONObject(sessionMap.get("sapoUser"));
		JSONObject sapoUserJSON = new JSONObject(sapoUser.getValue());
		String userType = sapoUserJSON.get("type").toString(); 
		
		if(userType.equals("PREMIUM")){
			return "none";
		}
		return "";
	}
		
}
