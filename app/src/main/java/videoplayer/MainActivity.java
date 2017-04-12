package videoplayer;

import java.io.IOException;

import com.example.mycloud.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
/*
 * @author 褚国庆
 */
@SuppressLint("SdCardPath")
public class MainActivity extends Activity implements OnClickListener,
		OnTouchListener {
	private String path = /*"/sdcard/001.mp4";*/"/storage/sdcard1/001.mp4";
	private SeekBar videoSeekBar = null, voice_SeekBar, light_SeekBar = null;
	private ImageView startImgBtn = null, rewind_down, Fast_Forward;
	private SurfaceView mVideoSurfaceView = null;
	private SurfaceHolder mHolder = null;
	private MediaPlayer mMediaPlayer = null;
	public AudioManager audiomanage;
	private int maxVolume, currentVolume;
	private int volume = 0; // 初始化声音
	private DelayThread dThread = null;
	private boolean bIfSDExist = false;
	private PopupWindow pW_menu = null, pW_voice = null;
	private VideoView mVideoView01;
	private TextView path_name;
	private boolean Initialization, state, other;
	private int musicMaxTime, currentTime, setTime;
	private ImageView img_popcot;
	private RelativeLayout RelativeLayout01, RelativeLayout02;
	private int width, height;
	private int i = 1, j = 0;
	float x1 = 0, y1 = 0, x2 = 0, y2 = 0, x3 = 0, y3 = 0;

	// 垂直seekbar
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.videoplayer_main);

		Intent intent = getIntent();
		path = intent.getDataString();
		if(path==null){
		path = "/storage/sdcard1/001.mp4";}
		path_name = (TextView) findViewById(R.id.path_name);
		path_name.setText("正在播放："+path);
		width = getWindowManager().getDefaultDisplay().getWidth();
		height = getWindowManager().getDefaultDisplay().getHeight();

		Initialization = false;
		state = false;

		// 创建MediaPlayer实例
		mMediaPlayer = new MediaPlayer();
		audiomanage = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		maxVolume = audiomanage.getStreamMaxVolume(AudioManager.STREAM_MUSIC); // 获取系统最大音量

		// 获取SurfaceView组件
		mVideoSurfaceView = (SurfaceView) findViewById(R.id.VideoSurfaceView);
		mVideoSurfaceView.setOnClickListener(this);
		mVideoSurfaceView.setOnTouchListener(this);
		// mVideoSurfaceView.setFocusable(true);
		// 通过SurfaceHolder对SurfaceView实例进行引用
		mHolder = mVideoSurfaceView.getHolder();
		// 设置Surface的类型
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		// 判断sd卡是否存在
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			bIfSDExist = true;
			Toast.makeText(MainActivity.this, "存在sd卡", 1000).show();
		}
		// 设置视频播放完成时采取的操作
		mMediaPlayer
				.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
					public void onCompletion(MediaPlayer mp) {
						// 提示视频结束
						// Toast.makeText(MainActivity.this, "结束", 1000).show();
						mMediaPlayer.stop();
						mMediaPlayer.release();
						mMediaPlayer = null;
						// 退出当前Activity
						// VideoPlayDemo.this.finish();
					}
				});
	}

	// 设置按健退出程序
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// dThread.stop();
			mMediaPlayer.stop();
			mMediaPlayer.release();
			mMediaPlayer = null;
			this.finish();
			return true;
		}
		if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
			// 在这里获得当前的seekbar的值，当按下的是下键是，将当前的值减一，然后重新赋值给seekbar（setProgress（int）），
		} else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
			// 在这里获得当前的seekbar的值，当按下的是下键是，将当前的值加一，
			// 然后重新赋值给seekbar（setProgress（int））

		}
		return super.onKeyDown(keyCode, event);
	}

	// 处理线程消息的Handle
	private Handler mHandle = new Handler() {
		public void handleMessage(Message msg) {
			// 设置当前播放的位置
			if (mMediaPlayer != null) {
				int pos = mMediaPlayer.getCurrentPosition();
				videoSeekBar.setProgress(pos);
			}
		}
	};

	// 延时线程，用于定期更新进度条
	public class DelayThread extends Thread {
		int milliseconds;

		public DelayThread(int msec) {
			milliseconds = msec;
		}

		// 每100毫秒更新一次进度条
		public void run() {
			while (true) {
				try {
					sleep(milliseconds);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 发送消息更新进度条
				mHandle.sendEmptyMessage(0);
			}
		}

	}

	public void startProgressUpdate() {
		dThread = new DelayThread(100);
		dThread.start();
	}

	private void showPopWindow(View anchor) {
		path_name.setVisibility(View.VISIBLE);
		View view = null;
		if (pW_menu == null) {
			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = layoutInflater.inflate(R.layout.videoplayer_bar, null);
			// 获取SeekBar组件

			startImgBtn = (ImageView) view.findViewById(R.id.startImgBtn);
			startImgBtn.setOnClickListener(this);
			rewind_down = (ImageView) view.findViewById(R.id.rewind_down);
			rewind_down.setOnClickListener(this);
			Fast_Forward = (ImageView) view.findViewById(R.id.Fast_Forward);
			Fast_Forward.setOnClickListener(this);
			videoSeekBar = (SeekBar) view.findViewById(R.id.VideoSeekBar);
			videoSeekBar
					.setOnSeekBarChangeListener(new OnSeekBarChangeListener() // 调音监听器
					{
						public void onProgressChanged(SeekBar arg0,
								int progress, boolean fromUser) {
						}

						@Override
						public void onStartTrackingTouch(SeekBar seekBar) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onStopTrackingTouch(SeekBar seekBar) {
							// TODO Auto-generated method stub
							mMediaPlayer.seekTo(seekBar.getProgress());

						}
					});
			// 创建popWindow
			pW_menu = new PopupWindow(view, LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT);
		} else {
			view = pW_menu.getContentView();
		}
		// 可以聚集 ，按返回键，先popWindow，不然popWindow和activity会同时消失，估计这既是Android焦点顺序的原理吧。
		pW_menu.setFocusable(true);
		// view.setFocusable(true); // 这个很重要
		// view.setFocusableInTouchMode(true);
		// 重写onKeyListener
		pW_menu.setOnDismissListener(new PopupWindow.OnDismissListener() {
			public void onDismiss() {
				// TODO Auto-generated method stub
				path_name.setVisibility(View.GONE);
			}
		});
		view.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					// pW_menu.dismiss();
					return true;
				}
				return false;
			}
		});
		pW_menu.setOutsideTouchable(false);
		// 为了按返回键消失和外部点击消失 ，不会影响背景
		// popWindow.setBackgroundDrawable(new BitmapDrawable());
		pW_menu.setBackgroundDrawable(new ColorDrawable(0xb0000000));
		// popWindow.setAnimationStyle(R.style.AnimBottom);
		WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		int xoffInPixels = windowManager.getDefaultDisplay().getWidth() / 2
				- pW_menu.getWidth() / 2;
		int xoffInDip = px2dip(this, xoffInPixels);
		// 默认位置(anchor翻译为“靠山”)
		// popWindow.showAsDropDown(anchor);
		// anchor的居中位置
		// pW_menu.showAsDropDown(anchor, -xoffInDip, 0);
		// 偏移位置
		// popWindow.showAsDropDown(anchor,0,-50);
		pW_menu.showAtLocation(
				MainActivity.this.findViewById(R.id.RelativeLayout01),
				Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
	}

	// 音量
	private void showvoice(View anchor, int cla, final int voice, int light) {
		View view = null;
		if (pW_voice == null) {
			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = layoutInflater.inflate(R.layout.cell_voice, null);
			// 获取SeekBar组件
			voice_SeekBar = (SeekBar) view.findViewById(R.id.voice_SeekBar);
			audiomanage = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
			maxVolume = audiomanage
					.getStreamMaxVolume(AudioManager.STREAM_MUSIC); // 获取系统最大音量
			voice_SeekBar.setMax(maxVolume); // 拖动条最高值与系统最大声匹配
			currentVolume = audiomanage
					.getStreamVolume(AudioManager.STREAM_MUSIC); // 获取当前值
			voice_SeekBar.setProgress(currentVolume + voice);

			audiomanage.setStreamVolume(AudioManager.STREAM_MUSIC, voice, 0); // tempVolume:音量绝对值

			voice_SeekBar
					.setOnSeekBarChangeListener(new OnSeekBarChangeListener() // 调音监听器
					{
						public void onProgressChanged(SeekBar arg0,
								int progress, boolean fromUser) {

						}

						@Override
						public void onStartTrackingTouch(SeekBar seekBar) {
							// TODO Auto-generated method stub
						}

						@Override
						public void onStopTrackingTouch(SeekBar seekBar) {
							// TODO Auto-generated method stub
							audiomanage.setStreamVolume(
									AudioManager.STREAM_MUSIC,
									seekBar.getProgress(), 0);
							currentVolume = audiomanage
									.getStreamVolume(AudioManager.STREAM_MUSIC); // 获取当前值

							voice_SeekBar.setProgress(currentVolume);
							// mVolume.setText(currentVolume*100/maxVolume +
							// " %");
						}
					});

			light_SeekBar = (SeekBar) view.findViewById(R.id.light_SeekBar);
			// 进度条绑定最大亮度，255是最大亮度
			light_SeekBar.setMax(255);
			// 取得当前亮度
			int normal = Settings.System.getInt(getContentResolver(),
					Settings.System.SCREEN_BRIGHTNESS, 255);
			// 进度条绑定当前亮度
			light_SeekBar.setProgress(normal);
			light_SeekBar
					.setOnSeekBarChangeListener(new OnSeekBarChangeListener() // 调音监听器
					{
						public void onProgressChanged(SeekBar arg0,
								int progress, boolean fromUser) {
							// 取得当前进度
							int tmpInt = light_SeekBar.getProgress();
							// 当进度小于80时，设置成80，防止太黑看不见的后果。
							if (tmpInt < 80) {
								tmpInt = 80;
							}
							// 根据当前进度改变亮度
							Settings.System.putInt(getContentResolver(),
									Settings.System.SCREEN_BRIGHTNESS, tmpInt);
							tmpInt = Settings.System.getInt(
									getContentResolver(),
									Settings.System.SCREEN_BRIGHTNESS, -1);
							WindowManager.LayoutParams wl = getWindow()
									.getAttributes();
							float tmpFloat = (float) tmpInt / 255;
							if (tmpFloat > 0 && tmpFloat <= 1) {
								wl.screenBrightness = tmpFloat;
							}
							getWindow().setAttributes(wl);
						}

						@Override
						public void onStartTrackingTouch(SeekBar seekBar) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onStopTrackingTouch(SeekBar seekBar) {
							// TODO Auto-generated method stub

						}
					});
			img_popcot = (ImageView) view.findViewById(R.id.img_popcot);
			// 创建popWindow
			pW_voice = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
		} else {
			view = pW_voice.getContentView();
			if (cla == 0) {
				img_popcot.setImageResource(R.drawable.ic_video_voice);
				light_SeekBar.setVisibility(view.GONE);
				voice_SeekBar.setVisibility(view.VISIBLE);
			} else {
				img_popcot.setImageResource(R.drawable.ic_video_light);
				voice_SeekBar.setVisibility(view.GONE);
				light_SeekBar.setVisibility(view.VISIBLE);
			}
		}
		// 可以聚集 ，按返回键，先popWindow，不然popWindow和activity会同时消失，估计这既是Android焦点顺序的原理吧。
		pW_voice.setFocusable(true);
		// view.setFocusable(true); // 这个很重要
		// view.setFocusableInTouchMode(true);
		// 重写onKeyListener
		pW_voice.setOnDismissListener(new PopupWindow.OnDismissListener() {
			public void onDismiss() {
				// TODO Auto-generated method stub
				pW_menu.dismiss();
			}
		});
		view.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					// pW_menu.dismiss();
					return true;
				}
				return false;
			}
		});
		pW_voice.setOutsideTouchable(true);
		// 为了按返回键消失和外部点击消失 ，不会影响背景
		// popWindow.setBackgroundDrawable(new BitmapDrawable());
		pW_voice.setBackgroundDrawable(new ColorDrawable(0x00000000));
		// popWindow.setAnimationStyle(R.style.AnimBottom);
		WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		int xoffInPixels = windowManager.getDefaultDisplay().getWidth() / 2
				- pW_voice.getWidth() / 2;
		int xoffInDip = px2dip(this, xoffInPixels);
		// 默认位置(anchor翻译为“靠山”)
		// popWindow.showAsDropDown(anchor);

		// anchor的居中位置
		// pW_menu.showAsDropDown(anchor, -xoffInDip, 0);

		// 偏移位置
		// popWindow.showAsDropDown(anchor,0,-50);
		pW_voice.showAtLocation(
				MainActivity.this.findViewById(R.id.RelativeLayout01),
				Gravity.CENTER | Gravity.CENTER, 0, 0);
		pW_voice.update();
	}

	// public void miss(int time) {
	//
	// new Handler().postDelayed(new Runnable() {
	// public void run() {
	// pW_menu.dismiss();
	// }
	// }, time);
	// }

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public boolean startplay() {
		if (Initialization == true) {
			// stopPlay();
			if (state == true) {
				startImgBtn.setBackgroundResource(R.drawable.ic_media_state);

				mMediaPlayer.pause();
				state = false;
			} else {
				startImgBtn.setBackgroundResource(R.drawable.ic_media_pause);
				mMediaPlayer.start();
				state = true;
			}

		} else {
			startImgBtn.setBackgroundResource(R.drawable.ic_media_pause);
			mMediaPlayer.reset();
			Toast.makeText(MainActivity.this, "path:+"+path, 1000).show();
			try {
				// 设置播放视频源
				Toast.makeText(MainActivity.this, "准备完毕", 1000).show();
				mMediaPlayer.setDataSource(path);
				Log.d("myerrors", "111");
				// 设置音轨流的类型
				mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				// 设置surface的尺寸
				mHolder.setFixedSize(mMediaPlayer.getVideoWidth(),
						mMediaPlayer.getVideoHeight());
				// 为视频设置显示视图
				mMediaPlayer.setDisplay(mHolder);
				// 准备播放
				mMediaPlayer.prepare();
				// 为进度条设置最大值
				videoSeekBar.setMax(mMediaPlayer.getDuration());
				currentTime = mMediaPlayer.getCurrentPosition();
				// 开始播放
				mMediaPlayer.start();
				// 开始更新进度条
				startProgressUpdate();

			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				Toast.makeText(MainActivity.this, "没有找到文件", 1000).show();
			}

			PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
			PowerManager.WakeLock mWakeLock = pm.newWakeLock(
					PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
			// in onResume() call
			mWakeLock.acquire();
			state = true;
			Initialization = true;
		}
		return state;
	}

	public void stopPlay() {
		mMediaPlayer.pause();
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock mWakeLock = pm.newWakeLock(
				PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
		// in onPause() call
		mWakeLock.release();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// miss(5000);
		switch (v.getId()) {
		case R.id.VideoSurfaceView:
			showPopWindow(v);
			break;
		case R.id.rewind_down:

			if (0 < currentTime - setTime) {
				// mMediaPlayer.seekTo(musicMaxTime);
				mMediaPlayer.seekTo(currentTime - setTime);
			} else {
			}
			break;
		case R.id.Fast_Forward:
			if (currentTime + setTime >= musicMaxTime) {
				// mMediaPlayer.seekTo(musicMaxTime);
				mMediaPlayer.seekTo(currentTime + setTime);
			} else {
			}
			break;
		case R.id.startImgBtn:
			startplay();
			break;
		default:
			break;
		}
	}

	// 手势

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) { // 处理屏幕屏点下事件
															// 手指点击屏幕时触发
			x1 = event.getX();
			y1 = event.getY();

		} else if (event.getAction() == MotionEvent.ACTION_UP) {// 处理屏幕屏抬起事件
																// 手指离开屏幕时触发
			x3 = event.getX();
			y3 = event.getY();
			if (other == true) {
				showPopWindow(v);
			} else {
				// pW_voice.dismiss();
			}
			// miss(6000);
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {// 处理移动事件
																	// 手指在屏幕上移动时触发
			x2 = event.getX();
			y2 = event.getY();
			other = false;
			if (x2 <= width / 2) {
				if (y2 - y1 >= 40 || y2 - y1 <= -40) {
					// voice_verticalSeekBar.setVisibility(View.VISIBLE);
					// pW_menu.dismiss();
					float f1 = (y2 - y1) / 5;
					int voice = (int) f1;
					showvoice(v, 0, voice, 0);

				} else {
					other = true;
				}
			} else if (width / 2 <= x2) {
				if (y2 - y1 >= 40 || y2 - y1 <= -40) {
					// light_verticalSeekBar.setVisibility(View.VISIBLE);
					// pW_menu.dismiss();
					float f2 = (y2 - y1) * 2;
					int light = (int) f2;
					showvoice(v, 1, 0, light);
				} else {
					other = true;
				}
			} else {
				other = true;
			}
		}
		return true; // 此处需要返回true 才可以正常处理move事件 详情见后面的 说明
	}
}
