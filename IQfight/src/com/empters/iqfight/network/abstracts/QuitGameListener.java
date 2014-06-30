package com.empters.iqfight.network.abstracts;

import android.util.Log;

import com.empters.iqfight.network.REST;
import com.empters.iqfight.network.data.ws.ResponseStatus;
import com.empters.iqfight.network.helpters.ApiConnection;
import com.empters.iqfight.network.helpters.JsonParser;

public abstract class  QuitGameListener extends RequestListener {

	private ResponseStatus responseStatus;

	@Override
	public void call() {
		JsonParser instance = JsonParser.getInstance();
		String currentUrl = ApiConnection.URL + "quit";
		String queryPostJSON;
		queryPostJSON = REST.queryRESTurl(currentUrl, REST.ENCODING_UTF);
		
		try {

			responseStatus=instance.getResponseStatus(queryPostJSON);
			onResponse();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Network","ERROR in QuitGame");
		}
		
	}

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}



}
