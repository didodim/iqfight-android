package com.example.iqfight.network.helpters;

import android.util.Log;

import com.example.iqfight.network.data.ws.GameResponse;
import com.example.iqfight.network.data.ws.GamesResponse;
import com.example.iqfight.network.data.ws.LoginResponse;
import com.example.iqfight.network.data.ws.RegisterResponse;
import com.example.iqfight.network.data.ws.ResponseStatus;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonParser {

	/** The only instance of the class. */
	private static JsonParser instance = new JsonParser();

	/** The parse to use for serialziation/deserialization to/from json. */
	private Gson gsonParser;

	private JsonParser() {
		GsonBuilder gsonBuilder = new GsonBuilder();
	
		gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE);
		gsonParser = gsonBuilder.create();
	}

	/** Returns the only instance of the class. */
	public static JsonParser getInstance() {
		return instance;
	}

	public String toJSON(Object object) {
		String jsonString = gsonParser.toJson(object);
		Log.d("JSONParser", "toJSON: " + jsonString);
		return jsonString;
	}
	
	public ResponseStatus getResponseStatus(String json) {
		ResponseStatus fromJson = gsonParser.fromJson(json,
				ResponseStatus.class);
		return fromJson;
	}
	
	public RegisterResponse getRegister(String json) {
		RegisterResponse fromJson = gsonParser.fromJson(json,
				RegisterResponse.class);
		return fromJson;
	}
	
	public LoginResponse getLogin(String json) {
		LoginResponse fromJson = gsonParser.fromJson(json,
				LoginResponse.class);
		return fromJson;
	}
	
	public GamesResponse getGames(String json) {
		GamesResponse fromJson = gsonParser.fromJson(json,
				GamesResponse.class);
		return fromJson;
	}
	
	public GameResponse getNewGame(String json) {
		GameResponse fromJson = gsonParser.fromJson(json,
				GameResponse.class);
		return fromJson;
	}


}
