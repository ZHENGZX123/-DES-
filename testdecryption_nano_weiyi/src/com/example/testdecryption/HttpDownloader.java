package com.example.testdecryption;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;

public class HttpDownloader {

	private int FILESIZE = 4 * 1024;
	private int lastProgress = 0;

	public int downloadOneFile(String urlStr, String savedFilePath) {
		try {
			InputStream input = null;
			HttpURLConnection urlConn = null;
			URL url = new URL(urlStr);
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setRequestProperty("Accept-Encoding", "identity");
			urlConn.setReadTimeout(10000);
			urlConn.setConnectTimeout(10000);
			input = urlConn.getInputStream();
			int total = urlConn.getContentLength();
			// write file

			long remainSize = getSDcardRemainSize();

			// 预留1M的空间
			if (total + 1024 * 1024 > remainSize) {
				return -1;
			}
			File file = null;
			OutputStream output = null;
			int read = 0;

			file = new File(savedFilePath);
			output = new FileOutputStream(file);
			byte[] buffer = new byte[FILESIZE];
			int temp = 0;
			while ((temp = input.read(buffer)) != -1) {
				output.write(buffer, 0, temp);
				read += temp;
				float progress = ((float) read) / total;

				int progress_int = (int) (progress * 100);// * 95
				if (lastProgress != progress_int) {
					lastProgress = progress_int;

				} else {
					// do not send msg
				}
			}
			output.flush();
			output.close();
			input.close();
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}

	private long getSDcardRemainSize() {
		// 判断存储卡是否存在
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			// 取得SD卡文件路径
			File path = Environment.getExternalStorageDirectory();
			// StatFs看文件系统空间的使用情况
			StatFs statFs = new StatFs(path.getPath());
			// Block的size
			long blockSize = statFs.getBlockSize();
			// 总的Block数量
			long availableBlock = statFs.getAvailableBlocks();
			return availableBlock * blockSize;
		} else {
			return 0;
		}
	}

	// true成功 false失败
	public boolean downloadCover(String cover_url, String savedPaht) {
		try {
			URL url = null;
			URLConnection conn = null;
			InputStream in = null;
			Bitmap itemBitmap = null;
			url = new URL(cover_url);
			conn = url.openConnection();
			conn.setConnectTimeout(10000);
			conn.connect();
			in = conn.getInputStream();
			itemBitmap = BitmapFactory.decodeStream(in);
			File file = new File(savedPaht + "/" + "cover.png");
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			itemBitmap.compress(CompressFormat.PNG, 100, fos);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private boolean cancel;

	public boolean isCancel() {
		return cancel;
	}

	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}

	public void cancelDownloadContent() {
		setCancel(true);
	}

}