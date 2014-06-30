package com.empters.iqfight.network.abstracts;

import android.util.Log;

import com.empters.iqfight.network.REST;
import com.empters.iqfight.network.data.ws.AnswerUserResponse;
import com.empters.iqfight.network.helpters.ApiConnection;
import com.empters.iqfight.network.helpters.JsonParser;

public abstract class AnswerUserListener extends RequestListener{

	private String answerId;
	
	private AnswerUserResponse answerUserResponse;
	
	@Override
	public void call() {

	
		JsonParser instance = JsonParser.getInstance();

		String currentUrl = ApiConnection.URL + "answer?answer_id";
		String queryPostJSON;
		queryPostJSON = REST.queryRESTurl(currentUrl, REST.ENCODING_UTF);

		try {

			answerUserResponse=instance.getAnswerUserResponse(queryPostJSON);
			onResponse();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Network","ERROR in GamesListener");
		}
	
		
	}

	public String getAnswerId() {
		return answerId;
	}

	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}

	public AnswerUserResponse getAnswerUserResponse() {
		return answerUserResponse;
	}

	public void setAnswerUserResponse(AnswerUserResponse answerUserResponse) {
		this.answerUserResponse = answerUserResponse;
	}
}
