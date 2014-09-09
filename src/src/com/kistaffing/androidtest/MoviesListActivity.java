package com.kistaffing.androidtest;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.aplit.dev.tasks.ImageDownloadTask.ImageDownloadTaskListener;
import com.aplit.dev.utilities.Utilities;
import com.aplit.dev.utilities.Utilities.AlertDialogListener;
import com.aplit.dev.wrappers.DebugLog;
import com.kistaffing.androidtest.adapters.MoviesAdapter;
import com.kistaffing.androidtest.models.Movie;
import com.kistaffing.androidtest.utilities.Constants;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

public class MoviesListActivity extends Activity implements
AlertDialogListener, ImageDownloadTaskListener {
	private static final String TAG = "MoviesListActivity";

	private ListView moviesList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		DebugLog.e(this, TAG, "onCreate");

		View view = View.inflate(this, R.layout.movies, null);
		moviesList = (ListView) view.findViewById(R.id.moviesList);
		setContentView(view);
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
		Bundle extras = this.getIntent().getExtras();
		if (extras != null) {
			String response = extras.getString(Constants.EXTRA_RESULT);
			try {
				JSONObject moviesResult = new JSONObject(response);
				if (moviesResult.has("error")) {
					String errorValue = moviesResult.getString("error");
					Utilities.showAlertbox(this, "Error", errorValue, null, Constants.DIALOG_SEARCH_FAILED, this).show();
				} else if (moviesResult.getInt("total") > 0) {
					JSONArray moviesArray = moviesResult.getJSONArray("movies");
					ArrayList<Movie> list = new ArrayList<Movie>();
					JSONObject tempMovie = null;
					long id;
					String title, year, runtime, imageUrl;
					for (int i = 0; i < moviesArray.length(); i++) {
						tempMovie = moviesArray.getJSONObject(i);
						id = tempMovie.getLong("id");
						title = tempMovie.getString("title");
						year = tempMovie.getString("year");
						runtime = tempMovie.getString("runtime");
						imageUrl = tempMovie.getJSONObject("posters").getString("thumbnail");
						list.add(new Movie(id, title, year, runtime, imageUrl));
					}

					MoviesAdapter adapter = new MoviesAdapter(this, list, this);
					moviesList.setAdapter(adapter);
				}
			} catch (JSONException e) {
				e.printStackTrace();
				Utilities.showAlertbox(this, "Error", "Search result incorrect.", null, Constants.DIALOG_SEARCH_FAILED, this).show();
			}
		}
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


	/** Interfaces */
	@Override
	public void onAlertboxNegativeButtonClick(int dialogId) { }

	@Override
	public void onAlertboxNeutralButtonClick(int dialogId) {
		switch (dialogId) {
		case Constants.DIALOG_SEARCH_FAILED:
			finish();
			break;
		}
	}

	@Override
	public void onAlertboxPositiveButtonClick(int dialogId) { }

	@Override
	public void onImageDownloadFinish(long controlId, int type) {
		DebugLog.w(this, TAG, "onImageDownloadFinish controlId:" + controlId + "|type:" + type + "***");
	}
}

