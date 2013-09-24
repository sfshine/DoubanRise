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
import com.su.doubanrise.api.bean.Music;
import com.su.doubanrise.widget.AsyncImageLoader;
import com.su.doubanrise.widget.AsyncImageLoader.ImageCallback;

public class MusicListAdapter extends BaseAdapter {
	private List<Music> subjects;
	private LayoutInflater mInflater;
	private Context context;
	static AsyncImageLoader asyncImageLoader = new AsyncImageLoader();

	public MusicListAdapter(Context context, List<Music> subjects) {
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
		Music music = subjects.get(i);

		TextView txtTitle = (TextView) view.findViewById(R.id.book_title);
		txtTitle.setText(music.getTitle());

		TextView txtDescription = (TextView) view
				.findViewById(R.id.book_description);
		txtDescription.setText("导演:" + music.getAuthor());

		RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingbar);
		// 首页，没取到评分，则不显示RatingBar
		if (music.getRating() == 0) {
			ratingBar.setVisibility(View.INVISIBLE);
		} else {
			ratingBar.setVisibility(View.VISIBLE);
			ratingBar.setRating(music.getRating());
		}

		String imgUrl = music.getImage();
		ImageView imgMusic = viewCache.getImageView();
		imgMusic.setTag(imgUrl);

		asyncImageLoader.setAsyDrawableFromurl(imgUrl, imgMusic);
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