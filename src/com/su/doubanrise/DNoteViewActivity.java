package com.su.doubanrise;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnLongClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebView.HitTestResult;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.su.doubanrise.api.bean.DNote;
import com.su.doubanrise.fragment.ConfirmDeleteDnoteDialog;
import com.su.doubanrise.fragment.ExitDialog;
import com.su.doubanrise.fragment.VerifyDialog;
import com.su.doubanrise.overall.Constant;
import com.su.doubanrise.util.FileDownLoader;
import com.su.doubanrise.util.FileUtil;
import com.su.doubanrise.widget.PopMenu;

public class DNoteViewActivity extends BaseActivity implements
		OnItemClickListener {

	WebView content_web;
	DNote dNote;
	int type;
	private PopMenu nPopMenu;

	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		type = this.getIntent().getIntExtra("type", 0);
		setContentView(R.layout.dnote_webview);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.common_second_title_layout);

		super.onCreate(savedInstanceState);
		setTitle("查看日志");
		nPopMenu = new PopMenu(this, 100, -2);
		nPopMenu.setOnItemClickListener(this);
		nPopMenu.addItems(new String[] { "编辑", "删除" });
	}

	@Override
	protected void findViewById() {
		showProgressBar();
		common_title_menu_button.setVisibility(View.VISIBLE);
		content_web = (WebView) findViewById(R.id.content_web);
		content_web.getSettings().setDefaultTextEncodingName("utf-8");// 避免中文乱码
		content_web.addJavascriptInterface(this, "javatojs");
		content_web.setScrollBarStyle(0);
		content_web.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				closeProgressBar();
				super.onPageFinished(view, url);
			}

		});
		WebSettings webSetting = content_web.getSettings();
		webSetting.setJavaScriptEnabled(true);
		webSetting.setNeedInitialFocus(false);
		webSetting.setSupportZoom(true);
		webSetting.setCacheMode(WebSettings.LOAD_DEFAULT
				| WebSettings.LOAD_CACHE_ELSE_NETWORK);

	}

	@Override
	protected void processLogic() {
		Serializable serializable = this.getIntent().getSerializableExtra(
				"dNote");
		dNote = (DNote) serializable;
		try {

			String htmlContent = FileUtil.getStringFromAssets(
					"notedetail.html", DNoteViewActivity.this);

			String _newsContent = dNote.getContent();

			String newsInfo = "发表时间:" + dNote.getPublish_time();
			String newsTitle = dNote.getTitle();
			String LOCAL_PATH = "file:///android_asset/";
			content_web.loadDataWithBaseURL(
					LOCAL_PATH,
					htmlContent.replace("#title#", newsTitle)
							.replace("#time#", newsInfo)
							.replace("#content#", _newsContent), "text/html",
					"utf-8", null);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void deleteNote() {
		if (dNote == null) {
			return;
		}
		new AsyncTask<Void, Void, Integer>() {

			@Override
			protected Integer doInBackground(Void... params) {

				String result = douban.deleteDNote(dNote);
				if (result.contains("{}")) {
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
					Toast("删除成功！");
					Constant.REFRESGDNOTE = true;
					finish();
					break;
				case 1:
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
			Intent intent = new Intent(DNoteViewActivity.this,
					DNoteEditActivity.class);
			intent.putExtra("dNote", dNote);
			startActivity(intent);
			finish();
			break;
		case 1:
			ConfirmDeleteDnoteDialog exitDialog = new ConfirmDeleteDnoteDialog();
			exitDialog.show(getSupportFragmentManager(), "");
			break;
		default:
			break;
		}
		nPopMenu.dismiss();
	}

}
