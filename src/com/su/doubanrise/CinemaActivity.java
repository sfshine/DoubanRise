package com.su.doubanrise;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.su.doubanrise.adapter.CinemaListAdapter;
import com.su.doubanrise.api.bean.Cinema;
import com.su.doubanrise.overall.Constant;
import com.su.doubanrise.util.MLog;
import com.su.doubanrise.websitebiz.MovieService;
import com.su.doubanrise.widget.PopMenu;

public class CinemaActivity extends BaseActivity implements OnItemClickListener {
	private ListView cinema_list;
	private String movie_id;
	List<Cinema> cinemas = null;
	private PopMenu nPopMenu, popMenu;
	String time = "", today, tomorrow, dayafttmro;
	String district = "";
	private String[] times;
	private String[] timenames = new String[] { "今日影讯", "明日影讯", "后天影讯" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.cinema_activity);
		initTime();
		movie_id = this.getIntent().getStringExtra("movie_id");
		popMenu = new PopMenu(this);
		popMenu.setOnItemClickListener(dayListener);
		times = new String[] { today, tomorrow, dayafttmro };
		popMenu.addItems(timenames);
		nPopMenu = new PopMenu(this, 170, -2);
		nPopMenu.setOnItemClickListener(this);

		super.onCreate(savedInstanceState);
		setTitle("今天影讯");
		title_button
				.setBackgroundResource(R.drawable.base_catbar_background_selector);
		// TextView textView = new TextView(CinemaActivity.this);
		// textView.setText("暂时没有相关影讯");
		// textView.setGravity(Gravity.CENTER);
		// cinema_list.setEmptyView(textView);
	}

	void initTime() {
		Date date = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		today = f.format(date);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		date = calendar.getTime();
		tomorrow = f.format(date);
		calendar.add(Calendar.DATE, 1);
		date = calendar.getTime();
		dayafttmro = f.format(date);

	}

	@Override
	protected void findViewById() {
		cinema_list = (ListView) findViewById(R.id.cinema_list);

	}

	private String[] districts;
	private CinemaListAdapter cinemaListAdapter;

	@Override
	protected void processLogic() {

		new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(Void... params) {

				cinemas = MovieService.getCinemaInfo(movie_id, time, district);

				if (null == cinemas) {
					MLog.e("===========================================");
					return false;
				}
				return true;

			}

			protected void onPostExecute(Boolean result) {
				if (result) {
					if (districts == null) {
						districts = new String[Constant.DISTRICTS.size()];
						for (int i = 0; i < districts.length; i++) {
							districts[i] = Constant.DISTRICTS.get(i).getName();
						}
						nPopMenu.addItem("全部");
						nPopMenu.addItems(districts);
					}

					cinemaListAdapter = new CinemaListAdapter(
							CinemaActivity.this, cinemas);

					cinema_list.setAdapter(cinemaListAdapter);
					cinema_list
							.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									Intent intent = new Intent(
											CinemaActivity.this,
											CinemaViewActivity.class);
									intent.putExtra("cinema", cinemas.get(arg2));
									startActivity(intent);
								}
							});
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
		common_title_menu_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				nPopMenu.showAsDropDown(v, -35, -2);
			}
		});
		title_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				popMenu.showAsDropDown(v, 0, -2);
			}
		});

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (arg2 == 0) {
			district = "";
		} else {
			district = Constant.DISTRICTS.get(arg2 - 1).getId();
		}

		nPopMenu.dismiss();
		processLogic();

	}

	private OnItemClickListener dayListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			popMenu.dismiss();
			setTitle(timenames[arg2]);
			time = times[arg2];
			processLogic();

		}
	};
}
