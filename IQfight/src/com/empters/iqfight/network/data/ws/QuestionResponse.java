package com.empters.iqfight.network.data.ws;

import com.google.gson.annotations.SerializedName;

public class QuestionResponse extends ResponseStatus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SerializedName("picture")
	private String picture;
	
	@SerializedName("explanation")
	private String explanation;

	
	@SerializedName("question")
	private String question;
	
	@SerializedName("number")
	private int number;
	
	
	@SerializedName("source")
	private String source;
	
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
}
