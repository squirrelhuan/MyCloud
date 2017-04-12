package mysystembar;

import mysystembar.SystemBarTintManager;

import com.example.mycloud.R;

import android.app.Activity;
import android.os.Bundle;

public class DefaultActivity extends Activity { 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_default);

		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setNavigationBarTintEnabled(true);
	}

}

