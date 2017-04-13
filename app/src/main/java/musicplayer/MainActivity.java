package musicplayer;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import service.PreferencesService;
import service.dbopenhelper;
import cells.Welcome;

import com.example.mycloud.AllAppList;
import com.example.mycloud.R;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import musicplayer.MusicLoader.MusicInfo;
import musicplayer.CMusicService.NatureBinder;

public class MainActivity extends Activity implements OnClickListener, OnScrollListener{
	NotificationManager notificationManager;
	public static final String TAG = "musicplayer.MAIN_ACTIVITY";
	public ViewPager viewPager;
	private ListView lvSongs;	
	private SeekBar musicSeekBar;
	private TextView music_name;
	private List<MusicInfo> musicList;
	private int bmWidth,offSet,currentItem,currentMusic; // The music that is playing.
	private int currentPosition,currentpoint; //The position of the music is playing.
	private int currentMax;
	private boolean scrollFlag = false;
	private int lastVisibleItemPosition;
	private File tempFile;
	private static PreferencesService service;
	private RelativeLayout rel_01;
	private Animation animation;
	private TextView musicMaxTime_text,musiccurrentTime;
	private ImageView ic_users,startImgBtn,stopImgBtn,rewind_down,Fast_Forward,play_mode,music_bar1,music_bar3,music_bar4;
	private ProgressReceiver progressReceiver;	
	private NatureBinder natureBinder;	
	public Adapter adapter;
	
	private ServiceConnection serviceConnection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			natureBinder = (NatureBinder) service;			
		}
	};
	
	private void connectToNatureService(){		
		Intent intent = new Intent(MainActivity.this, CMusicService.class);				
		bindService(intent, serviceConnection,  BIND_AUTO_CREATE);				
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "OnCreate");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.music_viewpage);	
		service=new PreferencesService(this);
		Map<String,String> params=service.getcurrentmusic();
		String ccu=params.get("currentID");
		String ccu2=params.get("currentTIME");
		currentMusic=Integer.valueOf(ccu);
		currentpoint=Integer.valueOf(ccu2);
		//natureBinder.changeProgress(currentpoint);
		//Toast.makeText(getApplicationContext(), currentMusic, 1).show();
		
//		Intent intent13 =getIntent();
//		Bundle bundle13 = intent13.getExtras();
		//String name = bundle13.getString("re_name");
