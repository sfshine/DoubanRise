package com.su.doubanrise.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.su.doubanrise.R;
import com.su.doubanrise.api.bean.Movie;
import com.su.doubanrise.widget.AsyncImageLoader;
import com.su.doubanrise.widget.AsyncImageLoader.ImageCallback;

public class MovieListAdapter extends BaseAdapter {
	private List<Movie> subjects;
	private LayoutInflater mInflater;
	private Context context;
	static AsyncImageLoader asyncImageLoader = new AsyncImageLoader();

	public MovieListAdapter(Context context, List<Movie> subjects) {
		this.context = context;
		this.subjects = subjects;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return subjects.size();
	}

	public Object getItem(int i) {
		return subjects.get(i);
	}

	public long getItemId(int i) {
		return i;
	}

	public View getView(int i, View view, ViewGroup vg) {
		ViewCache viewCache;

		if (view == null) {
			view = mInflater.inflate(R.layout.book_item, null);
			viewCache = new ViewCache(view);
			view.setTag(viewCache);
		} else {
			viewCache = (ViewCache) view.getTag();
		}
		Movie movie = subjects.get(i);
		if (movie == null) {
			return view;
		}
		TextView txtTitle = (TextView) view.findViewById(R.id.book_title);
		txtTitle.setText(movie.getTitle() + "");

		TextView txtDescription = (TextView) view
				.findViewById(R.id.book_description);
		txtDescription.setText("导演:" + movie.getAuthor());

		RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingbar);

		if (movie.getRating() == 0) {
			ratingBar.setVisibility(View.INVISIBLE);
		} else {
			ratingBar.setVisibility(View.VISIBLE);
			ratingBar.setRating(movie.getRating());
		}

		String imgUrl = movie.getImage();
		ImageView imgMovie = viewCache.getImageView();
		imgMovie.setTag(imgUrl);

		asyncImageLoader.setAsyDrawableFromurl(imgUrl, imgMovie);
		return view;
	}

	public class ViewCache {

		private View baseView;
		private ImageView imageView;

		public ViewCache(View baseView) {
			this.baseView = baseView;
		}

		public ImageView getImageView() {
			if (imageView == null) {
				imageView = (ImageView) baseView.findViewById(R.id.book_img);
			}
			return imageView;
		}

	}
}