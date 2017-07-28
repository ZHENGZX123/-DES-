package com.example.testdecryption;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.CommentNode;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.HtmlNode;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.TagNodeVisitor;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnHoverListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class HtmlSpeaker extends Activity {
	public static final String TAG = "TestTextSpeaker2 ---------------------------";

	protected static String titles = null;
	protected static String exclude_titles = null;
	protected static String title = null;
	protected static String link;

	// private Button pauseSpeakBtn;

	boolean mIsReading;
	ImageView mReturnIv;

	private ImageView mPlayPauseIv;
	private ImageView mVolumeUpIv;
	private ImageView mVolumeDownIv;
	private ImageView mVoiceChildIv;
	private ImageView mVoiceManIv;
	private ImageView mVoiceWomenIv;

	ScrollView story_sv;

	private TtsPlayerWrapper mTTSPlayer;
	private Context mContext;
	private SharedPreferences mSharedData;
	private int mIntVoiceType = -1;
	private int VOICE_TYPE_DEFAULT = 2;

	private Handler uiHandler;

	//
	public Story story;
	public Paragraph mParagraph;
	int nParagraph = 0;
	int readNPara = 0;
	ArrayList<Paragraph> paragraphs = new ArrayList<Paragraph>();
	ArrayList<View> contentViews = new ArrayList<View>();

	private AudioManager mAudioManager = null;
	private int mVolumeMax;
	private int mVolumeDef;
	private int mVolumeCurr;

	private String mCurrDir = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_html);

		// 4.0上用这段代码放开网络访问限制
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
				.penaltyLog().penaltyDeath().build());

		loadPreference();

		/*
		 * Toast.makeText( mContext,
		 * "Download and install http://tianxing.com:8090/Content/Android/SharedTTS.apk"
		 * , Toast.LENGTH_LONG).show();
		 */

		registerBroadcast();
		//
		mTTSPlayer = new TtsPlayerWrapper(getApplicationContext());
		initView();
		init_ui_handler();
		tidyUpHtml();
		printAllGraph();
		addFileContentView();
		setVoiceType();

		startSpeakStory();
		updateScroll();

		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		mVolumeMax = mAudioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		mVolumeDef = mVolumeCurr = mAudioManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);
	}

	private void updateScroll() {
		story_sv = (ScrollView) findViewById(R.id.scrollview_story);
		story_sv.scrollTo(0, 0);
	}

	private void startSpeakStory() {
		String title = story.getTitle();
		if (null != title && title.length() > 0) {
			mTTSPlayer.playText(title);
			readNPara = -1;
		} else {
			mTTSPlayer.playText(story.getParagraphs().get(readNPara)
					.getContent());
			try {
				TextView v = (TextView) contentViews.get(readNPara);
				v.setTextColor(0x80c37e00);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		mIsReading = true;

		// View v = contentViews.get(readNPara);
		// v.setBackgroundColor(0x80494e8f);
	}

	private void printAllGraph() {
		Log.v(TAG, "printAllGraph(). ");

		ArrayList<Paragraph> pList = story.getParagraphs();
		int size = pList.size();
		Paragraph p = null;
		Log.v(TAG, "size = " + size);
		for (int i = 0; i < size; ++i) {
			p = pList.get(i);
			Log.v(TAG, "index =  " + i + ",  p = " + p.getContent());
		}
	}

	private void addFileContentView() {
		LinearLayout layout2 = (LinearLayout) findViewById(R.id.file_content);
		if (story.getTitle() != null) {
			LinearLayout ll = (LinearLayout) findViewById(R.id.title_ll);
			TextView titleTv = new TextView(mContext);
			LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			titleTv.setLayoutParams(mParams);
			titleTv.setText(story.getTitle());
			titleTv.setTextSize(40);

			ll.addView(titleTv);

		}
		if (story.getParagraphs() != null) {
			for (int i = 0; i < story.getParagraphs().size(); i++) {
				String pContent = story.getParagraphs().get(i).getContent();
				Log.v(TAG, "========> pContent = " + pContent);
				if (pContent.trim().length() < 3) {
					continue;
				}

				if (pContent.substring(0, 3).equals("img")) {
					String a[] = pContent.split("src=");
					Log.v(TAG, "================> image. pContent = "
							+ pContent);

					System.out.println(a[1]);

					ImageView iv = new ImageView(mContext);
					iv.setId(i);

					LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.WRAP_CONTENT,
							LinearLayout.LayoutParams.WRAP_CONTENT);
					mParams.gravity = Gravity.CENTER_HORIZONTAL;
					iv.setLayoutParams(mParams);
					iv.setImageURI(Uri.parse(mCurrDir + a[1]));

					/*
					 * final Bitmap bm = BitmapFactory.decodeFile(mCurrDir +
					 * a[1]); Log.v(TAG, "================> image. bm = " + bm +
					 * ", a[1] = " + a[1]); iv.setImageBitmap(bm);
					 */

					layout2.addView(iv);
					contentViews.add(iv);
				} else {
					TextView contentPTv = new TextView(mContext);
					contentPTv.setId(i);
					contentPTv.setTextSize(50);
					LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.MATCH_PARENT,
							LinearLayout.LayoutParams.WRAP_CONTENT);

					contentPTv.setLayoutParams(mParams);
					contentPTv.setText(story.getParagraphs().get(i)
							.getContent());

					// chenjian 2012-12-24 read text when clicked
					contentPTv.setClickable(true);
					contentPTv.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							handleTextViewClicked(v.getId());
						}
					});

					layout2.addView(contentPTv);
					contentViews.add(contentPTv);
				}

			}
		}
	}

	private void handleTextViewClicked(int viewId) {
		Log.v(TAG, "handleTextViewClicked(). viewId = " + viewId);

		if (null != mTTSPlayer) {

			Log.v(TAG, "mTTSPlayer = " + mTTSPlayer);

			readNPara = viewId - 1;
			mTTSPlayer.ttsStop(); // 将会引起 TTS complete
		}
	}

	private void tidyUpHtml() {
		TagNode tagNode = null;
		CleanerProperties props = new CleanerProperties();

		titles = new String();

		// set some properties to non-default values
		props.setTranslateSpecialEntities(true);
		props.setTransResCharsToNCR(true);
		props.setOmitComments(true);

		try {
			// 播放http开头的
			String fileStr = getIntent().getStringExtra("URL");
			if (fileStr.startsWith("http://")) {

				System.out.println("filestr = " + fileStr);

				URL url = new URL(fileStr);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setConnectTimeout(6000);
				InputStream is = conn.getInputStream();

				tagNode = new HtmlCleaner(props).clean(is);
				story = new Story();
			} else {
				mCurrDir = fileStr.substring(0,
						fileStr.lastIndexOf("index.html"));
				Log.v(TAG, "mCurrDir = " + mCurrDir);
				File file = new File(fileStr);
				tagNode = new HtmlCleaner(props).clean(file);
				story = new Story(new File("index.html"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		tagNode.traverse(new TagNodeVisitor() {
			public boolean visit(TagNode tagNode, HtmlNode htmlNode) {

				if (htmlNode instanceof TagNode) {
					TagNode tag = (TagNode) htmlNode;
					String tagName = tag.getName();
					if ("h1".equals(tagName)) {
						title = tag.getText().toString();
						System.out.println("标题：" + title);
						story.setTitle(title);
					}
					if ("p".equals(tagName)) {
						if (null != tag.getText().toString()
								&& tag.getText().toString().length() > 0
								&& !tag.getText().toString()
										.equals(story.getTitle())) {
							nParagraph++;
							// System.out.println(tag.getText().toString());
							Paragraph p = new Paragraph();
							p.setId(nParagraph);
							p.setContent(tag.getText().toString());
							p.setType(1);
							paragraphs.add(p);

						}

					}
					if ("img".equals(tagName)) {
						String src = tag.getAttributeByName("src");
						nParagraph++;
						Paragraph p = new Paragraph();
						p.setId(nParagraph);
						p.setContent("img src=" + src);
						p.setType(0);
						paragraphs.add(p);
						// System.out.println("img src=" + src);
					}
				} else if (htmlNode instanceof CommentNode) {
					CommentNode comment = ((CommentNode) htmlNode);
					comment.getContent().append(" -- By HtmlCleaner");
				}
				// tells visitor to continue traversing the DOM tree
				return true;
			}
		});
		story.setParagraphs(paragraphs);

	}

	private void initView() {
		/*
		 * pauseSpeakBtn = (Button) findViewById(R.id.btn_pause_speak);
		 * 
		 * pauseSpeakBtn.setOnClickListener(new OnClickListener() {
		 * 
		 * public void onClick(View v) { if (pauseSpeakBtn.getText().equals(
		 * getString(R.string.str_pause_speak))) {
		 * pauseSpeakBtn.setText(getString(R.string.str_play_speak));
		 * System.out.println(TAG + " PAUSE BTN CLICK     ");
		 * mTTSPlayer.ttsPause(); } else if (pauseSpeakBtn.getText().equals(
		 * getString(R.string.str_play_speak))) {
		 * pauseSpeakBtn.setText(getString(R.string.str_pause_speak));
		 * System.out.println(TAG + " PAUSE BTN CLICK     ");
		 * mTTSPlayer.ttsResume(); }
		 * 
		 * } });
		 */

		mReturnIv = (ImageView) findViewById(R.id.book_return_iv);
		setImageViewOnHover(mReturnIv);
		mReturnIv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				HtmlSpeaker.this.finish();
			}
		});

		// play and pause imageview
		mPlayPauseIv = (ImageView) findViewById(R.id.voice_play_pause);
		setImageViewOnHover(mPlayPauseIv);
		mPlayPauseIv.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (mIsReading) {
					System.out.println(TAG + " PAUSE BTN CLICK     ");
					mTTSPlayer.ttsPause();
					mIsReading = false;
					mPlayPauseIv.setImageResource(R.drawable.voice_play2);
				} else {
					System.out.println(TAG + " PAUSE BTN CLICK     ");
					mTTSPlayer.ttsResume();
					mIsReading = true;
					mPlayPauseIv.setImageResource(R.drawable.voice_pause2);
				}
			}
		});

		// volume up
		mVolumeUpIv = (ImageView) findViewById(R.id.volume_up);
		setImageViewOnHover(mVolumeUpIv);
		mVolumeUpIv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mVolumeCurr < mVolumeMax) {
					mVolumeCurr += 1;
					mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
							mVolumeCurr, 0);
				} else {
					Toast.makeText(getApplicationContext(), "已经达最高音量！",
							Toast.LENGTH_LONG).show();
				}
			}
		});

		// volume down
		mVolumeDownIv = (ImageView) findViewById(R.id.volume_down);
		setImageViewOnHover(mVolumeDownIv);
		mVolumeDownIv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mVolumeCurr > 0) {
					mVolumeCurr -= 1;
					mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
							mVolumeCurr, 0);
				} else {
					Toast.makeText(getApplicationContext(), "已经达最低音量！",
							Toast.LENGTH_LONG).show();
				}
			}
		});

		// voice type child
		mVoiceChildIv = (ImageView) findViewById(R.id.voice_type_child);
		setImageViewOnHover(mVoiceChildIv);
		mVoiceChildIv.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				handleVoiceTypeChange(2);
			}
		});

		// voice type man
		mVoiceManIv = (ImageView) findViewById(R.id.voice_type_man);
		setImageViewOnHover(mVoiceManIv);
		mVoiceManIv.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				handleVoiceTypeChange(0);
			}
		});

		// voice type child
		mVoiceWomenIv = (ImageView) findViewById(R.id.voice_type_women);
		setImageViewOnHover(mVoiceWomenIv);
		mVoiceWomenIv.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				handleVoiceTypeChange(1);
			}
		});
	}

	// TODO
	private void handleVoiceTypeChange(int type) {
		Log.d("TestTextSpeaker", "handleVoiceTypeChange() type = " + type);

		if (mIntVoiceType == type)
			return;
		else {
			// save voice type
			mIntVoiceType = type;
			SharedPreferences.Editor localEditor = this.mSharedData.edit();
			localEditor.putInt("VoiceType", mIntVoiceType);
			localEditor.commit();

			// change voice type
			// mTTSPlayer.initTTSLib(mIntVoiceType);

			changeVoiceTypeImageView(mIntVoiceType);
		}
	}

	private void changeVoiceTypeImageView(int type) {
		mVoiceWomenIv.setImageResource(R.drawable.voice_type_women3);
		mVoiceManIv.setImageResource(R.drawable.voice_type_man3);
		mVoiceChildIv.setImageResource(R.drawable.voice_type_child3);

		if (0 == mIntVoiceType) {
			mVoiceManIv.setImageResource(R.drawable.voice_type_man2);
		} else if (1 == mIntVoiceType) {
			mVoiceWomenIv.setImageResource(R.drawable.voice_type_women2);
		} else {
			mVoiceChildIv.setImageResource(R.drawable.voice_type_child2);
		}
	}

	private void init_ui_handler() {
		uiHandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0: // completed
				{
					mTTSPlayer.ttsStop();
					mTTSPlayer.initTTSLib(mIntVoiceType);

					mIsReading = false;

					// find next TextView
					readNPara++;
					while (readNPara < story.getParagraphs().size()
							&& 0 == story.getParagraphs().get(readNPara)
									.getType()) {
						readNPara++;
					}

					if (readNPara < story.getParagraphs().size()) {
						mTTSPlayer.playText(story.getParagraphs()
								.get(readNPara).getContent());
						mIsReading = true;
						int x = (int) contentViews.get(readNPara).getX();
						int y = (int) contentViews.get(readNPara).getY();
						// story_sv.scrollBy(x, y);
						story_sv.smoothScrollTo(x, y);

						// set background
						// View v = contentViews.get(readNPara);
						// v.setBackgroundColor(0x80494e8f);

						try {
							TextView v = (TextView) contentViews.get(readNPara);
							v.setTextColor(0x80c37e00);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						// 读完退出
						HtmlSpeaker.this.finish();
					}
				}
					break;
				case 1: {
					// to do (webview scroll to next page)
					// mTTSPlayer.
					// mWebView.loadUrl("");

				}
					break;
				}
			}
		};
	}

	private void loadPreference() {
		mContext = getApplicationContext();
		mSharedData = mContext.getSharedPreferences("VoiceBookSharedData", 0);
	}

	private void setVoiceType() {
		mIntVoiceType = this.mSharedData.getInt("VoiceType", -1);
		if (-1 == mIntVoiceType) {
			mIntVoiceType = VOICE_TYPE_DEFAULT;
			SharedPreferences.Editor localEditor = this.mSharedData.edit();
			localEditor.putInt("VoiceType", mIntVoiceType);
			localEditor.commit();
		}

		Log.e("TestTextSpeaker", "mIntVoiceType = " + this.mIntVoiceType);
		mTTSPlayer.ttsStop();
		mTTSPlayer.ttsEnd();
		mTTSPlayer.initTTSLib(mIntVoiceType); // 默认童声
		mIsReading = false;

		changeVoiceTypeImageView(mIntVoiceType);
	}

	private void setVoiceSpeed() {
		mTTSPlayer.SetParam(1, -3000L);
	}

	@Override
	protected void onStop() {
		super.onStop();

		// fix bug: 在暂停时，按返回键，程序崩溃
		mTTSPlayer.ttsResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mTTSPlayer.ttsStop();
		mTTSPlayer.ttsEnd();

		mIsReading = false;
		unregisterReceiver(mBroadcastReceiver);

		if (null != mAudioManager)
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
					mVolumeDef, 0);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if (mTTSPlayer != null) {
			mTTSPlayer.ttsStop();
			mIsReading = false;
		}
	}

	protected BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		public void onReceive(Context paramAnonymousContext, Intent intent) {
			String action = intent.getAction();
			if (action.equals("com.sinovoice.action.OnTTSCompleteBroadcast")) {
				uiHandler.sendEmptyMessage(0); // completed notification
			} else if (action
					.equals("com.sinovoice.action.TurnPageCompleteBroadcast")) {
				System.out.println(TAG
						+ "  receive the TurnPageCompleteBroadcast");
				uiHandler.sendEmptyMessage(1);// truen to next page
			}
		}
	};

	private void registerBroadcast() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter
				.addAction("com.sinovoice.action.OnOutputCallBackBroadcast");
		intentFilter.addAction("com.sinovoice.action.OnTTSCompleteBroadcast");
		intentFilter
				.addAction("com.sinovoice.action.TurnPageCompleteBroadcast");
		registerReceiver(mBroadcastReceiver, intentFilter);
	}

	private void setImageViewOnHover(final ImageView view) {
		view.setOnHoverListener(new OnHoverListener() {
			@Override
			public boolean onHover(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_HOVER_ENTER:
					view.setScaleX((float) (view.getScaleX() * 1.1));
					view.setScaleY((float) (view.getScaleY() * 1.1));
					break;
				case MotionEvent.ACTION_HOVER_MOVE:
					break;
				case MotionEvent.ACTION_HOVER_EXIT:
					view.setScaleX((float) (view.getScaleX() / 1.1));
					view.setScaleY((float) (view.getScaleY() / 1.1));
					break;
				default:
					break;
				}

				return false;
			}
		});
	}
}
