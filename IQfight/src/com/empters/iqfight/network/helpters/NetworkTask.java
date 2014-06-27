package com.empters.iqfight.network.helpters;

import com.empters.iqfight.network.abstracts.RequestListener;

import android.os.AsyncTask;

public class NetworkTask extends AsyncTask<RequestListener, Void, Boolean> {

	@Override
	protected Boolean doInBackground(RequestListener... requests) {

		RequestListener requestListener = requests[0];
		requestListener.call();
		
		return true;
	}

}
