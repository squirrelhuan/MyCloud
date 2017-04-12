package musicplayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import com.example.mycloud.R;
import musicplayer.CMusicService;
import musicplayer.FormatHelper;
import musicplayer.CMusicService.NatureBinder;
import musicplayer.MusicLoader;
import musicplayer.MusicLoader.MusicInfo;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class Musicplayer extends Activity implements OnClickListener{
	
private ImageView startImgBtn,stopImgBtn,rewind_down,Fast_Forward,music_bar1,music_bar3,music_bar4;
private String path = "/sdcard/001.mp4";
private SeekBar musicSeekBar = null;
boolean isPaused=false,isChanging=false;
private Handler handler;
private DelayThread dThread = null;
private TextView musicMaxTime_text,musiccurrentTime,tvCurrentMusic;;
private int offSet;
private int currentItem,currentMax;
private int bmWidth;
private Animation animation;
private WordView mWordView;
private int currentMusic,currentPosition; // The music that is playing.
private NatureBinder natureBinder;	
private ListView lvSongs;
private List mTimeList;
private List<MusicInfo> musicList;
private Button btnStartStop,btnNext, btnDetail;
private ProgressReceiver progressReceiver;

//声明音乐的状态常量
private final int MEDIAPLAYER_PAUSE = 0;//暂停
private final int MEDIAPLAYER_PLAY = 1;//播放中
private final int MEDIAPLAYER_STOP = 2;//停止
//音乐的当前的状态
private int mediaSate = 0;
//声明一个音乐播放器
private MediaPlayer mediaPlayer;
//当前音乐播放的时间点
private int currentTime;
//当前音乐的总时间
private int musicMaxTime;
//当前音乐的音量大小
private int currentVolume,maxVolume;
//快进、快退时间戳
private int setTime = 5000;
//播放器管理类
private AudioManager audiomanage;

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
	Intent intent = new Intent(Musicplayer.this, CMusicService.class);				
	bindService(intent, serviceConnection, BIND_AUTO_CREATE);				
}
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.music_viewpage);
	
	Intent intent=getIntent();
    path=intent.getDataString(); 
    
 // 创建MediaPlayer实例
    mediaPlayer = new MediaPlayer();
    audiomanage = (AudioManager)getSystemService(Context.AUDIO_SERVICE);      
	maxVolume = audiomanage.getStreamMaxVolume(AudioManager.STREAM_MUSIC);  //获取系统最大音量  
	currentVolume = audiomanage.getStreamVolume(AudioManager.STREAM_MUSIC);  //获取当前值
  
    MusicLoader musicLoader = MusicLoader.instance(getContentResolver());		
	musicList = musicLoader.getMusicList();
	connectToNatureService();
	initComponents(); 
}
public void onResume(){

	super.onResume();										
	registerReceiver();
	if(natureBinder != null){
		if(natureBinder.isPlaying()){
			btnStartStop.setBackgroundResource(R.drawable.baidu);
		}else{
			btnStartStop.setBackgroundResource(R.drawable.baidu);
		}
		natureBinder.notifyActivity();
	}
}

public void onPause(){
	super.onPause();
	unregisterReceiver(progressReceiver);
}

public void onStop(){
	super.onStop();				
}

public void onDestroy(){
	super.onDestroy();
	if(natureBinder != null){
		unbindService(serviceConnection);
	}
}
private void initComponents(){
	//实例化一个LayoutInflater对象
	   LayoutInflater inflater = getLayoutInflater();
	   //通过步骤1中声明的组件ID来获取ViewPager
	   final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
	   //新建一个viewlist对象来保存各个分页的内容
	   ArrayList<View> viewList = new ArrayList<View>();
	   //通过LayoutInflater来实例化各个分页
	   View view0 = inflater.inflate(R.layout.music_1, null);
	   View view1 = inflater.inflate(R.layout.music_1, null);
	   View view2 = inflater.inflate(R.layout.music_lrc, null);
	   musicSeekBar=(SeekBar)findViewById(R.id.musicSeekBar);
		 musiccurrentTime=(TextView) findViewById(R.id.musiccurrentTime);
		 musicMaxTime_text=(TextView) findViewById(R.id.musicMaxTime_text);
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
	
	
   
	

	btnStartStop.setOnClickListener(this);	

	btnNext.setOnClickListener(this);
	

	btnDetail.setOnClickListener(this);
    MusicAdapter adapter = new MusicAdapter();
	lvSongs = (ListView) view2.findViewById(R.id.lvSongs);		
	lvSongs.setAdapter(adapter);
	lvSongs.setOnItemClickListener(new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			currentMusic = position;
			natureBinder.startPlay(currentMusic,0);
			if(natureBinder.isPlaying()){					
				btnStartStop.setBackgroundResource(R.drawable.audio);		
			}
		}
	});	
  //歌词显示	
	/*
  mWordView = (WordView) view1.findViewById(R.id.text);
  mMediaPlayer.reset();
  LrcHandle lrcHandler = new LrcHandle();
  try {
  lrcHandler.readLRC("/sdcard/陪我去流浪.lrc");
  mTimeList = lrcHandler.getTime();
  mMediaPlayer.setDataSource("/sdcard/陪我去流浪.mp3");
  mMediaPlayer.prepare();
  } catch (IOException e) {
  e.printStackTrace();
  } catch (IllegalArgumentException e) {
  e.printStackTrace();
  } catch (SecurityException e) {
  e.printStackTrace();
  } catch (IllegalStateException e) {
  e.printStackTrace();
  }
  final Handler handler1 = new Handler();
  mMediaPlayer.start();
  new Thread(new Runnable() {
  int i = 0;

  @Override
  public void run() {
  while (mMediaPlayer.isPlaying()) {
  handler1.post(new Runnable() {

  @Override
  public void run() {
  mWordView.invalidate();
  }
  });
  try {
  	int j=(Integer) mTimeList.get(i + 1)- (Integer)mTimeList.get(i);
  Thread.sleep(j);
  } catch (InterruptedException e) {
  }
  i++;
  if (i == mTimeList.size() - 1) {
	  mMediaPlayer.stop();
  break;
  }
  }
  }
  }).start();
*/
  
