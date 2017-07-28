package com.example.testdecryption;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.Toast;

public class SWFPlayerActivity extends Activity {

	private WebView mWebview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_swf);

		String swf_url = getIntent().getStringExtra("SWF_URL");
		if (swf_url == null) {
			Toast.makeText(this, "url is null", Toast.LENGTH_SHORT).show();
			finish();
		} else {
			loadSWF(swf_url);
		}
	}

	private void loadSWF(String swf_url) {
		mWebview = (WebView) findViewById(R.id.webView1);
		mWebview.getSettings().setAllowFileAccess(true);
		// mWebview.getSettings().setPluginsEnabled(true);
		String data = "<html>"
				+ "<body marginwidth='0' marginheight='0' style='background: #000000;'>"
				+ "<embed width='100%' height='100%' name='plugin' "
				+ "src='__URL__' type='application/x-shockwave-flash'></body></html>";
		data = data.replace("__URL__", swf_url);
		mWebview.loadData(data, "text/html", "utf-8");

	}

}
