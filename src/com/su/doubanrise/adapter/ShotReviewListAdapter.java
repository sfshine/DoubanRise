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
import com.su.doubanrise.api.bean.Review;
import com.su.doubanrise.api.bean.Subject;
import com.su.doubanrise.util.MLog;
import com.su.doubanrise.widget.AsyncImageLoader;
import com.su.doubanrise.widget.AsyncImageLoader.ImageCallback;

public class ShotReviewListAdapter extends BaseAdapter {
	private List<Review> subjects;
	private LayoutInflater mInflater;
	private Context context;
	static AsyncImageLoader asyncImageLoader = new AsyncImageLoader();

	public ShotReviewListAdapter(Context context, List<Review> subjects) {
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
			view = mInflater.inflate(R.layout.shotreview_item, null);
			viewCache = new ViewCache(view);
			view.setTag(viewCache);
		} else {
			viewCache = (ViewCache) view.getTag();
		}
		Review review = subjects.get(i);

		TextView txtTitle = (TextView) view.findViewById(R.id.user_name);
		txtTitle.setText(review.getCreator());
		TextView txtDescription = (TextView) view.findViewById(R.id.r_content);
		txtDescription.setText(review.getContent());

		String imgUrl = review.getAuthorImageUrl();

		ImageView imgBook = viewCache.getImageView();
		imgBook.setTag(imgUrl);

		asyncImageLoader.setAsyDrawableFromurl(imgUrl, imgBook);
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
				imageView = (ImageView) baseView.findViewById(R.id.user_img);
			}
			return imageView;
		}

	}
}