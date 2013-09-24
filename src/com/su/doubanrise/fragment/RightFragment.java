package com.su.doubanrise.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.su.doubanrise.MainActivity;
import com.su.doubanrise.R;
import com.su.doubanrise.api.Douban;
import com.su.doubanrise.api.UserApi;
import com.su.doubanrise.api.bean.User;
import com.su.doubanrise.overall.Constant;
import com.su.doubanrise.util.DataStoreUtil;
import com.su.doubanrise.widget.AsyncImageLoader;

public class RightFragment extends BaseFragment {

	private ImageView userIcon_iv;
	private TextView user_tv;
	private static User user;
	private View view;
	private TextView motto_tv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.tab_navigation_layout, null);
		super.onCreateView(inflater, container, savedInstanceState);// 执行这个方法通过父类获取douban对象
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	protected void findViewById() {
		userIcon_iv = (ImageView) view.findViewById(R.id.userIcon_iv);
		user_tv = (TextView) view.findViewById(R.id.user_tv);
		motto_tv = (TextView) view.findViewById(R.id.motto_tv);

	}

	@Override
	protected void processLogic() {
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
			}

			@Override
			protected Void doInBackground(Void... params) {
				if (user == null) {
					user = douban.getMyinfo();
				}

				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				if (user != null) {

					user_tv.setText(user.getName());
					Constant.USERID = user.getId();
					motto_tv.setText(user.getDesc());
					AsyncImageLoader asyncImageLoader = new AsyncImageLoader(
							mainActivity);
					asyncImageLoader.setAsyDrawableFromurl(user.getAvatar(),
							userIcon_iv);
				} else {

					user_tv.setText("豆豆梦想版");
					motto_tv.setText("暂时无法获取信息，请重试");
				}

				super.onPostExecute(result);
			}

		}.execute();

	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

}
