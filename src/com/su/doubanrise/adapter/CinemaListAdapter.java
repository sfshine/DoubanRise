package com.su.doubanrise.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.su.doubanrise.R;
import com.su.doubanrise.api.bean.Cinema;

public class CinemaListAdapter extends BaseAdapter {
	private List<Cinema> cinemas;
	private LayoutInflater mInflater;
	private Context context;

	public CinemaListAdapter(Context context, List<Cinema> cinemas) {
		this.context = context;
		this.cinemas = cinemas;

		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return cinemas.size();
	}

	public Object getItem(int i) {
		return cinemas.get(i);
	}

	public long getItemId(int i) {
		return i;
	}

	public View getView(int i, View view, ViewGroup vg) {

		if (view == null) {

			view = mInflater.inflate(R.layout.cinema_item, null);
		}
		Cinema cinema = cinemas.get(i);

		TextView cinema_name = (TextView) view.findViewById(R.id.cinema_name);
		cinema_name.setText(cinema.getName());
		TextView cinema_address = (TextView) view
				.findViewById(R.id.cinema_address);
		cinema_address.setText(cinema.getAddress());
		return view;
	}

}