package com.empters.iqfight.activities;

import java.util.ArrayList;

import com.empters.iqfight.network.abstracts.GamesListener;
import com.empters.iqfight.network.abstracts.LogoutListener;
import com.empters.iqfight.network.abstracts.NewGameListener;
import com.empters.iqfight.network.data.ws.GameResponse;
import com.empters.iqfight.network.data.ws.GamesResponse;
import com.empters.iqfight.network.helpters.NetworkTask;
import com.empters.iqfight.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ModeActivity extends Activity {
	// TODO LOGIN
	private ListView modeView;

	private ListView gamesView;
	private GamesResponse gamesResponse;
	private Button newGameSubmit;
	private EditText newGameName;

	private TextView newGameText;

	private View newGameView;

	private GamesListener gamesListener;

	private NewGameListener newGameRequest;
	private SharedPreferences settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		setContentView(R.layout.activity_mode);
		settings = getSharedPreferences("IqFight", Activity.MODE_PRIVATE);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		createViews();

	}

	private void createViews() {

		gamesListener = new GamesListener() {

			@Override
			public void onResponse() {

				gamesResponse = this.getGamesResponse();

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						refreshGames();

					}
				});
			}
		};

		new NetworkTask().execute(gamesListener);

		newGameView = findViewById(R.id.new_game);
		newGameSubmit = (Button) findViewById(R.id.new_game_submit);
		newGameText = (TextView) findViewById(R.id.new_game_text);
		newGameSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (newGameName.getText().toString().length() > 3) {
					Log.i("Iqfight", "Clicked button, save new game:"
							+ newGameName.getText().toString());

					newGameRequest = new NewGameListener() {

						@Override
						public void onResponse() {

						}
					};
					newGameRequest.setNewGame(newGameName.getText().toString());
					new NetworkTask().execute(newGameRequest);

					newGameSubmit.setClickable(false);
					newGameText.setVisibility(View.FOCUS_FORWARD);

				}
			}
		});
		newGameName = (EditText) findViewById(R.id.new_game_name);
		modeView = (ListView) findViewById(R.id.modeView);

		modeView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				onModeViewSelected(arg0, arg1, arg2, arg3);

			}
		});
		gamesView = (ListView) findViewById(R.id.gamesView);
		gamesView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				onGamesViewSelected(arg0, arg1, arg2, arg3);

			}

		});
	}

	private void onGamesViewSelected(AdapterView<?> parentView, View childView,
			int position, long id) {

		SharedPreferences.Editor editor = settings.edit();

		Intent i = new Intent(getApplicationContext(), GameActivity.class);
		i.putExtra("gameResponse", gamesResponse.getGames().get(position));
		editor.putString("gameId", gamesResponse.getGames().get(position)
				.getId());
		editor.putString("gameName", gamesResponse.getGames().get(position)
				.getName());
		editor.commit();
		startActivity(i);
		finish();

	}

	private void onModeViewSelected(AdapterView<?> parentView, View childView,
			int position, long id) {
		Log.i("Mode Lists", "You clicked Item: " + id + " at position:"
				+ position);

		switch (position) {
		case 0:
			modeView.setVisibility(View.GONE);
			Log.i("Iqfight", "SHOW");
			showGames();
			break;
		case 1:
			modeView.setVisibility(View.GONE);
			newGameView.setVisibility(View.FOCUS_FORWARD);
			break;
			
		case 2:
			modeView.setVisibility(View.GONE);
			showStatistics();
			

		}
	}

	private void showStatistics() {
		Intent i = new Intent(getApplicationContext(), StatisticActivity.class);
		startActivity(i);
		finish();
		
	}

	private void showGames() {
		new NetworkTask().execute(gamesListener);
		gamesView.setVisibility(View.FOCUS_FORWARD);

	}

	private void refreshGames() {
		ArrayList<String> listItems = new ArrayList<String>();
		for (GameResponse game : gamesResponse.getGames()) {

			listItems.add(game.getName());
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listItems);
		gamesView.setAdapter(adapter);
	}

	public void showModeView() {
		gamesView.setVisibility(View.GONE);
		modeView.setVisibility(View.FOCUS_FORWARD);
		newGameView.setVisibility(View.GONE);
		newGameSubmit.setClickable(true);
		newGameText.setVisibility(View.GONE);
	}

	@Override
	public void onBackPressed() {
		

		if (modeView.getVisibility() != View.GONE) {
			logoutUser();
			Intent i = new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(i);
			finish();
		} else {
			showModeView();
		}

	}

	private void logoutUser() {
		LogoutListener logoutListener = new LogoutListener() {

			@Override
			public void onResponse() {
				Log.i("Network", "User is logout");

			}
		};
		new NetworkTask().execute(logoutListener);
		SharedPreferences.Editor editor = settings.edit();
		editor.remove("username");
		editor.remove("password");

		editor.commit();

	}

}
