package com.su.doubanrise.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.su.doubanrise.R;
import com.su.doubanrise.api.bean.DNote;

public class DNoteListAdapter extends BaseAdapter {
	private List<DNote> dnotes;
	private LayoutInflater mInflater;
	private Context context;

	public DNoteListAdapter(Context context, List<DNote> dnotes) {
		this.context = context;
		this.dnotes = dnotes;

		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return dnotes.size();
	}

	public Object getItem(int i) {
		return dnotes.get(i);
	}

	public long getItemId(int i) {
		return i;
	}

	public View getView(int i, View view, ViewGroup vg) {

		if (view == null) {

			view = mInflater.inflate(R.layout.note_item, null);
		}
		DNote DNote = dnotes.get(i);

		TextView cinema_name = (TextView) view.findViewById(R.id.note_name);
		cinema_name.setText(DNote.getTitle());
		TextView cinema_address = (TextView) view
				.findViewById(R.id.note_summary);
		cinema_address.setText(DNote.getSummary());
		return view;
	}

}