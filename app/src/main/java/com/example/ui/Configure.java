package com.example.ui;

import com.example.laucher.CommonUtil;

import android.app.Activity;
import android.util.DisplayMetrics;

public class Configure {
	public static boolean isMove = false;
	public static boolean isChangingPage = false;
	public static boolean isDelDrak = false;
	public static boolean isDelVisible = false;
	public static ScrollLayout scrollLayout;//滑动的区域
	public static int screenHeight = 0;
	public static int screenWidth = 0;
	public static float screenDensity = 0;// 屏幕密度
	public static int delLX = 0;
	public static int delRX = 0;
	public static int delY = 0;
	public static int currentPage = 0;// 当前所处页数
	public static int countPages = 0;// 总共页数
	public static int removeItem = 0;

	public static void init(Activity context) {
		if (screenDensity == 0 && screenHeight == 0 && screenWidth == 0) {

			DisplayMetrics dm = new DisplayMetrics();
			// 获取设备的分辨率
			context.getWindowManager().getDefaultDisplay().getMetrics(dm);
			Configure.screenDensity = dm.density;
			Configure.screenHeight = dm.heightPixels;
			Configure.screenWidth = dm.widthPixels;
			delLX = Configure.screenWidth / 2 - CommonUtil.dip2px(context, 100);
			delRX = Configure.screenWidth / 2 + CommonUtil.dip2px(context, 100);
			delY = Configure.screenHeight - CommonUtil.dip2px(context, 200);

		}
		currentPage = 0;
		countPages = 0;
	}
//重新排序
	public int[] ret(int[] intArray) {
		int size = intArray.length;
		for (int i = size - 1; i >= 0; i--) {
			for (int j = 0; j < i; j++) {
				if (intArray[j] > intArray[j + 1]) {
					int t = intArray[j];
					intArray[j] = intArray[j + 1];
					intArray[j + 1] = t;
				}
			}
		}
		return intArray;
	}

}
