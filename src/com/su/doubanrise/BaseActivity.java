package com.su.doubanrise;

import java.util.Date;
import java.util.List;

import org.apache.http.cookie.Cookie;

import com.su.doubanrise.api.Douban;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class BaseActivity extends FragmentActivity {
	ProgressDialog pd;
	public Button title_button;
	public ImageButton common_title_menu_button;
	Douban douban;

	protected void onResume() {

		super.onResume();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		douban = Douban.getDouban();
		initTitile();
		findViewById();
		setListener();
		processLogic();

		super.onCreate(savedInstanceState);
	}

	void initTitile() {
		title_button = (Button) findViewById(R.id.common_title_button);
		title_button.setBackgroundColor(Color.TRANSPARENT);
		common_title_menu_button = (ImageButton) findViewById(R.id.common_title_menu_button);

	}

	void setTitle(String title) {
		title_button.setText(title);
	}

	public void exit(View view) {
		finish();
	}

	// 退出
	protected void doExit() {
		new AlertDialog.Builder(BaseActivity.this)
				.setTitle("提示")
				.setMessage("确定要退出我的豆瓣吗？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialoginterface, int i) {
						finish();
					}
				})
				.setNeutralButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
					}

				}).show();

	}

	// 加载对话框
	public void showDialog() {
		pd = ProgressDialog.show(BaseActivity.this, "信息", "加载数据中...");
	}

	public void showProgressBar() {
		AnimationSet set = new AnimationSet(true);

		Animation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setDuration(500);
		set.addAnimation(animation);

		animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				-1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		animation.setDuration(500);
		set.addAnimation(animation);

		LayoutAnimationController controller = new LayoutAnimationController(
				set, 0.5f);
		FrameLayout loading = (FrameLayout) findViewById(R.id.loading);
		loading.setVisibility(View.VISIBLE);
		loading.setLayoutAnimation(controller);
	}

	public void closeProgressBar() {

		AnimationSet set = new AnimationSet(true);

		Animation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setDuration(500);
		set.addAnimation(animation);

		animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
		animation.setDuration(500);
		set.addAnimation(animation);

		LayoutAnimationController controller = new LayoutAnimationController(
				set, 0.5f);
		FrameLayout loading = (FrameLayout) findViewById(R.id.loading);

		loading.setLayoutAnimation(controller);

		loading.setVisibility(View.INVISIBLE);
	}

	public void showProgressBar(String title) {
		TextView loading = (TextView) findViewById(R.id.txt_loading);
		loading.setText(title);
		showProgressBar();
	}

	// 检查过期时间
	private boolean checkExperid(List<Cookie> cookies) {
		for (Cookie cookie : cookies) {
			if ("ue".equals(cookie.getName())) {
				Date now = new Date();
				if (now.compareTo(cookie.getExpiryDate()) > 0) {
					return false;
				}
			}
		}
		return true;
	}

	void Toast(String msg) {
		android.widget.Toast.makeText(this, msg, 10).show();
	}

	/**
	 * 并要绑定事件
	 * 
	 * @param view
	 */
	protected abstract void findViewById();

	/**
	 * 向后台请求数据
	 */
	protected abstract void processLogic();

	/**
	 * 设置监听
	 */
	protected abstract void setListener();

}
