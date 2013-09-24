package com.su.doubanrise;

import java.io.Serializable;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.su.doubanrise.api.bean.DNote;
import com.su.doubanrise.util.FileDownLoader;
import com.su.doubanrise.util.FileUtil;

public class DDNoteViewActivity extends BaseActivity {

	TextView title_tv, content_tv; 
	DNote dNote;
	int type;

	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		type = this.getIntent().getIntExtra("type", 0);
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
		Serializable serializable = this.getIntent().getSerializableExtra(
				"dNote");
		// if (serializable instanceof DNote) {
		dNote = (DNote) serializable;
		title_tv.setText(dNote.getTitle());
		// Spanned spanned = Html.fromHtml(dNote.getContent(), imageGetter,
		// null);
		Spanned spanned = Html.fromHtml(dNote.getContent());
		content_tv.setText(spanned);
		// }

	}

	// ImageGetter imageGetter = new ImageGetter() {
	//
	// @Override
	// public Drawable getDrawable(String source) {
	// FileDownLoader.getDrawableFromUrl(source);
	// Drawable drawable = FileDownLoader.getDrawableFromUrl(source);
	//
	// return drawable;
	// }
	// };

	@Override
	protected void setListener() {
		common_title_menu_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

	}

}
