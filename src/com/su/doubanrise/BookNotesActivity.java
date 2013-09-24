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

import com.su.doubanrise.adapter.NoteListAdapter;
import com.su.doubanrise.api.bean.Note;
import com.su.doubanrise.overall.Constant;
import com.su.doubanrise.util.MLog;
import com.su.doubanrise.websitebiz.MovieService;
import com.su.doubanrise.widget.PopMenu;

public class BookNotesActivity extends BaseActivity implements
		OnItemClickListener {
	private ListView booknote_list;
	private String book_id;
	List<Note> notes = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.cinema_activity);

		book_id = this.getIntent().getStringExtra("book_id");

		super.onCreate(savedInstanceState);
		setTitle("笔记");
		title_button
				.setBackgroundResource(R.drawable.base_catbar_background_selector);
		// TextView textView = new TextView(CinemaActivity.this);
		// textView.setText("暂时没有相关影讯");
		// textView.setGravity(Gravity.CENTER);
		// booknote_list.setEmptyView(textView);
	}

	@Override
	protected void findViewById() {
		booknote_list = (ListView) findViewById(R.id.cinema_list);

	}

	private NoteListAdapter noteListAdapter;

	@Override
	protected void processLogic() {

		new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(Void... params) {

				notes = douban.getNotes(book_id);

				if (null == notes) {

					return false;
				}
				return true;

			}

			protected void onPostExecute(Boolean result) {
				if (result) {

					noteListAdapter = new NoteListAdapter(
							BookNotesActivity.this, notes);

					booknote_list.setAdapter(noteListAdapter);
					booknote_list
							.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									Intent intent = new Intent(
											BookNotesActivity.this,
											BookNoteViewActivity.class);
									intent.putExtra("note_id", notes.get(arg2)
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

		}.execute();

	}

	@Override
	protected void setListener() {

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		processLogic();

	}

}
