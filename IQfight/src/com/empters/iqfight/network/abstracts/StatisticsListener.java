package com.empters.iqfight.network.abstracts;

import android.util.Log;

import com.empters.iqfight.network.REST;
import com.empters.iqfight.network.data.ws.StatisticsResponse;
import com.empters.iqfight.network.helpters.ApiConnection;
import com.empters.iqfight.network.helpters.JsonParser;

public abstract class StatisticsListener extends RequestListener{
	
	private int limit;
	private int offset;
	
	private StatisticsResponse statistics;
	
	
	
	
	@Override
	public void call() {
		JsonParser instance = JsonParser.getInstance();
		
		String currentUrl = ApiConnection.URL + "statistics?limit="+limit+"&offset="+offset;
		
		String queryPostJSON;
		
		queryPostJSON = REST.queryRESTurl(currentUrl, REST.ENCODING_UTF);
		
		try {

			setStatistics(instance.getStatisticsResponse(queryPostJSON));
			onResponse();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Network","ERROR in Statistics");
		}
		
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public StatisticsResponse getStatistics() {
		return statistics;
	}

	public void setStatistics(StatisticsResponse statistics) {
		this.statistics = statistics;
	}

}
