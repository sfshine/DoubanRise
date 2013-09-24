package com.su.doubanrise;

import org.json.JSONObject;

import com.su.doubanrise.api.bean.SendMail;
import com.su.doubanrise.api.bean.User;
import com.su.doubanrise.fragment.VerifyDialog;
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

public class MailEditActivity extends BaseActivity implements
		OnItemClickListener {

	ImageView user_img;
	TextView user_name, title_et, content_et;
	User user;
	private PopMenu nPopMenu;

	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		user = (User) this.getIntent().getSerializableExtra("user");
		setContentView(R.layout.mail_edit);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.common_second_title_layout);
		nPopMenu = new PopMenu(this, 100, -2);
		nPopMenu.setOnItemClickListener(this);
		nPopMenu.addItems(new String[] { "发送", "取消" });
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void findViewById() {
		common_title_menu_button.setVisibility(View.VISIBLE);
		user_img = (ImageView) findViewById(R.id.user_img);
		user_name = (TextView) findViewById(R.id.user_name);
		title_et = (EditText) findViewById(R.id.title_et);
		content_et = (EditText) findViewById(R.id.content_et);
		user_name.setText("给" + user.getName() + "发邮件：");
		AsyncImageLoader asyncImageLoader = new AsyncImageLoader(this);
		asyncImageLoader.setAsyDrawableFromurl(user.getAvatar(), user_img);
	}

	@Override
	protected void processLogic() {
		// TODO Auto-generated method stub

	}

	private String captcha_token;
	private String captcha_string;

	public void sendMail(final String verify_code) {
		new AsyncTask<Void, Void, Integer>() {

			@Override
			protected Integer doInBackground(Void... params) {
				SendMail sendMail = new SendMail();
				sendMail.setTitle(title_et.getText().toString());
				sendMail.setContent(content_et.getText().toString());
				sendMail.setReceiver_id(user.getId());
				sendMail.setCaptcha_string(verify_code);
				sendMail.setCaptcha_token(captcha_token);
				String result = douban.sendMail(sendMail);
				if (result.contains("{}")) {
					return 0;

				} else if (result.contains("captcha_token")) {
					try {
						JSONObject jsonObject = new JSONObject(result);
						captcha_token = jsonObject.getString("captcha_token");
						captcha_string = jsonObject.getString("captcha_url");
						// 12-20 12:01:57.183: I/HTTPUTIL(1117): result
						// =>{"captcha_token":"2ob1Wqy3pxaZqOq0UzkbxQXd","captcha_url":"http:\/\/www.douban.com\/misc\/captcha?id=2ob1Wqy3pxaZqOq0UzkbxQXd&size=s"}403ERROR

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
					Toast("发送成功！");
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
			sendMail("");
			break;
		default:

			break;
		}
		nPopMenu.dismiss();

	}

}