//		if(re_name.equals("desktop_setting")){
//			play(currentMusic,R.id.startImgBtn);
//		   }
		
		MusicLoader musicLoader = MusicLoader.instance(getContentResolver());		
		musicList = musicLoader.getMusicList();
		connectToNatureService();
		initComponents();	
		//viewPager.setCurrentItem(1);
	}
	
	public void onResume(){
		Log.v(TAG, "OnResume register Progress Receiver");
		super.onResume();										
		registerReceiver();
		if(natureBinder != null){
			if(natureBinder.isPlaying()){
				startImgBtn.setBackgroundResource(R.drawable.button_pause_flag_normal);
				animation(0);
			}else{
				startImgBtn.setBackgroundResource(R.drawable.button_play_flag_normal);
			}
		}		
	}	
	
	public void onPause(){
		Log.v(TAG, "OnPause unregister Progress Receiver");
		super.onPause();
		unregisterReceiver(progressReceiver);
	}
	
	public void onStop(){
		Log.v(TAG, "OnStop");
		super.onStop();				
	}
	
	public void onDestroy(){
		Log.v(TAG, "OnDestroy");
		super.onDestroy();
		if(natureBinder != null){
			unbindService(serviceConnection);
		}
		privateparametersave(currentMusic,currentpoint);
	}
	public static void privateparametersave(int position,int point){		
	     int currentID=position;
	     int currentTIME=point;		
		service.savecurrentmusic(currentID,currentTIME);
	}	
	private void initComponents(){
		   LayoutInflater inflater = getLayoutInflater();
		    viewPager = (ViewPager) findViewById(R.id.viewPager);
		   ArrayList<View> viewList = new ArrayList<View>();
		   View view0 = inflater.inflate(R.layout.music_0, null);
		   View view1 = inflater.inflate(R.layout.music_1, null);
		   View view2 = inflater.inflate(R.layout.music_2, null);
		   rel_01=(RelativeLayout) findViewById(R.id.rel_01);
		   music_name=(TextView) view1.findViewById(R.id.music_name);
		   play_mode=(ImageView) findViewById(R.id.play_mode);
		   play_mode.setOnClickListener(this);
		   musiccurrentTime=(TextView) findViewById(R.id.musiccurrentTime);
		   musicMaxTime_text=(TextView) findViewById(R.id.musicMaxTime_text);
		   ic_users=(ImageView) findViewById(R.id.ic_users);
		   ic_users.setOnClickListener(this);
		      startImgBtn = (ImageView) findViewById(R.id.startImgBtn);
			  startImgBtn.setOnClickListener(this);
			  stopImgBtn = (ImageView) findViewById(R.id.stopImgBtn);
			  stopImgBtn.setOnClickListener(this); 
			  rewind_down=(ImageView) findViewById(R.id.rewind_down);
			  rewind_down.setOnClickListener(this);
			  Fast_Forward=(ImageView) findViewById(R.id.Fast_Forward);
			  Fast_Forward.setOnClickListener(this);
			  music_bar1=(ImageView) view1.findViewById(R.id.music_bar1);
			  music_bar1.setOnClickListener(this);
			  music_bar4=(ImageView) view1.findViewById(R.id.music_bar4);  
			  music_bar3=(ImageView) view1.findViewById(R.id.music_bar3);
			  musicSeekBar=(SeekBar) findViewById(R.id.musicSeekBar);
			  
			  musicSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					
					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {}
					
					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {}
					
					@Override
					public void onProgressChanged(SeekBar seekBar, int progress,
							boolean fromUser) {
						if(fromUser){
							natureBinder.changeProgress(progress);
						}
					}
				});

			// ͼ��ҳ
				GridView gridview1 = (GridView) view0.findViewById(R.id.gridview1);// ���ɶ�̬���飬����ת������
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
			  
		MusicAdapter adapter = new MusicAdapter();
		lvSongs = (ListView) view2.findViewById(R.id.lvSongs);		
		lvSongs.setAdapter(adapter);
		lvSongs.setOnScrollListener(this);
		lvSongs.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				currentMusic = position;
				natureBinder.startPlay(currentMusic,0);
				if(natureBinder.isPlaying()){
					startImgBtn.setBackgroundResource(R.drawable.button_pause_flag_normal);
					animation(0);
				}
			}
		});
		lvSongs.setOnLongClickListener(new OnLongClickListener(){

			@Override
			public boolean onLongClick(View v) {
				
				return false;
			}
			
		});
		//��ӷ�ҳ��list��
		  viewList.add(view0);
		  viewList.add(view1);
		  viewList.add(view2);
		  viewPager.setAdapter(new MyAdapter(viewList));
		  
		  viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {    
		      @Override
		      public void onPageSelected(int arg0) {                                 //������ʽ��������imageView��ͨ��animation�����Ļ���
		          // TODO Auto-generated method stub
		          switch (arg0)
		          {
		          case 0:
		        	  
		              if (currentItem == 1)
		              {
		            	  rel_01.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.bottom_down));
		            	  rel_01.setVisibility(View.GONE);
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
		        	  rel_01.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.bottom_up));
		        	  rel_01.setVisibility(View.VISIBLE);
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
		        	  rel_01.setVisibility(View.VISIBLE);
		              if (currentItem == 0)
		              {
		                  animation = new TranslateAnimation(
		                          0, 4 * offSet + 2 * bmWidth, 0, 0);
		              }
		              else if (currentItem == 1)
		              {
		            	  rel_01.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.bottom_down));
		            	  rel_01.setVisibility(View.GONE);
		                  animation = new TranslateAnimation(
		                          offSet * 2 + bmWidth, 4 * offSet + 2 * bmWidth, 0, 0);
		              }
		          }
		          currentItem = arg0;      
		          animation.setDuration(500);
		          animation.setFillAfter(true);        
		      }
		      
		      @Override
		      public void onPageScrolled(int arg0, float arg1, int arg2) {
		          // TODO Auto-generated method stub   
//		    	  arg0 :��ǰҳ�棬������������ҳ��
//
//		    	  arg1:��ǰҳ��ƫ�Ƶİٷֱ�
//
//		    	  arg2:��ǰҳ��ƫ�Ƶ�����λ�� 
		    	 
		      }      
		      @Override
		      public void onPageScrollStateChanged(int arg0) {
		          // TODO Auto-generated method stub         
		      }
		  });
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
	
	private void registerReceiver(){
		progressReceiver = new ProgressReceiver();	
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(CMusicService.ACTION_UPDATE_PROGRESS);
		intentFilter.addAction(CMusicService.ACTION_UPDATE_DURATION);
		intentFilter.addAction(CMusicService.ACTION_UPDATE_CURRENT_MUSIC);
		registerReceiver(progressReceiver, intentFilter);
	}
	
	class MusicAdapter extends BaseAdapter{

		@Override 
		public int getCount() {
			return musicList.size();
		}

		@Override
		public Object getItem(int position) {
			return musicList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return musicList.get(position).getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder; 
			if(convertView == null){
				convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.music_item, null);
				ImageView pImageView = (ImageView) convertView.findViewById(R.id.albumPhoto);
				TextView pTitle = (TextView) convertView.findViewById(R.id.title);
				TextView pDuration = (TextView) convertView.findViewById(R.id.duration);
				TextView pArtist = (TextView) convertView.findViewById(R.id.artist);
				viewHolder = new ViewHolder(pImageView, pTitle, pDuration, pArtist);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
			viewHolder.imageView.setImageResource(R.drawable.audio);
			viewHolder.title.setText(musicList.get(position).getTitle());
			viewHolder.duration.setText(FormatHelper.formatDuration(musicList.get(position).getDuration()));
			viewHolder.artist.setText(musicList.get(position).getArtist());
			
			return convertView;
		}		
	}
	
	class ViewHolder{
		public ViewHolder(ImageView pImageView, TextView pTitle, TextView pDuration, TextView pArtist){
			imageView = pImageView;
			title = pTitle;
			duration = pDuration;
			artist = pArtist;
		}
		
		ImageView imageView;
		TextView title;
		TextView duration;
		TextView artist;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {		
		switch (v.getId()) {		
		case R.id.startImgBtn:
			play(currentMusic,R.id.startImgBtn);
			break;
		case R.id.music_bar1:
			play(currentMusic,R.id.startImgBtn);
			break;
		case R.id.Fast_Forward:
			natureBinder.toNext();
			animation(0);
			break;
		case R.id.rewind_down:
			natureBinder.toLast();
			animation(0);
			break;
		case R.id.play_mode:						
			natureBinder.changeMode();
			break;
		case R.id.ic_users:  
			startActivityForResult(new Intent(MainActivity.this, userscenter.MainActivity.class), 1);
			break;
			/*case R.id.btnDetail:						
			Intent intent = new Intent(MainActivity.this,DetailActivity.class);
			intent.putExtra(DetailActivity.MUSIC_LENGTH, currentMax);
			intent.putExtra(DetailActivity.CURRENT_MUSIC, currentMusic);
			intent.putExtra(DetailActivity.CURRENT_POSITION, currentPosition);			
			startActivity(intent);		*/	
			
		}		
	}
	
	private void play(int position, int resId){		
		
		if(natureBinder.isPlaying()){
			natureBinder.stopPlay();
			startImgBtn.setBackgroundResource(R.drawable.button_play_flag_normal);
			animation(1);
		}else{
			natureBinder.startPlay(position,currentPosition);
			startImgBtn.setBackgroundResource(R.drawable.button_pause_flag_normal);
			animation(0);
		}
	}

	@SuppressWarnings("static-access")
	public void animation(int st){
		LinearInterpolator lir = new LinearInterpolator();
		if(st==0){
		AnimationSet animationSet1 = new AnimationSet(true);
		  RotateAnimation rotateAnimation1 = new RotateAnimation(0, 25,
		          Animation.RELATIVE_TO_SELF,0.5f,
		          Animation.RELATIVE_TO_SELF,0.1f);
		   rotateAnimation1.setDuration(300);
		   rotateAnimation1.setFillAfter(true);
		   music_bar1.startAnimation(rotateAnimation1);
		  RotateAnimation rotateAnimation4 = new RotateAnimation(0, 360,
		          Animation.RELATIVE_TO_SELF,0.5f,
		          Animation.RELATIVE_TO_SELF,0.5f); 
		  rotateAnimation4.setInterpolator(lir);
		  rotateAnimation4.setDuration(20000);
		  rotateAnimation4.setRepeatMode(rotateAnimation4.RESTART);
		  rotateAnimation4.setRepeatCount(10000);
		   music_bar4.startAnimation(rotateAnimation4);
		   RotateAnimation rotateAnimation3 = new RotateAnimation(0, -360,
		           Animation.RELATIVE_TO_SELF,0.5f,
		           Animation.RELATIVE_TO_SELF,0.5f);
			  rotateAnimation3.setInterpolator(lir);
			  rotateAnimation3.setDuration(20000);
			  rotateAnimation3.setRepeatMode(rotateAnimation4.RESTART);
			  rotateAnimation3.setRepeatCount(10000);
		    music_bar3.startAnimation(rotateAnimation3);}
		else{
			AnimationSet animationSet1 = new AnimationSet(true);
			  RotateAnimation rotateAnimation1 = new RotateAnimation(0, 0,
			          Animation.RELATIVE_TO_SELF,0.5f,
			          Animation.RELATIVE_TO_SELF,0.1f);
			   animationSet1.addAnimation(rotateAnimation1);
			   rotateAnimation1.setDuration(300);
			   animationSet1.setFillAfter(true);
			   music_bar1.startAnimation(animationSet1);
			   music_bar3.clearAnimation();
			   music_bar4.clearAnimation();
		}
	}

	class ProgressReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if(CMusicService.ACTION_UPDATE_PROGRESS.equals(action)){
				int progress = intent.getIntExtra(CMusicService.ACTION_UPDATE_PROGRESS, 0);
				if(progress > 0){
					currentpoint=progress;
					currentPosition = progress; // Remember the current position
					musicSeekBar.setProgress(progress/1000);
					musiccurrentTime.setText(FormatHelper.formatDuration(progress));
				}
			}else if(CMusicService.ACTION_UPDATE_CURRENT_MUSIC.equals(action)){
				//Retrive the current music and get the title to show on top of the screen.
				currentMusic = intent.getIntExtra(CMusicService.ACTION_UPDATE_CURRENT_MUSIC, 0);				
				music_name.setText(musicList.get(currentMusic).getTitle());
			}else if(CMusicService.ACTION_UPDATE_DURATION.equals(action)){
				//Receive the duration and show under the progress bar
				//Why do this ? because from the ContentResolver, the duration is zero.
				currentMax = intent.getIntExtra(CMusicService.ACTION_UPDATE_DURATION, 0);	
				int max = currentMax / 1000;
				Log.v(TAG, "[Main ProgressReciver] Receive duration : " + max);
				musicSeekBar.setMax(currentMax/1000);
				musicMaxTime_text.setText(FormatHelper.formatDuration(currentMax));
			}
		}
		
	}
	@Override  
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
		super.onActivityResult(requestCode, resultCode, data);  
	    switch (requestCode) {  
	    case 1:  
	    	ic_users.setImageDrawable(Drawable.createFromPath(tempFile.getAbsolutePath()));  
	    }
	}


	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if (scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
			scrollFlag = true;
			} else {
			scrollFlag = false;
			}
	}


	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		if (scrollFlag) {
			if (firstVisibleItem > lastVisibleItemPosition) {
				if (rel_01.getVisibility() == View.VISIBLE) {
				rel_01.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.bottom_down));
				rel_01.setVisibility(View.GONE);}
			Log.d("dc", "�ϻ�");
			}
			if (firstVisibleItem < lastVisibleItemPosition) {
				if (rel_01.getVisibility() == View.GONE) {
				rel_01.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.bottom_up));
				rel_01.setVisibility(View.VISIBLE);}
			Log.d("dc", "�»�");
			}
			if (firstVisibleItem == lastVisibleItemPosition) {
			return;
			}
			lastVisibleItemPosition = firstVisibleItem;
			}
	}


}
