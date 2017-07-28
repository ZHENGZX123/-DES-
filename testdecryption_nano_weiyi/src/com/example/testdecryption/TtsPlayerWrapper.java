package com.example.testdecryption;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.content.Context;
import android.util.Log;

public class TtsPlayerWrapper {
	public static final int TTS_PLAYER_IDLE = 1;
	public static final int TTS_PLAYER_NOT_INIT = 0;
	public static final int TTS_PLAYER_PAUSE = 4;
	public static final int TTS_PLAYER_PLAYING = 3;
	public static final int TTS_PLAYER_STOPPING = 5;
	public static final int TTS_PLAYER_SYNTHESIZING = 2;
	public static final int jtTTS_BACKAUDIO_NOTREPEAT = 1;
	public static final int jtTTS_BACKAUDIO_REPEAT = 0;
	public static final int jtTTS_CODEPAGE_ASCII = 437;
	public static final int jtTTS_CODEPAGE_BIG5 = 950;
	public static final int jtTTS_CODEPAGE_GB18030 = 936;
	public static final int jtTTS_CODEPAGE_GB2312 = 936;
	public static final int jtTTS_CODEPAGE_GBK = 936;
	public static final int jtTTS_CODEPAGE_UNICODE = 1200;
	public static final int jtTTS_CODEPAGE_UNICODE_BE = 1201;
	public static final int jtTTS_CODEPAGE_UTF16 = 1200;
	public static final int jtTTS_CODEPAGE_UTF16_BE = 1201;
	public static final int jtTTS_CODEPAGE_UTF16_LE = 1200;
	public static final int jtTTS_CODEPAGE_UTF7 = 65000;
	public static final int jtTTS_CODEPAGE_UTF8 = 65001;
	public static final int jtTTS_DIGIT_NUMBER_AUTO = 0;
	public static final int jtTTS_DIGIT_NUMBER_ONLY = 3;
	public static final int jtTTS_DIGIT_TELEGRAM_AUTO = 1;
	public static final int jtTTS_DIGIT_TELEGRAM_ONLY = 2;
	public static final int jtTTS_ENG_AUTO = 0;
	public static final int jtTTS_ENG_ENGLISH = 2;
	public static final int jtTTS_ENG_LETTER = 1;
	public static final int jtTTS_ERR_ALREADY_INIT = 12;
	public static final int jtTTS_ERR_DONOTHING = 13;
	public static final int jtTTS_ERR_ENGINE = 14;
	public static final int jtTTS_ERR_ENGINE_BUSY = 10;
	public static final int jtTTS_ERR_INPUT_MODE = 9;
	public static final int jtTTS_ERR_INPUT_PARAM = 3;
	public static final int jtTTS_ERR_INVALID_SESSION = 11;
	public static final int jtTTS_ERR_LICENCE = 2;
	public static final int jtTTS_ERR_MEMORY = 15;
	public static final int jtTTS_ERR_MORE_TEXT = 8;
	public static final int jtTTS_ERR_MUSIC_TEMPLATE = 17;
	public static final int jtTTS_ERR_NONE = 0;
	public static final int jtTTS_ERR_NOT_INIT = 5;
	public static final int jtTTS_ERR_NO_INPUT = 7;
	public static final int jtTTS_ERR_OPEN_DATA = 6;
	public static final int jtTTS_ERR_OPEN_FILE = 16;
	public static final int jtTTS_ERR_TIME_EXPIRED = 1;
	public static final int jtTTS_ERR_TOO_MORE_TEXT = 4;
	public static final int jtTTS_FORMAT_ALAW_8K = 13;
	public static final int jtTTS_FORMAT_PCM_11K16B = 6;
	public static final int jtTTS_FORMAT_PCM_11K8B = 5;
	public static final int jtTTS_FORMAT_PCM_16K16B = 4;
	public static final int jtTTS_FORMAT_PCM_16K8B = 3;
	public static final int jtTTS_FORMAT_PCM_22K16B = 8;
	public static final int jtTTS_FORMAT_PCM_22K8B = 7;
	public static final int jtTTS_FORMAT_PCM_44K16B = 10;
	public static final int jtTTS_FORMAT_PCM_44K8B = 9;
	public static final int jtTTS_FORMAT_PCM_8K16B = 2;
	public static final int jtTTS_FORMAT_PCM_8K8B = 1;
	public static final int jtTTS_FORMAT_PCM_NORMAL = 0;
	public static final int jtTTS_FORMAT_VOX_6K = 11;
	public static final int jtTTS_FORMAT_VOX_8K = 12;
	public static final int jtTTS_FORMAT_uLAW_8K = 14;
	public static final int jtTTS_INPUT_TEXT_CALLBACK = 1;
	public static final int jtTTS_INPUT_TEXT_DIRECT = 0;
	public static final int jtTTS_INPUT_TEXT_SIZE = 1024;
	public static final int jtTTS_OUTPUT_DATA_SIZE = 4096;
	public static final int jtTTS_PARAM_BACKAUDIO_FILESIZE = 22;
	public static final int jtTTS_PARAM_BACKAUDIO_PATH = 19;
	public static final int jtTTS_PARAM_BACKAUDIO_REPEAT = 21;
	public static final int jtTTS_PARAM_BACKAUDIO_VOLUME = 20;
	public static final int jtTTS_PARAM_CALLBACK_USERDATA = 17;
	public static final int jtTTS_PARAM_CODEPAGE = 3;
	public static final int jtTTS_PARAM_DIGIT_MODE = 4;
	public static final int jtTTS_PARAM_ENG_MODE = 8;
	public static final int jtTTS_PARAM_INPUTTXT_MODE = 9;
	public static final int jtTTS_PARAM_LANGUAGE = 24;
	public static final int jtTTS_PARAM_OUTPUT_SIZE = 10;
	public static final int jtTTS_PARAM_PITCH = 2;
	public static final int jtTTS_PARAM_PUNC_MODE = 5;
	public static final int jtTTS_PARAM_SOUNDEFFECT = 23;
	public static final int jtTTS_PARAM_SPEAK_STYLE = 18;
	public static final int jtTTS_PARAM_SPEED = 1;
	public static final int jtTTS_PARAM_TAG_MODE = 6;
	public static final int jtTTS_PARAM_VOLUME = 0;
	public static final int jtTTS_PARAM_WAV_FORMAT = 7;
	public static final int jtTTS_PITCH_MAX = 32767;
	public static final int jtTTS_PITCH_MIN = -32768;
	public static final int jtTTS_PITCH_NORMAL = 0;
	public static final int jtTTS_PUNC_OFF = 0;
	public static final int jtTTS_PUNC_OFF_RTN = 2;
	public static final int jtTTS_PUNC_ON = 1;
	public static final int jtTTS_PUNC_ON_RTN = 3;
	public static final int jtTTS_SPEAK_STYLE_CLEAR = 0;
	public static final int jtTTS_SPEAK_STYLE_NORMAL = 1;
	public static final int jtTTS_SPEAK_STYLE_PLAIN = 2;
	public static final int jtTTS_SPEAK_STYLE_VIVID = 3;
	public static final int jtTTS_SPEED_MAX = 32767;
	public static final int jtTTS_SPEED_MIN = -32768;
	public static final int jtTTS_SPEED_NORMAL = 0;
	public static final int jtTTS_TAG_NONE = 0;
	public static final int jtTTS_TAG_S3ML = 1;
	public static final int jtTTS_VOLUME_MAX = 32767;
	public static final int jtTTS_VOLUME_MIN = -32768;
	public static final int jtTTS_VOLUME_NORMAL = 0;
	private final String CLASS_NAME = "com.sinovoice.sharedtts.SharedTTS";
	private final String METHOD_GET_STATUS = "getStatus";
	private final String METHOD_INIT = "init";
	private final String METHOD_INIT_TTSLIB = "initTTSLib";
	private final String METHOD_INSTANCE = "getInstance";
	private final String METHOD_PLAY_FILE = "playFile";
	private final String METHOD_PLAY_TEXT = "playText";
	private final String METHOD_SET_CONTEXT = "setContext";
	private final String METHOD_SET_PARAM = "ttsSetParam";
	private final String METHOD_TTS_END = "ttsEnd";
	private final String METHOD_TTS_PAUSE = "ttsPause";
	private final String METHOD_TTS_RESUME = "ttsResume";
	private final String METHOD_TTS_STOP = "ttsStop";
	private final String METHOD_VOICE_TYPE = "getVoiceType";
	private final String PACKAGE_NAME = "com.sinovoice.sharedtts";
	private final String TAG = "TTSPlayer Wrapper";
	private Class<?> mClassTTS;
	private Context mContext = null;
	private Object mObjectTTS;

