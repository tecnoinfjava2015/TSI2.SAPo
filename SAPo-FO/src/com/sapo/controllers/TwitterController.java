
package com.sapo.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Properties;

import javax.faces.bean.*;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.JSONArray;
import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;

@ManagedBean(name = "twitter")
@SessionScoped
public class TwitterController {

	public void login() throws IOException {
		Properties props = new Properties();
		props.load(TwitterController.class.getResourceAsStream("sapo-config.properties"));
		String twitterConsumerKey = props.getProperty("twitterConsumerKey");
		String twitterConsumerSecret = props.getProperty("twitterConsumerSecret");
		String twitterCalbackURL = props.getProperty("twitterCallbackURL");

		String authURL = "noSoupForYou!!!";
		RequestToken requestToken = null;
		Twitter twitterK = null;
		try {
			 twitterK = new TwitterFactory().getInstance();
			 //Pasar datos a archivo properties
			 twitterK.setOAuthConsumer(twitterConsumerKey, twitterConsumerSecret);
			 requestToken = twitterK.getOAuthRequestToken(twitterCalbackURL);
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
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

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
	
		//llamo a login y guardo datos de usuario en cookie (y en sesion)
		String sapoUser = sapoLogin(userScreenName, userId);
		Cookie userCookie = new Cookie("sapoUser", sapoUser);
		response.addCookie(userCookie);
		
		//sessionMap.put("sapoUser", sapoUser);
		//externalContext.addResponseCookie("sapoUser", sapoUser, null);
	
		//paso el string a JSON para usar datos en la siguiente llamada
		JSONObject sapoUserJson = new JSONObject(sapoUser);
		
		//llamo a pedir los VS del usuario y guardarlos en otra cookie
		String myVSs = getMyVSs(sapoUserJson.getString("id"));
		JSONObject vsJSON = removeLogos(myVSs);
		
		
		//Cookie vsCookie = new Cookie("sapoVirtualStorages", myVSs);
		Cookie vsCookie = new Cookie("sapoVirtualStorages", vsJSON.toString());
		response.addCookie(vsCookie);
		
		externalContext.redirect("#/");
	}

	private JSONObject removeLogos(String myVSs) throws JSONException {
		JSONObject result = new JSONObject();

		JSONArray listOwned = new JSONArray();     
		JSONArray listFollowing = new JSONArray();     
		//JSONArray jsonArray = new JSONArray(myVSs);
		JSONObject jsonObject = new JSONObject(myVSs);

		//JSONArray owned = jsonArray.getJSONArray(0);
		String ownedStr = jsonObject.getString("owned");
		JSONArray owned = new JSONArray(ownedStr);
		int len = owned.length();
		if (owned != null) { 
		   for (int i=0;i<len;i++)
		   {
			   JSONObject position = new JSONObject(owned.get(i).toString());
			   position.remove("logo");
			   listOwned.put(position);
		   }
		}
		
		String followingStr = jsonObject.getString("following");
		//JSONArray following = jsonArray.getJSONArray(1);
		JSONArray following = new JSONArray(followingStr);
		int len2 = following.length();
		if (following != null) { 
		   for (int i=0;i<len2;i++)
		   {
			   JSONObject position = new JSONObject(following.get(i).toString());
			   position.remove("logo");
			   listFollowing.put(position);
		   }
		}
		result.put("owned", listOwned);
		result.put("following", listFollowing);
		
//		result.put(listOwned);
//		result.put(listFollowing);
		
		return result;
	}

	private String sapoLogin(String userScreenName, Long userId) throws IOException, JSONException {
		//Traigo la url para el post dsde el restLocation.properties
		Properties props = new Properties();
		props.load(TwitterController.class.getResourceAsStream("sapo-config.properties"));
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
	    	//llamo al REST registroTwitter que registra y 
	    	//luego el login que devuelve el json del user creado
	    	registroTwitter(body);
	    	return postToRest(loginTwitterURL, body);
	    }
	    
	}
	
	private String registroTwitter(JSONObject body) throws IOException {
		
		Properties props = new Properties();
		props.load(TwitterController.class.getResourceAsStream("sapo-config.properties"));
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

	private String getMyVSs (String userId) throws IOException {
		Properties props = new Properties();
		props.load(TwitterController.class.getResourceAsStream("sapo-config.properties"));
		String restURL = props.getProperty("getVirtualStoragesREST")+userId;
		//Conecto al rest para el login
		URL url = new URL(restURL);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/json");
		
		if (connection.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ connection.getResponseCode());
		}
		
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
