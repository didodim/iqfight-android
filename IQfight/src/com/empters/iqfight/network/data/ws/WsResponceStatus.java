package com.empters.iqfight.network.data.ws;

import com.google.gson.annotations.SerializedName;

public class WsResponceStatus {

	@SerializedName("session_id")
	private int sessionId;
	@SerializedName("status")
	private ResponseStatus status;

	public ResponseStatus getStatus() {
		return status;
	}

	public void setStatus(ResponseStatus status) {
		this.status = status;
	}

	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}
}
