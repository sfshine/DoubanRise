package com.su.doubanrise;

import org.json.JSONObject;

import com.su.doubanrise.api.bean.DNote;
import com.su.doubanrise.api.bean.SendMail;
import com.su.doubanrise.api.bean.User;
import com.su.doubanrise.fragment.VerifyDialog;
import com.su.doubanrise.overall.Constant;
import com.su.doubanrise.util.MLog;
import com.su.doubanrise.widget.AsyncImageLoader;
import com.su.doubanrise.widget.PopMenu;

import android.R.integer;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DNoteEditActivity extends BaseActivity implements
		OnItemClickListener {

	ImageView user_img;
	TextView user_name, title_et, content_et;
	private PopMenu nPopMenu;
	private DNote dNote;

	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		// user = (User) this.getIntent().getSerializableExtra("user");
		setContentView(R.layout.dnote_edit);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.common_second_title_layout);
		nPopMenu = new PopMenu(this, 100, -2);
		nPopMenu.setOnItemClickListener(this);
		nPopMenu.addItems(new String[] { "保存", "取消" });
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void findViewById() {
		common_title_menu_button.setVisibility(View.VISIBLE);
		common_title_menu_button
				.setBackgroundResource(R.drawable.btn_title_menu);

		title_et = (EditText) findViewById(R.id.title_et);
		content_et = (EditText) findViewById(R.id.content_et);
		dNote = (DNote) this.getIntent().getSerializableExtra("dNote");
		if (dNote != null) {
			title_et.setText(dNote.getTitle());
			content_et.setText(dNote.getContent());

		}

	}

	@Override
	protected void processLogic() {
		// TODO Auto-generated method stub

	}

	private String captcha_token;
	private String captcha_string;
	int REQUESTCODE = 5296;

	public void saveNote(final String verify_code) {
		new AsyncTask<Void, Void, Integer>() {

			@Override
			protected Integer doInBackground(Void... params) {
				String title = title_et.getText().toString();
				DNote dNote = new DNote(title, "", content_et.getText()
						.toString(), "");
				String result = douban.writeDNote(dNote);
				if (result.contains(title)) {
					return 0;

				} else if (result.contains("captcha_token")) {
					try {
						JSONObject jsonObject = new JSONObject(result);
						captcha_token = jsonObject.getString("captcha_token");
						captcha_string = jsonObject.getString("captcha_url");

					} catch (Exception e) {
						return 1;
					}
					return 1;
				} else {
					return 2;
				}

			}

			protected void onPreExecute() {
				showProgressBar();
			};

			protected void onPostExecute(Integer result) {
				closeProgressBar();
				switch (result) {
				case 0:
					Toast("保存成功！");
					setResult(REQUESTCODE);
					finish();
					break;
				case 1:
					VerifyDialog verifyDialog = new VerifyDialog(captcha_string);
					verifyDialog.show(getSupportFragmentManager(), "");
					break;
				case 2:
					Toast("发生错误。请重试");
					break;
				default:
					break;
				}

			};
		}.execute();
	}

	public void modNote() {
		if (dNote == null) {
			return;
		}
		new AsyncTask<Void, Void, Integer>() {

			@Override
			protected Integer doInBackground(Void... params) {
				String title = title_et.getText().toString();
				dNote.setTitle(title);
				dNote.setContent(content_et.getText().toString());
				String result = douban.modDNote(dNote);
				if (result.contains(title)) {
					return 0;
				}
				return 2;

			}

			protected void onPreExecute() {
				showProgressBar();
			};

			protected void onPostExecute(Integer result) {
				closeProgressBar();
				switch (result) {
				case 0:
					Toast("保存成功！");
					Constant.REFRESGDNOTE = true;
					finish();
					break;
				case 1:
					VerifyDialog verifyDialog = new VerifyDialog(captcha_string);
					verifyDialog.show(getSupportFragmentManager(), "");
					break;
				case 2:
					Toast("发生错误。请重试");
					break;
				default:
					break;
				}

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
			if (dNote == null) {
				saveNote("");
			} else {
				modNote();
			}

			break;
		default:

			break;
		}
		nPopMenu.dismiss();

	}

}
