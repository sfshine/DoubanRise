package com.su.doubanrise.widget;

import java.util.ArrayList;

import android.R.integer;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.su.doubanrise.R;

public class PopMenu {
	private ArrayList<String> itemList;
	private Context context;
	private PopupWindow popupWindow;
	private ListView listView;

	// private OnItemClickListener listener;

	public PopMenu(Context context) {
		this.context = context;
		init(170, LayoutParams.WRAP_CONTENT);

	}

	public PopMenu(Context context, int width, int height) {
		this.context = context;
		init(width, height);
	}

	void init(int width, int height) {
		itemList = new ArrayList<String>(5);

		View view = LayoutInflater.from(context).inflate(
				R.layout.common_popmenu, null);

		listView = (ListView) view.findViewById(R.id.listView);
		listView.setAdapter(new PopAdapter());
		listView.setFocusableInTouchMode(true);
		listView.setFocusable(true);

		popupWindow = new PopupWindow(view, width, height);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());

	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		// this.listener = listener;
		listView.setOnItemClickListener(listener);
	}

	// 批量添加菜单项
	public void addItems(String[] items) {
		for (String s : items)
			itemList.add(s);
	}

	// 单个添加菜单项
	public void addItem(String item) {
		itemList.add(item);
	}

	public void showAsDropDown(View parent) {
		showAsDropDown(parent, 0, -2);

	}

	public void showAsDropDown(View parent, int x, int y) {
		popupWindow.showAsDropDown(parent, x, y);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.update();
	}

	// 隐藏菜单
	public void dismiss() {
		popupWindow.dismiss();
	}

	// 适配器
	private final class PopAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return itemList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return itemList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.common_popmenu_item, null);
				holder = new ViewHolder();

				convertView.setTag(holder);

				holder.groupItem = (TextView) convertView
						.findViewById(R.id.textView);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.groupItem.setText(itemList.get(position));

			return convertView;
		}

		private final class ViewHolder {
			TextView groupItem;
		}
	}
}
