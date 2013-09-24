package com.su.doubanrise.fragment;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.su.doubanrise.BookViewActivity;
import com.su.doubanrise.MailEditActivity;
import com.su.doubanrise.MailViewActivity;
import com.su.doubanrise.R;
import com.su.doubanrise.adapter.MailListAdapter;
import com.su.doubanrise.adapter.SubjectListAdapter;
import com.su.doubanrise.adapter.UserListAdapter;
import com.su.doubanrise.api.bean.Book;
import com.su.doubanrise.api.bean.Mail;
import com.su.doubanrise.api.bean.Subject;
import com.su.doubanrise.api.bean.User;
import com.su.doubanrise.overall.RSS;

public class MailFragment extends BaseFragment {
	private List<Mail> mails;

	private View view;
	private ListView mail_list;
	String[] titles = new String[] { "未读邮件", "收件箱", "发件箱", "写邮件" };

	private TextView tv_none;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.mail_frame, null);
		super.onCreateView(inflater, container, savedInstanceState);

		setPullMenu(titles);
		setTitle(titles[0], view);
		return view;
	}

	@Override
	protected void findViewById() {
		mail_list = (ListView) view.findViewById(R.id.mail_list);
		tv_none = (TextView) view.findViewById(R.id.tv_none);

		mail_list.setEmptyView(tv_none);
	}

	@Override
	protected void processLogic() {
		getMails(0);
	}

	List<User> users;

	protected void getUsers() {

		new AsyncTask<Integer, Void, Boolean>() {
			@Override
			protected Boolean doInBackground(Integer... arg0) {
				users = douban.getUserList();
				if (users != null) {
					return true;
				}
				return false;
			}

			@Override
			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);

				mainActivity.closeProgressBar(view);

				if (result) {

					UserListAdapter reviewListAdapter = new UserListAdapter(
							mainActivity, users);

					mail_list.setAdapter(reviewListAdapter);
					mail_list.setOnItemClickListener(new OnItemClickListener() {

						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {

							Intent intent = new Intent(mainActivity,
									MailEditActivity.class);
							User user = users.get(arg2);
							intent.putExtra("user", user);
							mainActivity.startActivity(intent);

						}
					});
					Toast("点击选择要发送的用户！");

				} else {
					clear();
					Toast("当前无数据！");
				}

			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				mainActivity.showProgressBar(view);
			}

		}.execute();

	}

	private void clear() {
		if (mails != null) {
			mails.clear();
			MailListAdapter reviewListAdapter = new MailListAdapter(
					mainActivity, mails, 0);
			mail_list.setAdapter(reviewListAdapter);
		}
	}

	protected void getMails(final int type) {
		new AsyncTask<Integer, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(Integer... arg0) {
				try {
					switch (arg0[0]) {
					case 0:
						mails = douban.getUnread();
						break;
					case 1:
						mails = douban.getInbox();
						break;
					case 2:
						mails = douban.getOutbox();
						break;
					default:
						break;
					}

					return true;
				} catch (Exception e) {
					e.printStackTrace();
				}
				return false;
			}

			@Override
			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);

				mainActivity.closeProgressBar(view);

				if (result) {
					MailListAdapter reviewListAdapter = new MailListAdapter(
							mainActivity, mails, type);
					if (mails == null) {
						return;
					}
					// if (mails.size() > 0) {
					// tv_none.setVisibility(View.GONE);
					// } else {
					// tv_none.setVisibility(View.VISIBLE);
					// }
					mail_list.setAdapter(reviewListAdapter);
					mail_list.setOnItemClickListener(new OnItemClickListener() {

						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {

							Intent intent = new Intent(mainActivity,
									MailViewActivity.class);
							Mail mail = mails.get(arg2);
							intent.putExtra("mail", mail);
							intent.putExtra("type", type);
							mainActivity.startActivity(intent);

						}
					});

				} else {
					clear();
					Toast("当前无数据！");

				}

			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				mainActivity.showProgressBar(view);
			}

		}.execute(type);

	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		switch (arg2) {
		case 3:
			getUsers();
			break;
		default:
			getMails(arg2);
			break;
		}
		setTitle(titles[arg2], view);
		popMenu.dismiss();

	}
}
