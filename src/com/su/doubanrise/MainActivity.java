package com.su.doubanrise;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.su.doubanrise.api.Douban;
import com.su.doubanrise.fragment.ActivitiesFragment;
import com.su.doubanrise.fragment.BookFragment;
import com.su.doubanrise.fragment.DNoteFragment;
import com.su.doubanrise.fragment.ExitDialog;
import com.su.doubanrise.fragment.MailFragment;
import com.su.doubanrise.fragment.MovieFragment;
import com.su.doubanrise.fragment.MusicFragment;
import com.su.doubanrise.fragment.RightFragment;
import com.su.doubanrise.fragment.SearchFragment;
import com.su.doubanrise.fragment.VerifyDialog;
import com.su.doubanrise.util.DataStoreUtil;
import com.su.doubanrise.util.FileUtil;
import com.su.doubanrise.util.MLog;
import com.su.doubanrise.widget.SlidingMenu;

public class MainActivity extends FragmentActivity {
	SlidingMenu mSlidingMenu;

	RightFragment rightFragment;

	FragmentTransaction fragmentTransaction;
	Douban douban;

	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tab_activity);

		// 实例化一次这个代理类
		douban = Douban.getDouban(this,
				DataStoreUtil.getString((Context) this, "access_token"));

		mSlidingMenu = (SlidingMenu) findViewById(R.id.slidingMenu);
		mSlidingMenu.setRightView(getLayoutInflater().inflate(
				R.layout.tab_right_frame, null));
		mSlidingMenu.setCenterView(getLayoutInflater().inflate(
				R.layout.tab_center_frame, null));

		fragmentTransaction = this.getSupportFragmentManager()
				.beginTransaction();

		rightFragment = new RightFragment();
		fragmentTransaction.replace(R.id.right_frame, rightFragment);
		setDefaultFragment();
		fragmentTransaction.commit();
		deleteOldFiles();

	}

	void deleteOldFiles() {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				FileUtil.deleteOldFiles();
				return null;
			}

		}.execute();
	}

	// 加载对话框
	public void showDialog() {
		pd = ProgressDialog.show(MainActivity.this, "信息", "加载数据中...");
	}

	// 加载自定义对话框
	public void showProgressBar(View view) {
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
		FrameLayout loading = (FrameLayout) view.findViewById(R.id.loading);
		loading.setVisibility(View.VISIBLE);
		loading.setLayoutAnimation(controller);
	}

	public void closeProgressBar(View view) {

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
		FrameLayout loading = (FrameLayout) view.findViewById(R.id.loading);

		loading.setLayoutAnimation(controller);

		loading.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onBackPressed() {
		doExit();
		// super.onBackPressed();
	}

	// 退出
	public void doExit() {
		// new AlertDialog.Builder(MainActivity.this)
		// .setTitle("提示")
		// .setMessage("确定要退出我的豆瓣吗？")
		// .setPositiveButton("确定", new DialogInteqrface.OnClickListener() {
		// public void onClick(DialogInterface dialoginterface, int i) {
		// finish();
		// }
		// })
		// .setNeutralButton("取消", new DialogInterface.OnClickListener() {
		// public void onClick(DialogInterface arg0, int arg1) {
		// }
		//
		// }).show();
		ExitDialog exitDialog = new ExitDialog();
		exitDialog.show(getSupportFragmentManager(), "");

	}

	public Douban getDouban() {
		return douban;
	}

	void replaceView(Fragment fragment) {
		fragmentTransaction = this.getSupportFragmentManager()
				.beginTransaction();
		fragmentTransaction.replace(R.id.center_frame, fragment);
		fragmentTransaction.commit();

	}

	public void showRight() {

		mSlidingMenu.showRightView();
	}

	/**
	 * 按钮的事件实在xml中注射的
	 * 
	 * @param view
	 */
	public void exit(View view) {
		doExit();
	}

	public void smenu(View view) {
		showRight();
	}

	public void music(View view) {
		MusicFragment musicfragment = new MusicFragment();
		replaceView(musicfragment);
		showRight();
	}

	public void book(View view) {
		BookFragment bookFragment = new BookFragment();
		replaceView(bookFragment);
		showRight();
	}

	public void search(View view) {
		// Intent intent = new Intent(MainActivity.this, SearchActivity.class);
		// startActivity(intent);
		SearchFragment searchFragment = new SearchFragment();
		replaceView(searchFragment);
		showRight();
	}

	public void note(View view) {
		DNoteFragment tmsgFragment = new DNoteFragment();
		replaceView(tmsgFragment);
		showRight();

	}

	public void mail(View view) {
		MailFragment tmsgFragment = new MailFragment();
		replaceView(tmsgFragment);
		showRight();

	}

	public void movie(View view) {
		MovieFragment tmsgFragment = new MovieFragment();
		replaceView(tmsgFragment);
		showRight();

	}

	void setDefaultFragment() {
		// DiaryFragment tmsgFragment = new DiaryFragment();
		MovieFragment tmsgFragment = new MovieFragment();
		fragmentTransaction.replace(R.id.center_frame, tmsgFragment);

	}

	public void activities(View view) {
		ActivitiesFragment tmsgFragment = new ActivitiesFragment();
		replaceView(tmsgFragment);
		showRight();

	}

}