	public TtsPlayerWrapper(Context context) {
		try {
			this.mContext = context.createPackageContext(
					"com.sinovoice.sharedtts", 3);
			this.mClassTTS = Class.forName("com.sinovoice.sharedtts.SharedTTS",
					true, this.mContext.getClassLoader());
			this.mClassTTS.getMethod("init", new Class[0]).invoke(null,
					new Object[0]);
			this.mObjectTTS = this.mClassTTS.getMethod("getInstance",
					new Class[0]).invoke(null, new Object[0]);
			Method method_setContext = this.mClassTTS.getMethod("setContext",
					new Class[] { Context.class });
			method_setContext.invoke(mObjectTTS, mContext);
		} catch (Exception localException) {
			Log.e("TTSPlayer Wrapper", "Construction Exception: "
					+ localException.toString());
		}
	}

	public int SetParam(int paramInt, long paramLong) {
		try {
			Class[] arrayOfClass = new Class[2];
			arrayOfClass[0] = Integer.TYPE;
			arrayOfClass[1] = Long.TYPE;
			Method method_ttsSetParam = mClassTTS.getMethod("ttsSetParam",
					arrayOfClass);
			Object[] arrayOfObject = new Object[2];
			arrayOfObject[0] = Integer.valueOf(paramInt);
			arrayOfObject[1] = Long.valueOf(paramLong);
			return ((Integer) method_ttsSetParam.invoke(mObjectTTS,
					arrayOfObject)).intValue();
		} catch (Exception localException) {
			Log.e("TTSPlayer Wrapper", "ttsSetParam Exception: "
					+ localException.toString());
		}

		return jtTTS_ERR_DONOTHING;
	}

