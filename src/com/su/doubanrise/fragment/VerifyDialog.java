package com.su.doubanrise.fragment;

import com.su.doubanrise.MailEditActivity;
import com.su.doubanrise.R;
import com.su.doubanrise.R.id;
import com.su.doubanrise.R.layout;
import com.su.doubanrise.util.FileDownLoader;
import com.su.doubanrise.util.MLog;
import com.su.doubanrise.widget.AsyncImageLoader;

import android.R.raw;
import android.content.Context;
import android.graphics.drawable.Drawable;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class VerifyDialog extends DialogFragment implements
		View.OnClickListener {
	private String image;
	Context context;
	private EditText captcha_edit;

	public VerifyDialog() {
		super();

	}

	public VerifyDialog(String image) {
		super();
		this.image = image;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 可以再这里设置样式

		setStyle(0, R.style.MyDialogStyle);
		// setStyle(STYLE_NO_TITLE, 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		View view = inflater.inflate(R.layout.mail_verify_dialog, null);
		final ImageView imageView = (ImageView) view
				.findViewById(R.id.verify_imageView);

		new AsyncTask<String, Void, Drawable>() {

			@Override
			protected Drawable doInBackground(String... params) {

				return FileDownLoader.getDrawableFromUrl(params[0]);
			}

			protected void onPostExecute(Drawable result) {
				imageView.setImageDrawable(result);
			};
		}.execute(image);
		imageView.setOnClickListener(this);
		Button common_dialog_btn_ok = (Button) view
				.findViewById(R.id.common_dialog_btn_ok);
		Button common_dialog_btn_cancel = (Button) view
				.findViewById(R.id.common_dialog_btn_cancel);
		TextView title = (TextView) view
				.findViewById(R.id.common_dialog_title_text);
		title.setText("请填入验证码");
		captcha_edit = (EditText) view.findViewById(R.id.captcha_edit);
		common_dialog_btn_cancel.setOnClickListener(this);
		common_dialog_btn_ok.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.verify_imageView:
		case R.id.common_dialog_btn_ok:
			((MailEditActivity) context).sendMail(captcha_edit.getText()
					.toString() + "");
			this.dismiss();
			break;

		default:
			this.dismiss();
			break;
		}

	}
}