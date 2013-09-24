package com.su.doubanrise.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.su.doubanrise.api.Douban;

import android.os.Environment;
import android.util.Log;

public class TestHttpUtil {

	private static final String BOUNDARY = "---------------------------7db1c523809b2";

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

		conn.setRequestProperty("Authorization",
				"Bearer " + Douban.getAccessToken());
		conn.setRequestProperty("Content-Length",
				String.valueOf(before.length + file.length() + after.length));
		// conn.setRequestProperty("HOST", murl);
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
		MLog.e(inputStream2String(conn.getInputStream()) + "");
		return conn.getContent().toString();

	}

	public static String inputStream2String(InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}
}
