package musicplayer;

import java.util.List;

import service.PreferencesService;

import com.example.mycloud.AllAppList;
import com.example.mycloud.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebView.FindListener;
import android.widget.RemoteViews;
import android.widget.Toast;

public class CMusicService extends Service{

	private static final String TAG = "com.example.nature.NATURE_SERVICE";
	
	public static final String MUSICS = "com.example.nature.MUSIC_LIST";
	
	public static final String NATURE_SERVICE = "com.example.nature.NatureService";	
	
	private MediaPlayer mediaPlayer;
	
	private boolean isPlaying = false;
	
	private List<MusicLoader.MusicInfo> musicList;
	
	private Binder natureBinder = new NatureBinder();
	
	private int currentMusic,progress;
	private int currentPosition;
	
	private static final int updateProgress = 1;
	private static final int updateCurrentMusic = 2;
	private static final int updateDuration = 3;
	
	public static final String ACTION_UPDATE_PROGRESS = "musicplayer.UPDATE_PROGRESS";
	public static final String ACTION_UPDATE_DURATION = "musicplayer.UPDATE_DURATION";
	public static final String ACTION_UPDATE_CURRENT_MUSIC = "musicplayer.UPDATE_CURRENT_MUSIC";
	private static PreferencesService service;
	private int currentMode = 3; //default sequence playing
	
	public static final String[] MODE_DESC = {"", "", "", ""};
	
	public static final int MODE_ONE_LOOP = 0;
	public static final int MODE_ALL_LOOP = 1;
	public static final int MODE_RANDOM = 2;
	public static final int MODE_SEQUENCE = 3; 
	
	private Notification notification;
	private NotificationManager notificationManager; 
		
	private Handler handler = new Handler(){
		
		public void handleMessage(Message msg){
			switch(msg.what){
			case updateProgress:				
				toUpdateProgress();
				break;
			case updateDuration:				
				toUpdateDuration();
				break;
			case updateCurrentMusic:
				toUpdateCurrentMusic();
				break;
			}
		}
	};
	
	private void toUpdateProgress(){
		if(mediaPlayer != null && isPlaying){					
			progress = mediaPlayer.getCurrentPosition();					
			Intent intent = new Intent();
			intent.setAction(ACTION_UPDATE_PROGRESS);
			intent.putExtra(ACTION_UPDATE_PROGRESS,progress);
			sendBroadcast(intent);
			handler.sendEmptyMessageDelayed(updateProgress, 1000);					
		}
	}
	
	private void toUpdateDuration(){
		if(mediaPlayer != null){					
			int duration = mediaPlayer.getDuration();					
			Intent intent = new Intent();
			intent.setAction(ACTION_UPDATE_DURATION);
			intent.putExtra(ACTION_UPDATE_DURATION,duration);
			sendBroadcast(intent);									
		}
	}
	
