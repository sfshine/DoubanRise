package com.su.doubanrise.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.su.doubanrise.MainActivity;
import com.su.doubanrise.R;

public class ExitDialog extends DialogFragment implements View.OnClickListener {

	Context context;
	private TextView content_tv;
	private TextView title_tv;
	private String content = "确实要离开吗？";
	private String title = "确认";

	public ExitDialog(String content_tv, String title) {
		super();
		this.content = content_tv;
		this.title = title;
	}

	public ExitDialog() {
		super();

	}

	/**
	 * 也可以在外部调用
	 * 
	 * @param title
	 * @param content
	 */
	public void setDialog(String title, String content) {
		title_tv.setText(title);
		content_tv.setText(content);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(0, R.style.MyDialogStyle);
		// setStyle(R.style.MyDialogStyle, 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		View view = inflater.inflate(R.layout.common_dialog, null);

		Button common_dialog_btn_ok = (Button) view
				.findViewById(R.id.common_dialog_btn_ok);
		Button common_dialog_btn_cancel = (Button) view
				.findViewById(R.id.common_dialog_btn_cancel);
		title_tv = (TextView) view.findViewById(R.id.common_dialog_title_text);
		content_tv = (TextView) view
				.findViewById(R.id.common_dialog_content_tv);
		setDialog(title, content);
		common_dialog_btn_cancel.setOnClickListener(this);
		common_dialog_btn_ok.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.common_dialog_btn_ok:
			((MainActivity) context).finish();
			this.dismiss();
			break;

		default:
			this.dismiss();
			break;
		}

	}
}