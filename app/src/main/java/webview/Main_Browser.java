package webview;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Picture;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
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
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsoluteLayout;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.mycloud.R;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Main_Browser<DatabaseHelper> extends Activity implements OnLongClickListener, OnClickListener, OnGestureListener, OnTouchListener {
	private static final String[] countriesStr = { "", "", "", "" };
	private static final String[] sousuo = { "", "", "", "" };
	private static final String[] COUNTRIES = new String[] {
		 "", "", "", "", ""};
	private  AutoCompleteTextView textView;

	private Spinner mSpinner;
	private MyAdapter mMyAdapter;
	private ArrayAdapter<String> adapter_sipnner, adapter_sousuos;
	private List<String> allCountries, sousuos;
	private ImageView ic_re_windows2, ic_home, ic_menu, ic_back, ic_forward,	finder, menu_quit, menu_preferences, menu_night_mode,
			menu_downmanager, menu_no_picture_mode, menu_refresh_d, 	menu_add_to_bookmark, menu_add_to_bookmark_d, spinner_imageView,
			ic_bookmark, titlebar_refresh,float_id,float_home_id,float_recent_id,float_menu_id,float_back_id;
	private RelativeLayout ic_re_windows1,re_01;
	private LinearLayout lay_menu, lay_windows, ll_01, ll_02;
	private TableLayout tablayout_01;
	private WebView CB_webview;
	private ViewFlipper dl_vf;
	private GridView gridview1;
	private ViewFlipper viewFlipper = null;
	private int[] imgs = { R.drawable.c1, R.drawable.c2, R.drawable.c3,
			R.drawable.c4, R.drawable.c5 };

	private EditText url_input;
	private TextView windows_num, windows_textview, no_picture_textview,
			night_textview;
	private String url_String, windows_String;
	private ProgressBar ic_pb;
	public Adapter adapter;
	private GridView gridView;
	private LayoutInflater inflater;
	private List<String> dataList = new ArrayList<String>();
	private int[] imageId = new int[] {R.drawable.baidu,R.drawable.baidu,R.drawable.baidu,
			R.drawable.butn_open,R.drawable.baidu,R.drawable.baidu,R.drawable.baidu,R.drawable.baidu,R.drawable.baidu,R.drawable.baidu};
	private List<ImageView> dataListv = new ArrayList<ImageView>();
	public long firClick, secClick, distanceTime;
	private Calendar myCalendar;
	private GestureDetector gestureDetector = null;
	private Activity mActivity = null;
	private Notification notification;
	private NotificationManager notificationManager;
	
    private static final String TAG = "FloatWindowTest";
    
	
    private boolean waitDouble = true;  
    private static final int DOUBLE_CLICK_TIME = 350;
	WindowManager mWindowManager;
	WindowManager.LayoutParams wmParams;
	AbsoluteLayout mFloatLayout;
	ImageView mFloatView,mFloatView1,mFloatView2,mFloatView3,mFloatView4;
	Button tesButton;
	
	private static final int toleft = 1;
	private static final int toright = 2;
	protected static int i = 0;
	private int width,height;
	private int xlocation,ylocation;
	private PopupWindow pW = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.web_main);
     
	       
		textView=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);
        ArrayAdapter<String> adaptert2=new ArrayAdapter<String>(this, 
        							android.R.layout.simple_dropdown_item_1line,COUNTRIES);
        textView.setAdapter(adaptert2);
		/* String service=NOTIFICATION_SERVICE;
	        notificationManager=(NotificationManager)getSystemService(service);
	        notification=new Notification();
	        notification.icon=R.drawable.ic_launcher;
	        notification.tickerText="��лʹ�ã�";
	        notification.when=System.currentTimeMillis();
	        Intent intent=new Intent(Main_Browser.this,MainActivity0.class);
	        PendingIntent pendingIntent=PendingIntent.getActivity(Main_Browser.this, 0, intent, 0);
	        notification.setLatestEventInfo(Main_Browser.this, "CBrowser", "��л����squirrel����֧�֣�ף��������죡", pendingIntent);
	        notificationManager.notify(1,notification);*/
	
		
		gestureDetector = new GestureDetector(this); // ������������¼�
		setupViews();

		mActivity = this;

		viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
		viewFlipper.setLongClickable(true);
		gestureDetector = new GestureDetector(this); // ������������¼�

		
		GridView iv = new GridView(this);

		viewFlipper.addView(iv, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		viewFlipper.setAutoStart(true);
		viewFlipper.setFlipInterval(3000);
		if (viewFlipper.isAutoStart() && !viewFlipper.isFlipping()) {
			viewFlipper.startFlipping();
		}
		
		allCountries = new ArrayList<String>();
		for (int i = 0; i < countriesStr.length; i++) {
			allCountries.add(countriesStr[i]);
		}
		adapter_sipnner = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, allCountries);
		adapter_sipnner
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		
		url_input = (EditText) findViewById(R.id.url_input);
		url_input.setOnClickListener(this);
		ic_pb = (ProgressBar) findViewById(R.id.ic_pb);

		ic_re_windows1 = (RelativeLayout) findViewById(R.id.ic_re_windows1);
		windows_textview = (TextView) findViewById(R.id.windows_textview);
		windows_textview.setOnClickListener(this);

		re_01 = (RelativeLayout) findViewById(R.id.re_01);
		re_01.setOnClickListener(this);
		ll_02 = (LinearLayout) findViewById(R.id.ll_02);
		ll_02.setOnClickListener(this);
		lay_windows = (LinearLayout) findViewById(R.id.lay_windows);
		lay_windows.setOnClickListener(this);
		lay_menu = (LinearLayout) findViewById(R.id.lay_menu);
		lay_menu.setOnClickListener(this);
		tablayout_01 = (TableLayout) findViewById(R.id.tablayout_01);
		tablayout_01.setOnClickListener(this);
		ic_home = (ImageView) findViewById(R.id.ic_home);
		ic_home.setOnClickListener(this);
		ic_menu = (ImageView) findViewById(R.id.ic_menu);
		ic_menu.setOnClickListener(this);
		ic_back = (ImageView) findViewById(R.id.ic_back);
		ic_back.setOnClickListener(this);
		ic_forward = (ImageView) findViewById(R.id.ic_forward);
		ic_forward.setOnClickListener(this);
		titlebar_refresh = (ImageView) findViewById(R.id.titlebar_refresh);
		titlebar_refresh.setOnClickListener(this);
		ic_bookmark = (ImageView) findViewById(R.id.ic_bookmark);
		ic_bookmark.setOnClickListener(this);
		finder = (ImageView) findViewById(R.id.finder);
		finder.setOnClickListener(this);

		menu_preferences = (ImageView) findViewById(R.id.menu_preferences);
		menu_preferences.setOnClickListener(this);
		menu_refresh_d = (ImageView) findViewById(R.id.menu_refresh_d);
		menu_refresh_d.setOnClickListener(this);
		menu_add_to_bookmark = (ImageView) findViewById(R.id.menu_add_to_bookmark);
		menu_add_to_bookmark.setOnClickListener(this);
		menu_add_to_bookmark_d = (ImageView) findViewById(R.id.menu_add_to_bookmark_d);
		menu_add_to_bookmark_d.setOnClickListener(this);
		menu_no_picture_mode = (ImageView) findViewById(R.id.menu_no_picture_mode);
		menu_no_picture_mode.setOnClickListener(this);
		menu_downmanager = (ImageView) findViewById(R.id.menu_downmanager);
		menu_downmanager.setOnClickListener(this);
		menu_night_mode = (ImageView) findViewById(R.id.menu_night_mode);
		menu_night_mode.setOnClickListener(this);
		menu_quit = (ImageView) findViewById(R.id.menu_quit);
		menu_quit.setOnClickListener(this);

		no_picture_textview = (TextView) findViewById(R.id.no_picture_textview);
		gridview1 = (GridView) findViewById(R.id.gridview1);

		// ͼ��ҳ
		GridView gridview1 = (GridView) findViewById(R.id.gridview1);// ���ɶ�̬���飬����ת������
		ArrayList<HashMap<String, Object>> ItemList = new ArrayList<HashMap<String, Object>>();

		HashMap<String, Object> map_0 = new HashMap<String, Object>();
		map_0.put("Image", R.drawable.google);
		map_0.put("Tag", "google");
		ItemList.add(map_0);

		HashMap<String, Object> map_1 = new HashMap<String, Object>();
		map_1.put("Image", R.drawable.baidu);
		map_1.put("Tag", "�ٶ�");
		ItemList.add(map_1);
		HashMap<String, Object> map_2 = new HashMap<String, Object>();
		map_2.put("Image", R.drawable.qq);
		map_2.put("Tag", "��Ѷ");
		ItemList.add(map_2);
		HashMap<String, Object> map_3 = new HashMap<String, Object>();
		map_3.put("Image", R.drawable.taobao);
		map_3.put("Tag", "�Ա�");
		ItemList.add(map_3);
		HashMap<String, Object> map_4 = new HashMap<String, Object>();
		map_4.put("Image", R.drawable.yahoo);
		map_4.put("Tag", "�Ż�");
		ItemList.add(map_4);
		HashMap<String, Object> map_5 = new HashMap<String, Object>();
		map_5.put("Image", R.drawable.wandoujia);
		map_5.put("Tag", "�㶹��");
		ItemList.add(map_5);
		HashMap<String, Object> map_6 = new HashMap<String, Object>();
		map_6.put("Image", R.drawable.renren);
		map_6.put("Tag", "����");
		ItemList.add(map_6);
		HashMap<String, Object> map_7 = new HashMap<String, Object>();
		map_7.put("Image", R.drawable.sohu);
		map_7.put("Tag", "�Ѻ�");
		ItemList.add(map_7);
		HashMap<String, Object> map_8 = new HashMap<String, Object>();
		map_8.put("Image", R.drawable.sina);
		map_8.put("Tag", "����");
		ItemList.add(map_8);
		HashMap<String, Object> map_9 = new HashMap<String, Object>();
		map_9.put("Image", R.drawable.qzone);
		map_9.put("Tag", "qzone");
		ItemList.add(map_9);
		HashMap<String, Object> map_10 = new HashMap<String, Object>();
		map_10.put("Image", R.drawable.wode);
		map_10.put("Tag", "�ҵ�");
		ItemList.add(map_10);
		HashMap<String, Object> map_11 = new HashMap<String, Object>();
		map_11.put("Image", R.drawable.rss);
		map_11.put("Tag", "����");
		ItemList.add(map_11);

		adapter = new SimpleAdapter(getApplicationContext(), ItemList,
				R.layout.home_item_style, new String[] { "Image", "Tag" },
				new int[] { R.id.ItemImage, R.id.ItemText });
		gridview1.setAdapter((ListAdapter) adapter);
		gridview1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent_MainView = new Intent();
				// ���ô��ݷ���
				CB_webview.setVisibility(View.VISIBLE);
				switch (arg2) {
				case 0:
					CB_webview.loadUrl("http://www.google.com.hk");
					// ������
					// intent_MainView.putExtra("url_text1","http://www.google.com.hk");
					/*
					 * ���߰󶨳�һ������ Bundle data = new Bundle();
					 * data.putString("username1",username);
					 * data.putString("userpwd1",userpwd);
					 * intent.putExtras(data);
					 */

					// MainView.instance.cur_url = "http://www.google.com.hk";
					break;
				case 1:
					CB_webview.loadUrl("http://www.baidu.com");
					// intent_MainView.putExtra("http://www.baidu.com");
					// MainView.instance.cur_url = "http://www.baidu.com";
					break;
				case 2:
					CB_webview.loadUrl("http://www.qq.com");
					// intent_MainView.putExtra("url_text1","http://www.qq.com");
					// MainView.instance.cur_url = "http://www.qq.com";
					break;
				case 3:
					CB_webview.loadUrl("http://www.taobao.com");
					// intent_MainView.putExtra("url_text1","http://www.taobao.com");
					// MainView.instance.cur_url = "http://www.taobao.com";
					break;
				case 4:
					CB_webview.loadUrl("http://www.yahoo.com");
					// intent_MainView.putExtra("url_text1","http://www.yahoo.com");
					// MainView.instance.cur_url = "http://www.yahoo.com";
					break;
				case 5:
					CB_webview.loadUrl("http://www.wandoujia.com");
					// intent_MainView.putExtra("url_text1","http://www.wandoujia.com");
					// MainView.instance.cur_url = "http://www.wandoujia.com";
					break;
				case 6:
					CB_webview.loadUrl("http://www.renren.com");
					// intent_MainView.putExtra("url_text1","http://www.renren.com");
					// MainView.instance.cur_url = "http://www.renren.com";
					break;
				case 7:
					CB_webview.loadUrl("http://www.sohu.com");
					// intent_MainView.putExtra("url_text1","http://www.sohu.com");
					// MainView.instance.cur_url = "http://www.sohu.com";
					break;
				// add my rss reader
				case 10:
					CB_webview
							.loadUrl("http://v.youku.com/v_show/id_XODE1ODYzMzY4.html?f=22997997");
					// intent_MainView.putExtra("url_text1",url_String);
					// MainView.instance.cur_url =
					// "file:///android_asset/index.html";
					break;
				case 11:
					CB_webview.loadUrl("url_text1");
					// intent_MainView.putExtra("url_text1",url_String);
					// MainView.instance.cur_url =
					// "file:///android_asset/index.html";
					break;

				}
				tablayout_01.setVisibility(View.GONE);
				// ����activity
				// intent_MainView.setClass(MainView.this,MainView.class);
				// startActivity(intent_MainView);
				// Intent intent = new Intent();
				// intent.setClass(getApplicationContext(), MainView.class);
				// startActivity(intent)
			}
		});
		// ͼ��ҳ

		gridView = (GridView) this.findViewById(R.id.gridview_windows);
		for (int i = 0; i < 10; i++) {
			dataList.add("����" + i);
			dataListv.add(ic_bookmark);
		}
		inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		GridViewAdapter adapter = new GridViewAdapter();
		gridView.setAdapter(adapter);
		int size = dataList.size();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		float density = dm.density;
		int allWidth = (int) (110 * size * density);
		int itemWidth = (int) (100 * density);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				allWidth, LinearLayout.LayoutParams.FILL_PARENT);
		gridView.setLayoutParams(params);
		gridView.setColumnWidth(itemWidth);
		gridView.setHorizontalSpacing(10);
		gridView.setStretchMode(GridView.NO_STRETCH);
		gridView.setNumColumns(size);

		// url_input.setFocusable(true);
		CB_webview = (WebView) findViewById(R.id.CB_webview);
		CB_webview.getSettings().setJavaScriptEnabled(true);
		CB_webview.getSettings().setSupportZoom(true);
	    //CB_webview.getSettings().setDefaultFontSize(size);
	    //CB_webview.getSettings().setBlockNetworkImage(true);
		CB_webview.requestFocus();
		CB_webview.getSettings().setBuiltInZoomControls(true); // ����
		CB_webview.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
		CB_webview.loadUrl("http://www.baidu.com");
		CB_webview.requestFocus();
		CB_webview.setOnLongClickListener(this);
		CB_webview.getSettings().setAllowFileAccess(true);
		CB_webview.loadUrl("file:///mnt/sdcard/index.html");
		//Button capture = (Button) findViewById(R.id.capture);  
	    //capture.setOnClickListener(this); 
	              

		CB_webview.setDownloadListener(new DownloadListener() {
			@Override
			public void onDownloadStart(String url, String userAgent,
					String contentDisposition, String mimetype,
					long contentLength) {
				// TODO Auto-generated method stub
				Uri uri = Uri.parse(url);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});

		CB_webview.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				return true;
				// return super.shouldOverrideUrlLoading(view, url);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				ic_pb.setVisibility(android.view.View.VISIBLE);
				titlebar_refresh.setImageResource(R.drawable.titlebar_stop);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				ic_pb.setVisibility(android.view.View.GONE);
				titlebar_refresh.setImageResource(R.drawable.titlebar_refresh);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				super.onReceivedError(view, errorCode, description, failingUrl);
				ic_pb.setVisibility(android.view.View.VISIBLE);
			}
		});
	}
	private Bitmap captureWebViewVisibleSize(WebView webView){
	    Bitmap bmp = CB_webview.getDrawingCache();
	    return bmp;
	    }
	//ֻ��ȡ��Ļ����ʾ�������ֵ�webView����:
	private Bitmap captureWebView(WebView webView){
        Picture snapShot = CB_webview.capturePicture();
         
        Bitmap bmp = Bitmap.createBitmap(snapShot.getWidth(),snapShot.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        snapShot.draw(canvas);
        return bmp;
    }
	//��ȡwebView������ҳ��
	private Bitmap captureScreen(Activity context){
	      View cv = context.getWindow().getDecorView();
	      Bitmap bmp = Bitmap.createBitmap(cv.getWidth(), cv.getHeight(),Config.ARGB_8888);
	      Canvas canvas = new Canvas(bmp);
	      cv.draw(canvas);
	      return bmp;
	      }

	
	/*class mHandler extends Handler{
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what) {
			case toleft:
				wmParams.x -=30;
				if (wmParams.x >= 0) {
					mWindowManager.updateViewLayout(mFloatLayout, wmParams);
					handle.sendEmptyMessageDelayed(toleft, 1);
				}
				else {
					wmParams.x =0; 
					mWindowManager.updateViewLayout(mFloatLayout, wmParams);
				}
				break;
			case toright:
				wmParams.x +=30;
				if (wmParams.x <= width) {
					mWindowManager.updateViewLayout(mFloatLayout, wmParams);
					handle.sendEmptyMessageDelayed(toright, 1);
				}else {
					wmParams.x =width;
					mWindowManager.updateViewLayout(mFloatLayout, wmParams);
				}
				break;
			default:
				break;
			}
		}        	
    }     */
    private void createFloatView()
    {
    	//获取LayoutParams对象
        wmParams = new WindowManager.LayoutParams();
        
        //获取的是LocalWindowManager对象
        mWindowManager = this.getWindowManager();
     
        //获取的是CompatModeWrapper对象
        wmParams.type = LayoutParams.TYPE_PHONE;
        wmParams.format = PixelFormat.RGBA_8888;;
        wmParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        //设置初始位置
        wmParams.x = width;
        wmParams.y = height-50;
        
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        
        mFloatLayout = (AbsoluteLayout) inflater.inflate(R.layout.float_layout, null);
        mWindowManager.addView(mFloatLayout, wmParams);
        
        mFloatView = (ImageView)mFloatLayout.findViewById(R.id.float_id);
        mFloatView1 = (ImageView)mFloatLayout.findViewById(R.id.float_back_id);
        mFloatView2 = (ImageView)mFloatLayout.findViewById(R.id.float_home_id);
        mFloatView3 = (ImageView)mFloatLayout.findViewById(R.id.float_menu_id);
        tesButton = (Button)mFloatLayout.findViewById(R.id.btn3);
        //绑定触摸移动监听
    	//Button btn3=(Button) mFloatLayout.findViewById(R.id.btn3);
    	//final ImageView btn2=(ImageView) findViewById(R.id.float_id);

    	mFloatView.setOnTouchListener(this);
    	mFloatView1.setOnTouchListener(this);
    	mFloatView2.setOnTouchListener(this);
    	mFloatView3.setOnTouchListener(this);
        //绑定点击监听
        /*tesButton .setOnClickListener(new OnClickListener()
        {			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainView.this, ResultActivity.class);
				startActivity(intent);
			}
		});*/
        
        mFloatView1.setOnClickListener(this);
        mFloatView2.setOnClickListener(this);
        mFloatView3.setOnClickListener(this);
        mFloatView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mFloatView.getVisibility() == View.VISIBLE) {
					mFloatView1.setVisibility(View.VISIBLE);
					mFloatView2.setVisibility(View.VISIBLE);
					mFloatView3.setVisibility(View.VISIBLE);
					mFloatView.setVisibility(View.GONE);
					}
			}			
		});    
    }
	
	

	public void setupViews() {
		sousuos = new ArrayList<String>();
		for (int i = 0; i < sousuo.length; i++) {
			sousuos.add(sousuo[i]);
		}
		adapter_sousuos = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, sousuos);
		adapter_sousuos
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		mMyAdapter = new MyAdapter();
		mSpinner = (Spinner) findViewById(R.id.spinner);
		mSpinner.setAdapter(mMyAdapter);

		mSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	// �����Լ���������,ע��getCount��getView����
	private class MyAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			// �����Ҿͷ���10�ˣ�Ҳ����һ����10��������
			return 5;
		}

		@Override
		public Object getItem(int arg0) {
			return arg0;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			spinner_imageView = (ImageView) findViewById(R.id.spinner_imageView);
			// spinner_imageView.setImageResource(R.drawable.back);
			String webs;
			CB_webview.setVisibility(View.VISIBLE);
			switch (position) {
			case 0:
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.baseadapter_provider1, null);
				webs = "�ٶ�����";
				break;
			case 1:
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.baseadapter_provider2, null);
				webs = "�ѹ�����";
				break;
			case 2:
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.baseadapter_provider3, null);
				webs = "�ȸ�����";
				break;
			case 3:
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.baseadapter_provider4, null);
				webs = "�ٶ�һ��";
				break;
			case 4:
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.baseadapter_provider5, null);
				webs = "�ٶ�һ��";
				break;

			default:
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.baseadapter_provider1, null);
				webs = "Ĭ��";
				break;
			}

			TextView mTextView = (TextView) convertView.findViewById(R.id.spinner_textview);

			mTextView.setText(webs + position);
			mTextView.setTextColor(Color.BLACK);
			return convertView;
		}
	}	

	public boolean onKeyDown(int keyCode, KeyEvent event) {// ��׽���ؼ�
		visibility(00);
		if ((keyCode == KeyEvent.KEYCODE_BACK) && CB_webview.canGoBack()) {
			CB_webview.goBack();
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_BACK) {
			onDoubleClick(1);// ���˷��ؼ������Ѿ����ܷ��أ���ִ���˳�ȷ��
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void ConfirmExit() {// �˳�ȷ��

		AlertDialog.Builder ad = new AlertDialog.Builder(Main_Browser.this);
		ad.setTitle("�˳�");
		ad.setMessage("�Ƿ��˳����?");
		ad.setPositiveButton("��", new DialogInterface.OnClickListener() {// �˳���ť
					@Override
					public void onClick(DialogInterface dialog, int i) {
						// TODO Auto-generated method stub
						Main_Browser.this.finish();// �ر�activity
					}
				});
		ad.setNegativeButton("��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int i) {
				// ���˳�����ִ���κβ���
			}
		});
		ad.show();// ��ʾ�Ի���
	}

	public boolean onDoubleClick(int n) {

		// ��ȡCalendar����
		myCalendar = Calendar.getInstance();
		// ����ǵ�һ�ε��
		if (firClick == 0l) {

			// ��ȡ��һ�ε����ʱ��
			firClick = myCalendar.getTimeInMillis();
			// ��û��ʱ����
			distanceTime = 0l;
			// �ж��Ƿ�Ϊ�ڶ��ε��
		} else if (secClick == 0l) {

			// ��ȡ�ڶ��ε����ʱ��
			secClick = myCalendar.getTimeInMillis();

			// �ó����ε���ļ��ʱ��
			distanceTime = secClick - firClick;
		}
		// ���ʱ����С��500ms��Ϊ˫��
		if (distanceTime > 0l && distanceTime < 500l) {
			switch (n) {
			case 1:
				Main_Browser.this.finish();
				break;
			case 2:
				
			break;	
			default:
				break;
			}		   
			// �����ε���¼����
			firClick = 0l;
			secClick = 0l;

			// ʱ��������������˫���¼�
		} else if (distanceTime > 500l) {
			Toast.makeText(this, "˫���˳���", Toast.LENGTH_SHORT).show();
			// �ѵڶ��ε�����ɵ�һ�ε��
			firClick = secClick;
			secClick = 0l;
		}
		return true;
	}

	public void showCustomDialog() {
		AlertDialog.Builder builder;
		AlertDialog alertDialog;
		Context mContext = Main_Browser.this;

		// �������ַ���������
		// //LayoutInflaterinflater=getLayoutInflater();

		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(LAYOUT_INFLATER_SERVICE);

		View layout = inflater.inflate(R.layout.custom_dialog, null);
		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText("Hello,WelcometoMrWei'sblog!");
		ImageView image = (ImageView) layout.findViewById(R.id.image);
		image.setImageResource(R.drawable.ic_launcher);
		builder = new AlertDialog.Builder(mContext);
		builder.setView(layout);
		alertDialog = builder.create();
		alertDialog.show();
	}

	public void showMenu() {
		Context context = Main_Browser.this;
		LayoutInflater flater = LayoutInflater.from(this);

		View layout = flater.inflate(R.layout.custom_dialog, null);
		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText("Hello,WelcometoMrWei'sblog!");
		ImageView image = (ImageView) layout.findViewById(R.id.image);
		image.setImageResource(R.drawable.ic_launcher);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// Public static LayoutInflater from(Context context);
		// MainView.add(layout, layoutParams);
		Main_Browser.add(layout, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
	}

	private static void add(View layout, LayoutParams layoutParams) {
		// TODO Auto-generated method stub

	}

	final class GridViewAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return dataList.size();
		}

		@Override
		public Object getItem(int position) {
			return dataList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = inflater.inflate(R.layout.windows_item, null);
			TextView textView = (TextView) convertView
					.findViewById(R.id.item_textview);
			String str = dataList.get(position);
			textView.setText(str);
			ImageView imageView=(ImageView)convertView
					.findViewById(R.id.shotscreen);
			
			imageView.setImageResource(imageId[position]);
			return convertView;
		}

	}

	public void visibility(int visi) {
		switch (visi) {
		case 00:
			lay_menu.setVisibility(View.GONE);
			lay_windows.setVisibility(View.GONE);
			break;
		case 01:
			if (re_01.getVisibility() == View.VISIBLE) {
				re_01.setVisibility(View.GONE);
			} else {
				re_01.setVisibility(View.VISIBLE);
			}
			break;
		case 02:
			if (ll_02.getVisibility() == View.VISIBLE) {
				ll_02.setVisibility(View.GONE);
			} else {
				ll_02.setVisibility(View.GONE);
			}
			break;
		case 03:
			if (lay_menu.getVisibility() == View.VISIBLE) {
				lay_menu.setVisibility(View.GONE);
			} else {
				lay_menu.setVisibility(View.VISIBLE);
				lay_windows.setVisibility(View.GONE);
			}
			break;
		case 04:
			if (lay_windows.getVisibility() == View.VISIBLE) {
				lay_windows.setVisibility(View.GONE);
			} else {
				lay_windows.setVisibility(View.VISIBLE);
				lay_menu.setVisibility(View.GONE);
			}
			break;
		case 05:
			if (tablayout_01.getVisibility() == View.VISIBLE) {
				tablayout_01.setVisibility(View.GONE);
				CB_webview.setVisibility(View.VISIBLE);
				lay_menu.setVisibility(View.GONE);
				lay_windows.setVisibility(View.GONE);
			} else {
				tablayout_01.setVisibility(View.VISIBLE);
				lay_menu.setVisibility(View.GONE);
				lay_windows.setVisibility(View.GONE);
			}
			break;
		case 06:
			if (re_01.getVisibility() == View.VISIBLE) {
				re_01.setVisibility(View.GONE);
				ll_02.setVisibility(View.GONE);
				lay_menu.setVisibility(View.GONE);
				lay_windows.setVisibility(View.GONE);
			} else {
				re_01.setVisibility(View.VISIBLE);
				ll_02.setVisibility(View.VISIBLE);
				lay_menu.setVisibility(View.GONE);
				lay_windows.setVisibility(View.GONE);
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
   
		case R.id.ic_home:
			visibility(05);
			//ȡ��android.graphics.Pictureʵ��  
	        Picture picture = CB_webview.capturePicture();  
	        int width = picture.getWidth();  
	        int height = picture.getHeight();  
	        if (width > 0 && height > 0) {  
	            //����ָ���߿��Bitmap����  
	            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);  
	            //����Canvas,����bitmapΪ����Ŀ��  
	            Canvas canvas = new Canvas(bitmap);  
	            //��WebViewӰ�������Canvas��  
	            picture.draw(canvas);  
	            
	            try {  
	                String fileName = "/sdcard/webview_capture.jpg";  
	                FileOutputStream fos = new FileOutputStream(fileName);  
	                //ѹ��bitmap���������  
	                bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);  
	                fos.close();  
	                Toast.makeText(Main_Browser.this, "CAPTURE SUCCESS", Toast.LENGTH_LONG).show();  
	            } catch (Exception e) {  
	                Log.e(TAG, e.getMessage());  
	            }
	            
	        }
	      
			// Intent intent_download=new Intent(MainView.this,MainHome.class);
			// startActivity(intent_download);
			break;
		case R.id.ic_menu:
			visibility(03);
			break;
		case R.id.ic_forward:
			CB_webview.goForward();
			break;
		case R.id.ic_back:
			CB_webview.goBack();
			break;
		case R.id.ic_bookmark:
			// inflateInsert(db.getReadableDatabase(),wordEdit.getText().toString(),detailEdit.getText().toString());

			break;
		case R.id.windows_textview:
			visibility(04);
			break;
		case R.id.menu_quit:
			ConfirmExit();
			break;
		// case R.id.menu_imagebtn_refresh:
		// CB_webview.reload();
		// showMenu();
		// break;
		case R.id.menu_no_picture_mode:
			if (CB_webview.getSettings().getBlockNetworkImage() == false) {
				CB_webview.getSettings().setBlockNetworkImage(true);
				Toast.makeText(this, "������ͼģʽ��", Toast.LENGTH_SHORT).show();
				menu_no_picture_mode
						.setImageResource(R.drawable.menu_no_picture_mode_off);
				no_picture_textview.setText("��ͼ");
			} else {
				CB_webview.getSettings().setBlockNetworkImage(false);
				Toast.makeText(this, "������ͼģʽ��", Toast.LENGTH_SHORT).show();
				menu_no_picture_mode
						.setImageResource(R.drawable.menu_no_picture_mode);
				no_picture_textview.setText("��ͼ");
			}
			break;
		case R.id.menu_refresh_d:
			CB_webview.reload();
			break;
		case R.id.titlebar_refresh:
			CB_webview.reload();
			break;
		/*case R.id.menu_preferences:
			Intent intent_setting = new Intent(Main_Browser.this, MainSetting.class);
			startActivity(intent_setting);
			break;
		case R.id.menu_downmanager:
			Intent intent_loading = new Intent(Main_Browser.this,
					MainDownload.class);
			startActivity(intent_loading);
			break;*/
		case R.id.float_back_id:
			if (mFloatView.getVisibility() == View.GONE) {
				mFloatView1.setVisibility(View.GONE);
				mFloatView2.setVisibility(View.GONE);
				mFloatView3.setVisibility(View.GONE);
				mFloatView.setVisibility(View.VISIBLE);
				}
			break;
		/*case R.id.float_home_id:
			if (mFloatView.getVisibility() == View.GONE) {
				mFloatView1.setVisibility(View.GONE);
				mFloatView2.setVisibility(View.GONE);
				mFloatView3.setVisibility(View.GONE);
				mFloatView.setVisibility(View.VISIBLE);
				Intent intent_float = new Intent(Main_Browser.this, FxService1.class);
			    startService(intent_float); 
				
				}
			break;*/
		case R.id.float_menu_id:
			if (mFloatView.getVisibility() == View.GONE) {
				mFloatView1.setVisibility(View.GONE);
				mFloatView2.setVisibility(View.GONE);
				mFloatView3.setVisibility(View.GONE);
				mFloatView.setVisibility(View.VISIBLE);
				}
			break;

		case R.id.finder:
			String url_head = "http://";
			String url_head1 = "http://www.";
			url_String = url_input.getText().toString();
			if (!url_String.contains("http://")) {
				url_String = url_head.concat(url_String);
			} else {
				if (!url_String.contains("http://www.")) {
					url_String = url_head1.concat(url_String);
				}
			}
			CB_webview.loadUrl(url_String);
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.CB_webview:
			visibility(06);
			break;
		case R.id.float_id:
			LayoutInflater layout = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = layout.inflate(R.layout.keyboard, null);	
			pW= new PopupWindow(view,460,60,true);
//			pW = new PopupWindow(view, view.getLayoutParams().WRAP_CONTENT, view.getLayoutParams().WRAP_CONTENT, true);
			//	以下这行加上去后就可以使用BACK键关闭POPWINDOW
//			pW.setBackgroundDrawable(new ColorDrawable(0xb0000000));
			pW.setBackgroundDrawable(new ColorDrawable(0x00000000));
			//pW.setWidth(300);
			//pW.setHeight(60);
			pW.setOutsideTouchable(true);
			pW.setAnimationStyle(R.style.FromRightAnimation);//从右进入
			pW.setOnDismissListener(new PopupWindow.OnDismissListener(){	
				@Override
			public void onDismiss() {
					// TODO Auto-generated method stub
					if (mFloatView.getVisibility() == View.GONE) {
						mFloatView1.setVisibility(View.GONE);
						mFloatView2.setVisibility(View.GONE);
						mFloatView3.setVisibility(View.GONE);
						mFloatView.setVisibility(View.VISIBLE);
						}
				}
			});
//			pW.setAnimationStyle(android.R.style.Animation_Toast);
//			pW.setAnimationStyle(R.style.PopupAnimation);
	        int[] location = new int[2];
	        mFloatLayout.getLocationOnScreen(location);			
			pW.showAtLocation(mFloatLayout, Gravity.CENTER,location[0],0);
			pW.update();	
			
		default:
			break;
		}
		return true;

	}

	public void inflateInsert(SQLiteDatabase db, String word, String detail) {
		String insertSql = "insert into mydb(_id,word,detail) values(null,?,?)";
		db.execSQL(insertSql, new String[] { word, detail });
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		viewFlipper.stopFlipping(); // ����¼���ֹͣ�Զ�����
		viewFlipper.setAutoStart(false);
		return gestureDetector.onTouchEvent(event); // ע�������¼�
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (e2.getX() - e1.getX() > 120) { // �������һ���������ҳ���
			Animation rInAnim = AnimationUtils.loadAnimation(mActivity,
					R.anim.push_right_in); // ���һ���������Ľ���Ч����alpha 0.1 -> 1.0��
			Animation rOutAnim = AnimationUtils.loadAnimation(mActivity,
					R.anim.push_right_out); // ���һ����Ҳ໬���Ľ���Ч����alpha 1.0 -> 0.1��

			viewFlipper.setInAnimation(rInAnim);
			viewFlipper.setOutAnimation(rOutAnim);
			viewFlipper.showPrevious();
			return true;
		} else if (e2.getX() - e1.getX() < -120) { // �������󻬶����ҽ������
			Animation lInAnim = AnimationUtils.loadAnimation(mActivity,
					R.anim.push_left_in); // ���󻬶�������Ľ���Ч����alpha 0.1 -> 1.0��
			Animation lOutAnim = AnimationUtils.loadAnimation(mActivity,
					R.anim.push_left_out); // ���󻬶��Ҳ໬���Ľ���Ч����alpha 1.0 -> 0.1��

			viewFlipper.setInAnimation(lInAnim);
			viewFlipper.setOutAnimation(lOutAnim);
			viewFlipper.showNext();
			return true;
		}
		return true;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			// TODO Auto-generated method stub
			xlocation = (int)event.getRawX();
			ylocation = (int)event.getRawY();
			wmParams.x = xlocation - mFloatLayout.getWidth()/2;
			//25为状态栏高度
			wmParams.y = ylocation- mFloatLayout.getHeight()/2 - 40;
			mWindowManager.updateViewLayout(mFloatLayout, wmParams);					
			break;
		/*case MotionEvent.ACTION_UP:
			if (xlocation > width / 2) {
				handle.sendEmptyMessage(toright);
			}else {
				handle.sendEmptyMessage(toleft);
			}			
			break;*/
		default:
	
			break;
	}
		return false;
	}
}
