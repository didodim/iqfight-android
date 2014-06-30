package com.empters.iqfight.network.abstracts;

import android.util.Log;

import com.empters.iqfight.network.REST;
import com.empters.iqfight.network.data.ws.PlayResponse;
import com.empters.iqfight.network.helpters.ApiConnection;
import com.empters.iqfight.network.helpters.JsonParser;

public abstract class PlayListener extends RequestListener {
	
	private PlayResponse playResponse;
	
	@Override
	public void call() {
		JsonParser instance = JsonParser.getInstance();
		String currentUrl = ApiConnection.URL + "play";
		String queryPostJSON;
		queryPostJSON = REST.queryRESTurl(currentUrl, REST.ENCODING_UTF);
		
		try {

			
			setPlayResponse(instance.getPlayResponse(queryPostJSON));
			onResponse();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Network","ERROR in Play");
		}
		
	}

	public PlayResponse getPlayResponse() {
		return playResponse;
	}

	public void setPlayResponse(PlayResponse playResponse) {
		this.playResponse = playResponse;
	}
}
