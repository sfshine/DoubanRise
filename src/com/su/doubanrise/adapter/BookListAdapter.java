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
import com.su.doubanrise.api.bean.Book;
import com.su.doubanrise.widget.AsyncImageLoader;
import com.su.doubanrise.widget.AsyncImageLoader.ImageCallback;

public class BookListAdapter extends BaseAdapter {
	private List<Book> subjects;
	private LayoutInflater mInflater;
	private Context context;
	static AsyncImageLoader asyncImageLoader = new AsyncImageLoader();

	public BookListAdapter(Context context, List<Book> subjects) {
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
		Book book = subjects.get(i);

		TextView txtTitle = (TextView) view.findViewById(R.id.book_title);
		txtTitle.setText(book.getTitle());

		TextView txtDescription = (TextView) view
				.findViewById(R.id.book_description);
		txtDescription.setText(book.getSummary());

		RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingbar);
		// 首页，没取到评分，则不显示RatingBar
		if (book.getAverage() == 0) {
			ratingBar.setVisibility(View.INVISIBLE);
		} else {
			ratingBar.setVisibility(View.VISIBLE);
			ratingBar.setRating(book.getAverage());
		}

		String imgUrl = book.getImage();
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
				imageView = (ImageView) baseView.findViewById(R.id.book_img);
			}
			return imageView;
		}

	}
}