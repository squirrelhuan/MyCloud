package com.example.fileexample;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.mycloud.MyAdapter;
import com.example.mycloud.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Page extends Activity {

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.cell_viewpage);
        
        Resources res = this.getResources();
		array_dt=res.getStringArray(R.array.menuitems);
		ArrayList<HashMap<String,Object>>
        item=new ArrayList<HashMap<String,Object>>();		
        for(int i=0;i<6;i++){
        	HashMap<String,Object>item_web=new HashMap<String,Object>();
        	if(i==0){
        		item_web.put("username",array_dt[i]);
        		item_web.put("age","11");
        		item.add(item_web);
        	}
        	else{
        	item_web.put("img",imageId[i]);
        	item_web.put("username","("+i+")"+array_dt[i]);
        	item_web.put("age",(20+i)+"");
        	item.add(item_web);
        	}
        }
        SimpleAdapter saImageItems=new SimpleAdapter(this,item,R.layout.cell_listview_item_image,
        		new String[]{"img","username"},
        		new int[]{R.id.img,R.id.name});
        ((ListView)findViewById(R.id.list)).setAdapter(saImageItems);
        
   
        
        
        imageView = (ImageView) findViewById (R.id.cursor);
        textView1 = (TextView) findViewById (R.id.textView1);
        textView2 = (TextView) findViewById (R.id.textView2);
        textView3 = (TextView) findViewById (R.id.textView3);
        
        
		
        
        lists.add(getLayoutInflater().inflate(R.layout.layout1, null));
        lists.add(getLayoutInflater().inflate(R.layout.layout2, null));
        lists.add(getLayoutInflater().inflate(R.layout.layout3, null));
        lists.add(getLayoutInflater().inflate(R.layout.layout4, null));
        //lists.add(getLayoutInflater().inflate(R.layout.activity_browser, null));
        
        initeCursor();
        
        myAdapter = new MyAdapter(lists);
        
        viewPager = (ViewPager) findViewById (R.id.viewPager);
       
        
        viewPager.setAdapter(myAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            
            @Override
            public void onPageSelected(int arg0) {//imageView�
                // TODO Auto-generated method stub
                switch (arg0)
                {
                case 0:
                    if (currentItem == 1)
                    {
                        animation = new TranslateAnimation(
                                offSet * 2 + bmWidth, 0 , 0, 0);
                    }
                    else if(currentItem == 2)
                    {
                        animation = new TranslateAnimation(
                                offSet * 4 + 2 * bmWidth, 0, 0, 0);
                    }
                    break;
                case 1:
                    if (currentItem == 0)
                    {
                        animation = new TranslateAnimation(
                                0, offSet * 2 + bmWidth, 0, 0);
                    }
                    else if (currentItem == 2)
                    {
                        animation = new TranslateAnimation(
2* offSet + 2 * bmWidth, offSet * 2 + bmWidth, 0, 0);
                    }
                    break;
                case 2:
                    if (currentItem == 0)
                    {
                        animation = new TranslateAnimation(
                                0, 4 * offSet + 2 * bmWidth, 0, 0);
                    }
                    else if (currentItem == 1)
                    {
                        animation = new TranslateAnimation(
                                offSet * 2 + bmWidth, 4 * offSet + 2 * bmWidth, 0, 0);
                    }
                }
                currentItem = arg0;
                animation.setDuration(500);
                animation.setFillAfter(true);
                imageView.startAnimation(animation);
                
            }
            
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
                
            }
        });
        
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    private void initeCursor()
    {
        cursor = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        bmWidth = cursor.getWidth();
        
        DisplayMetrics dm;
        dm = getResources().getDisplayMetrics();
        
        offSet = (dm.widthPixels - 3 * bmWidth) / 6;
        matrix.setTranslate(offSet, 0);
        imageView.setImageMatrix(matrix);                                             //��ҪiamgeView��scaleTypeΪmatrix
        currentItem = 0;
    }
}