package com.kistaffing.androidtest.adapters;

import java.util.ArrayList;

import com.aplit.dev.tasks.ImageDownloadTask;
import com.aplit.dev.tasks.ImageDownloadTask.ImageDownloadTaskListener;
import com.kistaffing.androidtest.R;
import com.kistaffing.androidtest.models.Movie;
import com.kistaffing.androidtest.utilities.Constants;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MoviesAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private ArrayList<Movie> list;
	private ImageDownloadTaskListener listener;

	public MoviesAdapter(Context context, ArrayList<Movie> list, ImageDownloadTaskListener listener) {
		this.inflater = LayoutInflater.from(context);
		this.list = list;
		this.listener = listener;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public String getItem(int position) {
		return list.get(position).getTitle();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private class ViewHolder {
		ImageView image;
		TextView title;
		TextView year;
		TextView runtime;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.movies_item, null);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView.findViewById(R.id.moviesItemImage);
			holder.title = (TextView) convertView.findViewById(R.id.moviesItemTitle);
			holder.year = (TextView) convertView.findViewById(R.id.moviesItemYear);
			holder.runtime = (TextView) convertView.findViewById(R.id.moviesItemRuntime);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (position < getCount()) {
			holder.title.setText(getItem(position));
			holder.year.setText(list.get(position).getYear());
			holder.runtime.setText(list.get(position).getRuntime());
			new ImageDownloadTask(convertView.getContext(), listener, holder.image,
					list.get(position).getImageUrl(), "testAndroidMovies", Constants.REQUEST_IMAGE_DOWNLOAD,
					position, false).execute();
		}
		return convertView;
	}
}

