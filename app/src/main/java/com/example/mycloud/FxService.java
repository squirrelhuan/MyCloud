package com.example.mycloud;


import android.app.Instrumentation;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

public class FxService extends Service implements OnTouchListener, OnClickListener, OnLongClickListener 
{

	//定义浮动窗口布局
    LinearLayout mFloatLayout;
    WindowManager.LayoutParams wmParams;
   // WindowManager.LayoutParams wmParams;
    //创建浮动窗口设置布局参数的对象
	WindowManager mWindowManager;
	ImageView mFloatView,mFloatView1,mFloatView2,mFloatView3,mFloatView4;
	
	private static final String TAG = "FxService";
	private static final int toleft = 1;
	private static final int toright = 2;
	private int height;
	private int xlocation,ylocation;
	private mHandler handle;
	private int width;
	private PopupWindow pW = null;
	
	@Override
	public void onCreate() 
	{
		// TODO Auto-generated method stub
		
		

		DisplayMetrics dm = new DisplayMetrics();
		dm = getResources().getDisplayMetrics();
		width = dm.widthPixels;
		super.onCreate();
		Log.i(TAG, "oncreat");

		//com.example.mycloud.MainActivity.width;
		//width=intentdata.getIntent(intentdata);
		
		handle = new mHandler();
		createFloatView();
        //Toast.makeText(FxService.this, "create FxService", Toast.LENGTH_LONG);
		new Handler().postDelayed(new Runnable(){   
		    public void run() {   
		    	if (mFloatView.getVisibility() == View.VISIBLE) {
		    		mFloatView.setAlpha(130);
		    	}
		    }   
		 }, 5000); 
		}
	@Override
	public IBinder onBind(Intent intent)
	{
		
		// TODO Auto-generated method stub
		return null;
	}
	

	class mHandler extends Handler{
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
    }  
	private void createFloatView()
	{
		wmParams = new WindowManager.LayoutParams();
		//获取WindowManagerImpl.CompatModeWrapper
		
		mWindowManager = (WindowManager)getApplication().getSystemService(getApplication().WINDOW_SERVICE);
		//设置window type
		wmParams.type = LayoutParams.TYPE_PHONE; 
		//设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888; 
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.flags = 
//          LayoutParams.FLAG_NOT_TOUCH_MODAL |
          LayoutParams.FLAG_NOT_FOCUSABLE
//          LayoutParams.FLAG_NOT_TOUCHABLE
          ; 
        //调整悬浮窗显示的停靠位置为左侧置顶
        wmParams.gravity = Gravity.LEFT | Gravity.TOP; 
        
        // 以屏幕左上角为原点，设置x、y初始值
        wmParams.x = width;
        wmParams.y = 200;
        
        /*// 设置悬浮窗口长宽数据
        wmParams.width = 200;
        wmParams.height = 80;*/
        
        //设置悬浮窗口长宽数据  
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        
        LayoutInflater inflater = LayoutInflater.from(getApplication());
        //获取浮动窗口视图所在布局
        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.float_layout, null);
        //添加mFloatLayout
        mWindowManager.addView(mFloatLayout, wmParams);
        
        Log.i(TAG, "mFloatLayout-->left" + mFloatLayout.getLeft());
        Log.i(TAG, "mFloatLayout-->right" + mFloatLayout.getRight());
        Log.i(TAG, "mFloatLayout-->top" + mFloatLayout.getTop());
        Log.i(TAG, "mFloatLayout-->bottom" + mFloatLayout.getBottom());      
        
        //浮动窗口按钮
        mFloatView = (ImageView)mFloatLayout.findViewById(R.id.float_id);
        mFloatView1 = (ImageView)mFloatLayout.findViewById(R.id.float_back_id);
        mFloatView2 = (ImageView)mFloatLayout.findViewById(R.id.float_home_id);
        mFloatView3 = (ImageView)mFloatLayout.findViewById(R.id.float_menu_id);
        
        mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
				.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        
        Log.i(TAG, "Width/2--->" + mFloatView.getMeasuredWidth()/2);
        Log.i(TAG, "Height/2--->" + mFloatView.getMeasuredHeight()/2);
        //设置监听浮动窗口的触摸移动
        mFloatView.setOnLongClickListener(this);;
        
        mFloatView.setOnTouchListener(this);
    	mFloatView1.setOnTouchListener(this);
    	mFloatView2.setOnTouchListener(this);
    	mFloatView3.setOnTouchListener(this);
       
