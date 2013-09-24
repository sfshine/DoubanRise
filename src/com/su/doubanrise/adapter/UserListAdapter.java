package com.su.doubanrise.adapter;

import java.util.List;

import android.R.integer;
import android.content.Context;
import android.graphics.Color;
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
import com.su.doubanrise.api.bean.User;
import com.su.doubanrise.api.bean.User;
import com.su.doubanrise.api.bean.Subject;
import com.su.doubanrise.api.bean.User;
import com.su.doubanrise.util.MLog;
import com.su.doubanrise.widget.AsyncImageLoader;
import com.su.doubanrise.widget.AsyncImageLoader.ImageCallback;

public class UserListAdapter extends BaseAdapter {
	private List<User> users;
	private LayoutInflater mInflater;
	private Context context;
	static AsyncImageLoader asyncImageLoader = new AsyncImageLoader();

	public UserListAdapter(Context context, List<User> users) {
		this.context = context;
		this.users = users;

		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return users.size();
	}

	public Object getItem(int i) {
		return users.get(i);
	}

	public long getItemId(int i) {
		return i;
	}

	public View getView(int i, View view, ViewGroup vg) {
		ViewCache viewCache;

		if (view == null) {

			view = mInflater.inflate(R.layout.mail_select_user_item, null);

			viewCache = new ViewCache(view);
			view.setTag(viewCache);
		} else {
			viewCache = (ViewCache) view.getTag();
		}
		User user = users.get(i);

		TextView txtTitle = (TextView) view.findViewById(R.id.user_name);
		txtTitle.setText(user.getName());
		String imgUrl = user.getAvatar();
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