	public int getStatus() {
		try {
			return ((Integer) this.mClassTTS.getMethod("getStatus",
					new Class[0]).invoke(this.mObjectTTS, new Object[0]))
					.intValue();
		} catch (Exception localException) {
			Log.e("TTSPlayer Wrapper",
					"getStatus Exception: " + localException.toString());
		}

		return 1;
	}

	public String[] getVoiceType() {
		String[] arrayOfString = (String[]) null;
		try {
			arrayOfString = (String[]) this.mClassTTS.getMethod("getVoiceType",
					new Class[0]).invoke(this.mObjectTTS, new Object[0]);
			return arrayOfString;
		} catch (Exception localException) {
			Log.e("TTSPlayer Wrapper", "getVoiceType Exception: "
					+ localException.toString());
		}
		return null;
	}

	public int initTTSLib(int paramInt) {
		try {
			Method method_setContext = this.mClassTTS.getMethod("setContext",
					new Class[] { Context.class });
			Object localObject1 = this.mObjectTTS;
			Object[] arrayOfObject1 = new Object[1];
			arrayOfObject1[0] = this.mContext;
			method_setContext.invoke(localObject1, arrayOfObject1);
			Class[] arrayOfClass = new Class[1];
			arrayOfClass[0] = Integer.TYPE;
			Method method_initTTSLib = mClassTTS.getMethod("initTTSLib",
					arrayOfClass);
			Object localObject2 = this.mObjectTTS;
			Object[] arrayOfObject2 = new Object[1];
			arrayOfObject2[0] = Integer.valueOf(paramInt);
			return ((Integer) method_initTTSLib.invoke(localObject2,
					arrayOfObject2)).intValue();
		} catch (Exception localException) {
			Log.e("TTSPlayer Wrapper", "initTTSLib Exception: "
					+ localException.toString());
		}

		return 5;
	}

