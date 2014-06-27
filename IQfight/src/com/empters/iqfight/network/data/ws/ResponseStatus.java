package com.empters.iqfight.network.data.ws;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class ResponseStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6263856575920218345L;
	@SerializedName("status")
	protected String status;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@SerializedName("error_message")
	protected String errorMessage;
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	

}
