package com.su.doubanrise.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;

import com.su.doubanrise.MainActivity;
import com.su.doubanrise.R;
import com.su.doubanrise.api.Douban;
import com.su.doubanrise.widget.PopMenu;

public abstract class BaseFragment extends Fragment implements
		OnItemClickListener {
	Douban douban;
	MainActivity mainActivity;
	PopMenu popMenu;
	public Button title_button;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		mainActivity = (MainActivity) getActivity();
		douban = mainActivity.getDouban();
		popMenu = new PopMenu(mainActivity);
		popMenu.setOnItemClickListener(this);

		super.onCreate(savedInstanceState);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		findViewById();
		processLogic();
		setListener();
		return super.onCreateView(inflater, container, savedInstanceState);

	}

	/**
	 * 设置下拉菜单
	 * 
	 * @param pullmenu
	 */
	void setPullMenu(String[] pullmenu) {
		popMenu.addItems(pullmenu);

	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 */
	void setTitle(String title, View view) {
		title_button = (Button) view.findViewById(R.id.common_title_button);
		title_button.setOnClickListener(clicklistener);
		title_button.setText(title);

	}

	private OnClickListener clicklistener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.common_title_button:
				popMenu.showAsDropDown(v);
				break;

			default:
				break;
			}

		}
	};

	void Toast(String msg) {
		android.widget.Toast.makeText(mainActivity, msg, 10).show();
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
