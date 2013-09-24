package com.su.doubanrise.fragment;

import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.su.doubanrise.BookViewActivity;
import com.su.doubanrise.R;
import com.su.doubanrise.ReviewViewActivity;
import com.su.doubanrise.adapter.ReviewListAdapter;
import com.su.doubanrise.adapter.SubjectListAdapter;
import com.su.doubanrise.api.bean.Book;
import com.su.doubanrise.api.bean.Review;
import com.su.doubanrise.api.bean.Subject;
import com.su.doubanrise.overall.RSS;
import com.su.doubanrise.websitebiz.BookService;
import com.su.doubanrise.websitebiz.ReviewService;

public class BookFragment extends BaseFragment {
	private List<Review> reviews;
	private List<Subject> books;
	private View view;
	private ListView book_list;
	String[] titles = new String[] { "最新书评", "推荐书籍" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.book_frame, null);
		super.onCreateView(inflater, container, savedInstanceState);

		setPullMenu(titles);
		setTitle(titles[0], view);
		return view;
	}

	@Override
	protected void findViewById() {
		book_list = (ListView) view.findViewById(R.id.book_list);

	}

	@Override
	protected void processLogic() {
		new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(Void... arg0) {
				try {

					reviews = new ReviewService().getReviews(RSS.BOOKREVIEW);

					return true;
				} catch (Exception e) {
					e.printStackTrace();
				}
				return false;
			}

			@Override
			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);

				mainActivity.closeProgressBar(view);

				if (result) {
					ReviewListAdapter reviewListAdapter = new ReviewListAdapter(
							mainActivity, reviews);
					book_list.setAdapter(reviewListAdapter);
					book_list.setOnItemClickListener(new OnItemClickListener() {

						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {

							Intent intent = new Intent(mainActivity,
									ReviewViewActivity.class);
							Review review = reviews.get(arg2);
							intent.putExtra("review", review);
							mainActivity.startActivity(intent);

						}
					});

				} else {
					Toast.makeText(mainActivity, "数据加载失败！", Toast.LENGTH_SHORT)
							.show();
				}

			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				mainActivity.showProgressBar(view);
			}

		}.execute();

	}

	protected void getBookReviews() {
		new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(Void... arg0) {
				try {

					books = BookService.getDoubanNewBooks();

					return true;
				} catch (Exception e) {
					e.printStackTrace();
				}
				return false;
			}

			@Override
			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);

				mainActivity.closeProgressBar(view);

				if (result) {
					SubjectListAdapter subjectListAdapter = new SubjectListAdapter(
							mainActivity, books);
					book_list.setAdapter(subjectListAdapter);
					book_list.setOnItemClickListener(new OnItemClickListener() {

						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {

							Intent intent = new Intent(mainActivity,
									BookViewActivity.class);

							intent.putExtra("book_id", books.get(arg2).getId());
							mainActivity.startActivity(intent);

						}
					});
				} else {
					Toast.makeText(mainActivity, "数据加载失败！", Toast.LENGTH_SHORT)
							.show();
				}

			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				mainActivity.showProgressBar(view);
			}

		}.execute();

	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		switch (arg2) {
		case 0:
			processLogic();
			break;
		case 1:
			getBookReviews();
			break;
		case 2:

			break;
		default:
			break;
		}
		setTitle(titles[arg2], view);
		popMenu.dismiss();

	}
}
