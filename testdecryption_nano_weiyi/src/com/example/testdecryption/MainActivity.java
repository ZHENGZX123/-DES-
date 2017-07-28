package com.example.testdecryption;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

@SuppressLint("SdCardPath")
public class MainActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		startHttpServer();

		Button flv = (Button) findViewById(R.id.flv);
		flv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playFlv();
			}
		});

		Button mp3 = (Button) findViewById(R.id.mp3);
		mp3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread() {
					@Override
					public void run() {
						playMP3();
					}
				}.start();
			}
		});

		Button swf3th = (Button) findViewById(R.id.swf3th);
		swf3th.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playSwf();
			}
		});

		Button html = (Button) findViewById(R.id.html);
		html.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playHtml();
			}
		});

		Button png = (Button) findViewById(R.id.png);
		png.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playpng();
			}
		});

	}

	@SuppressLint("NewApi")
	protected void playpng() {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
				.penaltyLog().penaltyDeath().build());
		try {
			ImageView iv = (ImageView) findViewById(R.id.iv);
			// 路径要写对，中文要urlencode
			String path;
			path = URLEncoder.encode("/mnt/sdcard/test/test.png.cn", "utf-8");
			String uriString = "http://localhost:8888/getfile?path=" + path;

			URL url = new URL(uriString);

			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();

			InputStream input = urlConn.getInputStream();

			Bitmap bm = BitmapFactory.decodeStream(input);
			iv.setImageBitmap(bm);

			input.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public byte[] getBytes(InputStream is) throws IOException {
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024]; // 用数据装
		int len = -1;
		while ((len = is.read(buffer)) != -1) {
			outstream.write(buffer, 0, len);
		}
		outstream.flush();
		outstream.close();
		return outstream.toByteArray();
	}

	private void playFlv() {
		// flv mp4
		String path;
		try {
			path = URLEncoder.encode("/mnt/sdcard/weiyi/test0.mp4.cn", "utf-8");
			final String uriString = "http://localhost:8888/getfile?path="
					+ path;

			// Intent i = new Intent();
			// i.setClass(MainActivity.this, MediaPlayerActivity.class);
			// i.putExtra("url", uriString);
			// startActivity(i);

			Intent i = new Intent();
			i.setClass(MainActivity.this, VideoViewActivity.class);
			i.putExtra("url", uriString);
			startActivity(i);

			// use vitamio
			// Intent i = new Intent();
			// i.setClassName("io.vov.vitamio.demo",
			// "io.vov.vitamio.demo.VideoViewDemo");
			// i.putExtra("url", uriString);
			// startActivity(i);

			// new Thread() {
			// public void run() {
			// String save = "/mnt/sdcard/test0_0.mp4";
			// new HttpDownloader().downloadOneFile(uriString, save);
			// System.out.println("下完了再播放");
			// Intent i = new Intent();
			// i.setClassName("io.vov.vitamio.demo",
			// "io.vov.vitamio.demo.VideoViewDemo");
			// i.putExtra("url", save);
			// startActivity(i);
			// };
			// }.start();

		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	private void playHtml() {
		String path;

		try {
			path = URLEncoder.encode("/mnt/sdcard/1/index.html.cn", "utf-8");
			String uriString = "http://localhost:8888/getfile?path=" + path;
			Intent mIntent = new Intent();
			mIntent = new Intent(this, HtmlSpeaker.class);
			mIntent.putExtra("URL", uriString);
			startActivity(mIntent);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void playMP3() {
		Log.d("test", "play mp3");
		String path;
		try {
			path = URLEncoder.encode("/mnt/sdcard/kidguide/01春天在哪里.mp3.cn",
					"utf-8");
			// path =
			// URLEncoder.encode("/mnt/sdcard/kidguide/七、迷你听书馆/听歌谣/01春天在哪里.mp3.cn","utf-8");
			String uriString = "http://localhost:8888/getfile?path=" + path;
			MediaPlayer mp = new MediaPlayer();
			try {
				mp.setDataSource(uriString);
				mp.prepare();
				mp.setOnPreparedListener(new OnPreparedListener() {

					@Override
					public void onPrepared(MediaPlayer mp) {
						System.out.println("mp3 prepare");
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
			mp.start();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
	}

	protected void playSwf() {
		// localhost:8888/getfile?path=index.html.cn
		Log.d("test", "play swf");
		String path;
		try {
			// path =
			// URLEncoder.encode("/mnt/sdcard/kidguide/二、魔幻手工坊/彩泥王国/02草莓.swf.cn","utf-8");
			path = URLEncoder.encode("/mnt/sdcard/test/test.swf.cn", "utf-8");
			String uriString = "http://localhost:8888/getfile?path=" + path;
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, SWFPlayerActivity.class);
			intent.putExtra("SWF_URL", uriString);
			startActivity(intent);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void startHttpServer() {
		try {
			new NanoHTTPD(8888, new File("."), this);
		} catch (IOException ioe) {
			System.err.println("Couldn't start server:\n" + ioe);
		}
		System.out.println("Listening on port 8888. Hit Enter to stop.\n");
	}

}
