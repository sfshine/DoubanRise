package com.su.doubanrise;

import java.util.List;

import com.su.doubanrise.adapter.CinemaListAdapter;
import com.su.doubanrise.adapter.ReviewListAdapter;
import com.su.doubanrise.adapter.ShotReviewListAdapter;
import com.su.doubanrise.api.bean.Cinema;
import com.su.doubanrise.api.bean.Review;
import com.su.doubanrise.websitebiz.MovieService;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ShortReviewActivity extends BaseActivity {
	private ListView cinema_list;
	private String movie_id;
	List<Review> reviews = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.cinema_activity);
		movie_id = this.getIntent().getStringExtra("movie_id");
		super.onCreate(savedInstanceState);
		setTitle("短评");
	}

	@Override
	protected void findViewById() {
		cinema_list = (ListView) findViewById(R.id.cinema_list);

	}

	@Override
	protected void processLogic() {

		new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(Void... params) {
				reviews = MovieService.getShortReview(movie_id, "time");
				if (null == reviews || reviews.size() == 0) {
					return false;
				}
				return true;
			}

			protected void onPostExecute(Boolean result) {
				if (result) {
					ShotReviewListAdapter cinemaListAdapter = new ShotReviewListAdapter(
							ShortReviewActivity.this, reviews);
					cinema_list.setAdapter(cinemaListAdapter);

				}
				closeProgressBar();

			};

			protected void onPreExecute() {
				showProgressBar();
			};

		}.execute();

	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

	}

}
