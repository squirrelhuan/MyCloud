package com.example.laucher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



import com.example.mycloud.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

/**
 * 宸ュ叿绫�
 * 
 * @author luoyao E-mail:iluoyao@qq.com
 * @version 2013-4-26 涓婂崍11:34:54
 * @Description TODO
 */
/**
 * @author Administrator
 * 
 */
public class CommonUtil {
	private static final String CRYPT_KEY = "androidecp";
	public static HashMap<Integer, LauncherItem> launcherItems = loadAllLauncherItem();
	public static HashMap<String, String> phoneInfoMaps = null;
	private static final String PHONE_INFO_KEY_IMEI = "imei";
	private static final String PHONE_INFO_KEY_SIM = "sim";
	private static final String PHONE_INFO_KEY_PHONE_NUMBER = "phoneNumber";
	private static final String PHONE_INFO_KEY_PHONE_TYPE = "phoneType";
	private static final String PHONE_INFO_KEY_RESOLUTION = "resolution";
	private static final String PHONE_INFO_KEY_MAC_ADDR = "macAddr";

	public static HashMap<Integer, LauncherItem> loadAllLauncherItem() {
		HashMap<Integer, LauncherItem> result = new HashMap<Integer, LauncherItem>();

		/** 鏍煎紡鈥渋d,绫诲瀷,鑹插潡鍥炬爣,鑹插潡鏂囨湰,璺宠浆椤甸潰鈥� */

		result.put(1, new LauncherItem(1, -1, R.drawable.lm_index_icon6, 0,
				R.string.launcher_item_message, null));

		result.put(2, new LauncherItem(2, 0, R.drawable.lm_index_icon6, 0,
				R.string.launcher_item_user_center, null));

		result.put(3, new LauncherItem(3, 0, R.drawable.lm_index_icon6,
				R.drawable.lm_shape1, R.string.launcher_item_user_buy_record,
				null));

		result.put(4, new LauncherItem(4, -1, R.drawable.lm_index_icon6,
				R.drawable.lm_shape1, R.string.launcher_item_bet_sales, null));

		result.put(5, new LauncherItem(5, 0, R.drawable.lm_index_icon6,
				R.drawable.lm_shape1, R.string.launcher_item_user_recharge,
				null));

		result.put(6, new LauncherItem(6, 0, R.drawable.lm_index_icon6,
				R.drawable.lm_shape1, R.string.launcher_item_user_draw_money,
				null));

		result.put(7, new LauncherItem(7, 46, R.drawable.lm_index_icon6,
				R.drawable.lm_shape1, R.string.launcher_item_bet_lottery46,
				null));

		result.put(8, new LauncherItem(8, 11, R.drawable.lm_index_icon6,
				R.drawable.lm_shape1, R.string.launcher_item_bet_lottery11,
				null));

		result.put(9, new LauncherItem(9, 13, R.drawable.lm_index_icon6,
				R.drawable.lm_shape1, R.string.launcher_item_bet_lottery13,
				null));

		result.put(10, new LauncherItem(10, 12, R.drawable.lm_index_icon6,
				R.drawable.lm_shape1, R.string.launcher_item_bet_lottery12,
				null));

		result.put(11, new LauncherItem(11, 34, R.drawable.lm_index_icon6,
				R.drawable.lm_shape1, R.string.launcher_item_bet_lottery34,
				null));

		result.put(12, new LauncherItem(12, 30, R.drawable.lm_index_icon6,
				R.drawable.lm_shape1, R.string.launcher_item_bet_lottery30,
				null));

		result.put(13, new LauncherItem(13, 31, R.drawable.lm_index_icon6,
				R.drawable.lm_shape1, R.string.launcher_item_bet_lottery31,
				null));

		result.put(14, new LauncherItem(14, 14, R.drawable.lm_index_icon6,
				R.drawable.lm_shape1, R.string.launcher_item_bet_lottery14,
				null));

		result.put(15, new LauncherItem(15, 36, R.drawable.lm_index_icon6,
				R.drawable.lm_shape1, R.string.launcher_item_bet_lottery36,
				null));

		result.put(16, new LauncherItem(16, 89, R.drawable.lm_index_icon6,
				R.drawable.lm_shape1, R.string.launcher_item_bet_lottery89,
				null));

		result.put(17, new LauncherItem(17, -1, R.drawable.lm_index_icon6,
				R.drawable.lm_shape1, R.string.launcher_item_open_award, null));

		return result;
	}

