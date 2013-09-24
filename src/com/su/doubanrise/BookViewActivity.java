package com.su.doubanrise;

import com.su.doubanrise.api.bean.Book;
import com.su.doubanrise.overall.Constant;
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

public class BookViewActivity extends BaseActivity implements
		OnItemClickListener {
	ImageView book_img;
	String book_id;
	TextView book_title, book_author, book_press, book_summary;
	RatingBar ratingbar;
	private PopMenu nPopMenu;

	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.book_view);
		book_id = this.getIntent().getStringExtra("book_id");
		super.onCreate(savedInstanceState);
		setTitle("查看图书");

		nPopMenu = new PopMenu(this, 170, -2);
		nPopMenu.setOnItemClickListener(this);
		nPopMenu.addItems(new String[] { "查看笔记", "查看评论", "返回上级" });
	}

	@Override
	protected void findViewById() {
		book_img = (ImageView) findViewById(R.id.book_img);
		book_title = (TextView) findViewById(R.id.book_title);
		book_author = (TextView) findViewById(R.id.book_author);
		book_press = (TextView) findViewById(R.id.book_press);
		book_summary = (TextView) findViewById(R.id.book_summary);
		ratingbar = (RatingBar) findViewById(R.id.ratingbar);

	}

	@Override
	protected void processLogic() {
		new AsyncTask<Void, Void, Book>() {

			@Override
			protected Book doInBackground(Void... params) {
				Book book = douban.getBookById(book_id);
				return book;
			}

			protected void onPreExecute() {
				showProgressBar();
			};

			protected void onPostExecute(Book book) {
				// book_img.;
				if (book == null) {
					return;
				}
				book_title.setText(book.getTitle());
				book_author.setText("作者" + book.getAuthor());
				book_press.setText(book.getPublisher());
				book_summary.setText(book.getSummary());
				ratingbar.setRating(book.getAverage() / 2);
				AsyncImageLoader asyncImageLoader = new AsyncImageLoader(
						BookViewActivity.this);
				asyncImageLoader.setAsyDrawableFromurl(book.getImage(),
						book_img);
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
			Intent intent = new Intent(BookViewActivity.this,
					BookNotesActivity.class);
			intent.putExtra("book_id", book_id);
			startActivity(intent);
			break;
		case 1:
			Intent intent1 = new Intent(BookViewActivity.this,
					ReviewActivity.class);
			// http://book.douban.com/feed/subject/20382491/reviews
			String url = "http://book.douban.com/feed/subject/" + book_id
					+ "/reviews";
			intent1.putExtra("url", url);
			startActivity(intent1);
			break;
		default:
			break;
		}
		nPopMenu.dismiss();

	}

}
