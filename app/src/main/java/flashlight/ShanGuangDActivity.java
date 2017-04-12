package flashlight;

import com.example.mycloud.R;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;



public class ShanGuangDActivity extends Activity {
	private Button onebutton = null;
	private Camera camera = null;
	private Parameters parameters = null;
	public static boolean kaiguan = true; // 定义开关状态，状态为false，打开状态，状态为true，关闭状态
	// public static boolean action = false;
	// //定义的状态，状态为false，当前界面不退出，状态为true，当前界面退出
	private int back = 0;// 判断按几次back

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 全屏设置，隐藏窗口所有装饰
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置屏幕显示无标题，必须启动就要设置好，否则不能再次被设置
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD,
				WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.light);
		// 推送接口 false表示被调用时发送请求
		
		/**
		 * 加入精品推荐的酷悬，传入1表示显示酷仔
		 */
		
		// false表示悬浮在右侧
		
        
        //启用Debug模式，该模式下每2分钟会收到一次广告，
        //该模式下所有广告都只供测试使用，不进入真实统计。
        //在正式发布时记得一定要删掉本行代码
//        manager.setDebugMode();
        
       
		onebutton = (Button) findViewById(R.id.onebutton);
		onebutton.setOnClickListener(new Mybutton());
	}

	class Mybutton implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (kaiguan) {
				onebutton.setBackgroundResource(R.drawable.bg1);
				camera = Camera.open();
				parameters = camera.getParameters();
				parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);// 开启
				camera.setParameters(parameters);
				// onebutton.setText("关闭");
				kaiguan = false;
			} else {
				onebutton.setBackgroundResource(R.drawable.bg);
				parameters.setFlashMode(Parameters.FLASH_MODE_OFF);// 关闭
				camera.setParameters(parameters);
				// onebutton.setText("开启");
				kaiguan = true;
				camera.release();
			}
		}
	}
	@Override
	protected void onDestroy() {
		
		super.onDestroy();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 2, 2, "退出");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 2:
			Myback();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			back++;
			switch (back) {
			case 1:
				Toast.makeText(ShanGuangDActivity.this, "再按一次退出",
						Toast.LENGTH_SHORT).show();
				break;
			case 2:
				back = 0;// 初始化back值
				Myback();
				break;
			}
			return true;// 设置成false让back失效 ，true表示 不失效
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	public void Myback() { // 关闭程序
		if (kaiguan) {// 开关关闭时
			ShanGuangDActivity.this.finish();
			android.os.Process.killProcess(android.os.Process.myPid());// 关闭进程
		} else if (!kaiguan) {// 开关打开时
			camera.release();
			ShanGuangDActivity.this.finish();
			android.os.Process.killProcess(android.os.Process.myPid());// 关闭进程
			kaiguan = true;// 避免，打开开关后退出程序，再次进入不打开开关直接退出时，程序错误
		}
	}
}