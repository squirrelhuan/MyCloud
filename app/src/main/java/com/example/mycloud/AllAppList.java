package com.example.mycloud;

import java.util.ArrayList;
import java.util.List;

import musicplayer.MainActivity;
import mysystembar.SystemBarTintManager;
import webview.Main_Browser;
import ccontacts.ContactListActivity;
import cells.Welcome;
import cphone.Phone_Activity;
import flashlight.ShanGuangDActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.Vibrator;
import android.support.v4.view.ViewPager;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Adapter;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * GridView鍒嗛〉鏄剧ず瀹夎鐨勫簲鐢ㄧ▼搴� * 
 * 
 * 
 */
public class AllAppList extends Activity implements OnClickListener,
		OnGestureListener, OnTouchListener, OnItemLongClickListener,
		OnItemClickListener {
	private static final String TAG = "ScrollLayoutTest";
	private ScrollLayout mScrollLayout;
	private static final float APP_PAGE_SIZE = 16.0f;
	private Context mContext;
	private PopupWindow pW_menu = null;
	private Notification cloudnotification;
	private NotificationManager cloudnotificationManager;
	GestureDetector detector;
	final int FLIP_DISTANCE = 40;
	private Vibrator mVibrator01;

	private LinearLayout bottom;
	private ViewPager viewPager;
	private ImageView imageView, ic_menu_add, ic_menu_wallpaper, ic_menu_theme,
			ic_menu_screen_mag, ic_menu_hide_app, ic_menu_desktop,
			ic_phone_launcher, ic_contacts_launcher, ic_sns_launcher,
			ic_web_launcher;
	private List<View> lists = new ArrayList<View>();
	private MyAdapter myAdapter;
	private Bitmap cursor;
	private int menu_i = 0, width, height;
	private int offSet;
	private int currentItem;
	private Matrix matrix = new Matrix();
	private int bmWidth;
	public DelayThread dThread = null;
	public static ImageView[] iamges = new ImageView[4];
	private Animation animation;
	private TextView textView1;
	private TextView textView2;
	private TextView textView3, desktime;
	public Adapter adapter;
	public Intent uninstallIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mContext = this;
		setContentView(R.layout.main_home);

		/*SharedPreferences preferences = getSharedPreferences("first",
				Context.MODE_PRIVATE);
		boolean isFirst = preferences.getBoolean("isfrist", true);
		if (isFirst) {
			createDeskShortCut();
			Intent welIntent = new Intent(AllAppList.this, Welcome.class);
			startActivity(welIntent);
		}
		createDeskShortCut();
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("isfrist", false);
		editor.commit();*/

		detector = new GestureDetector(this, this);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
		}

		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.tm);

		iamges[0] = (ImageView) findViewById(R.id.imageview1);
		iamges[1] = (ImageView) findViewById(R.id.imageview2);
		iamges[2] = (ImageView) findViewById(R.id.imageview3);
		iamges[3] = (ImageView) findViewById(R.id.imageview4);

		CloudNotification();
		width = getWindowManager().getDefaultDisplay().getWidth();
		height = getWindowManager().getDefaultDisplay().getHeight();

		bottom = (LinearLayout) findViewById(R.id.bottom);
		bottom.setOnTouchListener(this);
		ic_phone_launcher = (ImageView) findViewById(R.id.ic_phone_launcher);
		ic_phone_launcher.setOnClickListener(this);
		ic_contacts_launcher = (ImageView) findViewById(R.id.ic_contacts_launcher);
		ic_contacts_launcher.setOnClickListener(this);
		ic_sns_launcher = (ImageView) findViewById(R.id.ic_sns_launcher);
		ic_sns_launcher.setOnClickListener(this);
		ic_web_launcher = (ImageView) findViewById(R.id.ic_web_launcher);
		ic_web_launcher.setOnClickListener(this);
		desktime = (TextView) findViewById(R.id.desktime);

		dThread = new DelayThread(60000);
		dThread.start();
		/*
		 * DisplayMetrics dm = new DisplayMetrics(); dm =
		 * getResources().getDisplayMetrics(); width = dm.widthPixels;
		 */
	/*
		 * GridView gridview1 = (GridView) findViewById(R.id.gv_01);
		 * 鐢熸垚鍔ㄦ�鏁扮粍锛屽苟涓旇浆鍏ユ暟鎹�ArrayList<HashMap<String, Object>> ItemList = new
		 * ArrayList<HashMap<String, Object>>();
		 * 
		 * HashMap<String, Object> map_0 = new HashMap<String, Object>();
		 * map_0.put("Image", R.drawable.ic_phone_launcher); map_0.put("Tag",
		 * "鎷ㄥ彿");
		 * 
		 * ItemList.add(map_0);
		 * 
		 * HashMap<String, Object> map_1 = new HashMap<String, Object>();
		 * map_1.put("Image", R.drawable.ic_contacts_launcher); map_1.put("Tag",
		 * "鑱旂郴浜�);
		 * 
		 * ItemList.add(map_1); HashMap<String, Object> map_2 = new
		 * HashMap<String, Object>(); map_2.put("Image",
		 * R.drawable.ic_sns_launcher); map_2.put("Tag", "鐭俊");
		 * 
		 * ItemList.add(map_2); HashMap<String, Object> map_3 = new
		 * HashMap<String, Object>(); map_3.put("Image",
		 * R.drawable.ic_web_launcher); map_3.put("Tag", "娴忚鍣�);
		 * 
		 * ItemList.add(map_3); adapter = new
		 * SimpleAdapter(getApplicationContext(), ItemList, R.layout.app_item,
		 * new String[] { "Image", "Tag" }, new int[] { R.id.ivAppIcon,
		 * R.id.tvAppName }); gridview1.setAdapter((ListAdapter) adapter);
		 * gridview1.setOnItemClickListener(this);
		 */

		mScrollLayout = (ScrollLayout) findViewById(R.id.ScrollLayoutTest);
		initViews();

	}

	// 澶勭悊绾跨▼娑堟伅鐨凥andle
	private Handler mHandle = new Handler() {
		public void handleMessage(Message msg) {
			Time t = new Time(); // or Time t=new Time("GMT+8"); 鍔犱笂Time Zone璧勬枡銆�			t.setToNow(); // 鍙栧緱绯荤粺鏃堕棿銆�			int year = t.year;
			int month = t.month;
			int date = t.monthDay;
			int hour = t.hour; // 0-23
			int minute = t.minute;
			int second = t.second;
			desktime.setText(hour + ":" + minute);
		}
	};

	// 
	public class DelayThread extends Thread {
		int milliseconds;

		public DelayThread(int msec) {
			milliseconds = msec;
		}

		// 姣�00姣鏇存柊涓�杩涘害鏉�		
		public void run() {
			while (true) {
				try {
					sleep(milliseconds);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 鍙戦�娑堟伅鏇存柊杩涘害鏉�				mHandle.sendEmptyMessage(0);
			}
		}

	}

	private String formatTime(int t) {
		return "" + t / 60 + ":" + t % 60;// 涓夊厓杩愮畻绗�t>10鏃跺彇 ""+t
	}

	/**
	 * 鑾峰彇绯荤粺鎵�湁鐨勫簲鐢ㄧ▼搴忥紝骞舵牴鎹瓵PP_PAGE_SIZE鐢熸垚鐩稿簲鐨凣ridView椤甸潰
	 */
	public void initViews() {
		final PackageManager packageManager = getPackageManager();

		final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

		// get all apps
		final List<ResolveInfo> apps = packageManager.queryIntentActivities(
				mainIntent, 0);

		// the total pages
		final int PageCount = (int) Math.ceil(apps.size() / APP_PAGE_SIZE);
		Log.e(TAG, "size:" + apps.size() + " page:" + PageCount);
		for (int i = 0; i < PageCount; i++) {
			GridView appPage = new GridView(this);
			// get the "i" page data
			appPage.setAdapter(new AppAdapter(this, apps, i));

			appPage.setNumColumns(4);
			appPage.setVerticalSpacing(10);
			appPage.setHorizontalSpacing(10);
			// appPage.setColumnWidth(90);

			// appPage.setStretchMode(columnWidth);
			// appPage.setGravity();

			appPage.setOnItemClickListener(this);
			appPage.setOnItemLongClickListener(this);
			mScrollLayout.addView(appPage);
		}
	}

	/**
	 * gridView 鐨刼nItemLick鍝嶅簲浜嬩欢
	 */

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		android.os.Process.killProcess(android.os.Process.myPid());
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			return true;
		} else if (keyCode == KeyEvent.KEYCODE_MENU) {
			MenuView();
		}

		return super.onKeyDown(keyCode, event);
	}

	private void initeCursor() {
		cursor = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_launcher);
		bmWidth = cursor.getWidth();

		DisplayMetrics dm;
		dm = getResources().getDisplayMetrics();

		offSet = (dm.widthPixels - 3 * bmWidth) / 6;
		matrix.setTranslate(offSet, 0);
		// imageView.setImageMatrix(matrix); //闇�iamgeView鐨剆caleType涓簃atrix
		currentItem = 0;
	}

	public void MenuView() {
		LayoutInflater layout = (LayoutInflater) getApplicationContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layout.inflate(R.layout.launcher_menu, null);

		pW_menu = new PopupWindow(view, width, height / 11 * 5, true);
		// pW = new PopupWindow(view, view.getLayoutParams().WRAP_CONTENT,
		// view.getLayoutParams().WRAP_CONTENT, true);
		// 娴犮儰绗呮潻娆掝攽閸旂姳绗傞崢璇叉倵鐏忓崬褰叉禒銉ゅ▏閻⑺橝CK闁款喖鍙ч梻鐠揙PWINDOW
		// pW.setBackgroundDrawable(new ColorDrawable(0xb0000000));
		pW_menu.setBackgroundDrawable(new ColorDrawable(0x00000000));
		// pW.setWidth(300);
		// pW.setHeight(60);
		pW_menu.setOutsideTouchable(true);
		pW_menu.setAnimationStyle(R.style.FromBottomAnimation);// 娴犲骸褰告潻娑樺弳
		pW_menu.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
			}
		});

		imageView = (ImageView) view.findViewById(R.id.cursor);

		textView1 = (TextView) view.findViewById(R.id.textView1);
		textView2 = (TextView) view.findViewById(R.id.textView2);
		textView3 = (TextView) view.findViewById(R.id.textView3);
		textView1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				viewPager.setCurrentItem(0);
			}
		});

		textView2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				viewPager.setCurrentItem(1);
			}
		});

		textView3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				viewPager.setCurrentItem(2);
			}
		});

		lists.add(getLayoutInflater().inflate(R.layout.home_menu_0, null));
		lists.add(getLayoutInflater().inflate(R.layout.home_menu_1, null));
		lists.add(getLayoutInflater().inflate(R.layout.home_menu_2, null));
		initeCursor();
		myAdapter = new MyAdapter(lists);
		viewPager = (ViewPager) view.findViewById(R.id.viewPager);
		viewPager.setAdapter(myAdapter);
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) { // 褰撴粦鍔ㄥ紡锛岄《閮ㄧ殑imageView鏄�杩嘺nimation缂撴參鐨勬粦鍔�				// TODO Auto-generated method stub
				switch (arg0) {
				case 0:
					if (currentItem == 1) {
						animation = new TranslateAnimation(
								offSet * 2 + bmWidth, 0, 0, 0);
					} else if (currentItem == 2) {
						animation = new TranslateAnimation(offSet * 4 + 2
								* bmWidth, 0, 0, 0);
					}
					break;
				case 1:
					if (currentItem == 0) {
						animation = new TranslateAnimation(0, offSet * 2
								+ bmWidth, 0, 0);
					} else if (currentItem == 2) {
						animation = new TranslateAnimation(2 * offSet + 2
								* bmWidth, offSet * 2 + bmWidth, 0, 0);
					}
					break;
				case 2:
					if (currentItem == 0) {
						animation = new TranslateAnimation(0, 4 * offSet + 2
								* bmWidth, 0, 0);
					} else if (currentItem == 1) {
						animation = new TranslateAnimation(
								offSet * 2 + bmWidth, 4 * offSet + 2 * bmWidth,
								0, 0);
					}
				}
				currentItem = arg0;

				animation.setDuration(500);
				animation.setFillAfter(true);
				imageView.startAnimation(animation);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}
		});
		// pW.setAnimationStyle(android.R.style.Animation_Toast);
		// pW.setAnimationStyle(R.style.PopupAnimation);
		int[] location = new int[2];
		mScrollLayout.getLocationOnScreen(location);
		// pW_menu.showAsDropDown(anchor, 0, 0);
		pW_menu.showAtLocation(mScrollLayout, Gravity.BOTTOM, location[0], 0);
		pW_menu.update();
	}

	public void CloudNotification() {
		String service = NOTIFICATION_SERVICE;
		cloudnotificationManager = (NotificationManager) getSystemService(service);
		cloudnotification = new Notification();
		cloudnotification.icon = R.drawable.ic_launcher;
		cloudnotification.tickerText = "";
		cloudnotification.when = System.currentTimeMillis();
		Intent intent = new Intent(AllAppList.this, AllAppList.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(
				AllAppList.this, 0, intent, 0);
		//cloudnotification.setLatestEventInfo(AllAppList.this,"","",pendingIntent);
		//cloudnotificationManager.notify(1, cloudnotification);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.ic_phone_launcher:
			intent.setClass(AllAppList.this, Phone_Activity.class);
			break;
		case R.id.ic_web_launcher:
			intent.setClass(AllAppList.this, Main_Browser.class);
			break;
		case R.id.ic_contacts_launcher:
			intent.setClass(AllAppList.this, ContactListActivity.class);
			break;
		case R.id.ic_sns_launcher:
			intent.setClass(AllAppList.this, message.firstActivity.class);
			break;
		default:
		case R.id.iv_delItem:
			startActivity(uninstallIntent);
			break;
		}
		this.startActivity(intent);
	}

	public void onImageViewClick(View view) {
		switch (view.getId()) {
		case R.id.ic_menu_hide_app:
			if (menu_i / 2 == 0) {
				Intent intentdata = new Intent(AllAppList.this, FxService.class);
				startService(intentdata);
				Toast.makeText(AllAppList.this, "灞忓箷鍔╂墜宸插紑鍚紒", Toast.LENGTH_SHORT)
						.show();
				menu_i++;
			} else {
				Intent intentdata = new Intent(AllAppList.this, FxService.class);
				stopService(intentdata);
				Toast.makeText(AllAppList.this, "灞忓箷鍔╂墜宸插叧闂紒", Toast.LENGTH_SHORT)
						.show();
				menu_i--;
			}
			break;

		case R.id.ic_setting_more:
			Intent intent = new Intent();
			intent.setClass(AllAppList.this,
					com.example.fileexample.CloudFileManger.class);
			Bundle data = new Bundle();
			data.putString("re_name", "main_setting");
			intent.putExtras(data);
			this.startActivity(intent);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			break;
		case R.id.ic_menu_theme:
			Intent intent1 = new Intent();
			intent1.setClass(AllAppList.this,
					com.example.fileexample.CloudFileManger.class);
			Bundle data1 = new Bundle();
			data1.putString("re_name", "desktop_setting");
			intent1.putExtras(data1);
			this.startActivity(intent1);
			break;
		default:
			break;
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent me) {
		return detector.onTouchEvent(me);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub

		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub

		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub

		if (height - 90 <= e1.getY() && e1.getY() <= height) {
			if (e1.getY() - e2.getY() > FLIP_DISTANCE) {
				MenuView();
				if (e1.getX() <= width / 3) {
					viewPager.setCurrentItem(0);
				} else if (e1.getX() <= width / 3 * 2) {
					viewPager.setCurrentItem(1);
				} else {
					viewPager.setCurrentItem(2);
				}

				return true;
			} else if (e2.getY() - e1.getY() > FLIP_DISTANCE) {
				Toast.makeText(getApplicationContext(), "浠�0涓嬪埌涓婃粦鍔",
						Toast.LENGTH_SHORT).show();
				return true;
			}
		} else if (e1.getX() - e2.getX() > FLIP_DISTANCE) {

			Toast.makeText(getApplicationContext(), "浠庡彸鍚戝乏婊戝姩",
					Toast.LENGTH_SHORT).show();
			return true;
		} else if (e2.getX() - e1.getX() > FLIP_DISTANCE) {
			Toast.makeText(getApplicationContext(), "浠庡乏鍚戝彸婊戝姩",
					Toast.LENGTH_SHORT).show();
			return true;
		} else if (e1.getY() - e2.getY() > FLIP_DISTANCE) {
			Toast.makeText(getApplicationContext(), "浠庝笂鍒颁笅婊戝姩",
					Toast.LENGTH_SHORT).show();
			return true;
		} else if (e2.getY() - e1.getY() > FLIP_DISTANCE) {
			Toast.makeText(getApplicationContext(), "浠庝笅鍒颁笂婊戝姩",
					Toast.LENGTH_SHORT).show();
			return true;
		}
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		return false;
	}

	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		ResolveInfo appInfo = (ResolveInfo) parent.getItemAtPosition(position);
		Intent mainIntent = mContext.getPackageManager()
				.getLaunchIntentForPackage(appInfo.activityInfo.packageName);
		mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		try {
			// launcher the package
			mContext.startActivity(mainIntent);
		} catch (ActivityNotFoundException noFound) {
			Toast.makeText(mContext, "Package not found!", Toast.LENGTH_SHORT)
					.show();
		}
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		ResolveInfo appInfo = (ResolveInfo) parent.getItemAtPosition(position);
		Uri packageURI = Uri.parse(appInfo.activityInfo.packageName);
		uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);

		mVibrator01 = (Vibrator) getApplication().getSystemService(
				Service.VIBRATOR_SERVICE);
		mVibrator01.vibrate(new long[] { 100, 10, 100, 300 }, -1);
		try {
			// launcher the package
			ImageView iv_delItem = (ImageView) view
					.findViewById(R.id.iv_delItem);
			iv_delItem.setOnClickListener(this);
			RelativeLayout rel_t1 = (RelativeLayout) view
					.findViewById(R.id.rel_t1);
			rel_t1.setBackgroundColor(R.color.black);
			iv_delItem.setImageResource(R.drawable.lm_qu);
			iv_delItem.setVisibility(View.VISIBLE);
		} catch (ActivityNotFoundException noFound) {
			Toast.makeText(mContext, "Package not found!", Toast.LENGTH_SHORT)
					.show();
		}
		return false;
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
