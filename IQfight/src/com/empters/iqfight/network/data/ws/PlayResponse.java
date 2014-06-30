package com.empters.iqfight.network.data.ws;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class PlayResponse extends ResponseStatus {




	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SerializedName("users")
	private List<UserResponse> users;
	
	@SerializedName("answered_user")
	private String answeredUser;
	
	@SerializedName("question")
	private QuestionResponse question;
	
	@SerializedName("game_over")
	private Boolean gameOver;
	
	


	@SerializedName("answers")
	private List<AnswerResponse> answers;
	
	@SerializedName("remaing_time")
	private int remainingTime;
	
	@SerializedName("is_blocked")
	private Boolean isBlocked;
	
	@SerializedName("refresh_interval")
	private int refreshInterval;

	public List<UserResponse> getUsers() {
		return users;
	}

	
	public Boolean getGameOver() {
		return gameOver;
	}


	public void setGameOver(Boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	public void setUsers(List<UserResponse> users) {
		this.users = users;
	}


	

	public List<AnswerResponse> getAnswers() {
		return answers;
	}

	public void setAnswers(List<AnswerResponse> answers) {
		this.answers = answers;
	}

	public int getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(int remainingTime) {
		this.remainingTime = remainingTime;
	}

	public Boolean getIsBlocked() {
		return isBlocked;
	}

	public void setIsBlocked(Boolean isBlocked) {
		this.isBlocked = isBlocked;
	}


	public int getRefreshInterval() {
		return refreshInterval;
	}


	public void setRefreshInterval(int refreshInterval) {
		this.refreshInterval = refreshInterval;
	}


	public QuestionResponse getQuestion() {
		return question;
	}


	public void setQuestion(QuestionResponse question) {
		this.question = question;
	}


	public String getAnsweredUser() {
		return answeredUser;
	}


	public void setAnsweredUser(String answeredUser) {
		this.answeredUser = answeredUser;
	}
	
	
	
	

}
