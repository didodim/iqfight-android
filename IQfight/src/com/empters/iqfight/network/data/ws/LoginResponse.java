package com.empters.iqfight.network.data.ws;

import com.google.gson.annotations.SerializedName;

public class LoginResponse  extends ResponseStatus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SerializedName("username")
	private String username;
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