//添加分页到list中
  viewList.add(view0);
  viewList.add(view1);
  viewList.add(view2);
  viewPager.setAdapter(new MyAdapter(viewList));	
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
  /*
   musicSeekBar.setOnSeekBarChangeListener(new SeekBarChangeEvent()); 	
   mMediaPlayer.reset();

   mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {			
			public void onCompletion(MediaPlayer mp) {
				Toast.makeText(Musicplayer.this, "结束", 1000).show();
				startImgBtn.setVisibility(View.VISIBLE);
				stopImgBtn.setVisibility(View.GONE);
				mMediaPlayer.reset();
			}
		});
   handler=new Handler(){
       public void handleMessage(Message msg){
    	   if (mMediaPlayer != null) {
   	int currentTime = mMediaPlayer.getCurrentPosition();
   	musicSeekBar.setProgress(currentTime);		
   	musiccurrentTime.setText(FormatHelper.formatDuration(currentTime));
   		}
       }
   };*///实现消息传递
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
				convertView = LayoutInflater.from(Musicplayer.this).inflate(R.layout.music_item, null);
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

public void startProgressUpdate() {
	dThread = new DelayThread(100);
	dThread.start();
}
 public  void musicstart(){
	  if(mediaPlayer!=null){ 
      }else{  
    	  mediaPlayer.reset(); 
       } 
		try 
		{//if(i/2==0){mMediaPlayer.reset();i++;}
			animation(0);
			
			// 设置播放视频源
			mediaPlayer.setDataSource(path);
			// 设置音轨流的类型
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			// 准备播放
			mediaPlayer.prepare();
			musicSeekBar.setMax(mediaPlayer.getDuration());
			//获取音乐文件的总时间
		 	  musicMaxTime = mediaPlayer.getDuration();  	  	  
     musicMaxTime_text.setText(FormatHelper.formatDuration(musicMaxTime));
     
	    } catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 开始播放
		mediaPlayer.start();
		// 开始更新进度条
        startProgressUpdate();
 }

