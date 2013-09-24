package com.su.doubanrise.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.su.doubanrise.DoubanApp;
import com.su.doubanrise.api.Auth;
import com.su.doubanrise.api.Douban;
import com.su.doubanrise.api.bean.FormFile;

/**
 * HttpUtil Class Capsule Most Functions of Http Operations
 * 
 * @author sfshine
 * 
 */
public class HttpUtil {

	private static Header[] headers = new BasicHeader[2];
	private static String TAG = "HTTPUTIL";
	private static int TIMEOUT = 5 * 1000;
	private static final String BOUNDARY = "---------------------------7db1c523809b2";

	/**
	 * 在第一次授权的时候可能头部是空需要调用这个方法初始化token
	 */
	public static void initAfterAuth() {
		headers[0] = new BasicHeader("Authorization", "Bearer "
				+ Douban.getAccessToken());

	}

	/**
	 * Your header of http op
	 * 
	 * @return
	 */
	static {
		headers[0] = new BasicHeader("Authorization", "Bearer "
				+ Douban.getAccessToken());
		headers[1] = new BasicHeader("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 5.0; Windows XP; DigExt)");
		// headers[0] = new BasicHeader("Authorization",
		// Douban.getAccessToken());
		// headers[1] = new BasicHeader("Udid", "");
		// headers[2] = new BasicHeader("Os", "");
		// headers[3] = new BasicHeader("Osversion", "");
		// headers[4] = new BasicHeader("Appversion", "");
		// headers[5] = new BasicHeader("Sourceid", "");
		// headers[6] = new BasicHeader("Ver", "");
		// headers[7] = new BasicHeader("Userid", "");
		// headers[8] = new BasicHeader("Usersession", "");
		// headers[9] = new BasicHeader("Unique", "");
		// headers[10] = new BasicHeader("Cookie", "");
	}

	public static boolean delete(String murl) throws Exception {
		URL url = new URL(murl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("DELETE");
		conn.setConnectTimeout(5000);
		if (conn.getResponseCode() == 204) {

			MLog.e(conn.toString());
			return true;
		}
		MLog.e(conn.getRequestMethod());
		MLog.e(conn.getResponseCode() + "");
		return false;
	}

	/**
	 * Op Http get request
	 * 
	 * @param url
	 * @param map
	 *            Values to request
	 * @return
	 */
	static public String get(String url) {
		return get(url, null);

	}

	static public String get(String url, HashMap<String, String> map) {

		DefaultHttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), TIMEOUT);
		HttpConnectionParams.setSoTimeout(client.getParams(), TIMEOUT);
		ConnManagerParams.setTimeout(client.getParams(), TIMEOUT);

