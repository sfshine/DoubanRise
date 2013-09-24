package com.su.doubanrise;

import java.util.ArrayList;
import java.util.List;

import com.su.doubanrise.adapter.BookListAdapter;
import com.su.doubanrise.adapter.MovieListAdapter;
import com.su.doubanrise.adapter.MusicListAdapter;
import com.su.doubanrise.api.bean.Book;
import com.su.doubanrise.api.bean.Movie;
import com.su.doubanrise.api.bean.Music;
import com.su.doubanrise.api.bean.Subject;
import com.su.doubanrise.util.MLog;
import com.su.doubanrise.util.Str;
import com.su.doubanrise.widget.PopMenu;
import com.zijunlin.Zxing.Demo.CaptureActivity;

import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SearchActivity extends BaseActivity implements OnItemClickListener {

	private PopMenu nPopMenu;
	String[] titles = new String[] { "搜索电影", "搜索图书", "搜索音乐" };
	private int type = 0;// 0电影 1书 2 音乐
	private int COUNT = 30;
	private ListView search_list;
	private EditText word_ed;
	private ImageButton search_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.search_acitvity);
		nPopMenu = new PopMenu(this);
		nPopMenu.setOnItemClickListener(this);
		nPopMenu.addItems(titles);
		super.onCreate(savedInstanceState);
		setTitle(titles[0]);
		common_title_menu_button.setVisibility(View.INVISIBLE);
	}

	@Override
	protected void findViewById() {
		title_button
				.setBackgroundResource(R.drawable.base_catbar_background_selector);
		search_list = (ListView) findViewById(R.id.search_list);
		word_ed = (EditText) findViewById(R.id.word_ed);
		search_btn = (ImageButton) findViewById(R.id.search_btn);
		common_title_menu_button.setBackgroundResource(R.drawable.btn_qecode);

	}

	public static int FLAG_SCAN_RETURN = 5006;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == FLAG_SCAN_RETURN) {
				if (data.getStringExtra("SCAN_RESULT") != null) {
					String word = data.getStringExtra("SCAN_RESULT");
					MLog.e(data.getStringExtra("SCAN_RESULT"));
					searchBook(word, 0, 1);
					// switch (type) {
					// case 0:
					// searchMovie(word, 0, 1);
					// break;
					// case 1:
					// searchBook(word, 0, 1);
					// break;
					// // case 2:
					// // searchMusic(word, 0);
					// // break;
					//
					// default:
					// break;
					// }
				}
			}
		}

	}

	@Override
	protected void setListener() {
		common_title_menu_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SearchActivity.this,
						CaptureActivity.class);

				startActivityForResult(intent, FLAG_SCAN_RETURN);
			}
		});
		title_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				nPopMenu.showAsDropDown(v, 0, -2);
			}
		});
		search_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String word = word_ed.getText().toString();
				if (Str.isNull(word)) {
					Toast("请输入内容");
					return;
				}
				switch (type) {
				case 0:
					searchMovie(word, 0, 0);
					break;
				case 1:
					searchBook(word, 0, 0);
					break;
				case 2:
					searchMusic(word, 0);
					break;

				default:
					break;
				}

			}
		});

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (arg2 == 1) {
			common_title_menu_button.setVisibility(View.VISIBLE);
		} else {
			common_title_menu_button.setVisibility(View.INVISIBLE);
		}
		setTitle(titles[arg2]);
		nPopMenu.dismiss();
		type = arg2;

	}

	void searchBook(String word, final int offset, final int qr) {
		new AsyncTask<String, Void, Boolean>() {
			List<Book> books = new ArrayList<Book>();

			@Override
			protected Boolean doInBackground(String... params) {
				if (qr == 0) {
					books = douban.searchAllBook(params[0], offset + "", COUNT
							+ "");
				} else if (qr == 1) {
					books.add(douban.getBookByISBN(params[0]));
				}

				if (books == null || books.get(0) == null) {
					return false;
				}
				return true;
			}

			protected void onPostExecute(Boolean result) {
				if (result) {
					BookListAdapter bookListAdapter = new BookListAdapter(
							SearchActivity.this, books);
					search_list.setAdapter(bookListAdapter);
					search_list
							.setOnItemClickListener(new OnItemClickListener() {

								public void onItemClick(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {

									Intent intent = new Intent(
											SearchActivity.this,
											BookViewActivity.class);

									intent.putExtra("book_id", books.get(arg2)
											.getId());
									startActivity(intent);

								}
							});
				}
				closeProgressBar();
			};

			protected void onPreExecute() {
				showProgressBar();
			};

		}.execute(word);

	}

	void searchMovie(String word, final int offset, final int qr) {
		new AsyncTask<String, Void, Boolean>() {
			List<Movie> movies = new ArrayList<Movie>();

			@Override
			protected Boolean doInBackground(String... params) {
				if (qr == 0) {
					movies = douban.searchAllMovie(params[0], offset + "",
							COUNT + "");
				} else if (qr == 1) {
					movies.add(douban.getMoiveByImdb(params[0]));
				}
				// movies = douban
				// .searchAllMovie(params[0], params[1], COUNT + "");
				if (movies == null || movies.get(0) == null) {
					return false;
				}
				return true;
			}

			protected void onPostExecute(Boolean result) {
				if (result) {
					MovieListAdapter bookListAdapter = new MovieListAdapter(
							SearchActivity.this, movies);
					search_list.setAdapter(bookListAdapter);
					search_list
							.setOnItemClickListener(new OnItemClickListener() {

								public void onItemClick(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {

									Intent intent = new Intent(
											SearchActivity.this,
											MovieViewActivity.class);

									intent.putExtra("movie_id", movies
											.get(arg2).getId());
									startActivity(intent);

								}
							});
				}

				closeProgressBar();
			};

			protected void onPreExecute() {
				showProgressBar();
			};

		}.execute(word);

	}

	void searchMusic(String word, int offset) {
		new AsyncTask<String, Void, Boolean>() {
			List<Music> musics = new ArrayList<Music>();

			@Override
			protected Boolean doInBackground(String... params) {
				musics = douban
						.searchAllMusic(params[0], params[1], COUNT + "");
				if (musics == null) {
					return false;
				}
				return true;
			}

			protected void onPostExecute(Boolean result) {
				if (result) {
					MusicListAdapter musicListAdapter = new MusicListAdapter(
							SearchActivity.this, musics);
					search_list.setAdapter(musicListAdapter);
					search_list
							.setOnItemClickListener(new OnItemClickListener() {

								public void onItemClick(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {

									Intent intent = new Intent(
											SearchActivity.this,
											MusicViewActivity.class);

									intent.putExtra("music_id", musics
											.get(arg2).getId());
									startActivity(intent);

								}
							});
				}

				closeProgressBar();
			};

			protected void onPreExecute() {
				showProgressBar();
			};

		}.execute(word, offset + "");

	}

	@Override
	protected void processLogic() {

	}

}
