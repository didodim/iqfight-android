package com.empters.iqfight.network.abstracts;

import android.util.Log;

import com.empters.iqfight.network.REST;
import com.empters.iqfight.network.data.ws.GamesResponse;
import com.empters.iqfight.network.helpters.ApiConnection;
import com.empters.iqfight.network.helpters.JsonParser;

public abstract class GamesListener extends RequestListener {

	private GamesResponse gamesResponse;


	@Override
	public void call() {

		Log.d("Network", "Get games");
		JsonParser instance = JsonParser.getInstance();

		String currentUrl = ApiConnection.URL + "get_games";
		String queryPostJSON;
		queryPostJSON = REST.queryRESTurl(currentUrl, REST.ENCODING_UTF);

		try {

			setGamesResponse(instance.getGames(queryPostJSON));
			onResponse();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Network","ERROR in GamesListener");
		}
	
		
	}

	public GamesResponse getGamesResponse() {
		return gamesResponse;
	}

	private void setGamesResponse(GamesResponse gamesResponse) {
		this.gamesResponse = gamesResponse;
	}
}
