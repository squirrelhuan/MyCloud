package sql;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class myService extends Service{

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		//iflab.test.firstActivity.logview.append("������myService��onBind������\n");
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		//iflab.test.firstActivity.logview.append("������myService��onCreate������\n");
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		//iflab.test.firstActivity.logview.append("������myService��onDestroy������\n");
		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		//iflab.test.firstActivity.logview.append("������myService��onStart������\n");
		super.onStart(intent, startId);
	}

}
