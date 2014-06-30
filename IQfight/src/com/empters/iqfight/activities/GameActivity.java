package com.empters.iqfight.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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

import com.empters.iqfight.R;
import com.empters.iqfight.network.abstracts.AnswerUserListener;
import com.empters.iqfight.network.abstracts.OpenGameListener;
import com.empters.iqfight.network.abstracts.PlayListener;
import com.empters.iqfight.network.abstracts.QuitGameListener;
import com.empters.iqfight.network.abstracts.RefreshGameListener;
import com.empters.iqfight.network.data.ws.AnswerResponse;
import com.empters.iqfight.network.data.ws.GameResponse;
import com.empters.iqfight.network.data.ws.PlayResponse;
import com.empters.iqfight.network.data.ws.QuestionResponse;
import com.empters.iqfight.network.data.ws.UserResponse;
import com.empters.iqfight.network.helpters.ApiConnection;
import com.empters.iqfight.network.helpters.ImageDownloadTask;
import com.empters.iqfight.network.helpters.NetworkTask;
import com.empters.iqfight.network.helpters.Player;
import com.empters.iqfight.utils.AnswerView;
import com.empters.iqfight.utils.AnswersAdapter;

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
	private Boolean isBlocked = false;
	private Boolean isCorrectAnswer = false;
	
	private Boolean isAnswered;

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
	int questionNum=-1;
	List<View> answerViews;
	List<AnswerView> holders;

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
		answerViews = new ArrayList<View>();
		holders = new ArrayList<AnswerView>();
		LayoutInflater mInflater = (LayoutInflater) this
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		answersView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
				sendAnswerUser(position);

			}

		});

		for (int i = 0; i < 4; i++) {
			AnswerView holder = new AnswerView();

			View currentView = mInflater
					.inflate(R.layout.fragment_answer, null);

			holder.setAnswerText((TextView) currentView
					.findViewById(R.id.answerText));
			holder.setAnswerView((ImageView) currentView
					.findViewById(R.id.answerView));
			currentView.setTag(holder);

			holders.add(holder);
			answerViews.add(currentView);
		}

	}

	private void sendAnswerUser(int position) {
		AnswerResponse answerR = answersResponse.get(position);
	
		AnswerUserListener answerUserListener = new AnswerUserListener() {

			@Override
			public void onResponse() {
				Log.i("AnswerUser", "" + getAnswerUserResponse().getCorrect());
				isAnswered = true;
				isCorrectAnswer = getAnswerUserResponse().getCorrect();
			}
		};
		answerUserListener.setAnswerId(answerR.getId());
		new NetworkTask().execute(answerUserListener);

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

								createPlayersView(false);

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
		createPlayersView(true);
		
		if(questionNum!=playResponse.getQuestion().getNumber()){
			isAnswered=false;
		}
		
		questionNum = playResponse.getQuestion().getNumber();
		if (isBlocked|| isAnswered) {
			showExplanation();
			
		} else {
			currentQuestion = playResponse.getQuestion();

			createQuestionView();

			createAnswersView();
		}
	}
	
	private void showExplanation(){
	
		String answeredUser =playResponse.getAnsweredUser();
		if(answeredUser.equalsIgnoreCase("nobody") || answeredUser.equalsIgnoreCase("")){
			questionTextQ.setText("Please wait for question");
		}else{
			questionTextQ.setText(answeredUser+" answered correct!");
		}
		
		if(answeredUser.equals(mEmail)){
			questionTextQ.setText("You answered correct!");
		}
		
		questionTextQ.setVisibility(View.FOCUS_FORWARD);
		answersView.setVisibility(View.GONE);
		questionImage.setVisibility(View.GONE);
	}

	private void createQuestionView() {

		this.setTitle("Question " + currentQuestion.getNumber());
		
		if (playResponse.getRemainingTime() > 0) {
			int time = (playResponse.getRemainingTime() / 5000) + 1;

			remainTime.setText("Remain Time: < " + time * 5);

			remainTime.setVisibility(View.FOCUS_FORWARD);
		} else {
			remainTime.setVisibility(View.FOCUS_FORWARD);
			remainTime.setText("Remain Time: 0");
			
		}

		if (checkStr(currentQuestion.getQuestion())) {
			
			questionTextQ.setText(currentQuestion.getQuestion());
			questionTextQ.setVisibility(View.FOCUS_FORWARD);
		} else {
			questionTextQ.setVisibility(View.GONE);
		}

		if (checkStr(currentQuestion.getPicture())) {
			
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
				isBlocked = playResponse.getIsBlocked();
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

	private void createPlayersView(Boolean isPlay) {

		players = new ArrayList<Player>();

		if (isPlay) {
			for (UserResponse user : playResponse.getUsers()) {

				players.add(new Player(user.getName(), user.getPoints()));
			}
		} else {
			for (String playerName : game.getUsers()) {
				players.add(new Player(playerName, -1));
			}
		}
		List<String> playersStr = new ArrayList<String>();
		int playersLenght = players.size();
		for (Player player : players) {
			playersStr.add(player.getNameAndPoints());
		}

		ArrayAdapter<String> playersAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1,
				playersStr.toArray(new String[playersLenght])) {

			@Override
			public boolean isEnabled(int position) {

				return false;
			}

			@Override
			public boolean areAllItemsEnabled() {
				return false;
			}

		};

		playersView.setAdapter(playersAdapter);

	}

	private void createAnswersView() {
		answersView.setVisibility(View.FOCUS_FORWARD);
		currentAnswers = playResponse.getAnswers();
		if (isNewAnswers()) {

			for (int i = 0; i < currentAnswers.size(); i++) {
				AnswerResponse answer = currentAnswers.get(i);
				//
				if (checkStr(answer.getAnswer())) {
					holders.get(i).getAnswerText()
							.setVisibility(View.FOCUS_FORWARD);
					holders.get(i).getAnswerText().setText(answer.getAnswer());
				} else {
					holders.get(i).getAnswerText().setVisibility(View.GONE);
					holders.get(i).getAnswerText().setText("");
				}

				if (checkStr(answer.getPicture())) {
					holders.get(i).getAnswerView()
							.setVisibility(View.FOCUS_FORWARD);
					new ImageDownloadTask(holders.get(i).getAnswerView())
							.execute(ApiConnection.URL
									+ answer.getPicture().substring(1));
					// .execute(ApiConnection.URL+"media/pictures/Q27/1.gif");

				} else {
					holders.get(i).getAnswerView().setVisibility(View.GONE);

				}

			}

			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					updateAdapter();

				}
			}, 1000);

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
