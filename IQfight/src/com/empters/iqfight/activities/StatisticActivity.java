package com.empters.iqfight.activities;

import java.util.ArrayList;
import java.util.List;

import com.empters.iqfight.R;
import com.empters.iqfight.R.layout;
import com.empters.iqfight.network.abstracts.StatisticsListener;
import com.empters.iqfight.network.data.ws.StatisticResponse;
import com.empters.iqfight.network.data.ws.StatisticsResponse;
import com.empters.iqfight.network.helpters.NetworkTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

public class StatisticActivity extends Activity {

	GridView statistics;
	List<String> users;
	String username;

	StatisticsResponse statisticsResponse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistic);
		parseSettings();
		users = new ArrayList<String>();

		statistics = (GridView) findViewById(R.id.statisticsView);

		statisticsRequest();

	}

	private void parseSettings() {
		SharedPreferences settings = getSharedPreferences("IqFight",
				Activity.MODE_PRIVATE);
		username = settings.getString("username", "");

	}

	private void statisticsRequest() {
		StatisticsListener statisticsListener = new StatisticsListener() {

			@Override
			public void onResponse() {
				Log.i("Statistics", "Response ");
				statisticsResponse = getStatistics();

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						loadStats();

					}

				});

			}
		};
		statisticsListener.setLimit(100);
		statisticsListener.setOffset(0);
		new NetworkTask().execute(statisticsListener);

	}

	private void loadStats() {
		List<String> stats = new ArrayList<String>();
		stats.add("Username");
		stats.add("Scores");
		stats.add("Wins");

		for (StatisticResponse response : statisticsResponse.getUsers()) {

			stats.add(response.getUsername());
			stats.add(response.getScores() + "");
			stats.add(response.getWins() + "");

		}

		statistics.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, stats) {

			@Override
			public boolean isEnabled(int position) {

				return false;
			}

			@Override
			public boolean areAllItemsEnabled() {
				return false;
			}

		});

	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent(getApplicationContext(), ModeActivity.class);
		startActivity(i);
		finish();
	}
}
