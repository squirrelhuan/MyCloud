package flashlight;

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

import com.example.mycloud.R;



public class ShanGuangDActivity extends Activity {
	private Button onebutton = null;
	private Camera camera = null;
	private Parameters parameters = null;
	public static boolean kaiguan = true;
	// public static boolean action = false;
	private int back = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ȫ�����ã����ش�������װ��
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD,
				WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.light);

        
       
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
				parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);// ����
				camera.setParameters(parameters);
				kaiguan = false;
			} else {
				onebutton.setBackgroundResource(R.drawable.bg);
				parameters.setFlashMode(Parameters.FLASH_MODE_OFF);// �ر�
				camera.setParameters(parameters);
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
		menu.add(0, 2, 2, "˳");
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
				Toast.makeText(ShanGuangDActivity.this, "",
						Toast.LENGTH_SHORT).show();
				break;
			case 2:
				back = 0;
				Myback();
				break;
			}
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	public void Myback() {
		if (kaiguan) {//
			ShanGuangDActivity.this.finish();
			android.os.Process.killProcess(android.os.Process.myPid());
		} else if (!kaiguan) {//
			camera.release();
			ShanGuangDActivity.this.finish();
			android.os.Process.killProcess(android.os.Process.myPid());
			kaiguan = true;
		}
	}
}