	public int initTTSLib(int paramInt1, int paramInt2, int paramInt3) {
		int i = 5;
		try {
			Method method_setContext = this.mClassTTS.getMethod("setContext",
					new Class[] { Context.class });
			method_setContext.invoke(mObjectTTS, mContext);
			Class[] arrayOfClass = new Class[3];
			arrayOfClass[0] = Integer.TYPE;
			arrayOfClass[1] = Integer.TYPE;
			arrayOfClass[2] = Integer.TYPE;
			Method method_initTTSLib = mClassTTS.getMethod("initTTSLib",
					arrayOfClass);
			Object[] params = new Object[3];
			params[0] = Integer.valueOf(paramInt1);
			params[1] = Integer.valueOf(paramInt2);
			params[2] = Integer.valueOf(paramInt3);
			return ((Integer) method_initTTSLib.invoke(mObjectTTS, params))
					.intValue();
		} catch (Exception localException) {
			while (true)
				Log.e("TTSPlayer Wrapper", "initTTSLib Exception: "
						+ localException.toString());
		}
	}

	public void playFile(File paramFile, long paramLong) {
		try {
			Class[] arrayOfClass = new Class[2];
			arrayOfClass[0] = File.class;
			arrayOfClass[1] = Long.TYPE;
			Method method_playFile = mClassTTS.getMethod("playFile",
					arrayOfClass);
			Object[] arrayOfObject = new Object[2];
			arrayOfObject[0] = paramFile;
			arrayOfObject[1] = Long.valueOf(paramLong);
			method_playFile.invoke(mObjectTTS, arrayOfObject);
			return;
		} catch (Exception localException) {
			while (true)
				Log.e("TTSPlayer Wrapper", "playFile Exception: "
						+ localException.toString());
		}
	}

	public void playText(String text) {
		try {
			this.mClassTTS.getMethod("playText", new Class[] { String.class })
					.invoke(this.mObjectTTS, new Object[] { text });
		} catch (Exception localException) {
			Log.e("TTSPlayer Wrapper",
					"playText Exception: " + localException.toString());
		}
	}

	public int ttsEnd() {
		try {
			return ((Integer) this.mClassTTS.getMethod("ttsEnd", new Class[0])
					.invoke(this.mObjectTTS, new Object[0])).intValue();
		} catch (Exception localException) {
			Log.e("TTSPlayer Wrapper",
					"ttsEnd Exception: " + localException.toString());
		}
		return jtTTS_ERR_DONOTHING;
	}

	public boolean ttsInstalled() {
		return true;
	}

	public void ttsPause() {
		try {
			this.mClassTTS.getMethod("ttsPause", new Class[0]).invoke(
					this.mObjectTTS, new Object[0]);
			return;
		} catch (Exception localException) {
			Log.e("TTSPlayer Wrapper",
					"ttsPause Exception: " + localException.toString());
		}
	}

	public void ttsResume() {
		try {
			this.mClassTTS.getMethod("ttsResume", new Class[0]).invoke(
					this.mObjectTTS, new Object[0]);
			return;
		} catch (Exception localException) {
			Log.e("TTSPlayer Wrapper",
					"ttsResume Exception: " + localException.toString());
		}
	}

	public int ttsStop() {
		try {
			return ((Integer) this.mClassTTS.getMethod("ttsStop", new Class[0])
					.invoke(this.mObjectTTS, new Object[0])).intValue();
		} catch (Exception localException) {
			Log.e("TTSPlayer Wrapper",
					"ttsStop Exception: " + localException.toString());
		}

		return jtTTS_ERR_DONOTHING;
	}
}