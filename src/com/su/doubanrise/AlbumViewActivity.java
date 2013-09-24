package com.su.doubanrise;

import com.su.doubanrise.api.bean.Music;
import com.su.doubanrise.api.bean.Subject;
import com.su.doubanrise.websitebiz.MusicService;
import com.su.doubanrise.widget.AsyncImageLoader;
import com.su.doubanrise.widget.PopMenu;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class AlbumViewActivity extends BaseActivity implements
		OnItemClickListener {
	ImageView music_img;
	Subject album;
	TextView music_title, music_author, music_press, music_summary;
	RatingBar ratingbar;
	private PopMenu nPopMenu; 

	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.book_view);
		album = (Subject) this.getIntent().getSerializableExtra("album");
		super.onCreate(savedInstanceState);
		nPopMenu = new PopMenu(this, 170, -2);
		nPopMenu.setOnItemClickListener(this);
		nPopMenu.addItems(new String[] { "查看评论", "返回上级" });
		setTitle("查看专辑");
	}

	@Override
	protected void findViewById() {
		music_img = (ImageView) findViewById(R.id.book_img);
		music_title = (TextView) findViewById(R.id.book_title);
		music_author = (TextView) findViewById(R.id.book_author);
		music_press = (TextView) findViewById(R.id.book_press);
		music_summary = (TextView) findViewById(R.id.book_summary);
		ratingbar = (RatingBar) findViewById(R.id.ratingbar);

	}

	@Override
	protected void processLogic() {
		new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				try {

					String summary = MusicService.getAlbumById(album.getId());
					return summary;
				} catch (Exception e) {
					// TODO: handle exception
				}
				return null;
			}

			protected void onPreExecute() {
				showProgressBar();
			};

			protected void onPostExecute(String summary) {

				music_title.setText(album.getTitle());
				music_author.setText(album.getDescription());
				if (summary != null) {
					music_summary.setText(summary);
				}

				ratingbar.setRating(album.getRating());
				AsyncImageLoader asyncImageLoader = new AsyncImageLoader(
						AlbumViewActivity.this);
				asyncImageLoader.setAsyDrawableFromurl(album.getImgUrl(),
						music_img);
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
			Intent intent1 = new Intent(AlbumViewActivity.this,
					ReviewActivity.class);
			String url = "http://music.douban.com/feed/subject/"
					+ album.getId() + "/reviews";
			intent1.putExtra("url", url);
			startActivity(intent1);
			break;
		default:
			finish();
			break;
		}
		nPopMenu.dismiss();

	}

}