		MLog.e(headers[0] + "");
		String result = "ERROR";
		if (null != map) {
			int i = 0;
			for (Map.Entry<String, String> entry : map.entrySet()) {

				Log.i(TAG, entry.getKey() + "=>" + entry.getValue());
				if (i == 0) {
					url = url + "?" + entry.getKey() + "=" + entry.getValue();
				} else {
					url = url + "&" + entry.getKey() + "=" + entry.getValue();
				}

				i++;

			}
		}
		HttpGet get = new HttpGet(url);
		get.setHeaders(headers);
		Log.i(TAG, url);
		try {

			HttpResponse response = client.execute(get);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// setCookie(response);
				result = EntityUtils.toString(response.getEntity(), "UTF-8");

			} else {
				result = EntityUtils.toString(response.getEntity(), "UTF-8")
						+ response.getStatusLine().getStatusCode() + "ERROR";
			}

		} catch (ConnectTimeoutException e) {
			result = "TIMEOUTERROR";
		}

		catch (Exception e) {
			result = "OTHERERROR";
			e.printStackTrace();

		}
		Log.i(TAG, "result =>" + result);
		if (result.contains("access_token_has_expired")) {
			new Auth(DoubanApp.getContext()).refreshToken();

		}
		return result;
	}

	/**
	 * 上传带图片的http请求
	 * 
	 * @param murl网址
	 * @param map
	 *            参数对 主要不要包括图片
	 * @param path
	 *            图片路径 也可以是其他格式 自行做
	 * @return
	 * @throws Exception
	 */
	static public String post(String murl, HashMap<String, String> map,
			String path) throws Exception {
		File file = new File(path);
		String filename = path.substring(path.lastIndexOf("/"));
		// String filename = Str.md5(path);
		StringBuilder sb = new StringBuilder();
		if (null != map) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				sb.append("--" + BOUNDARY + "\r\n");
				sb.append("Content-Disposition: form-data; name=\""
						+ entry.getKey() + "\"" + "\r\n");
				sb.append("\r\n");
				sb.append(entry.getValue() + "\r\n");

			}
		}

		sb.append("--" + BOUNDARY + "\r\n");
		sb.append("Content-Disposition: form-data; name=\"image\"; filename=\""
				+ filename + "\"" + "\r\n");

		sb.append("Content-Type: image/pjpeg" + "\r\n");
		sb.append("\r\n");

		byte[] before = sb.toString().getBytes("UTF-8");
		byte[] after = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("UTF-8");

		URL url = new URL(murl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type",
				"multipart/form-data; boundary=" + BOUNDARY);
		conn.setRequestProperty("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 5.0; Windows XP; DigExt)");
		conn.setRequestProperty("Authorization",
				"Bearer " + Douban.getAccessToken());
		conn.setRequestProperty("Content-Length",
				String.valueOf(before.length + file.length() + after.length));
		conn.setRequestProperty("HOST", url.getHost());
		conn.setDoOutput(true);

		OutputStream out = conn.getOutputStream();
		InputStream in = new FileInputStream(file);

		out.write(before);

		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) != -1)
			out.write(buf, 0, len);

		out.write(after);

		in.close();
		out.close();
		MLog.i("result=>" + inputStream2String(conn.getInputStream()) + "");
		if (conn.getResponseCode() == HttpStatus.SC_OK) {
			return inputStream2String(conn.getInputStream());
		} else {
			return inputStream2String(conn.getInputStream()) + "Code"
					+ conn.getResponseCode();
		}

	}

	/**
	 * is转String
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static String inputStream2String(InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}

	/**
	 * Op Http post request , "404error" response if failed
	 * 
	 * @param url
	 * @param map
	 *            Values to request
	 * @return
	 */

	static public String post(String url, HashMap<String, String> map) {

		DefaultHttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), TIMEOUT);
		HttpConnectionParams.setSoTimeout(client.getParams(), TIMEOUT);
		ConnManagerParams.setTimeout(client.getParams(), TIMEOUT);
		HttpPost post = new HttpPost(url);
		Log.i(TAG, url);
		MLog.e(headers[0] + "");
		post.setHeaders(headers);
		String result = "ERROR";
		ArrayList<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>();
		if (map != null) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				Log.i(TAG, entry.getKey() + "=>" + entry.getValue());
				BasicNameValuePair pair = new BasicNameValuePair(
						entry.getKey(), entry.getValue());
				pairList.add(pair);
			}

		}
		try {
			HttpEntity entity = new UrlEncodedFormEntity(pairList, "UTF-8");
			post.setEntity(entity);
			HttpResponse response = client.execute(post);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				result = EntityUtils.toString(response.getEntity(), "UTF-8");

			} else {
				result = EntityUtils.toString(response.getEntity(), "UTF-8")
						+ response.getStatusLine().getStatusCode() + "ERROR";
			}

		} catch (ConnectTimeoutException e) {
			result = "TIMEOUTERROR";
		}

		catch (Exception e) {
			result = "OTHERERROR";
			e.printStackTrace();

		}
		Log.i(TAG, "result =>" + result);
		return result;
	}

	/**
	 * 自定义的http请求可以设置为DELETE PUT等而不是GET
	 * 
	 * @param url
	 * @param params
	 * @param method
	 * @throws IOException
	 */

	public static String customrequest(String url,
			HashMap<String, String> params, String method) {
		try {

			URL postUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) postUrl
					.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestProperty("Authorization",
					"Bearer " + Douban.getAccessToken());
			conn.setRequestMethod(method);
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 5.0; Windows XP; DigExt)");

			conn.connect();
			OutputStream out = conn.getOutputStream();
			StringBuilder sb = new StringBuilder();
			if (null != params) {
				int i = params.size();
				for (Map.Entry<String, String> entry : params.entrySet()) {
					if (i == 1) {
						sb.append(entry.getKey() + "=" + entry.getValue());
					} else {
						sb.append(entry.getKey() + "=" + entry.getValue() + "&");
					}

					i--;
				}
			}
			String content = sb.toString();
			out.write(content.getBytes("UTF-8"));
			out.flush();
			out.close();
			InputStream inStream = conn.getInputStream();
			String result = inputStream2String(inStream);
			Log.i(TAG, "result>" + result);
			conn.disconnect();
			return result;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * 豆瓣必须严格限制get请求所以增加这个方法 这个方法也可以自定义请求
	 * 
	 * @param url
	 * @param method
	 * @throws Exception
	 */

	public static String customrequestget(String url,
			HashMap<String, String> map, String method) {

		if (null != map) {
			int i = 0;
			for (Map.Entry<String, String> entry : map.entrySet()) {

				if (i == 0) {
					url = url + "?" + entry.getKey() + "=" + entry.getValue();
				} else {
					url = url + "&" + entry.getKey() + "=" + entry.getValue();
				}

				i++;
			}
		}
		try {

			URL murl = new URL(url);
			System.out.print(url);
			HttpURLConnection conn = (HttpURLConnection) murl.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod(method);
			conn.setRequestProperty("Authorization",
					"Bearer " + Douban.getAccessToken());
			conn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 5.0; Windows XP; DigExt)");

			InputStream inStream = conn.getInputStream();
			String result = inputStream2String(inStream);
			Log.i(TAG, "result>" + result);
			conn.disconnect();
			return result;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * Post Bytes to Server
	 * 
	 * @param url
	 * @param bytes
	 *            of text
	 * @return
	 */
	public static String PostBytes(String url, byte[] bytes) {
		try {
			URL murl = new URL(url);
			final HttpURLConnection con = (HttpURLConnection) murl
					.openConnection();

			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);

			con.setRequestMethod("POST");
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			con.setRequestProperty("Content-Type", "text/html");
			String cookie = headers[10].getValue();
			if (!isNull(headers[10].getValue())) {
				con.setRequestProperty("cookie", cookie);
			}

			con.setReadTimeout(TIMEOUT);
			con.setConnectTimeout(TIMEOUT);
			Log.i(TAG, url);
			DataOutputStream dsDataOutputStream = new DataOutputStream(
					con.getOutputStream());
			dsDataOutputStream.write(bytes, 0, bytes.length);

			dsDataOutputStream.close();
			if (con.getResponseCode() == HttpStatus.SC_OK) {
				InputStream isInputStream = con.getInputStream();
				int ch;
				StringBuffer buffer = new StringBuffer();
				while ((ch = isInputStream.read()) != -1) {
					buffer.append((char) ch);
				}

				Log.i(TAG, "GetDataFromServer>" + buffer.toString());

				return buffer.toString();
			} else {
				return "404error";
			}
		} catch (SocketTimeoutException e) {
			return "timeouterror";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "404error";
		}
	}

	/**
	 * set Cookie
	 * 
	 * @param response
	 */
	private static void setCookie(HttpResponse response) {
		if (response.getHeaders("Set-Cookie").length > 0) {
			Log.d(TAG, response.getHeaders("Set-Cookie")[0].getValue());
			headers[10] = new BasicHeader("Cookie",
					response.getHeaders("Set-Cookie")[0].getValue());
		}
	}

	/**
	 * check net work
	 * 
	 * @param context
	 * @return
	 */
	public static boolean hasNetwork(Context context) {
		ConnectivityManager con = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo workinfo = con.getActiveNetworkInfo();
		if (workinfo == null || !workinfo.isAvailable()) {
			Toast.makeText(context, "当前无网络连接,请稍后重试", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	/***
	 * @category check if the string is null
	 * @return true if is null
	 * */
	public static boolean isNull(String string) {
		boolean t1 = "".equals(string);
		boolean t2 = string == null;
		boolean t3 = string.equals("null");
		if (t1 || t2 || t3) {
			return true;
		} else {
			return false;
		}
	}
}