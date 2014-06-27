package com.empters.iqfight.network.abstracts;

import android.util.Log;

import com.empters.iqfight.network.REST;
import com.empters.iqfight.network.data.ws.LoginResponse;
import com.empters.iqfight.network.helpters.ApiConnection;
import com.empters.iqfight.network.helpters.JsonParser;

public abstract class LoginListener extends RequestListener {

	private String username;
	private String password;
	private LoginResponse loginResponse;

	@Override
	public void call() {
		Log.d("Network", "Login request");
		JsonParser instance = JsonParser.getInstance();

		String currentUrl = ApiConnection.URL + "login?username=" + username
				+ "&password=" + password;
		String queryPostJSON;
		queryPostJSON = REST.queryRESTurl(currentUrl, REST.ENCODING_UTF);
		loginResponse = null;
		try {
			loginResponse =instance.getLogin(queryPostJSON);
			onResponse();

		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Network", "ERROR in LoginListener");
		}

	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LoginResponse getLoginResponse() {
		return loginResponse;
	}



}
