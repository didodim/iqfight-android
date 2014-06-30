package com.empters.iqfight.network.helpters;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.empters.iqfight.network.REST;
import com.empters.iqfight.network.data.ws.LoginResponse;
import com.empters.iqfight.network.data.ws.RegisterResponse;

import android.util.Log;

public class ApiConnection {

	

	public static final int MAX_WAIT=3000;


	public static final String URL = "http://iqfight.empters.com/";
	public static String newGameName;
	public static final long REFRESH_TIME = 3000;
	
	public static Map<String, String> map = new HashMap<String, String>();
	

	public static RegisterResponse callRegister(String username, String password) {

		Log.d("POST", "register");
		JsonParser instance = JsonParser.getInstance();

		String queryPostJSON;

		queryPostJSON = REST.queryPostJSON(
				createRegisterParams(username, password), URL + "register",
				REST.ENCODING_UTF, map);
		if (queryPostJSON != null)
			Log.d("Register", queryPostJSON);
		RegisterResponse registerResponse;
		try {
			registerResponse = instance.getRegister(queryPostJSON);

		} catch (Exception e) {
			return null;
		}
		return registerResponse;
	}

	public static LoginResponse callLogin(String username, String password) {

		Log.d("Network", "Login request");
		JsonParser instance = JsonParser.getInstance();

		String currentUrl = URL + "login?username=" + username + "&password="
				+ password;
		String queryPostJSON;
		queryPostJSON = REST.queryRESTurl(currentUrl, REST.ENCODING_UTF);
		LoginResponse loginResponse;
		try {
			loginResponse = instance.getLogin(queryPostJSON);

		} catch (Exception e) {
			return null;
		}
		return loginResponse;
	}

	public static LoginResponse callIsLogged(String username, String password) {

		Log.d("Netwokr", "Is logged request");
		JsonParser instance = JsonParser.getInstance();

		String currentUrl = URL + "is_logged?username=" + username
				+ "&password=" + password;
		String queryPostJSON;
		queryPostJSON = REST.queryRESTurl(currentUrl, REST.ENCODING_UTF);
		LoginResponse loginResponse;
		try {
			loginResponse = instance.getLogin(queryPostJSON);

		} catch (Exception e) {
			return null;
		}
		return loginResponse;
	}



	




	private static List<NameValuePair> createRegisterParams(String username,
			String pass) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("username", username));
		nameValuePairs.add(new BasicNameValuePair("password", pass));
		nameValuePairs.add(new BasicNameValuePair("password1", pass));
		return nameValuePairs;

	}


}


