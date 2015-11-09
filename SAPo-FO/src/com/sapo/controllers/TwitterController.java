
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
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;

@ManagedBean(name = "twitter")
@SessionScoped
public class TwitterController {
	
	public Properties loadProperties() throws IOException {
		Properties props = new Properties();
		props.load(TwitterController.class.getResourceAsStream("sapo-config.properties"));
		return props;
	}

	public void login() throws IOException {
		Properties props = loadProperties();
		String twitterConsumerKey = props.getProperty("twitterConsumerKey");
		String twitterConsumerSecret = props.getProperty("twitterConsumerSecret");
		String twitterCallbackURL = props.getProperty("twitterCallbackURL");
		
		String authURL = "noSoupForYou!!!";
		RequestToken requestToken = null;
		Twitter twitterK = null;
		try {
			 twitterK = new TwitterFactory().getInstance();
			 //Pasar datos a archivo properties
			 twitterK.setOAuthConsumer(twitterConsumerKey, twitterConsumerSecret);
			 requestToken = twitterK.getOAuthRequestToken(twitterCallbackURL);
			 authURL = requestToken.getAuthenticationURL();
			 
			 
			 //request.getSession().setAttribute("requestToken", requestToken);
			 //response.sendRedirect(authURL);
			 } catch (Exception  twitterException) {
			 twitterException.printStackTrace();
			 }
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		sessionMap.put("requestToken", requestToken);
		sessionMap.put("twitterK", twitterK);

		externalContext.redirect(authURL);
	}
	
	public void verify() throws TwitterException, IOException, JSONException {
		//Traigo el request para tomar parametros de la URL y atributos de la sesion.
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest origRequest = (HttpServletRequest) externalContext.getRequest();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		//Traigo el objeto Twitter que guardé en el login()
		Twitter twitterK = (Twitter) sessionMap.get("twitterK");
		
		//Tomo el verifier de la URL
		String verifier = origRequest.getParameter("oauth_verifier");
		
		//Recupero el request token que guardé en la sesión en el login()
		RequestToken requestToken = (RequestToken) sessionMap.get("requestToken");
		
		//Cargo en twitterK el access token mandando los datos que obtuve
		//AccessToken at = twitterK.getOAuthAccessToken(requestToken, verifier);
		twitterK.getOAuthAccessToken(requestToken, verifier);
		
		String userScreenName = twitterK.getScreenName();
		Long userId = twitterK.getId();
		sessionMap.put("twitterScreenName", userScreenName);
		sessionMap.put("twitterUserId", userId);

		String sapoUser = sapoLogin(userScreenName, userId);
		
		sessionMap.put("sapoUser", sapoUser);
		
		externalContext.addResponseCookie("sapoUserName", userScreenName, null);
		externalContext.addResponseCookie("sapoUser", sapoUser, null);
		
		externalContext.redirect("#/");
	}

	private String sapoLogin(String userScreenName, Long userId) throws IOException, JSONException {
		//Traigo la url para el post dsde el restLocation.properties
		Properties props = loadProperties();
		String loginTwitterURL = props.getProperty("twitterLoginREST");

		//Armo el json para mandar al rest
		JSONObject body = new JSONObject();
		body.put("nombre", userScreenName);
		body.put("twitterId", userId);
		
		String result = postToRest(loginTwitterURL, body);

	    if(!result.isEmpty()){
	    	return result;
	    } else {
	    	//el json vino vacío, por ende es el primer ingreso del usuario
	    	//llamo al REST registroTwitter que registra y devuelve el json del user creado
	    	return registroTwitter(body);
	    }
	    
	}
	
	private String registroTwitter(JSONObject body) throws IOException {
		Properties props = loadProperties();
		String registroTwitterURL = props.getProperty("twitterRegisterREST");

		String result = postToRest(registroTwitterURL, body);
		return result;
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
