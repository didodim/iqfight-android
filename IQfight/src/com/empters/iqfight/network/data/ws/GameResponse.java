package com.empters.iqfight.network.data.ws;


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
	
	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}

	@SerializedName("players_to_start")
	private int playersToStart;
	
	

	
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

	public int getPlayersToStart() {
		return playersToStart;
	}

	public void setPlayersToStart(int playersToStart) {
		this.playersToStart = playersToStart;
	}


}
