package com.empters.iqfight.network.data.ws;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class StatisticsResponse extends ResponseStatus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SerializedName("users")
	private List<StatisticResponse> users;

	public List<StatisticResponse> getUsers() {
		return users;
	}

	public void setUsers(List<StatisticResponse> users) {
		this.users = users;
	}

}
