package com.empters.iqfight.network.abstracts;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.util.Log;

import com.empters.iqfight.network.REST;
import com.empters.iqfight.network.data.ws.GameResponse;
import com.empters.iqfight.network.helpters.ApiConnection;
import com.empters.iqfight.network.helpters.JsonParser;

public abstract class NewGameListener extends RequestListener {
	private String newGame;
	
	private static GameResponse gameResponse = null;
	
	
	
	@Override
	public  void call() {

		Log.d("Network", "New game");
		JsonParser instance = JsonParser.getInstance();
		String newGameEncode = "";
		try {
			newGameEncode = URLEncoder.encode(newGame, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			Log.e("Network", "Cannot encode " + newGame);
			e1.printStackTrace();
		}
		String currentUrl = ApiConnection.URL + "new_game?name=" + newGameEncode;
		String queryPostJSON;
		queryPostJSON = REST.queryRESTurl(currentUrl, REST.ENCODING_UTF);

		try {

			gameResponse = instance.getNewGame(queryPostJSON);
			onResponse();

		} catch (Exception e) {
		
		}
		

	}
	
	public static GameResponse getGameResponse() {
		return gameResponse;
	}

	public static void setGameResponse(GameResponse gameResponse) {
		NewGameListener.gameResponse = gameResponse;
	}

	public void setNewGame(String newGame){
		this.newGame = newGame;
	}
	
}
