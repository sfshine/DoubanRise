package com.su.doubanrise;

import com.su.doubanrise.api.bean.Music;
import com.su.doubanrise.widget.AsyncImageLoader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class MusicViewActivity extends BaseActivity {
	ImageView music_img;
	String music_id;
	TextView music_title, music_author, music_press, music_summary;
	RatingBar ratingbar;

	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.book_view);
		music_id = this.getIntent().getStringExtra("music_id");
		super.onCreate(savedInstanceState);
		setTitle("查看音乐");
	}

	@Override
	protected void findViewById() {
		music_img = (ImageView) findViewById(R.id.book_img);
		music_title = (TextView) findViewById(R.id.book_title);
		music_author = (TextView) findViewById(R.id.book_author);
		music_press = (TextView) findViewById(R.id.book_press);
		music_summary = (TextView) findViewById(R.id.book_summary);
		ratingbar = (RatingBar) findViewById(R.id.ratingbar);
		common_title_menu_button.setVisibility(View.INVISIBLE);

	}

	@Override
	protected void processLogic() {
		new AsyncTask<Void, Void, Music>() {

			@Override
			protected Music doInBackground(Void... params) {
				Music music = douban.getMusicById(music_id);
				return music;
			}

			protected void onPreExecute() {
				showProgressBar();
			};

			protected void onPostExecute(Music music) {
				// music_img.;
				if (music == null) {
					return;
				}
				music_title.setText(music.getTitle());
				music_author.setText(music.getAuthor());
				music_press.setText(music.getPublisher());
				music_summary.setText(music.getSummary());
				ratingbar.setRating(music.getRating() / 2);
				AsyncImageLoader asyncImageLoader = new AsyncImageLoader(
						MusicViewActivity.this);
				asyncImageLoader.setAsyDrawableFromurl(music.getImage(),
						music_img);
				closeProgressBar();
			};

		}.execute();

	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

	}

}