	private void toUpdateCurrentMusic(){
		Intent intent = new Intent();
		intent.setAction(ACTION_UPDATE_CURRENT_MUSIC);
		intent.putExtra(ACTION_UPDATE_CURRENT_MUSIC,currentMusic);
		sendBroadcast(intent);
		notification();
	}
	
	
	public void onCreate(){
		initMediaPlayer();
		musicList = MusicLoader.instance(getContentResolver()).getMusicList();
		Log.v(TAG, "OnCreate");   
		super.onCreate();
		service=new PreferencesService(this);
		
	}
	private void notification(){
		//��ȡ��ϵͳ��notificationManager  
        notificationManager =  (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);  
		//ʵ����һ��notification   
        String tickerText = "���ڲ���";  
        long when = System.currentTimeMillis();  
        Notification notification = new Notification(R.drawable.audio, tickerText, when);  
          
        //�����ֶ�����  
        //notification.flags= Notification.FLAG_NO_CLEAR;  
        //�������  
        //notification.sound = Uri.parse("/sdcard/haha.mp3");   
          
        //�����û����notification�Ķ���   
        // pendingIntent ���ڵ���ͼ   
        Intent intent0 = new Intent(this,MainActivity.class);  
        Intent intent1 = new Intent(this,CMusicService.class);
       
        PendingIntent pendingIntent1  = PendingIntent.getService(this, 0, intent1, 0);
        PendingIntent pendingIntent0  = PendingIntent.getActivity(this, 0, intent0, 0);  
        notification.contentIntent = pendingIntent0;  

		//�Զ������   
        RemoteViews rv = new RemoteViews(getPackageName(), R.layout.noti_layout);  
        rv.setTextViewText(R.id.tv_rv, "���ڲ���"+musicList.get(currentMusic).getTitle());  
        rv.setProgressBar(R.id.pb_rv, 80, 20, false);  
       
        rv.setImageViewResource(R.id.play, R.drawable.button_play_flag_normal);
        rv.setOnClickPendingIntent(R.id.play,pendingIntent1 );
        notification.contentView = rv;  

        
        
        //�Ѷ����notification ���ݸ� notificationmanager   
        notificationManager.notify(0, notification);
	}
	
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void notificationfun(){
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
		notification = new Notification.Builder(this)
					.setTicker( "CrazyMusic���ڲ���"+musicList.get(currentMusic).getTitle())
					.setSmallIcon(R.drawable.audio)
					.setContentTitle("���ڲ���")
					.setContentText(musicList.get(currentMusic).getTitle())
					.setContentIntent(pendingIntent)
					.getNotification();		
		notification.flags |= Notification.FLAG_NO_CLEAR; 
		  
		startForeground(1, notification);	
	}
	public void onDestroy(){
		service.savecurrentmusic(currentMusic,progress);
		if(mediaPlayer != null){
			mediaPlayer.release();
			mediaPlayer = null;
		} 
		
	}	
	