    	 mFloatView1.setOnClickListener(this);
         mFloatView2.setOnClickListener(this);
         mFloatView3.setOnClickListener(this);
         mFloatView.setOnClickListener(this);
	}

	
	@Override
	public void onDestroy() 
	{
		// TODO Auto-generated method stub
		super.onDestroy();
		if(mFloatLayout != null)
		{
			mWindowManager.removeView(mFloatLayout);
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		mFloatView.setAlpha(255);
		new Handler().postDelayed(new Runnable(){   
		    public void run() {   
		    	if (mFloatView.getVisibility() == View.VISIBLE) {
		    		mFloatView.setAlpha(130);
		    	}
		    }   
		 }, 5000);
		 switch (event.getAction()) { 
			case MotionEvent.ACTION_MOVE:
				// TODO Auto-generated method stub
				xlocation = (int)event.getRawX();
				ylocation = (int)event.getRawY();
				wmParams.x = xlocation - mFloatLayout.getWidth()/2;
				//25涓虹姸鎬佹爮楂樺害
				wmParams.y = ylocation- mFloatLayout.getHeight()/2 - 40;
				mWindowManager.updateViewLayout(mFloatLayout, wmParams);					
				break;
			case MotionEvent.ACTION_UP:
				if (xlocation > width / 2) {
					handle.sendEmptyMessage(toright);
				}else {
					handle.sendEmptyMessage(toleft);
				}	
				break;
			default:
				break;
				
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.float_id||v.getId()==R.id.float_back_id
				||v.getId()==R.id.float_home_id||v.getId()==R.id.float_menu_id){
			mFloatView.setAlpha(255);
			new Handler().postDelayed(new Runnable(){   
			    public void run() {   
			    	if (mFloatView.getVisibility() == View.VISIBLE) {
			    		mFloatView.setAlpha(130);
			    	}
			    }   
			 }, 5000); 
			if (mFloatView.getVisibility() == View.VISIBLE) {
				mFloatView1.setVisibility(View.VISIBLE);
				mFloatView2.setVisibility(View.VISIBLE);
				mFloatView3.setVisibility(View.VISIBLE);
				mFloatView.setVisibility(View.GONE);
				}
			else{
				mFloatView1.setVisibility(View.GONE);
				mFloatView2.setVisibility(View.GONE);
				mFloatView3.setVisibility(View.GONE);
				mFloatView.setVisibility(View.VISIBLE);
			}
			
	
		}
		
		Toast.makeText(FxService.this, "onClick", Toast.LENGTH_SHORT).show();
	}
	
	private void sendKeyCode(final int keyCode) {
        new Thread() {
            public void run() {
                try {
                    Instrumentation inst = new Instrumentation();
                    inst.sendKeyDownUpSync(keyCode);
                } catch (Exception e) {
                    Log.e("Exception when sendPointerSync", e.toString());
                }
            }
        }.start();
    }
	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.float_id:
			LayoutInflater layout = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = layout.inflate(R.layout.keyboard, null);	
			pW= new PopupWindow(view,480,75,true);
//			pW = new PopupWindow(view, view.getLayoutParams().WRAP_CONTENT, view.getLayoutParams().WRAP_CONTENT, true);
			//	浠ヤ笅杩欒鍔犱笂鍘诲悗灏卞彲浠ヤ娇鐢˙ACK閿叧闂璓OPWINDOW
//			pW.setBackgroundDrawable(new ColorDrawable(0xb0000000));
			pW.setBackgroundDrawable(new ColorDrawable(0x00000000));
			//pW.setWidth(300);
			//pW.setHeight(60);
			pW.setOutsideTouchable(true);
			pW.setAnimationStyle(R.style.FromRightAnimation);//浠庡彸杩涘叆
			pW.setOnDismissListener(new PopupWindow.OnDismissListener(){	
				@Override
			public void onDismiss() {
					// TODO Auto-generated method stub	
				}
			});
//			pW.setAnimationStyle(android.R.style.Animation_Toast);
//			pW.setAnimationStyle(R.style.PopupAnimation);
	        int[] location = new int[2];
	        mFloatLayout.getLocationOnScreen(location);			
			pW.showAtLocation(mFloatLayout, Gravity.RIGHT,location[0],0);
			pW.update();	
			
		default:
			break;
		}
		return true;
			
	}
	
}
