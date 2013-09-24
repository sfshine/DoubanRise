package com.su.doubanrise.fragment;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
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
import com.su.doubanrise.DNoteViewActivity;
import com.su.doubanrise.MailEditActivity;
import com.su.doubanrise.MailViewActivity;
import com.su.doubanrise.DNoteEditActivity;
import com.su.doubanrise.R;
import com.su.doubanrise.adapter.DNoteListAdapter;
import com.su.doubanrise.adapter.MailListAdapter;
import com.su.doubanrise.adapter.NoteListAdapter;
import com.su.doubanrise.adapter.SubjectListAdapter;
import com.su.doubanrise.adapter.UserListAdapter;
import com.su.doubanrise.api.bean.Book;
import com.su.doubanrise.api.bean.DNote;
import com.su.doubanrise.api.bean.Subject;
import com.su.doubanrise.api.bean.User;
import com.su.doubanrise.overall.Constant;
import com.su.doubanrise.overall.RSS;
import com.su.doubanrise.util.DataStoreUtil;
import com.su.doubanrise.util.MLog;

public class DNoteFragment extends BaseFragment {
	private List<DNote> notes;

	private View view;
	private ListView mail_list;
	String[] titles = new String[] { "我的日志", "写日志" };
	String uid;
	private TextView tv_none;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uid = DataStoreUtil.getString(mainActivity, "douban_user_id");
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.mail_frame, null);
		super.onCreateView(inflater, container, savedInstanceState);

		setPullMenu(titles);
		setTitle(titles[0], view);
		getNotes(0);
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (Constant.REFRESGDNOTE) {
			getNotes(0);
			Constant.REFRESGDNOTE = false;
		}
	}

	@Override
	protected void findViewById() {
		mail_list = (ListView) view.findViewById(R.id.mail_list);
		tv_none = (TextView) view.findViewById(R.id.tv_none);
		tv_none.setText("当前无数据");
		mail_list.setEmptyView(tv_none);

	}

	@Override
	protected void processLogic() {
		// getNotes(0);
	}

	List<User> users;

	protected void getNotes(final int type) {
		new AsyncTask<Integer, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(Integer... arg0) {
				try {
					switch (arg0[0]) {
					case 0:
						notes = douban.getDNotes(uid);
						break;
					case 1:
						// notes = douban.getInbox();
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
					DNoteListAdapter reviewListAdapter = new DNoteListAdapter(
							mainActivity, notes);
					if (notes == null) {
						return;
					}
					// if (notes.size() > 0) {
					// tv_none.setVisibility(View.GONE);
					// } else {
					// tv_none.setVisibility(View.VISIBLE);
					// }
					mail_list.setAdapter(reviewListAdapter);
					mail_list.setOnItemClickListener(new OnItemClickListener() {

						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {

							Intent intent = new Intent(mainActivity,
									DNoteViewActivity.class);
							DNote dNote = notes.get(arg2);
							intent.putExtra("dNote", dNote);
							intent.putExtra("type", type);
							mainActivity.startActivity(intent);

						}
					});

				} else {
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

	int REQUESTCODE = 5296;

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		switch (arg2) {
		case 1:
			Intent intent = new Intent(mainActivity, DNoteEditActivity.class);

			startActivityForResult(intent, REQUESTCODE);
			// startActivity(intent);
			break;
		default:
			getNotes(arg2);

			break;
		}
		// setTitle(titles[arg2], view);
		popMenu.dismiss();

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		MLog.e(requestCode + "" + resultCode);
		if (resultCode == REQUESTCODE && requestCode == REQUESTCODE) {
			getNotes(0);

		}

	}
}
