package com.empters.iqfight.network.data.ws;

import com.google.gson.annotations.SerializedName;


public  class AnswerUserResponse extends ResponseStatus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SerializedName("answered_user")
	private String answeredUser;
	
	
	@SerializedName("correct")
	private Boolean correct;
	
	
	@SerializedName("already_answered")
	private Boolean alreadyAnswered;

	public String getAnsweredUser() {
		return answeredUser;
	}

	public void setAnsweredUser(String answeredUser) {
		this.answeredUser = answeredUser;
	}

	public Boolean getCorrect() {
		return correct;
	}

	public void setCorrect(Boolean correct) {
		this.correct = correct;
	}

	public Boolean getAlreadyAnswered() {
		return alreadyAnswered;
	}

	public void setAlreadyAnswered(Boolean alreadyAnswered) {
		this.alreadyAnswered = alreadyAnswered;
	}

}
