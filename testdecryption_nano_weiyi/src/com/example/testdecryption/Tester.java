package com.example.testdecryption;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;

@SuppressLint({ "SetJavaScriptEnabled", "SdCardPath" })
public class Tester extends Activity {

	// 这个页面是：使用webview来播放swf。

	WebView playView;
	RelativeLayout rl;

	@SuppressWarnings("deprecation")
	@SuppressLint({ "SdCardPath", "NewApi" })
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);

		Intent intent = getIntent();
		String swfURI = intent.getStringExtra("uri");
		rl = (RelativeLayout) findViewById(R.id.wrap);
		playView = (WebView) findViewById(R.id.web_flash);
		WebSettings settings = playView.getSettings();
		// settings.setPluginsEnabled(true);
		settings.setJavaScriptEnabled(true);
		settings.setAllowFileAccess(true);
		settings.setDefaultTextEncodingName("UTF-8");
		playView.setBackgroundColor(0);
		playView.loadUrl(swfURI);
	}

	// finish
	protected void onDestroy() {
		super.onDestroy();
		playView.loadData("", "text/html", "UTF-8");
	}

	public void buttonAction(View v) {

		this.finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// dialog();
			this.finish();
		} else if (keyCode == KeyEvent.KEYCODE_MENU) {
		} else {

		}
		return false;
	}
}
