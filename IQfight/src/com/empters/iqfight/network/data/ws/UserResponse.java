package com.empters.iqfight.network.data.ws;

import com.google.gson.annotations.SerializedName;

public class UserResponse extends ResponseStatus{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@SerializedName("name")
	private String name;
	
	@SerializedName("points")
	private String points;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}
}
