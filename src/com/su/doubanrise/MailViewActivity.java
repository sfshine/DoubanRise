package com.su.doubanrise;

import android.R.integer;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.su.doubanrise.api.bean.Mail;
import com.su.doubanrise.api.bean.User;
import com.su.doubanrise.widget.AsyncImageLoader;

public class MailViewActivity extends BaseActivity {

	ImageView mail_img;
	TextView user_name, title_tv, content_tv;
	Mail mail;
	int type;

	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		mail = (Mail) this.getIntent().getSerializableExtra("mail");
		type = this.getIntent().getIntExtra("type", 0);
		setContentView(R.layout.mail_view);
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
		mail_img = (ImageView) findViewById(R.id.user_img);
		user_name = (TextView) findViewById(R.id.user_name);
		title_tv = (TextView) findViewById(R.id.title_tv);
		content_tv = (TextView) findViewById(R.id.content_tv);
		if (type == 2) {
			user_name.setText("发给" + mail.getReceiver().getName() + "的邮件：");
			AsyncImageLoader asyncImageLoader = new AsyncImageLoader(this);
			asyncImageLoader.setAsyDrawableFromurl(mail.getReceiver()
					.getAvatar(), mail_img);
		} else {
			user_name.setText("来自" + mail.getSender().getName() + "的邮件：");
			AsyncImageLoader asyncImageLoader = new AsyncImageLoader(this);
			asyncImageLoader.setAsyDrawableFromurl(
					mail.getSender().getAvatar(), mail_img);
		}

	}

	@Override
	protected void processLogic() {
		title_tv.setText(mail.getTitle());
		content_tv.setText(mail.getContent());

	}

	@Override
	protected void setListener() {
		common_title_menu_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// sendMail("");
				Intent intent = new Intent(MailViewActivity.this,
						MailEditActivity.class);

				User user = mail.getSender();
				intent.putExtra("user", user);
				startActivity(intent);
			}
		});

	}

}
