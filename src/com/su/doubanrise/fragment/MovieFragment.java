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
import com.su.doubanrise.CinemaViewActivity;
import com.su.doubanrise.MovieViewActivity;
import com.su.doubanrise.R;
import com.su.doubanrise.ReviewViewActivity;
import com.su.doubanrise.adapter.ReviewListAdapter;
import com.su.doubanrise.adapter.SubjectListAdapter;
import com.su.doubanrise.api.bean.Review;
import com.su.doubanrise.api.bean.Subject;
import com.su.doubanrise.overall.RSS;
import com.su.doubanrise.websitebiz.BookService;
import com.su.doubanrise.websitebiz.MovieService;
import com.su.doubanrise.websitebiz.ReviewService;

public class MovieFragment extends BaseFragment {
	private List<Review> reviews;
	private List<Subject> movies;
	private View view;
	private ListView movie_list;
	String[] titles = new String[] { "推荐电影", "最新影评" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.movie_frame, null);
		super.onCreateView(inflater, container, savedInstanceState);

		setPullMenu(titles);
		setTitle(titles[0], view);
		//
		// Intent intent = new Intent(mainActivity, GMapActivity.class);
		// //intent.putExtra("loc", cinema.getLatLng());
		// startActivity(intent);
		return view;
	}

	@Override
	protected void findViewById() {
		movie_list = (ListView) view.findViewById(R.id.movie_list);

	}

	@Override
	protected void processLogic() {
		new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(Void... arg0) {
				try {

					movies = MovieService.getDoubanNewMovies();

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
							mainActivity, movies);
					movie_list.setAdapter(subjectListAdapter);
					movie_list
							.setOnItemClickListener(new OnItemClickListener() {

								public void onItemClick(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {

									Intent intent = new Intent(mainActivity,
											MovieViewActivity.class);

									intent.putExtra("movie_id", movies
											.get(arg2).getId());
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

					reviews = new ReviewService().getReviews(RSS.MOVIEREVIEW);

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
					movie_list.setAdapter(reviewListAdapter);
					movie_list
							.setOnItemClickListener(new OnItemClickListener() {

								public void onItemClick(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {

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
