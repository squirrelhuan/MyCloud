package com.example.fileexample;

import java.util.ArrayList;
import java.util.List;

import com.example.mycloud.MyAdapter;
import com.example.mycloud.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private ViewPager viewPager;
    private ImageView imageView;
    private List<View> lists = new ArrayList<View>();
    private MyAdapter myAdapter;
    private Bitmap cursor;
    private int offSet;
    private int currentItem;
    private Matrix matrix = new Matrix();
    private int bmWidth;
    private Animation animation;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    public Adapter adapter;
    
    
    private int[] imageId = new int[] { R.drawable.menu_bar_toggle_flight_mode, R.drawable.menu_bar_toggle_gps,
			R.drawable.menu_bar_toggle_bluetooth, R.drawable.menu_bar_toggle_screenshot, R.drawable.menu_bar_toggle_power,
			R.drawable.menu_bar_toggle_wifi, R.drawable.menu_bar_toggle_vibrate, R.drawable.menu_bar_toggle_torch,
			R.drawable.menu_bar_toggle_privacy_mode, R.drawable.menu_bar_toggle_data, R.drawable.menu_bar_toggle_brightness_manual,
			R.drawable.menu_bar_toggle_divider }; 
	private ListView lv_setting;
	private List<String>list;
	private ArrayAdapter<String>aa;
	private String[] array_dt = { };
	private LayoutInflater inflater;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent=new Intent();
		intent.setClass(MainActivity.this, MyFileManager.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
