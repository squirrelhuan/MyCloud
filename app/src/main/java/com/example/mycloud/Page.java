package com.example.mycloud;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;






import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.graphics.Matrix;
import android.widget.ImageView;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;






import java.util.List;
import java.util.ArrayList;




import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.graphics.Matrix;
import android.widget.ImageView;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher_menu);
 
        imageView = (ImageView) findViewById (R.id.cursor);
        textView1 = (TextView) findViewById (R.id.textView1);
        textView2 = (TextView) findViewById (R.id.textView2);
        textView3 = (TextView) findViewById (R.id.textView3);
        
       
        lists.add(getLayoutInflater().inflate(R.layout.home_menu_1, null));
        lists.add(getLayoutInflater().inflate(R.layout.home_menu_2, null));
        lists.add(getLayoutInflater().inflate(R.layout.home_menu_2, null));
        //lists.add(getLayoutInflater().inflate(R.layout.activity_browser, null));
        
        initeCursor();
        
        myAdapter = new MyAdapter(lists);
        
        viewPager = (ViewPager) findViewById (R.id.viewPager);
        viewPager.setAdapter(myAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            
            @Override
            public void onPageSelected(int arg0) {                                 //当滑动式，顶部的imageView是通过animation缓慢的滑动
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
        //imageView.setImageMatrix(matrix);                                             //需要iamgeView的scaleType为matrix
        currentItem = 0;
    }
}