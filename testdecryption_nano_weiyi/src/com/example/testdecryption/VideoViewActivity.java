package com.example.testdecryption;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoViewActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_videoview);
		VideoView videoView = (VideoView) findViewById(R.id.VideoView01);
		String url = getIntent().getStringExtra("url");

		videoView.setVideoURI(Uri.parse(url));
		videoView
				.setMediaController(new MediaController(VideoViewActivity.this));

		videoView.start();

	}
}