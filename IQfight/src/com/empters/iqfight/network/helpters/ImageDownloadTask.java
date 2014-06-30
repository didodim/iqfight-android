package com.empters.iqfight.network.helpters;

import java.io.InputStream;

import com.empters.iqfight.utils.AnswersAdapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class ImageDownloadTask extends AsyncTask<String, Void, Bitmap> {

	

	ImageView bmImage;

	public ImageDownloadTask(ImageView bmImage) {
		this.bmImage = bmImage;

	}

	public ImageDownloadTask() {
		bmImage = null;
	}

	protected Bitmap doInBackground(String... urls) {
		String urldisplay = urls[0];
		Bitmap mIcon11 = null;
		try {
			InputStream in = new java.net.URL(urldisplay).openStream();
			mIcon11 = BitmapFactory.decodeStream(in);
		} catch (Exception e) {
			Log.e("Error", e.getMessage());
			e.printStackTrace();
		}
		

		return mIcon11;
	}
	
	@Override
	protected void onPostExecute(Bitmap result) {
		if (bmImage != null) {
			bmImage.setImageBitmap(result);
			
		}
		super.onPostExecute(result);
	}

}
