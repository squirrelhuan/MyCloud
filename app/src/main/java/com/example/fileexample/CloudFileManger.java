package com.example.fileexample;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.mycloud.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CloudFileManger extends Activity implements OnItemClickListener{
	public Bundle mListViews;
	private List<? extends Map<String, ?>> herolist_wu;
	private String[] td;
	private int[][] layoutId = new int[][] {{R.layout.cell_listview_main,R.layout.cell_listview_main,R.layout.cell_listview_main},
			{R.layout.layout1,R.layout.layout2,R.layout.layout2,R.layout.layout4}};
	private int[][] imageId1=new int [][]{{R.drawable.ic_settings1_wifi,R.drawable.ic_settings1_network,R.drawable.ic_settings1_blueteeth,R.drawable.ic_settings1_more,R.drawable.ic_settings1_security
		,R.drawable.ic_settings1_gps,R.drawable.ic_settings1_account,R.drawable.ic_settings1_personality,R.drawable.ic_settings1_storage,R.drawable.ic_settings1_app,
		R.drawable.ic_settings1_network,R.drawable.ic_settings1_blueteeth,R.drawable.ic_settings1_buttery,R.drawable.ic_settings1_inputmethod,R.drawable.
		ic_settings1_phone,R.drawable.ic_settings1_more},{R.drawable.ic_settings2_volume,R.drawable.ic_settings2_silent_mode,R.drawable.ic_settings2_ring_incoming_call,
			R.drawable.ic_settings2_ring_sms,R.drawable.ic_settings2_ring_other_notification,R.drawable.ic_settings2_vibrate_when_ringing,R.drawable.ic_settings2_dial_sound,
			R.drawable.ic_settings2_touch_sound,R.drawable.ic_settings2_lock_sound,R.drawable.ic_settings2_power_on_off_sound,R.drawable.ic_settings2_touch_feedback},
			{R.drawable.ic_settings3_brightness,R.drawable.ic_settings3_wallpaper_settings,R.drawable.ic_settings3_hardkey_off_timeout,
				R.drawable.ic_settings3_accelerometer,R.drawable.ic_settings3_power_level,R.drawable.ic_settings3_accelerometer,R.drawable.ic_settings3_font,R.drawable.ic_settings3_festival_wallpaper}};
	
	private Bitmap cursor;
    private int offSet;
    private int currentItem;
    private Matrix matrix = new Matrix();
    private int bmWidth;
    private Animation animation;
    private ImageView imageView;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private int setting;
    private String re_name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.cell_viewpage);
		
		Intent intent =getIntent();
		Bundle bundle = intent.getExtras();
		re_name = bundle.getString("re_name");
		
		Resources res = this.getResources();
		String[] names1=res.getStringArray(R.array.main_setting_01);
		String[] names2=res.getStringArray(R.array.main_setting_02);
		String[] names3=res.getStringArray(R.array.main_setting_03);
		
		//LayoutInflater����
		   LayoutInflater inflater = getLayoutInflater();
		   //ViewPager
		   final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
		   //viewlistҳ������
		   ArrayList<View> viewList = new ArrayList<View>();
		   //LayoutInflaterҳ
		   imageView = (ImageView) findViewById (R.id.cursor);
	        textView1 = (TextView) findViewById (R.id.textView1);
	        textView2 = (TextView) findViewById (R.id.textView2);
	        textView3 = (TextView) findViewById (R.id.textView3);
		   int i=1;
		   if(re_name.equals("main_setting")){
			   i=0;  
			   textView1.setText("����");
			   textView2.setText("����");
			   textView3.setText("��ʾ");
		   }else if(re_name.equals("desktop_setting")){
			   i=0;
			   textView1.setText("��������");
			   textView2.setText("��������");
			   textView3.setText("���Ի��");
		   }
		   View view0 = inflater.inflate(layoutId[0][i+1], null);
		   View view1 = inflater.inflate(layoutId[0][i+1], null);
		   View view2 = inflater.inflate(layoutId[0][i+2], null);
		   ListView listview0 = (ListView) view0.findViewById(R.id.list);
		   ListView listview1 = (ListView) view1.findViewById(R.id.list);
		   ListView listview2 = (ListView) view2.findViewById(R.id.list);
		   ArrayList<HashMap<String,Object>> item1=new ArrayList<HashMap<String,Object>>();
		   ArrayList<HashMap<String,Object>> item2=new ArrayList<HashMap<String,Object>>();
		   ArrayList<HashMap<String,Object>> item3=new ArrayList<HashMap<String,Object>>();
		   switch (currentItem) {
		case 0:
			td=names1;
			break;
        case 1:
        	td=names2;
			break;
        case 3:
        	td=names2;
	        break;
		default:
			break;
		}
		   for(int j=0;j<names1.length;j++){
			   HashMap<String,Object>item_web=new HashMap<String,Object>();
			   item_web.put("username",names1[j]);
			   item_web.put("age","11");
			   item_web.put("img",imageId1[0][j] );
			   item1.add(item_web);
		   }
		   for(int j=0;j<names2.length;j++){
			   HashMap<String,Object>item_web=new HashMap<String,Object>();
			   item_web.put("username",names2[j]);
			   item_web.put("age","11");
			   item_web.put("img",imageId1[1][j] );
			   item2.add(item_web);
		   }
		   for(int j=0;j<names3.length;j++){
			   HashMap<String,Object>item_web=new HashMap<String,Object>();
			   item_web.put("username",names3[j]);
			   item_web.put("age","11");
			   item_web.put("img",imageId1[2][j] );
			   item3.add(item_web);
		   }
		   SimpleAdapter simpleAdapter_1 = new SimpleAdapter(this, item1,
				    R.layout.cell_listview_item_image,
				    new String[]{"img", "username"},
				    new int[]{R.id.img, R.id.name});
		   SimpleAdapter simpleAdapter_2 = new SimpleAdapter(this, item2,
				    R.layout.cell_listview_item_image,
				    new String[]{"img", "username"},
				    new int[]{R.id.img, R.id.name});
		   SimpleAdapter simpleAdapter_3 = new SimpleAdapter(this, item3,
				    R.layout.cell_listview_item_image,
				    new String[]{"img", "username"},
				    new int[]{R.id.img, R.id.name});
		   listview0.setAdapter(simpleAdapter_1);
		   listview0.setOnItemClickListener(this);
		   listview1.setAdapter(simpleAdapter_2);
		   listview2.setAdapter(simpleAdapter_3);

		   //��ӷ�ҳ��list��
		   viewList.add(view0);
		   viewList.add(view1);
		   viewList.add(view2);
		   viewPager.setAdapter(new MyAdapter(viewList));	
		   
		   
	        
	        initeCursor();
	        
	        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
	            
	            @Override
	            public void onPageSelected(int arg0) {                                 //������ʽ��������imageView��ͨ��animation�����Ļ���
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
	public class MyAdapter extends PagerAdapter{

	    List<View> viewLists;
	    
	    public MyAdapter(List<View> lists)
	    {
	        viewLists = lists;
	    }

	    @Override
	    public int getCount() {                                                                 //���size
	        // TODO Auto-generated method stub
	        return viewLists.size();
	    }

	    @Override
	    public boolean isViewFromObject(View arg0, Object arg1) {                         
	        // TODO Auto-generated method stub
	        return arg0 == arg1;
	    }
	    
	    @Override
	    public void destroyItem(View view, int position, Object object)                       //����Item
	    {
	        ((ViewPager) view).removeView(viewLists.get(position));
	    }
	    
	    @Override
	    public Object instantiateItem(View view, int position)                                //ʵ����Item
	    {
	        ((ViewPager) view).addView(viewLists.get(position), 0);
	        return viewLists.get(position);
	    }
	    
	}
	 @Override      protected void onDestroy() {         
		 super.onDestroy();         
		 overridePendingTransition( R.anim.in_from_left,R.anim.out_to_right);
      }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		if(re_name.equals("main_setting")){
			Intent intent=new Intent();
			intent.setClass(CloudFileManger.this, cells.Contant.class);
			Bundle data=new Bundle();
			switch (position) {
			case 0:
				
			break;
			case 1:data.putString("re_name", "gps");
			    break;
			case 2:data.putString("re_name", "storage");
				break;
			case 3:data.putString("re_name", "Telephony");
				break;
			case 4:data.putString("re_name", "storage");
				break;
			case 5:data.putString("re_name", "storage");
				break;
			case 6:data.putString("re_name", "storage");
				break;
			case 7:data.putString("re_name", "storage");
				break;
			case 8:data.putString("re_name", "storage");
				break;
			case 9:data.putString("re_name", "storage");
				break;
			case 10:data.putString("re_name", "storage");
				break;
			case 11:data.putString("re_name", "storage");
				break;
			case 12:data.putString("re_name", "storage");
				break;
			case 13:data.putString("re_name", "storage");
				break;
			case 14:data.putString("re_name", "mobile");
			    break;
			case 15:data.putString("re_name", "mobile");
		        break;
			default:
				break;
			}
			intent.putExtras(data);
			CloudFileManger.this.startActivity(intent);
		}
	}  
	}