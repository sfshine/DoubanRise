package com.su.doubanrise;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.su.doubanrise.R.id;
import com.su.doubanrise.api.bean.Book;
import com.su.doubanrise.api.bean.Movie;
import com.su.doubanrise.util.MLog;
import com.su.doubanrise.widget.AsyncImageLoader;
import com.su.doubanrise.widget.PopMenu;

public class MovieViewActivity extends BaseActivity implements
		OnItemClickListener {
	ImageView movie_img;
	String movie_id;
	TextView movie_title, movie_author, movie_cast, movie_summary;
	RatingBar ratingbar;
	private PopMenu nPopMenu;

	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.movie_view);

		movie_id = this.getIntent().getStringExtra("movie_id");
		MLog.e(movie_id);
		nPopMenu = new PopMenu(this, 170, -2);
		nPopMenu.setOnItemClickListener(this);
		nPopMenu.addItems(new String[] { "查看影讯", "查看短评", "查看影评" });
		super.onCreate(savedInstanceState);
		setTitle("查看电影");
	}

	@Override
	protected void findViewById() {
		common_title_menu_button.setVisibility(View.VISIBLE);
		movie_img = (ImageView) findViewById(R.id.movie_img);
		movie_title = (TextView) findViewById(R.id.movie_title);
		movie_author = (TextView) findViewById(R.id.movie_author);
		movie_cast = (TextView) findViewById(R.id.movie_cast);
		movie_summary = (TextView) findViewById(R.id.movie_summary);
		ratingbar = (RatingBar) findViewById(R.id.ratingbar);

	}

	static AsyncImageLoader asyncImageLoader = new AsyncImageLoader();

	@Override
	protected void processLogic() {
		new AsyncTask<Void, Void, Movie>() {

			@Override
			protected Movie doInBackground(Void... params) {
				Movie movie = douban.getMovieById(movie_id);
				return movie;
			}

			protected void onPreExecute() {
				showProgressBar();
			};

			protected void onPostExecute(Movie movie) {
				// movie_img.;
				if (movie == null) {
					return;
				}
				movie_title.setText(movie.getTitle());
				movie_author.setText("导演:" + movie.getAuthor());
				String cast = movie.getCast().replaceAll("\"", "");

				movie_cast.setText("主演:" + cast);
				movie_summary.setText(movie.getSummary());
				ratingbar.setRating(movie.getRating());
				asyncImageLoader.setAsyDrawableFromurl(movie.getImage(),
						movie_img);
				closeProgressBar();
			};

		}.execute();

	}

	@Override
	protected void setListener() {
		common_title_menu_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				nPopMenu.showAsDropDown(v, -35, -2);
			}
		});

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		switch (arg2) {
		case 0:
			Intent intent = new Intent(MovieViewActivity.this,
					CinemaActivity.class);
			intent.putExtra("movie_id", movie_id);
			startActivity(intent);
			break;
		case 1:
			Intent intent1 = new Intent(MovieViewActivity.this,
					ShortReviewActivity.class);
			intent1.putExtra("movie_id", movie_id);
			startActivity(intent1);
			break;
		case 2:
			Intent intent2 = new Intent(MovieViewActivity.this,
					ReviewActivity.class);
			// http://book.douban.com/feed/subject/20382491/reviews
			// http://movie.douban.com/feed/subject/3179706/reviews
			String url = "http://movie.douban.com/feed/subject/" + movie_id
					+ "/reviews";
			intent2.putExtra("url", url);
			startActivity(intent2);
			break;
		default:

			break;
		}
		nPopMenu.dismiss();

	}
}
