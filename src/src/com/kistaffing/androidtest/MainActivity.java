package com.kistaffing.androidtest;

import com.aplit.dev.listeners.ReadHttpTaskListener;
import com.aplit.dev.utilities.HardwareUtility;
import com.aplit.dev.utilities.Utilities;
import com.aplit.dev.wrappers.DebugLog;
import com.kistaffing.androidtest.tasks.SearchTask;
import com.kistaffing.androidtest.utilities.Constants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements
OnClickListener, ReadHttpTaskListener {
	private static final String TAG = "MainActivity";

	private EditText searchInput;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		DebugLog.e(this, TAG, "onCreate");

		View view = View.inflate(this, R.layout.main, null);
		searchInput = (EditText) view.findViewById(R.id.mainSearchInput);
		Button searchButton = (Button) view.findViewById(R.id.mainSearchButton);
		setContentView(view);

		searchButton.setOnClickListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		DebugLog.e(this, TAG, "onStart");
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		DebugLog.e(this, TAG, "onPostCreate");
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		DebugLog.e(this, TAG, "onRestoreInstanceState");
	}

	@Override
	protected void onResume() {
		super.onResume();
		DebugLog.e(this, TAG, "onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		DebugLog.e(this, TAG, "onPause");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		DebugLog.e(this, TAG, "onSaveInstanceState");
	}

	@Override
	protected void onStop() {
		super.onStop();
		DebugLog.e(this, TAG, "onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		DebugLog.e(this, TAG, "onDestroy");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		DebugLog.e(this, TAG, "onCreateOptionsMenu");
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		DebugLog.e(this, TAG, "onOptionsItemSelected");
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	/** Private Methods */
	private void search() {
		String searchString = searchInput.getEditableText().toString().trim();
		DebugLog.w(this, TAG, "search searchString:" + searchString + "***");
		if (searchString.contentEquals("")) {
			Utilities.showAlertbox(this, "Error", "Please enter a movie to search.", null, -1, null).show();
		} else if (!HardwareUtility.isInternetAvailable(this)) {
			Utilities.showAlertbox(this, "Error", "No internet connection available.", null, -1, null).show();
		} else {
			new SearchTask(this, this).execute(Constants.API_KEY, searchString);
		}
	}


	/** Interfaces */
	@Override
	public void onClick(View view) {
		HardwareUtility.hideSoftKeyboard(this, view);
		int id = view.getId();
		switch (id) {
		case R.id.mainSearchButton:
			search();
			break;
		}
		
	}

	@Override
	public void onObtainedHttpContent(int controlId, String response) {
		switch (controlId) {
		case Constants.REQUEST_SEARCH:
			if (response == null) {
				Utilities.showAlertbox(this, "Error", "Search failed.", null, -1, null).show();
			} else if (response.contentEquals(Constants.RESPONSE_CONNECTION_TIMEOUT)) {
				Utilities.showAlertbox(this, "Error", Constants.RESPONSE_CONNECTION_TIMEOUT, null, -1, null).show();
			} else {
				Intent intent = new Intent(this, MoviesListActivity.class);
				intent.putExtra(Constants.EXTRA_RESULT, response);
				startActivity(intent);
			}
			break;
		}
	}
}

