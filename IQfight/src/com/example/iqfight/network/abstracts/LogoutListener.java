package com.example.iqfight.network.abstracts;

import android.util.Log;

import com.example.iqfight.network.REST;
import com.example.iqfight.network.data.ws.ResponseStatus;
import com.example.iqfight.network.helpters.ApiConnection;
import com.example.iqfight.network.helpters.JsonParser;

public abstract class LogoutListener extends RequestListener {
	
	private ResponseStatus responseStatus;
	
	@Override
	public void call() {
		Log.i("Network", "Logout");
		JsonParser instance = JsonParser.getInstance();

		String currentUrl = ApiConnection.URL + "logout";
		String queryPostJSON;
		queryPostJSON = REST.queryRESTurl(currentUrl, REST.ENCODING_UTF);
	
		try {

			responseStatus =instance.getResponseStatus(queryPostJSON);
			onResponse();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Network","ERROR in Logout");
		}

	}

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}


	

}
