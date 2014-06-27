package com.example.iqfight.network.data.ws;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class GamesResponse extends ResponseStatus{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SerializedName("games")
	private List<GameResponse> games;
	
	@SerializedName("refresh_interval")
	private String refresh_interval;
	
	public List<GameResponse> getGames() {
		return games;
	}

	public void setGames(List<GameResponse> games) {
		this.games = games;
	}



	public String getRefresh_interval() {
		return refresh_interval;
	}

	public void setRefresh_interval(String refresh_interval) {
		this.refresh_interval = refresh_interval;
	}
}