@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()) {
	case R.id.music_bar1:
		if(startImgBtn.getVisibility()==View.GONE){
			animation(1);
			startImgBtn.setVisibility(View.VISIBLE);
			stopImgBtn.setVisibility(View.GONE);
			mediaPlayer.pause();
		}else{musicstart();}
		break;
	case R.id.rewind_down:	
		if (0<currentTime - setTime) {
			//mMediaPlayer.seekTo(musicMaxTime);
			mediaPlayer.seekTo(currentTime - setTime);	
		} else {	
		}
		break;
	case R.id.Fast_Forward:
		if (currentTime + setTime >= musicMaxTime) {
			//mMediaPlayer.seekTo(musicMaxTime);
			mediaPlayer.seekTo(currentTime + setTime);
		} else {
			
		}
		break;
	case R.id.startImgBtn:
		//int i=1;
		musicstart();
		
		break;
	case R.id.stopImgBtn:
		animation(1);
		mediaPlayer.pause();
		break;
	
						
		/*Intent intent = new Intent(MainActivity.this,DetailActivity.class);
		intent.putExtra(DetailActivity.MUSIC_LENGTH, currentMax);
		intent.putExtra(DetailActivity.CURRENT_MUSIC, currentMusic);
		intent.putExtra(DetailActivity.CURRENT_POSITION, currentPosition);			
		startActivity(intent);	*/		

	default:
		break;
	}
}
public void animation(int st){
	if(st==0){
		startImgBtn.setVisibility(View.GONE);
		stopImgBtn.setVisibility(View.VISIBLE);
	AnimationSet animationSet1 = new AnimationSet(true);
	  RotateAnimation rotateAnimation1 = new RotateAnimation(0, 25,
	          Animation.RELATIVE_TO_SELF,0.5f,
	          Animation.RELATIVE_TO_SELF,0.1f);
	   animationSet1.addAnimation(rotateAnimation1);
	   rotateAnimation1.setDuration(200);
	   animationSet1.setFillAfter(true);
	   music_bar1.startAnimation(animationSet1);
	 AnimationSet animationSet4 = new AnimationSet(true);
	  RotateAnimation rotateAnimation4 = new RotateAnimation(0, 8800,
	          Animation.RELATIVE_TO_SELF,0.5f,
	          Animation.RELATIVE_TO_SELF,0.5f);
	   animationSet4.addAnimation(rotateAnimation4);
	   animationSet4.setFillAfter(true);
	   rotateAnimation4.setDuration(100000);
	   music_bar4.startAnimation(animationSet4);
	   AnimationSet animationSet2 = new AnimationSet(true);
	   RotateAnimation rotateAnimation = new RotateAnimation(0, -8800,
	           Animation.RELATIVE_TO_SELF,0.5f,
	           Animation.RELATIVE_TO_SELF,0.5f);
	    rotateAnimation.setDuration(100000);
	    animationSet2.setFillAfter(true);
	    animationSet2.addAnimation(rotateAnimation);
	    music_bar3.startAnimation(animationSet2);}
	else{
		startImgBtn.setVisibility(View.VISIBLE);
		stopImgBtn.setVisibility(View.GONE);
		AnimationSet animationSet1 = new AnimationSet(true);
		  RotateAnimation rotateAnimation1 = new RotateAnimation(0, 0,
		          Animation.RELATIVE_TO_SELF,0.5f,
		          Animation.RELATIVE_TO_SELF,0.1f);
		   animationSet1.addAnimation(rotateAnimation1);
		   rotateAnimation1.setDuration(200);
		   animationSet1.setFillAfter(true);
		   music_bar1.startAnimation(animationSet1);
	}
}
class SeekBarChangeEvent implements OnSeekBarChangeListener{
    @Override
    public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
        // TODO Auto-generated method stub  
    }
    @Override
    public void onStartTrackingTouch(SeekBar arg0) {
//开始拖动进度条
        // TODO Auto-generated method stub
         //isChanging=true; 
    }
    @Override
    public void onStopTrackingTouch(SeekBar arg0) {
//停止拖动进度条
        // TODO Auto-generated method stub
    	mediaPlayer.seekTo(arg0.getProgress());
//将media进度设置为当前seekbar的进度
       // isChanging=false; 
    }
}
class DelayThread extends Thread{
    int milliseconds;
    public DelayThread(int i){
        milliseconds=i;
    }
    public void run(){
        while(true){
            try {
                sleep(milliseconds);
           //设置音乐进度读取频率
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(0);
        }
    }
    
}
public class MyAdapter extends PagerAdapter{

    List<View> viewLists;
    
    public MyAdapter(List<View> lists)
    {
        viewLists = lists;
    }

    @Override
    public int getCount() {                                                                 //获得size
        // TODO Auto-generated method stub
        return viewLists.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {                         
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }
    
    @Override
    public void destroyItem(View view, int position, Object object)                       //销毁Item
    {
        ((ViewPager) view).removeView(viewLists.get(position));
    }
    
    @Override
    public Object instantiateItem(View view, int position)                                //实例化Item
    {
        ((ViewPager) view).addView(viewLists.get(position), 0);
        return viewLists.get(position);
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
 private void play(int position, int resId){		
		if(natureBinder.isPlaying()){
			natureBinder.stopPlay();
			btnStartStop.setBackgroundResource(R.drawable.sina);
		}else{
			natureBinder.startPlay(position,currentPosition);
			btnStartStop.setBackgroundResource(R.drawable.sohu);
		}
	}

	

	class ProgressReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if(CMusicService.ACTION_UPDATE_PROGRESS.equals(action)){
				int progress = intent.getIntExtra(CMusicService.ACTION_UPDATE_PROGRESS, 0);
				if(progress > 0){
					currentPosition = progress; // Remember the current position
					musicSeekBar.setProgress(progress / 1000);
				}
			}else if(CMusicService.ACTION_UPDATE_CURRENT_MUSIC.equals(action)){
				//Retrive the current music and get the title to show on top of the screen.
				currentMusic = intent.getIntExtra(CMusicService.ACTION_UPDATE_CURRENT_MUSIC, 0);				
				tvCurrentMusic.setText(musicList.get(currentMusic).getTitle());
			}else if(CMusicService.ACTION_UPDATE_DURATION.equals(action)){
				//Receive the duration and show under the progress bar
				//Why do this ? because from the ContentResolver, the duration is zero.
				currentMax = intent.getIntExtra(CMusicService.ACTION_UPDATE_DURATION, 0);				
				int max = currentMax / 1000;
				musicSeekBar.setMax(currentMax / 1000);						
			}
		}
		
	}
}
