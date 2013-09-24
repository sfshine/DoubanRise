package com.su.doubanrise;

import java.io.Serializable;

import android.R.integer;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.su.doubanrise.api.bean.DNote;
import com.su.doubanrise.api.bean.Note;
import com.su.doubanrise.api.bean.User;
import com.su.doubanrise.util.Str;
import com.su.doubanrise.widget.AsyncImageLoader;

public class BookNoteViewActivity extends BaseActivity {

	TextView title_tv, content_tv;
	Note note;
	private String note_id;

	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		note_id = (String) this.getIntent().getSerializableExtra("note_id");

		setContentView(R.layout.dnote_view);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.common_second_title_layout);

		super.onCreate(savedInstanceState);
		setTitle("查看");
	}

	@Override
	protected void findViewById() {
		common_title_menu_button.setVisibility(View.VISIBLE);
		common_title_menu_button
				.setBackgroundResource(R.drawable.mail_write_selector);

		title_tv = (TextView) findViewById(R.id.title_tv);
		content_tv = (TextView) findViewById(R.id.content_tv);

	}

	@Override
	protected void processLogic() {
		new AsyncTask<Void, Void, Boolean>() {
			protected void onPreExecute() {
				showProgressBar();
			};

			@Override
			protected Boolean doInBackground(Void... params) {
				note = douban.getNote(note_id);
				if (note != null) {
					return true;
				}
				return false;
			}

			protected void onPostExecute(Boolean result) {
				closeProgressBar();
				if (result) {

					if (Str.isNull(note.getChapter())) {
						title_tv.setText("第" + note.getPage_no() + "页写道:");
					} else {
						title_tv.setText("《" + note.getChapter() + "》一章中写道:");
					}

					content_tv.setText(note.getContent());
				}

			};

		}.execute();

		// }
	}

	@Override
	protected void setListener() {
		common_title_menu_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

	}

}