	public static String getPhoneImei() {
		if (phoneInfoMaps != null)
			return phoneInfoMaps.get(PHONE_INFO_KEY_IMEI);
		return "";
	}

	public static String getPhoneSim() {
		if (phoneInfoMaps != null)
			return phoneInfoMaps.get(PHONE_INFO_KEY_SIM);
		return "";
	}

	public static String getPhoneNumber() {
		if (phoneInfoMaps != null)
			return phoneInfoMaps.get(PHONE_INFO_KEY_PHONE_NUMBER);
		return "";
	}

	public static String getPhoneType() {
		if (phoneInfoMaps != null)
			return phoneInfoMaps.get(PHONE_INFO_KEY_PHONE_TYPE);
		return "";
	}

	public static String getPhoneResolution() {
		if (phoneInfoMaps != null)
			return phoneInfoMaps.get(PHONE_INFO_KEY_RESOLUTION);
		return "";
	}

	public static String getPhoneMacAddress() {
		if (phoneInfoMaps != null)
			return phoneInfoMaps.get(PHONE_INFO_KEY_MAC_ADDR);
		return "";
	}

	/**
	 * 鑾峰彇鎵嬫満淇℃伅
	 */
	public static void getUserPhoneInfo(Activity activity) {
		phoneInfoMaps = new HashMap<String, String>();
		TelephonyManager tm = (TelephonyManager) activity
				.getSystemService(Context.TELEPHONY_SERVICE);
		// imei
		phoneInfoMaps.put(PHONE_INFO_KEY_IMEI, tm.getDeviceId());
		// sim
		phoneInfoMaps.put(PHONE_INFO_KEY_SIM, tm.getSimSerialNumber());
		// 鎵嬫満鍙风爜
		String phone = tm.getLine1Number();
		if (phone != null)
			phone = phone.replace("+86", "");
		else
			phone = "";
		phoneInfoMaps.put(PHONE_INFO_KEY_PHONE_NUMBER, phone);
		// 鎵嬫満鍨嬪彿
		phoneInfoMaps.put(PHONE_INFO_KEY_PHONE_TYPE, android.os.Build.MODEL);
		// 鎵嬫満鍒嗚鲸鐜�
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		phoneInfoMaps.put(PHONE_INFO_KEY_RESOLUTION, dm.widthPixels + "*"
				+ dm.heightPixels);
		// mac鍦板潃
		String macAddress = "";
		WifiManager wifiMgr = (WifiManager) activity
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
		if (null != info)
			macAddress = info.getMacAddress();
		phoneInfoMaps.put(PHONE_INFO_KEY_MAC_ADDR, macAddress);
	}

