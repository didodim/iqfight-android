package com.example.iqfight.activities;

import java.util.ArrayList;
import java.util.List;

import com.example.iqfight.R;
import com.example.iqfight.network.abstracts.RefreshGameListener;
import com.example.iqfight.network.data.ws.GameResponse;
import com.example.iqfight.network.helpters.NetworkTask;
import com.example.iqfight.network.helpters.Player;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameActivity extends Activity {

	private String mEmail;
	private Boolean isActive = true;
	protected String mPassword;
	protected String gameId;
	protected String gameName;
	private LinearLayout mWaitLayout;
	private RelativeLayout gameLayout;
	private SharedPreferences settings;
	private GameResponse game;
	private RefreshGameListener refreshGameListener;

	GridView playersView;

	GridView answersView;

	TextView questionView;

	List<Player> players = new ArrayList<Player>();

	static String[] answers = new String[] { "Toronto", "Zurich", "Beijing",
			"Istanbu" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		parseSettings();

		mWaitLayout = (LinearLayout) findViewById(R.id.wait_layout);
		gameLayout = (RelativeLayout) findViewById(R.id.gameplay);

		refreshGameRquest();

	}

	private void refreshGameRquest() {
		refreshGameListener = new RefreshGameListener() {

			@Override
			public void onResponse() {
				Log.i("Network", "Response from RefreshGame: "
						+ this.getGame().getPlayers_to_start());
				if (this.getGame().getPlayers_to_start() == 0) {
					game = this.getGame();
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							generateGame();

						}
					});
				} else {
					if (isActive) {
						try {
							Thread.sleep(3000);
							refreshGameRquest();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		};
		refreshGameListener.setId(gameId);
		new NetworkTask().execute(refreshGameListener);
	}

	@Override
	protected void onStop() {
		isActive=false;
		super.onStop();
	}

	private void generateGame() {
		generateContent();
		questionView = (TextView) findViewById(R.id.questionTextView);
		questionView.setText("Which of cities is redundant?");

		createPlayersView();
		createAnswersView();
	}

	private void parseSettings() {
		settings = getSharedPreferences("IqFight", Activity.MODE_PRIVATE);
		mEmail = settings.getString("username", "");
		mPassword = settings.getString("password", "");
		gameId = settings.getString("gameId", "");
		gameName = settings.getString("gameName", "");
	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent(getApplicationContext(), ModeActivity.class);
		startActivity(i);
		finish();

	}

	private void generateContent() {
		players.add(new Player(mEmail, "0"));
		players.add(new Player("ivanov@gmail.com ", "0"));
		players.add(new Player("petrov@gmail.com ", "0"));
	}

	public View getView(int position, View v, ViewGroup parent) {

		((TextView) v).setTextSize(15);
		((TextView) v).setTextColor(Color.BLUE);
		return v;
	}

	private void createPlayersView() {
		List<String> playersStr = new ArrayList<String>();
		int playersLenght = players.size();
		for (Player player : players) {
			playersStr.add(player.getNameAndPoints());
		}

		playersView = (GridView) findViewById(R.id.playersView);
		ArrayAdapter<String> playersAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1,
				playersStr.toArray(new String[playersLenght]));

		playersView.setAdapter(playersAdapter);

	}

	private void createAnswersView() {
		answersView = (GridView) findViewById(R.id.answersView);
		answersView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int arg2,
					long arg3) {
				Log.i("Players View", "Clicked! on" + ((TextView) v).getText());

			}
		});
		ArrayAdapter<String> answersAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, answers);

		answersView.setAdapter(answersAdapter);
	}

}