	/**
	 * initialize the MediaPlayer
	 */
	private void initMediaPlayer(){
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);		
		mediaPlayer.setOnPreparedListener(new OnPreparedListener() {				
			@Override
			public void onPrepared(MediaPlayer mp) {				
				mediaPlayer.start();
				mediaPlayer.seekTo(currentPosition);
				Log.v(TAG, "[OnPreparedListener] Start at " + currentMusic + " in mode " + currentMode + ", currentPosition : " + currentPosition);
				handler.sendEmptyMessage(updateDuration);
			}
		});
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {			
			@Override
			public void onCompletion(MediaPlayer mp) {
				if(isPlaying){
					Log.v(TAG, "[OnCompletionListener] On Completion at " + currentMusic);
					switch (currentMode) {
					case MODE_ONE_LOOP:
						Log.v(TAG, "[Mode] currentMode = MODE_ONE_LOOP.");
						mediaPlayer.start();
						break;					
					case MODE_ALL_LOOP:
						Log.v(TAG, "[Mode] currentMode = MODE_ALL_LOOP.");					
						play((currentMusic + 1) % musicList.size(), 0);
						break;
					case MODE_RANDOM:
						Log.v(TAG, "[Mode] currentMode = MODE_RANDOM.");					
						play(getRandomPosition(), 0);
						break;
					case MODE_SEQUENCE:
						Log.v(TAG, "[Mode] currentMode = MODE_SEQUENCE.");
						if(currentMusic < musicList.size() - 1){						
							playNext();
						}
						break;
					default:
						Log.v(TAG, "No Mode selected! How could that be ?");
						break;
					}
					Log.v(TAG, "[OnCompletionListener] Going to play at " + currentMusic);
				}
			}
		});
	}
	
	private void setCurrentMusic(int pCurrentMusic){
		currentMusic = pCurrentMusic;
		handler.sendEmptyMessage(updateCurrentMusic);
	}
	
	private int getRandomPosition(){
		int random = (int)(Math.random() * (musicList.size() - 1));
		return random;
	}
	
	private void play(int currentMusic, int pCurrentPosition) {
		currentPosition = pCurrentPosition;
		setCurrentMusic(currentMusic);
		mediaPlayer.reset();
		try {
			mediaPlayer.setDataSource(musicList.get(currentMusic).getUrl());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.v(TAG, "[Play] Start Preparing at " + currentMusic);
		mediaPlayer.prepareAsync();
		handler.sendEmptyMessage(updateProgress);
		notification();
		isPlaying = true;
	}
	
	private void stop(){
		mediaPlayer.stop();
		isPlaying = false;
	}
	private void playLast(){
		switch(currentMode){
		case MODE_ONE_LOOP:
			play(currentMusic, 0);
			break;
		case MODE_ALL_LOOP:
			if(currentMusic - 1 == 0){
				play(musicList.size(),0);
			}else{
				play(currentMusic - 1, 0);
			}
			break;
		case MODE_SEQUENCE:
			if(currentMusic - 1 == 0){
				Toast.makeText(this, "�ף��Ѿ������ˣ�", Toast.LENGTH_SHORT).show();
			}else{
				play(currentMusic + 1, 0);
			}
			break;
		case MODE_RANDOM:
			play(getRandomPosition(), 0);
			break;
		}
	}
	
	private void playNext(){
		switch(currentMode){
		case MODE_ONE_LOOP:
			play(currentMusic, 0);
			break;
		case MODE_ALL_LOOP:
			if(currentMusic + 1 == musicList.size()){
				play(0,0);
			}else{
				play(currentMusic + 1, 0);
			}
			break;
		case MODE_SEQUENCE:
			if(currentMusic + 1 == musicList.size()){
				Toast.makeText(this, "No more song.", Toast.LENGTH_SHORT).show();
			}else{
				play(currentMusic + 1, 0);
			}
			break;
		case MODE_RANDOM:
			play(getRandomPosition(), 0);
			break;
		}
	}
	
	private void playPrevious(){		
		switch(currentMode){
		case MODE_ONE_LOOP:
			play(currentMusic, 0);
			break;
		case MODE_ALL_LOOP:
			if(currentMusic - 1 < 0){
				play(musicList.size() - 1, 0);
			}else{
				play(currentMusic - 1, 0);
			}
			break;
		case MODE_SEQUENCE:
			if(currentMusic - 1 < 0){
				Toast.makeText(this, "No previous song.", Toast.LENGTH_SHORT).show();
			}else{
				play(currentMusic - 1, 0);
			}
			break;
		case MODE_RANDOM:
			play(getRandomPosition(), 0);
			break;
		}
	}
	

	@Override	
	public IBinder onBind(Intent intent) {		
		return natureBinder;
	}	
	
	class NatureBinder extends Binder{
		
		public void startPlay(int currentMusic, int currentPosition){
			play(currentMusic,currentPosition);
		}
		
		public void stopPlay(){
			stop();
		}
		
		public void toNext(){
			playNext();
		}
		public void toLast(){
			playLast();
		}
		public void toPrevious(){
			playPrevious();
		}
		
		/**
		 * MODE_ONE_LOOP = 1;
		 * MODE_ALL_LOOP = 2;
		 * MODE_RANDOM = 3;
		 * MODE_SEQUENCE = 4; 
		 */		
		public void changeMode(){			
			currentMode = (currentMode + 1) % 4;
			Log.v(TAG, "[NatureBinder] changeMode : " + currentMode);
			Toast.makeText(CMusicService.this, MODE_DESC[currentMode], Toast.LENGTH_SHORT).show();
		}
		
		/**
		 * return the current mode
		 * MODE_ONE_LOOP = 1;
		 * MODE_ALL_LOOP = 2;
		 * MODE_RANDOM = 3;
		 * MODE_SEQUENCE = 4; 
		 * @return
		 */
		public int getCurrentMode(){
			return currentMode; 
		}
		
		/**
		 * The service is playing the music
		 * @return
		 */
		public boolean isPlaying(){
			return isPlaying;
		}
		
		/**
		 * Notify Activities to update the current music and duration when current activity changes.
		 */
		public void notifyActivity(){
			toUpdateCurrentMusic();
			toUpdateDuration();			
		}
		
		/**
		 * Seekbar changes
		 * @param progress
		 */
		public void changeProgress(int progress){
			if(mediaPlayer != null){
				currentPosition = progress * 1000;
				if(isPlaying){
					mediaPlayer.seekTo(currentPosition);
				}else{
					play(currentMusic, currentPosition);
				}
			}
		}
	}

}