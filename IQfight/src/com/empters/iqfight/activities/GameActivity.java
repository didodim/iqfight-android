package com.empters.iqfight.activities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.empters.iqfight.R;
import com.empters.iqfight.network.abstracts.OpenGameListener;
import com.empters.iqfight.network.abstracts.PlayListener;
import com.empters.iqfight.network.abstracts.QuitGameListener;
import com.empters.iqfight.network.abstracts.RefreshGameListener;
import com.empters.iqfight.network.data.ws.AnswerResponse;
import com.empters.iqfight.network.data.ws.GameResponse;
import com.empters.iqfight.network.data.ws.PlayResponse;
import com.empters.iqfight.network.data.ws.QuestionResponse;
import com.empters.iqfight.network.helpters.ApiConnection;
import com.empters.iqfight.network.helpters.ImageDownloadTask;
import com.empters.iqfight.network.helpters.NetworkTask;
import com.empters.iqfight.network.helpters.Player;
import com.empters.iqfight.utils.AnswerView;
import com.empters.iqfight.utils.AnswersAdapter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
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

	private PlayResponse playResponse;

	private QuestionResponse currentQuestion;

	private List<AnswerResponse> currentAnswers;
	private List<AnswerResponse> answersResponse = null;

	GridView playersView;

	GridView answersView;

	TextView questionTextQ;
	TextView remainTime;
	ImageView questionImage;
	TextView waitText;
	AnswersAdapter<View> answersAdapter;

	List<View> answerViews;

	List<Player> players = new ArrayList<Player>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		parseSettings();

		loadViews();

		mWaitLayout.setVisibility(View.FOCUS_FORWARD);
		openGameRequest();
		refreshGameRequest();

	}

	private void loadViews() {
		mWaitLayout = (LinearLayout) findViewById(R.id.wait_layout);
		gameLayout = (RelativeLayout) findViewById(R.id.gamelayout);
		remainTime = (TextView) findViewById(R.id.remainTime);
		questionTextQ = (TextView) findViewById(R.id.questionText);
		questionImage = (ImageView) findViewById(R.id.questionImageView);
		playersView = (GridView) findViewById(R.id.playersView);
		waitText = (TextView) findViewById(R.id.waitview);
		answersView = (GridView) findViewById(R.id.answersView);

	}

	@Override
	protected void onDestroy() {
		isActive = false;
		QuitGameListener quitGameListener = new QuitGameListener() {

			@Override
			public void onResponse() {
				Log.i("Network", "Quit game");

			}
		};
		// new NetworkTask().execute(quitGameListener);
		super.onDestroy();
	}

	private void openGameRequest() {
		OpenGameListener openGameListener = new OpenGameListener() {

			@Override
			public void onResponse() {
				Log.i("Network", "Open game" + this.getGame().getStatus());
			}
		};
		openGameListener.setId(gameId);
		new NetworkTask().execute(openGameListener);
	}

	private void refreshGameRequest() {
		refreshGameListener = new RefreshGameListener() {

			@Override
			public void onResponse() {
				final int playersToStart = this.getGame().getPlayersToStart();

				Log.i("Network", "RefreshGame: " + playersToStart);

				game = this.getGame();
				if (playersToStart == 0) {

					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							playRequest();

						}
					});
				} else {
					if (isActive) {
						runOnUiThread(new Runnable() {

							@Override
							public void run() {

								if (playersToStart == 1) {
									waitText.setText("Wait for one player to start");
								} else {
									waitText.setText("Wait for "
											+ playersToStart + " players");
								}

								createPlayersView();

							}
						});

						try {
							Thread.sleep(3000);
							refreshGameRequest();
						} catch (InterruptedException e) {

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

		super.onStop();
	}

	private void generateGame() {
		mWaitLayout.setVisibility(View.GONE);
		createPlayersView();

		currentQuestion = playResponse.getQuestion();

		createQuestionView();

		createAnswersView();
	}

	private void createQuestionView() {

		this.setTitle("Question " + currentQuestion.getNumber());
		Log.i("Remain Time", "" + playResponse.getRemainingTime());
		if (playResponse.getRemainingTime() > 0) {
			int time = (playResponse.getRemainingTime() / 5000) + 1;

			remainTime.setText("Remain Time: < " + time * 5);

			remainTime.setVisibility(View.FOCUS_FORWARD);
		} else {
			remainTime.setVisibility(View.GONE);
		}

		if (checkStr(currentQuestion.getQuestion())) {
			Log.i("Question", currentQuestion.getQuestion());
			questionTextQ.setText(currentQuestion.getQuestion());
			questionTextQ.setVisibility(View.FOCUS_FORWARD);
		} else {
			questionTextQ.setVisibility(View.GONE);
		}

		// if (checkStr(currentQuestion.getExplanation())) {
		// Log.i("Question", currentQuestion.getExplanation());
		// questionTextE.setText(currentQuestion.getExplanation());
		// questionTextE.setVisibility(View.FOCUS_FORWARD);
		// }

		if (checkStr(currentQuestion.getPicture())) {
			Log.i("Question", currentQuestion.getPicture());
			questionImage.setVisibility(View.FOCUS_FORWARD);
			new ImageDownloadTask(questionImage).execute(ApiConnection.URL
					+ currentQuestion.getPicture());
		} else {
			questionImage.setVisibility(View.GONE);
		}

	}

	private void playRequest() {
		PlayListener playListener = new PlayListener() {

			@Override
			public void onResponse() {
				playResponse = this.getPlayResponse();
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						Log.i("Network", "PlayResponse");
						generateGame();

					}
				});

				try {
					Thread.sleep(ApiConnection.REFRESH_TIME);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (isActive) {
					playRequest();
				}

			}
		};

		new NetworkTask().execute(playListener);

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

		isActive = false;
		Intent i = new Intent(getApplicationContext(), ModeActivity.class);
		startActivity(i);
		finish();

	}

	public View getView(int position, View v, ViewGroup parent) {

		((TextView) v).setTextSize(15);
		((TextView) v).setTextColor(Color.BLUE);
		return v;
	}

	private void createPlayersView() {
		players = new ArrayList<Player>();
		for (String playerName : game.getUsers()) {
			players.add(new Player(playerName, -1));
		}

		List<String> playersStr = new ArrayList<String>();
		int playersLenght = players.size();
		for (Player player : players) {
			playersStr.add(player.getNameAndPoints());
		}

		ArrayAdapter<String> playersAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1,
				playersStr.toArray(new String[playersLenght]));

		playersView.setAdapter(playersAdapter);

	}

	private void createAnswersView() {
		currentAnswers = playResponse.getAnswers();
		if (isNewAnswers()) {
			answerViews = new ArrayList<View>();

			LayoutInflater mInflater = (LayoutInflater) this
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

			for (AnswerResponse answer : currentAnswers) {
				View currentView = mInflater.inflate(R.layout.fragment_answer,
						null);
				final AnswerView holder = new AnswerView();

				holder.setAnswerText((TextView) currentView
						.findViewById(R.id.answerText));
				holder.setAnswerView((ImageView) currentView
						.findViewById(R.id.answerView));
				currentView.setTag(holder);

				if (checkStr(answer.getAnswer())) {
					holder.getAnswerText().setVisibility(View.FOCUS_FORWARD);
					holder.getAnswerText().setText(answer.getAnswer());
				} else {
					holder.getAnswerText().setVisibility(View.GONE);
					holder.getAnswerText().setText("");
				}

				if (checkStr(answer.getPicture())) {
					holder.getAnswerView().setVisibility(View.FOCUS_FORWARD);

					ImageDownloadTask imgDown = new ImageDownloadTask() {

						@Override
						protected void onPostExecute(final Bitmap result) {
							runOnUiThread(new Runnable() {

								@Override
								public void run() {

									holder.getAnswerView().setImageBitmap(
											result);
								}
							});
						}

					};
					Log.i("Answers", ApiConnection.URL
							+ answer.getPicture().substring(1));
					imgDown.execute(ApiConnection.URL
							+ answer.getPicture().substring(1));
				} else {
					holder.getAnswerView().setVisibility(View.FOCUS_FORWARD);

				}

				answerViews.add(currentView);
			}

			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					updateAdapter();

				}
			}, 2000);
			answersView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View v, int arg2,
						long arg3) {
					Log.i("Players View",
							"Clicked! on" + ((TextView) v).getText());

				}
			});

		}
	}

	private Boolean isNewAnswers() {

		if (answersResponse == null) {
			answersResponse = currentAnswers;
			return true;
		}

		for (int i = 0; i < currentAnswers.size(); i++) {

			if (!currentAnswers.get(i).equals(answersResponse.get(i))) {
				answersResponse = currentAnswers;
				return true;
			}

		}
		return false;

	}

	private void updateAdapter() {
		answersAdapter = new AnswersAdapter<View>(this,
				android.R.layout.simple_list_item_1, answerViews);
		answersView.setAdapter(answersAdapter);

	}

	public static Boolean checkStr(String ch) {

		if (ch != null && !ch.equals("")) {
			return true;
		}
		return false;

	}
}
