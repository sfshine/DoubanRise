package com.su.doubanrise.widget;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.su.doubanrise.DoubanApp;
import com.su.doubanrise.R;
import com.su.doubanrise.overall.Constant;
import com.su.doubanrise.util.FileDownLoader;
import com.su.doubanrise.util.MLog;
import com.su.doubanrise.util.Str;

@SuppressWarnings("deprecation")
public class AsyncImageLoader {
	private Context mcontext;
	private HashMap<String, SoftReference<Drawable>> imageCache;

	public AsyncImageLoader(Context context) {
		this.mcontext = DoubanApp.getContext();
		imageCache = new HashMap<String, SoftReference<Drawable>>();
	}

	public AsyncImageLoader() {
		this.mcontext = DoubanApp.getContext();
		imageCache = new HashMap<String, SoftReference<Drawable>>();
	}

	/**
	 * 
	 * @param uri
	 *            圖片地址
	 * @param imageView
	 *            圖片imageview
	 * @param imageCallback
	 *            回調
	 * @return
	 */
	public Drawable loadDrawable(final String uri, final ImageView imageView,
			final ImageCallback imageCallback) {
		if (imageCache.containsKey(uri)) {

			SoftReference<Drawable> SoftReference = imageCache.get(uri);
			Drawable drawable = SoftReference.get();
			if (drawable != null) {
				return drawable;
			}
		}
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				imageCallback.imageLoaded((Drawable) msg.obj, imageView, uri);
			}
		};
		new Thread() {
			public void run() {
				Drawable drawable = null;
				drawable = getDrawableFromFile(uri);
				imageCache.put(uri, new SoftReference<Drawable>(drawable));
				if (!new File(uri).isFile()) {
					drawable = mcontext.getResources().getDrawable(
							R.drawable.ic_launcher);
				}
				Message msg = handler.obtainMessage(0, drawable);
				handler.sendMessage(msg);
				drawable = null;
			}
		}.start();
		return null;
	}

	/**
	 * 
	 * @param url
	 *            圖片網址
	 * @param imageView
	 *            圖片imageview
	 * @param imageCallback
	 *            回調
	 * @return
	 */
	public Drawable loadDrawableFromUrl(final String url,
			final ImageView imageView, final int opsize,
			final ImageCallback imageCallback) {

		final String savePath = Constant.PICPATH + Str.md5(url);

		if (imageCache.containsKey(url)) {

			Drawable drawable = imageCache.get(url).get();
			if (drawable != null) {
				return drawable;
			}
		}
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				imageCallback.imageLoaded((Drawable) msg.obj, imageView, url);
			}
		};
		new Thread() {
			public void run() {
				try {
					FileDownLoader.downloadImg(url, savePath);

				} catch (Exception e) {
					e.printStackTrace();
				}
				Drawable drawable = null;
				drawable = getDrawableFromFile(savePath, opsize);

				imageCache.put(url, new SoftReference<Drawable>(drawable));
				if (!new File(savePath).isFile()) {
					drawable = mcontext.getResources().getDrawable(
							R.drawable.ic_launcher);
				}
				Message msg = handler.obtainMessage(0, drawable);
				handler.sendMessage(msg);

			}
		}.start();
		return null;
	}

	public interface ImageCallback {
		public void imageLoaded(Drawable imageDrawable, ImageView imageView,
				String uri);
	}

	/**
	 * 从本读读取图片
	 * 
	 * @param uri
	 * @return
	 */
	private Drawable getDrawableFromFile(String uri) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 8;
		Bitmap bitmap = BitmapFactory.decodeFile(uri, options);
		Drawable drawable = new BitmapDrawable(bitmap);

		bitmap = null;
		return drawable;
	}

	/**
	 * 从本读读取图片 添加opt参数
	 * 
	 * @param uri
	 * @return
	 */
	private Drawable getDrawableFromFile(String uri, int opsize) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = opsize;
		options.inPurgeable = true;
		Bitmap bitmap = BitmapFactory.decodeFile(uri, options);// 这里还是会出现oom??

		Drawable drawable = new BitmapDrawable(bitmap);
		bitmap = null;
		return drawable;
	}

	public void setAsyDrawableFromurl(String url, ImageView imageView) {
		if (url == null) {
			return;
		}

		imageView.setImageDrawable(loadDrawableFromUrl(url, imageView, 1,
				new ImageCallback() {
					public void imageLoaded(Drawable imageDrawable,
							ImageView imageView, String imageUrl) {
						imageView.setImageDrawable(imageDrawable);
					}
				}));
	}

	public void setAsyDrawable(String uri, ImageView imageView) {
		if (uri == null) {
			return;
		}

		imageView.setImageDrawable(loadDrawable(uri, imageView,
				new ImageCallback() {
					public void imageLoaded(Drawable imageDrawable,
							ImageView imageView, String imageUrl) {
						imageView.setImageDrawable(imageDrawable);
					}
				}));
	}
}