	/**
	 * 淇濆瓨娲诲姩鏈娲诲姩鍏憡id鍜屾槸鍚﹀凡璇荤姸鎬佸�糹d@1#id@0 1琛ㄧず宸茶
	 * 
	 * @param context
	 * @param noticeIds
	 */
	public static void saveActiveNotice(Context context, String noticeIds) {
		SharedPreferences spCustom = context.getSharedPreferences(
				"activitNotice", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = spCustom.edit();
		editor.putString("noticeIds", noticeIds);
		editor.commit();
	}

	public static String readActiveNotice(Context context) {
		SharedPreferences spIds = context.getSharedPreferences("activitNotice",
				Context.MODE_PRIVATE);
		return spIds.getString("noticeIds", null);
	}

	public static final String DEFAULT_DINGYUE_ORDER = "1,2,3,5,7,8,16,13,9,10,11,12,15,14";// 榛樿妗岄潰鍥炬爣浣嶇疆

	/**
	 * 淇濆瓨妗岄潰鍥炬爣浣嶇疆
	 */
	public static void saveLauncherSharedPreferences(Context context,
			String custom) {
		SharedPreferences spCustom = context.getSharedPreferences("launcher",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = spCustom.edit();
		editor.putString("dingyue", custom);
		editor.commit();
	}

	/**
	 * 璇诲彇妗岄潰鍥炬爣浣嶇疆
	 */
	public static String readLauncherSharedPreferences(Context context) {
		SharedPreferences spCustom = context.getSharedPreferences("launcher",
				Context.MODE_PRIVATE);
		return spCustom.getString("dingyue", null);
	}

	/**
	 * 淇濆瓨鑳屾櫙鍥剧墖淇℃伅
	 * 
	 * @param context
	 * @param bgName
	 */
	public static void writeBackgroundSharedPreferences(Context context,
			String bgName) {
		SharedPreferences spBackground = context.getSharedPreferences(
				"bgBitmap", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = spBackground.edit();
		editor.putString("bg", bgName);
		editor.commit();
	}

	/**
	 * 璇诲彇鑳屾櫙鍥剧墖淇℃伅
	 * 
	 * @param context
	 * @return
	 */
	public static String readBackgroundSharedPreferences(Context context) {
		SharedPreferences spBackground = context.getSharedPreferences(
				"bgBitmap", Context.MODE_PRIVATE);
		return spBackground.getString("bg", null);
	}

	/**
	 * 淇濆瓨鐢ㄦ埛鐧诲綍淇℃伅
	 */
	public static void writeUserLoginSharedPreferences(Context context,
			String userName, String password) {
		SharedPreferences spUserLogin = context.getSharedPreferences(
				"userLogin", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = spUserLogin.edit();
		editor.putString("userName", userName);
		if (password != null) {
			editor.putString("password", setEncrypt(password));
		}
		editor.commit();
	}

	/**
	 * 璇诲彇鐢ㄦ埛鐧诲綍淇℃伅
	 */
	public static String[] readUserLoginSharedPreferences(Context context) {
		String[] strs = new String[2];
		SharedPreferences spUserLogin = context.getSharedPreferences(
				"userLogin", Context.MODE_PRIVATE);
		strs[0] = spUserLogin.getString("userName", "");
		String pwd = spUserLogin.getString("password", "");
		if (pwd != "")
			pwd = getEncrypt(pwd);
		strs[1] = pwd;
		return strs;
	}

	public final static String PREFS_FILE = "push.dat";
	public final static String PREF_KEY_REMAIND_SWITCH = "pn_remaind";
	public final static String PREF_KEY_OPEN_NUMBER_SWITCH = "pn_open_number";
	public final static String PREF_KEY_SSQ_SWITCH = "pn_ssq";
	public final static String PREF_KEY_DLT_SWITCH = "pn_dlt";
	public final static String PREF_KEY_FC3D_SWITCH = "pn_fc3d";

	/**
	 * 鍒濆鍖栨帹閫佸姛鑳�
	 */
	public static void initPN(Context context) {
		saveRemaindPN(context, "true");
		saveSSQPN(context, "true");
		saveDLTPN(context, "true");
		save3DPN(context, "true");
	}

	/**
	 * 妫�鏌ユ帹閫佸姛鑳芥槸鍚︽甯�
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkPNStatus(Context context) {
		if (readRemaindPN(context) == null
				|| readRemaindPN(context).length() == 0)
			return false;
		if (readSSQPN(context) == null || readSSQPN(context).length() == 0)
			return false;
		if (readDLTPN(context) == null || readDLTPN(context).length() == 0)
			return false;
		if (read3DPN(context) == null || read3DPN(context).length() == 0)
			return false;
		return true;
	}

	/**
	 * 淇濆瓨璐僵鎻愰啋鎺ㄩ�佸紑鍏�
	 */
	public static void saveRemaindPN(Context context, String value) {
		CommonUtil.writeSharePreferences(context, PREF_KEY_REMAIND_SWITCH,
				value);
	}

	/**
	 * 鑾峰彇璐僵鎻愰啋鎺ㄩ�佸紑鍏�
	 */
	public static String readRemaindPN(Context context) {
		return CommonUtil.readSharedPreferences(context,
				PREF_KEY_REMAIND_SWITCH, "true");
	}

	/**
	 * 淇濆瓨鍙岃壊鐞冩帹閫佸紑鍏�
	 */
	public static void saveSSQPN(Context context, String value) {
		CommonUtil.writeSharePreferences(context, PREF_KEY_SSQ_SWITCH, value);
	}

	/**
	 * 鑾峰彇鍙岃壊鐞冩帹閫佸紑鍏�
	 */
	public static String readSSQPN(Context context) {
		return CommonUtil.readSharedPreferences(context, PREF_KEY_SSQ_SWITCH,
				"true");
	}

	/**
	 * 淇濆瓨澶т箰閫忔帹閫佸紑鍏�
	 */
	public static void saveDLTPN(Context context, String value) {
		CommonUtil.writeSharePreferences(context, PREF_KEY_DLT_SWITCH, value);
	}

	/**
	 * 鑾峰彇鍙岃壊鐞冩帹閫佸紑鍏�
	 */
	public static String readDLTPN(Context context) {
		return CommonUtil.readSharedPreferences(context, PREF_KEY_DLT_SWITCH,
				"true");
	}

	/**
	 * 淇濆瓨3D鎺ㄩ�佸紑鍏�
	 */
	public static void save3DPN(Context context, String value) {
		CommonUtil.writeSharePreferences(context, PREF_KEY_FC3D_SWITCH, value);
	}

	/**
	 * 鑾峰彇3D鎺ㄩ�佸紑鍏�
	 */
	public static String read3DPN(Context context) {
		return CommonUtil.readSharedPreferences(context, PREF_KEY_FC3D_SWITCH,
				"true");
	}

	/**
	 * 淇濆瓨璐﹀彿淇濇姢寮�鍏�
	 */
	public static void saveTencentWeiboToken(Context context, String value) {
		CommonUtil.writeSharePreferences(context, "tencentWeiboToken", value);
	}

	/**
	 * 鑾峰彇璐﹀彿淇濇姢寮�鍏�
	 */
	public static String readTencentWeiboToken(Context context) {
		return CommonUtil.readSharedPreferences(context, "tencentWeiboToken",
				"");
	}

	/**
	 * 淇濆瓨璐﹀彿淇濇姢寮�鍏�
	 */
	public static void saveZhanghao(Context context, String value) {
		CommonUtil.writeSharePreferences(context, "zhanghao", value);
	}

	/**
	 * 鑾峰彇璐﹀彿淇濇姢寮�鍏�
	 */
	public static String readZhanghao(Context context) {
		return CommonUtil.readSharedPreferences(context, "zhanghao", "false");
	}

	/**
	 * 淇濆瓨婵�娲�
	 */
	public static void saveActive(Context context, String value) {
		CommonUtil.writeSharePreferences(context, "active", value);
	}

	/**
	 * 鑾峰彇婵�娲�
	 */
	public static String readActive(Context context) {
		return CommonUtil.readSharedPreferences(context, "active", "false");
	}

	/**
	 * 璇诲彇鍏变韩
	 */
	public static String readSharedPreferences(Context context, String key,
			String defValue) {
		SharedPreferences sh = context.getSharedPreferences(PREFS_FILE, 0);
		return sh.getString(key, defValue);
	}

	/**
	 * 鍐欏叆鍏变韩
	 */
	public static void writeSharePreferences(Context context, String key,
			String value) {
		SharedPreferences spUserLogin = context.getSharedPreferences(
				PREFS_FILE, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = spUserLogin.edit();
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * 璇诲彇鍥剧墖
	 * 
	 * @param context
	 * @param bgName
	 * @return
	 */
	public static Bitmap getBitmapFromSDCard(Context context, String bgName) {
		File saveDir = context.getFilesDir();
		File imageFile = new File(saveDir, bgName);
		Bitmap bitmap = null;
		if (imageFile.exists()) {
			try {
				bitmap = BitmapFactory.decodeStream(new FileInputStream(
						imageFile));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return bitmap;
	}

	/**
	 * 鍔犺浇asset鏂囨湰
	 * 
	 * @param context
	 * @param path
	 * @return
	 */
	public static String loadAssetText(Context context, String path) {
		AssetManager am = context.getAssets();
		StringBuffer sb = new StringBuffer();
		try {
			InputStream is = am.open(path);
			InputStreamReader reader = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(reader);
			String str;
			while ((str = br.readLine()) != null) {
				sb.append(str + "\n");
			}
		} catch (IOException e) {

			sb.setLength(0);
		}
		return sb.toString();
	}

	/**
	 * 灏哾ip杞崲涓簆x
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 灏唒x杞崲涓篸ip
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 浣跨敤寮傛垨杩涜绠�鍗曠殑瀵嗙爜鍔犲瘑
	 */
	private static String setEncrypt(String str) {
		int[] snNum = new int[str.length()];
		String result = "";
		String temp = "";

		for (int i = 0, j = 0; i < str.length(); i++, j++) {
			if (j == CRYPT_KEY.length())
				j = 0;
			snNum[i] = str.charAt(i) ^ CRYPT_KEY.charAt(j);
		}

		for (int k = 0; k < str.length(); k++) {

			if (snNum[k] < 10) {
				temp = "00" + snNum[k];
			} else {
				if (snNum[k] < 100) {
					temp = "0" + snNum[k];
				}
			}
			result += temp;
		}
		return result;
	}

	/**
	 * 瀵嗙爜瑙ｅ瘑
	 */
	private static String getEncrypt(String str) {
		char[] snNum = new char[str.length() / 3];
		String result = "";

		for (int i = 0, j = 0; i < str.length() / 3; i++, j++) {
			if (j == CRYPT_KEY.length())
				j = 0;
			int n = Integer.parseInt(str.substring(i * 3, i * 3 + 3));
			snNum[i] = (char) ((char) n ^ CRYPT_KEY.charAt(j));
		}

		for (int k = 0; k < str.length() / 3; k++) {
			result += snNum[k];
		}
		return result;
	}

	/**
	 * 鍚堝苟鏁扮粍
	 * 
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	public static String[] concat(String[] arr1, String[] arr2) {
		String[] ret = new String[arr1.length + arr2.length];
		System.arraycopy(arr1, 0, ret, 0, arr1.length);
		System.arraycopy(arr2, 0, ret, arr1.length, arr2.length);
		return ret;
	}

	/**
	 * 鎻愬彇鏁存暟
	 * 
	 * @param value
	 * @return
	 */
	public static int getIntFromString(String value) {
		if (value == null)
			return 0;

		Pattern pattern = Pattern.compile("^[-+]?\\d{1,9}$");
		Matcher matcher = pattern.matcher(value);
		if (matcher.find())
			return Integer.parseInt(matcher.group());

		return 0;
	}

	/**
	 * 鍏ㄨ杞崐瑙�
	 * 
	 * @param input
	 *            String.
	 * @return 鍗婅瀛楃涓�
	 */
	public static String toDBC(String input) {
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '\u3000') {
				c[i] = ' ';
			} else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
				c[i] = (char) (c[i] - 65248);
			}
		}
		String returnString = new String(c);
		return returnString;
	}

	/**
	 * 灏嗗瓧绗︿覆MD5鍔犲瘑锛�16浣嶏級
	 * 
	 * @param plainText
	 * @return
	 */
	public static String stringTo16Md5(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString().substring(8, 24);// 16浣嶇殑鍔犲瘑
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 灏嗗瓧绗︿覆MD5鍔犲瘑锛�32浣嶏級
	 * 
	 * @param plainText
	 * @return
	 */
	public static String stringTo32Md5(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();// 16浣嶇殑鍔犲瘑
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 閲戦鏍煎紡鍖�
	 * 
	 * @param s
	 *            閲戦
	 * @param len
	 *            灏忔暟浣嶆暟
	 * @return 鏍煎紡鍚庣殑閲戦
	 */
	public static String insertComma(String s, int len) {
		if (s == null || s.length() < 1) {
			return "";
		}

		NumberFormat formater = null;
		double num = Double.parseDouble(s);
		if (len == 0) {
			formater = new DecimalFormat("###,###");
		} else {
			StringBuffer buff = new StringBuffer();
			buff.append("###,###.");
			for (int i = 0; i < len; i++) {
				buff.append("#");
			}
			formater = new DecimalFormat(buff.toString());
		}
		return formater.format(num);
	}

	/**
	 * 璁＄畻瀛楃涓插瓧鑺傛暟,涓枃绠�2涓瓧鑺�
	 */
	public static int calChineseStringLength(String value) {
		if (value == null || value.length() == 0)
			return 0;

		int result = 0;
		char[] ary = value.toCharArray();
		for (char c : ary) {
			result++;

			if (c > 0xff)
				result++;
		}

		return result;
	}

	/**
	 * PingDomain.java
	 * 
	 * @author 鍒ゆ柇杈撳叆鐨勫煙鍚嶆槸鍚︽湁鏁�
	 */
	public static boolean isDomain(String addressArr) throws IOException {
		boolean flag = false;
		InetAddress address = InetAddress.getByName(addressArr);
		if (address.isReachable(30)) {
			flag = true;
		}
		return flag;
	}

}
