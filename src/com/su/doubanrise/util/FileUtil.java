package com.su.doubanrise.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.su.doubanrise.overall.Constant;

import android.content.Context;
import android.content.res.AssetManager;

public class FileUtil {
	/**
	 * 删除以前的缓存文件
	 */
	public static void deleteOldFiles() {
		File file = new File(Constant.PATH);
		if (file.exists()) {

			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (!files[i].getAbsolutePath().contains(Constant.PICPATH)
						&& !files[i].getAbsolutePath()
								.contains(Constant.ISPATH)) {

					if (files[i].isDirectory()) {
						delFolder(files[i].getAbsolutePath());
					} else {
						files[i].delete();
					}

				}
			}
		}

	}

	/**
	 * 从assets读取文件中的String
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static String getStringFromAssets(String path, Context context)
			throws IOException {
		AssetManager assetManager = context.getAssets();
		InputStream inputStream = assetManager.open(path);
		return inputStream2String(inputStream);

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
	 * 读取String
	 */
	public static String getString(String path) {
		try {

			File f = new File(path);
			InputStream is = null;
			String ret = null;
			try {
				is = new FileInputStream(f);
				long contentLength = f.length();
				byte[] ba = new byte[(int) contentLength];
				is.read(ba);
				ret = new String(ba);
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (Exception e) {
					}
				}
			}
			return ret;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;
	}

	/**
	 * 保存String
	 * 
	 * @param fileName
	 * @param text
	 */
	public static void saveString(String fileName, String text) {
		try {

			File tofile = new File(fileName);

			FileWriter fw = new FileWriter(tofile);
			BufferedWriter buffw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(buffw);

			pw.println(text);

			pw.close();
			buffw.close();
			fw.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * save inputstream if it is not exist
	 * 
	 * @param inputStream
	 * @param path
	 */
	public static void saveIS(InputStream inputStream, String path) {
		if (existFile(path)) {
			return;
		}
		String folderpath = path.substring(0, path.lastIndexOf("/"));
		FileUtil.createFolder(folderpath);

		try {

			FileOutputStream fos = new FileOutputStream(path);
			int data = inputStream.read();
			while (data != -1) {
				fos.write(data);
				data = inputStream.read();
			}
			fos.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * get inputstream if it the specific file is exist.
	 * 
	 * @param inputStream
	 * @param path
	 */
	public static InputStream getIS(String path) {

		try {
			InputStream in = new FileInputStream(new File(path));
			return in;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 读取asset/image文件夹下的图片文件
	 */
	public static void getImageFromAsset(String assetPath, Context context) {
		AssetManager assetManager = context.getAssets();
		try {
			String[] fileNames = assetManager.list(assetPath);
			for (String fileName : fileNames) {
				System.out.println("fileName:" + fileName);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 读取SD卡上的图片名称
	 * 
	 * @param path
	 *            sd卡上文件夹的路径
	 * @return ArrayList<String>
	 */
	public static ArrayList<String> getImageNamesFromSDCard(String path) {
		// 先创建文件夹
		createFolder(path);
		File file = new File(path);
		String[] subFileNames = file.list();
		if (subFileNames.length == 0) {
			return null;
		}
		ArrayList<String> imageFileNames = new ArrayList<String>();
		for (String subFileName : subFileNames) {
			String[] strings = subFileName.split("\\.");
			String hz = strings[strings.length - 1];
			if (hz.equals("jpg")) {
				imageFileNames.add(path + subFileName);
			} else if (hz.equals("png")) {
				imageFileNames.add(path + subFileName);
			}
		}
		return imageFileNames;
	}

	/**
	 * 复制asset文件夹下的文件到sd卡上
	 * 
	 * @param context
	 *            上下文
	 * @param assetDir
	 *            asset指定文件夹的路径
	 * @param dir
	 *            sd卡指定文件夹的路径
	 */
	public static void copyAssets(Context context, String assetDir, String dir) {
		String[] files;
		try {
			files = context.getResources().getAssets().list(assetDir);
		} catch (IOException e1) {
			return;
		}
		File mWorkingPath = new File(dir);
		// if this directory does not exists, make one.
		if (!mWorkingPath.exists()) {
			if (!mWorkingPath.mkdirs()) {

			}
		}
		for (int i = 0; i < files.length; i++) {
			try {
				String fileName = files[i];
				// we make sure file name not contains '.' to be a folder.
				if (!fileName.contains(".")) {
					if (0 == assetDir.length()) {
						// 这里使用的递归
						copyAssets(context, fileName, dir + fileName + "/");
					} else {
						copyAssets(context, assetDir + "/" + fileName, dir
								+ fileName + "/");
					}
					continue;
				}
				File outFile = new File(mWorkingPath, fileName);
				if (outFile.exists()) {
					continue;
				}
				InputStream in = null;
				if (0 != assetDir.length()) {
					in = context.getAssets().open(assetDir + "/" + fileName);
				} else {
					in = context.getAssets().open(fileName);
				}
				OutputStream out = new FileOutputStream(outFile);

				// Transfer bytes from in to out
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean existFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			return true;
		} else {
			return false;
		}

	}

	public static void createFolder(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}

	}

	/**
	 * 删除文件夹
	 * 
	 * @param folderPath
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹

		} catch (Exception e) {
			System.out.println("删除文件夹操作出错");
			e.printStackTrace();

		}
	}

	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
			}
		}
	}

}
