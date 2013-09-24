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
import com.su.doubanrise.api.bean.Mail;
import com.su.doubanrise.api.bean.Mail;
import com.su.doubanrise.api.bean.Subject;
import com.su.doubanrise.util.MLog;
import com.su.doubanrise.widget.AsyncImageLoader;
import com.su.doubanrise.widget.AsyncImageLoader.ImageCallback;

public class MailListAdapter extends BaseAdapter {
	private List<Mail> mails;
	private LayoutInflater mInflater;
	private Context context;
	private int type;
	static AsyncImageLoader asyncImageLoader = new AsyncImageLoader();

	public MailListAdapter(Context context, List<Mail> mails, int type) {
		this.context = context;
		this.mails = mails;
		this.type = type;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return mails.size();
	}

	public Object getItem(int i) {
		return mails.get(i);
	}

	public long getItemId(int i) {
		return i;
	}

	public View getView(int i, View view, ViewGroup vg) {
		ViewCache viewCache;

		if (view == null) {
			if (type == 2) {
				view = mInflater.inflate(R.layout.mail_sendbox_item, null);
			} else {
				view = mInflater.inflate(R.layout.mail_item, null);
			}

			viewCache = new ViewCache(view);
			view.setTag(viewCache);
		} else {
			viewCache = (ViewCache) view.getTag();
		}
		Mail mail = mails.get(i);

		TextView txtTitle = (TextView) view.findViewById(R.id.mail_title);
		txtTitle.setText(mail.getTitle());

		if (type == 2) {// 发件
			TextView mail_user = (TextView) view.findViewById(R.id.mail_user);
			mail_user.setText("接收者:" + mail.getReceiver().getName());
			String imgUrl = mail.getReceiver().getAvatar();
			ImageView imgBook = viewCache.getImageView();
			imgBook.setTag(imgUrl);
			AsyncImageLoader asyncImageLoader = new AsyncImageLoader(context);
			asyncImageLoader.setAsyDrawableFromurl(imgUrl, imgBook);
		} else {
			if (mail.getStatus().equals("U")) {
				txtTitle.setTextColor(Color.BLACK);
			}
			TextView mail_user = (TextView) view.findViewById(R.id.mail_user);
			mail_user.setText("来自:" + mail.getSender().getName());
			String imgUrl = mail.getSender().getAvatar();
			ImageView imgBook = viewCache.getImageView();
			imgBook.setTag(imgUrl);

			asyncImageLoader.setAsyDrawableFromurl(imgUrl, imgBook);
		}

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
				imageView = (ImageView) baseView.findViewById(R.id.mail_img);
			}
			return imageView;
		}

	}
}