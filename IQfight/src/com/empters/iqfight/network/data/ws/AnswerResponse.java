package com.empters.iqfight.network.data.ws;

import com.google.gson.annotations.SerializedName;

public class AnswerResponse extends ResponseStatus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SerializedName("answer")
	private String answer;
	
	@SerializedName("picture")
	private String picture;
	
	@SerializedName("id")
	private String id;

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		AnswerResponse answr = (AnswerResponse) o;
		return id.equals(answr.getId());
	}

}
