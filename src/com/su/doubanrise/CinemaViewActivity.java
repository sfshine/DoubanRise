package com.su.doubanrise;

import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.su.doubanrise.R.layout;
import com.su.doubanrise.adapter.CinemaListAdapter;
import com.su.doubanrise.api.bean.Cinema;
import com.su.doubanrise.websitebiz.MovieService;

public class CinemaViewActivity extends BaseActivity {
	private TextView cinema_name_tv, cinema_price_tv, cinema_timeline_tv,
			cinema_phone_tv, cinema_addr_tv;
	private Cinema cinema;
	List<Cinema> cinemas = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.cinema_view);
		cinema = (Cinema) this.getIntent().getSerializableExtra("cinema");
		super.onCreate(savedInstanceState);
		setTitle("本地影讯");
	}

	@Override
	protected void findViewById() {
		cinema_name_tv = (TextView) findViewById(R.id.cinema_name_tv);
		cinema_price_tv = (TextView) findViewById(R.id.cinema_price_tv);
		cinema_timeline_tv = (TextView) findViewById(R.id.cinema_timeline_tv);
		cinema_phone_tv = (TextView) findViewById(R.id.cinema_phone_tv);
		cinema_addr_tv = (TextView) findViewById(R.id.cinema_addr_tv);
		cinema_name_tv.setText(cinema.getName());
		cinema_price_tv.setText("票价：" + cinema.getPrice());
		cinema_timeline_tv.setText(cinema.getTimelines());
		cinema_phone_tv.setText(cinema.getPhone());
		cinema_addr_tv.setText(cinema.getAddress());

	}

	@Override
	protected void processLogic() {

	}

	@Override
	protected void setListener() {
		common_title_menu_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Intent intent = new Intent(CinemaViewActivity.this,
				// GMapActivity.class);
				// intent.putExtra("loc", cinema.getLatLng());
				// startActivity(intent);

			}
		});

	}

}
