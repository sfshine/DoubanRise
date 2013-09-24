package com.su.doubanrise.api;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.su.doubanrise.R;
import com.su.doubanrise.util.MLog;
import com.su.doubanrise.util.UIutil;

public class AuthWebViewActivity extends Activity {
	/**
	 * 只是为了从用户哪里获取后面要用的code
	 */
	WebView wv;
	ProgressDialog pd;
	Handler handler;
	Boolean geturl = true;
	private String url;
	private String code;
	private Context context;
	Auth auth;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.auth_webview);
		context = this;
		auth = new Auth(context);
		url = auth.getCodeURL();
		// url = Douban.TOKENURL;
		init();
		loadurl(wv, url);
		handler = new Handler() {
			public void handleMessage(Message msg) {
				if (!Thread.currentThread().isInterrupted()) {
					switch (msg.what) {
					case 0:
						pd.show();
						break;
					case 1:
						pd.hide();
						break;
					}
				}
				super.handleMessage(msg);
			}
		};
	}

	@Override
	protected void onDestroy() {
		pd.dismiss();
		super.onDestroy();
	}

	public void init() {// 初始化
		pd = new ProgressDialog(AuthWebViewActivity.this);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setMessage("正在进行首次授权...");

		wv = (WebView) findViewById(R.id.webview);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.setScrollBarStyle(0);
		wv.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(final WebView view,
					final String url) {

				loadurl(view, url);
				return true;
			}

		});
		wv.setWebChromeClient(new WebChromeClient() {

			public void onProgressChanged(WebView view, int progress) {

				if (geturl && null != view.getUrl()
						&& view.getUrl().contains("code=")) {
					MLog.e(view.getUrl() + "");
					String url = view.getUrl();
					code = url.substring(url.indexOf("=") + 1, url.length());
					MLog.e(code);
					wv.stopLoading();
					auth.getToken(code);
					geturl = false;
				}
				if (geturl && null != view.getUrl()
						&& view.getUrl().contains("error")) {
					UIutil.toast(AuthWebViewActivity.this, "你拒绝了授权，请重试。");
					geturl = false;
					finish();

				}
				setTitle("加载中..." + progress + "%");
				if (progress == 100) {
					setTitle("加载完成");
					handler.sendEmptyMessage(1);
				}
				super.onProgressChanged(view, progress);
			}
		});

	}

	public void loadurl(final WebView view, final String url) {
		MLog.e(url);
		pd.show();
		view.loadUrl(url);

	}

}