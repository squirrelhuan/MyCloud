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

    LinearLayout mFloatLayout;
    WindowManager.LayoutParams wmParams;
   // WindowManager.LayoutParams wmParams;
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
		//��ȡWindowManagerImpl.CompatModeWrapper
		
		mWindowManager = (WindowManager)getApplication().getSystemService(getApplication().WINDOW_SERVICE);
		//����window type
		wmParams.type = LayoutParams.TYPE_PHONE; 
		//����ͼƬ��ʽ��Ч��Ϊ����͸��
        wmParams.format = PixelFormat.RGBA_8888; 
        //���ø������ڲ��ɾ۽���ʵ�ֲ���������������������ɼ����ڵĲ�����
        wmParams.flags = 
//          LayoutParams.FLAG_NOT_TOUCH_MODAL |
          LayoutParams.FLAG_NOT_FOCUSABLE
//          LayoutParams.FLAG_NOT_TOUCHABLE
          ; 
        //������������ʾ��ͣ��λ��Ϊ����ö�
        wmParams.gravity = Gravity.LEFT | Gravity.TOP; 
        
        // ����Ļ���Ͻ�Ϊԭ�㣬����x��y��ʼֵ
        wmParams.x = width;
        wmParams.y = 200;
        
        /*// �����������ڳ�������
        wmParams.width = 200;
        wmParams.height = 80;*/
        
        //�����������ڳ�������  
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        
        LayoutInflater inflater = LayoutInflater.from(getApplication());
        //��ȡ����������ͼ���ڲ���
        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.float_layout, null);
        //���mFloatLayout
        mWindowManager.addView(mFloatLayout, wmParams);
        
        Log.i(TAG, "mFloatLayout-->left" + mFloatLayout.getLeft());
        Log.i(TAG, "mFloatLayout-->right" + mFloatLayout.getRight());
        Log.i(TAG, "mFloatLayout-->top" + mFloatLayout.getTop());
        Log.i(TAG, "mFloatLayout-->bottom" + mFloatLayout.getBottom());      
        
        //�������ڰ�ť
        mFloatView = (ImageView)mFloatLayout.findViewById(R.id.float_id);
        mFloatView1 = (ImageView)mFloatLayout.findViewById(R.id.float_back_id);
        mFloatView2 = (ImageView)mFloatLayout.findViewById(R.id.float_home_id);
        mFloatView3 = (ImageView)mFloatLayout.findViewById(R.id.float_menu_id);
        
        mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
				.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        
        Log.i(TAG, "Width/2--->" + mFloatView.getMeasuredWidth()/2);
        Log.i(TAG, "Height/2--->" + mFloatView.getMeasuredHeight()/2);
        //���ü����������ڵĴ����ƶ�
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
				//25为状态栏高度
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
