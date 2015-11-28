
package com.sapo.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
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
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

@ManagedBean(name = "twitter")
@SessionScoped
public class TwitterController {

	public void login() throws IOException {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Properties props = new Properties();
		props.load(TwitterController.class.getResourceAsStream("sapo-config.properties"));
		String twitterConsumerKey = props.getProperty("twitterConsumerKey");
		String twitterConsumerSecret = props.getProperty("twitterConsumerSecret");
		String twitterCalbackURL = props.getProperty("twitterCallbackURL");
		String serverURL = getServerURL(externalContext);
		
		String authURL = "noSoupForYou!!!";
		RequestToken requestToken = null;
		Twitter twitterK = null;
		//AccessToken accessToken = null;

		try {
			 twitterK = new TwitterFactory().getInstance();
			 
			 twitterK.setOAuthConsumer(twitterConsumerKey, twitterConsumerSecret);
			 requestToken = twitterK.getOAuthRequestToken(serverURL+twitterCalbackURL);
			 authURL = requestToken.getAuthenticationURL();
			 //accessToken = twitterK.getOAuthAccessToken();
			 
			 //request.getSession().setAttribute("requestToken", requestToken);
			 //response.sendRedirect(authURL);
			 } catch (Exception  twitterException) {
			 twitterException.printStackTrace();
			 }
		
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		sessionMap.put("requestToken", requestToken);
		sessionMap.put("twitterK", twitterK);
		//sessionMap.put("accessToken", accessToken);

		externalContext.redirect(authURL);
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

	public void verify() throws TwitterException, IOException, JSONException {		
		//Traigo el request para tomar parametros de la URL y atributos de la sesion.
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest origRequest = (HttpServletRequest) externalContext.getRequest();
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
		String serverURL = getServerURL(externalContext);
		
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		//Traigo el objeto Twitter que guardé en el login()
		Twitter twitterK = (Twitter) sessionMap.get("twitterK");

		//Tomo el verifier de la URL
		String verifier = origRequest.getParameter("oauth_verifier");
		
		//Recupero el request token que guardé en la sesión en el login()
		RequestToken requestToken = (RequestToken) sessionMap.get("requestToken");
		
		//Cargo en twitterK el access token mandando los datos que obtuve
		//AccessToken at = twitterK.getOAuthAccessToken(requestToken, verifier);
		AccessToken accessToken = (AccessToken) twitterK.getOAuthAccessToken(requestToken, verifier);
		
		//get user info
		String userScreenName = twitterK.getScreenName();
		User twitterUser = twitterK.showUser(userScreenName);
		Long userId = twitterUser.getId();
		String userLocation = twitterUser.getLocation();
		
		JSONObject geoLocation = null;
		if(userLocation != null && !userLocation.isEmpty()) {
			geoLocation = getGoogleGeolocation(userLocation);
		}
		
//		twitterK.createFriendship("SAPotsijee03");

		sessionMap.put("twitterScreenName", userScreenName);
		sessionMap.put("twitterUserId", userId);
		sessionMap.put("accessToken", accessToken);
	
		//llamo a login y guardo datos de usuario en cookie (y en sesion)
		String sapoUser = sapoLogin(userScreenName, userId, geoLocation);
		sessionMap.put("sapoUser", sapoUser);
//		Cookie userCookie = new Cookie("sapoUser", sapoUser);
//		response.addCookie(userCookie);
		
		//paso el string a JSON para usar datos en la siguiente llamada
		JSONObject sapoUserJSON = new JSONObject(sapoUser);
		
		//llamo a pedir los VS del usuario y guardarlos en otra cookie
		String myVSs = getMyVSs(serverURL, sapoUserJSON.getString("id"));
		JSONObject vsJSON = new JSONObject();
		String sapoUserCookie;
				
		if(myVSs == null || myVSs.isEmpty()){
			Cookie userCookie = new Cookie("sapoUser", myVSs);
			response.addCookie(userCookie);
		}else{
			vsJSON = removeLogos(myVSs);
			
			//Cookie vsCookie = new Cookie("sapoVirtualStorages", myVSs);
			Cookie vsCookie = new Cookie("sapoVirtualStorages", vsJSON.toString());
			response.addCookie(vsCookie);

			sapoUserCookie = removeDisabledVS(sapoUserJSON, vsJSON);
			Cookie userCookie = new Cookie("sapoUser", sapoUserCookie);
			response.addCookie(userCookie);
		}

		String userAvatar = twitterUser.getProfileImageURL();
		Cookie userAvatarCookie = new Cookie("userAvatar", userAvatar);
		response.addCookie(userAvatarCookie);

		externalContext.redirect("#/");
	}

	private String removeDisabledVS(JSONObject sapoUserJSON, JSONObject vsJSON) throws JSONException {
		
		JSONArray vsOwnedAll;
		JSONArray vsOwnedActive;
		List<Integer> vsOwnedEnabled = new ArrayList<Integer>();
		if(!sapoUserJSON.get("tenantCreados").equals(null) && sapoUserJSON.getJSONArray("tenantCreados").length() != 0){
			vsOwnedAll = sapoUserJSON.getJSONArray("tenantCreados");
			vsOwnedActive = vsJSON.getJSONArray("owned");
			//List<Integer> vsOwnedEnabled = new ArrayList<Integer>();
			for(int i = 0; i<vsOwnedAll.length(); i++){
				int position = vsOwnedAll.getInt(i);
				
				if(vsOwnedActive.toString().contains("\"id\":"+position+"}")){
					vsOwnedEnabled.add(position);
				}
			}
		}
		
		JSONArray vsFollowingAll;
		JSONArray vsFollowingActive;
		List<Integer> vsFollowingEnabled = new ArrayList<Integer>();
		if(!sapoUserJSON.get("vs_seguidos").equals(null) && sapoUserJSON.getJSONArray("vs_seguidos").length() != 0){
			vsFollowingAll = sapoUserJSON.getJSONArray("vs_seguidos");
			vsFollowingActive = vsJSON.getJSONArray("following");
			//List<Integer> vsFollowingEnabled = new ArrayList<Integer>();
			for(int i = 0; i<vsFollowingAll.length(); i++){
				int position = vsFollowingAll.getInt(i);
				
				if(vsFollowingActive.toString().contains("\"id\":"+position+"}")){
					vsFollowingEnabled.add(position);
				}
			}
		}
		
		int[] vsOwnedResult = new int[vsOwnedEnabled.size()];
		int index = 0;
		for(int vs : vsOwnedEnabled) {
			vsOwnedResult[index] = vs;
			index++;
		}
		sapoUserJSON.put("tenantCreados", vsOwnedResult);
		
		int[] vsFollowingResult = new int[vsFollowingEnabled.size()];
		index = 0;
		for(int vs : vsFollowingEnabled) {
			vsFollowingResult[index] = vs;
			index++;
		}
		sapoUserJSON.put("vs_seguidos", vsFollowingResult);
		
		
		return sapoUserJSON.toString();
	}

	private JSONObject getGoogleGeolocation(String userLocation) throws IOException, JSONException {

		Properties props = new Properties();
		props.load(TwitterController.class.getResourceAsStream("sapo-config.properties"));
		String googleAPIkey = props.getProperty("googleAPIkey");
		String googleAPIurl = props.getProperty("geoLocationURL");
		
		
		URL url = new URL(googleAPIurl + "json?address=" + userLocation.replace(" ", "+") + "&key=" + googleAPIkey);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		
		StringBuilder result = new StringBuilder();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		while ((line = br.readLine()) != null) {
			result.append(line);
		}
		br.close();
		
		JSONObject geolocationResponse = new JSONObject(result.toString());
		if(geolocationResponse.getString("status").equals("OK") && 
				geolocationResponse.getJSONArray("results").length() > 0){
			
			JSONObject location = geolocationResponse.getJSONArray("results").getJSONObject(0)
					.getJSONObject("geometry").getJSONObject("location");
			return location;
			//return location.getString("lat") + "," + location.getString("lng");
		}
//		JSONObject location = geolocationResponse.getJSONArray("results").getJSONObject(0)
//				.getJSONObject("geometry").getJSONObject("location");
//		return location.getString("lat") + "," + location.getString("lng");
		
		return null;
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
		if (owned != null && len > 0) { 
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
		if (following != null && len2 > 0) { 
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

	private String sapoLogin(String userScreenName, Long userId, JSONObject geoLocation) throws IOException, JSONException {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		String serverURL = getServerURL(externalContext);
		//Traigo la url para el post dsde el sapo-config.properties
		Properties props = new Properties();
		props.load(TwitterController.class.getResourceAsStream("sapo-config.properties"));
		String loginTwitterURL = props.getProperty("twitterLoginREST");

		//Armo el json para mandar al rest
		JSONObject body = new JSONObject();
		body.put("nombre", userScreenName);
		body.put("twitterId", userId);
		
		if(geoLocation != null && !geoLocation.getString("lat").isEmpty()) {
			body.put("latitud", Double.valueOf(geoLocation.getString("lat")));
			body.put("longitud", Double.valueOf(geoLocation.getString("lng")));			
		}
		else {
			body.put("latitud", "");
			body.put("longitud", "");	
		}
		//body.put("geoLocation", "");
		
		String result = postToRest(serverURL+loginTwitterURL, body);

	    if(!result.isEmpty()){
	    	return result;
	    } else {
	    	//el json vino vacío, por ende es el primer ingreso del usuario
	    	//llamo al REST registroTwitter que registra y 
	    	//luego el login que devuelve el json del user creado
	    	registroTwitter(serverURL, body);
	    	return postToRest(serverURL+loginTwitterURL, body);
	    }
	    
	}
	
	private String registroTwitter(String serverURL, JSONObject body) throws IOException {
		
		Properties props = new Properties();
		props.load(TwitterController.class.getResourceAsStream("sapo-config.properties"));
		String registroTwitterURL = props.getProperty("twitterRegisterREST");

		String result = postToRest(serverURL+registroTwitterURL, body);
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

	private String getMyVSs (String serverURL, String userId) throws IOException {
		Properties props = new Properties();
		props.load(TwitterController.class.getResourceAsStream("sapo-config.properties"));
		String restURL = props.getProperty("getVirtualStoragesREST")+userId;
		//Conecto al rest para el login
		URL url = new URL(serverURL+restURL);
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

	public void follow() throws IOException, TwitterException, IllegalStateException, JSONException {
		Properties props = new Properties();
		props.load(TwitterController.class.getResourceAsStream("sapo-config.properties"));

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		Twitter twitterK = (Twitter) sessionMap.get("twitterK");
		User dummy = twitterK.createFriendship("SAPotsijee03");
		
		JSONObject body = new JSONObject();
		body.put("nick", twitterK.getScreenName());
		
		String serverURL = getServerURL(externalContext);
		String restURL = props.getProperty("freemiumREST");
		String result = postToRest(serverURL+restURL, body);
		
		Cookie userCookie = new Cookie("sapoUser", result);
		response.addCookie(userCookie);
		
		externalContext.redirect("#/dashboard");

	}
}
