package com.empters.iqfight.network.data.ws;

import com.google.gson.annotations.SerializedName;

public class StatisticResponse extends ResponseStatus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SerializedName("usernam")
	private String username;

	@SerializedName("wins")
	private int wins;
	
	@SerializedName("played_games")
	private int playedGames;
	
	@SerializedName("scores")
	private int scores;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getPlayedGames() {
		return playedGames;
	}

	public void setPlayedGames(int playedGames) {
		this.playedGames = playedGames;
	}

	public int getScores() {
		return scores;
	}

	public void setScores(int scores) {
		this.scores = scores;
	}

}
