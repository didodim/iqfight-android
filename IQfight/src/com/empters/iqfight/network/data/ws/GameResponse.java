package com.empters.iqfight.network.data.ws;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class GameResponse extends ResponseStatus {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SerializedName("id")
	private String id;
	
	@SerializedName("name")
	private String name;
	
	@SerializedName(value="users")
	List<String> users;
	
	@SerializedName("players_to_start")
	private int players_to_start;
	
	

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPlayers_to_start() {
		return players_to_start;
	}

	public void setPlayers_to_start(int players_to_start) {
		this.players_to_start = players_to_start;
	}


}
