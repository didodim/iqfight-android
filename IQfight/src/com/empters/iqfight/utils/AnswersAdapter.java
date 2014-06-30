package com.empters.iqfight.utils;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class AnswersAdapter<T> extends ArrayAdapter<T> {

	@Override
	public void add(T object) {
		answers.add(object);
		super.add(object);
	}

	String[] answs = { "media/pictures/Q26/1.gif", "media/pictures/Q26/2.gif",
			"media/pictures/Q26/3.gif", "media/pictures/Q26/4.gif" };

	private Context context;
	private List<T> answers;

	public AnswersAdapter(Context context, int resource, List<T> answers) {

		super(context, resource, answers);
		this.context = context;
		this.answers = answers;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		this.notifyDataSetChanged();
		View currentAnswer = (View) answers.get(position);

		return currentAnswer;
		// LayoutInflater mInflater = (LayoutInflater) context
		// .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		// // Log.i("Answer",""+ position);
		//
		// convertView = mInflater.inflate(R.layout.fragment_answer, null);
		// holder = new ViewHolder();
		// holder.answerText = (TextView) convertView
		// .findViewById(R.id.answerText);
		// holder.answerView = (ImageView) convertView
		// .findViewById(R.id.answerView);
		// convertView.setTag(holder);
		//
		// // if(GameActivity.checkStr(currentAnswer.getAnswer())){
		// if (false) {
		// holder.answerText.setVisibility(View.FOCUS_FORWARD);
		// holder.answerText.setText(currentAnswer.getAnswer());
		// } else {
		// holder.answerText.setVisibility(View.GONE);
		// holder.answerText.setText("");
		// }
		//
		// // if(GameActivity.checkStr(currentAnswer.getPicture())){
		// Log.i("ANswers 123", "" + position);
		//
		//
		// holder.answerView.setVisibility(View.FOCUS_FORWARD);
		// ImageDownloadTask imgDown = new ImageDownloadTask(holder.answerView);
		//
		// imgDown.execute(ApiConnection.URL + answs[position]);
		// if (true) {
		//
		// } else {
		// holder.answerView.setVisibility(View.GONE);
		// }
		//
		// return convertView;
	}

}
