package com.su.doubanrise;

import java.util.List;

import com.su.doubanrise.adapter.ReviewListAdapter;
import com.su.doubanrise.api.bean.Review;
import com.su.doubanrise.overall.RSS;
import com.su.doubanrise.websitebiz.ReviewService;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ReviewActivity extends BaseActivity {
	private List<Review> reviews;
	private ListView cinema_list;

	String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		url = this.getIntent().getStringExtra("url");
		setContentView(R.layout.cinema_activity);
		super.onCreate(savedInstanceState);
		setTitle("评论列表");

	}

	@Override
	protected void findViewById() {
		cinema_list = (ListView) findViewById(R.id.cinema_list);

	}

	@Override
	protected void processLogic() {
		new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(Void... arg0) {
				try {

					reviews = new ReviewService().getReviews(url);

					return true;
				} catch (Exception e) {
					e.printStackTrace();
				}
				return false;
			}

			@Override
			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);

				closeProgressBar();

				if (result) {
					ReviewListAdapter reviewListAdapter = new ReviewListAdapter(
							ReviewActivity.this, reviews);
					cinema_list.setAdapter(reviewListAdapter);
					cinema_list
							.setOnItemClickListener(new OnItemClickListener() {

								public void onItemClick(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {

									Intent intent = new Intent(
											ReviewActivity.this,
											ReviewViewActivity.class);
									Review review = reviews.get(arg2);
									intent.putExtra("review", review);
									startActivity(intent);

								}
							});

				} else {
					Toast("获取信息失败");
				}

			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				showProgressBar();
			}

		}.execute();

	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

	}

}
