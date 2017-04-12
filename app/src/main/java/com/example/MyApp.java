package com.example;

import musicplayer.MainActivity;

import com.example.mycloud.R;

import flashlight.ShanGuangDActivity;
import android.app.Application;
import android.content.Intent;
import android.os.Parcelable;

public class MyApp extends Application{

	@Override
	public void onCreate() {
		createDeskShortCut();
		super.onCreate();
	}

	/**
	 * 创建桌面快捷方式
	 */
	public void createDeskShortCut() {
		// music
		// 鍒涘缓蹇嵎鏂瑰紡鐨処ntent
		Intent shortcutIntent = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");
		// 涓嶅厑璁搁噸澶嶅垱寤�		
		shortcutIntent.putExtra("duplicate", false);
		// 闇�鐜板疄鐨勫悕绉�		
		shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "闊充箰");
		// 蹇嵎鍥剧墖
		Parcelable icon = Intent.ShortcutIconResource.fromContext(
				getApplicationContext(), R.drawable.audio);
		shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		// 涓嬮潰涓や釜灞炴�鏄负浜嗗綋搴旂敤绋嬪簭鍗歌浇鏃舵闈�涓婄殑蹇嵎鏂瑰紡浼氬垹闄�		
		intent.setAction("android.intent.action.MAIN");
		intent.addCategory("android.intent.category.LAUNCHER");
		// 鐐瑰嚮蹇嵎鍥剧墖锛岃繍琛岀殑绋嬪簭涓诲叆鍙�		
		shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
		// 鍙戦�骞挎挱銆侽K
		sendBroadcast(shortcutIntent);

		// video
		// 鍒涘缓蹇嵎鏂瑰紡鐨処ntent
		Intent video = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");
		// 涓嶅厑璁搁噸澶嶅垱寤�		
		video.putExtra("duplicate", false);
		// 闇�鐜板疄鐨勫悕绉�		
		video.putExtra(Intent.EXTRA_SHORTCUT_NAME, "瑙嗛");
		// 蹇嵎鍥剧墖
		Parcelable icon1 = Intent.ShortcutIconResource.fromContext(
				getApplicationContext(), R.drawable.ic_video);
		video.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon1);
		Intent intent1 = new Intent(getApplicationContext(),
				videoplayer.MainActivity.class);
		// 涓嬮潰涓や釜灞炴�鏄负浜嗗綋搴旂敤绋嬪簭鍗歌浇鏃舵闈�涓婄殑蹇嵎鏂瑰紡浼氬垹闄�		
		intent1.setAction("android.intent.action.MAIN");
		intent1.addCategory("android.intent.category.LAUNCHER");
		// 鐐瑰嚮蹇嵎鍥剧墖锛岃繍琛岀殑绋嬪簭涓诲叆鍙�		
		video.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent1);
		// 鍙戦�骞挎挱銆侽K
		sendBroadcast(video);

		// light
		// 鍒涘缓蹇嵎鏂瑰紡鐨処ntent
		Intent light = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");
		// 涓嶅厑璁搁噸澶嶅垱寤�		
		light.putExtra("duplicate", false);
		// 闇�鐜板疄鐨勫悕绉�		
		light.putExtra(Intent.EXTRA_SHORTCUT_NAME, "鎵嬬數绛");
		// 蹇嵎鍥剧墖
		Parcelable icon2 = Intent.ShortcutIconResource.fromContext(
				getApplicationContext(), R.drawable.ic_light);
		light.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon2);
		Intent intent2 = new Intent(getApplicationContext(),
				ShanGuangDActivity.class);
		// 涓嬮潰涓や釜灞炴�鏄负浜嗗綋搴旂敤绋嬪簭鍗歌浇鏃舵闈�涓婄殑蹇嵎鏂瑰紡浼氬垹闄�		
		intent2.setAction("android.intent.action.MAIN");
		intent2.addCategory("android.intent.category.LAUNCHER");
		// 鐐瑰嚮蹇嵎鍥剧墖锛岃繍琛岀殑绋嬪簭涓诲叆鍙�		
		light.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent2);
		// 鍙戦�骞挎挱銆侽K
		sendBroadcast(light);

		// recode
		// 鍒涘缓蹇嵎鏂瑰紡鐨処ntent
		Intent recorder = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");
		// 涓嶅厑璁搁噸澶嶅垱寤�		
		recorder.putExtra("duplicate", false);
		// 闇�鐜板疄鐨勫悕绉�		
		recorder.putExtra(Intent.EXTRA_SHORTCUT_NAME, "褰曢煶鏈");
		// 蹇嵎鍥剧墖
		Parcelable icon3 = Intent.ShortcutIconResource.fromContext(
				getApplicationContext(), R.drawable.ic_launcher_soundrecorder);
		recorder.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon3);
		Intent intent3 = new Intent(getApplicationContext(),
				recorder.mediarecorder.class);
		// 涓嬮潰涓や釜灞炴�鏄负浜嗗綋搴旂敤绋嬪簭鍗歌浇鏃舵闈�涓婄殑蹇嵎鏂瑰紡浼氬垹闄�		
		intent3.setAction("android.intent.action.MAIN");
		intent3.addCategory("android.intent.category.LAUNCHER");
		// 鐐瑰嚮蹇嵎鍥剧墖锛岃繍琛岀殑绋嬪簭涓诲叆鍙�		
		recorder.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent3);
		// 鍙戦�骞挎挱銆侽K
		sendBroadcast(recorder);
	}
}
