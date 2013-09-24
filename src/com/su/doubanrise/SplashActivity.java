package com.su.doubanrise;

import com.su.doubanrise.api.Auth;
import com.su.doubanrise.api.AuthWebViewActivity;
import com.su.doubanrise.util.DataStoreUtil;
import com.su.doubanrise.MainActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends Activity {
	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		getWindow().setFormat(PixelFormat.RGBA_8888);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.common_splashscreen);
		if (!Auth.checkToken(this)) {
			Intent intent = new Intent(this, AuthWebViewActivity.class);
			startActivity(intent);
			finish();
			return;
		}
		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent mainIntent = null;
				mainIntent = new Intent(SplashActivity.this, MainActivity.class);
				SplashActivity.this.startActivity(mainIntent);
				SplashActivity.this.finish();

			}
		}, 1000); // 2900 for release

	}

}
