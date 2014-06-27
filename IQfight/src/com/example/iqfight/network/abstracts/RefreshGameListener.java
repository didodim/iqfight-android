package com.example.iqfight.network.abstracts;

import android.util.Log;

import com.example.iqfight.network.REST;
import com.example.iqfight.network.data.ws.GameResponse;
import com.example.iqfight.network.helpters.ApiConnection;
import com.example.iqfight.network.helpters.JsonParser;

public abstract class RefreshGameListener extends RequestListener {

	private GameResponse game;
	private String id;

	public GameResponse getGame() {
		return game;
	}

	public void setGameRefresh(GameResponse gameRefresh) {
		this.game = gameRefresh;
	}
	
	@Override
	public void call() {
		JsonParser instance = JsonParser.getInstance();
		String currentUrl = ApiConnection.URL + "refresh_game?id="+id;
		String queryPostJSON;
		queryPostJSON = REST.queryRESTurl(currentUrl, REST.ENCODING_UTF);
		
		try {

			game =instance.getNewGame(queryPostJSON);
			onResponse();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Network","ERROR in RefreshGameListener");
		}
		
	}



	public void setId(String id) {
		this.id = id;
	}

}
