package com.kistaffing.androidtest.tasks;

import com.aplit.dev.listeners.ReadHttpTaskListener;
import com.aplit.dev.listeners.TimerListener;
import com.aplit.dev.utilities.ConversionUtility;
import com.aplit.dev.utilities.HttpUtility;
import com.aplit.dev.views.ProgressDialogWrapper;
import com.aplit.dev.wrappers.DebugLog;
import com.aplit.dev.wrappers.Timer;
import com.kistaffing.androidtest.utilities.Constants;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

public class SearchTask extends AsyncTask<String, Void, String> implements TimerListener {
	private static final String TAG = "SearchTask";

	private Context context;
	private ReadHttpTaskListener listener;
	private ProgressDialogWrapper progressDialog;
	private Timer timer;

	public SearchTask(Context context, ReadHttpTaskListener listener) {
		this.context = context;
		this.listener = listener;
		timer = new Timer(Constants.CONNECTION_PERIOD, this, -1);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			progressDialog = new ProgressDialogWrapper(context, "Searching movie...");
		} else {
			progressDialog = new ProgressDialogWrapper(context, "Searching movie...", false, 0);
		}
		if (!progressDialog.isShowing()) {
			progressDialog.show();
		}
		timer.startTimer();
	}

	@Override
	protected String doInBackground(String... params) {
		String apiKey = params[0];
		String searchString = params[1];
		String url = Constants.MOVIES_URL + "?"
				 + Constants.PARAM_API_KEY + "=" + apiKey + "&"
				+ Constants.PARAM_SEARCH_QUERY + "=" + ConversionUtility.urlEncode(searchString);
		return HttpUtility.connect(context, url);
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
		if (progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
		if (listener != null) {
			listener.onObtainedHttpContent(Constants.REQUEST_SEARCH, Constants.RESPONSE_CONNECTION_TIMEOUT);
		}
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if (progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
		timer.stopTimer();
		if (listener != null) {
			listener.onObtainedHttpContent(Constants.REQUEST_SEARCH, result);
		}
	}

	@Override
	public void onTimeOut(int period, int timerId) {
		boolean taskCancelled = cancel(true);
		DebugLog.w(context, TAG, "onTimeOut period:" + period + "|timerId:" + timerId + "|taskCancelled:" + taskCancelled + "***");
	}
}

