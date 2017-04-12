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
		//iflab.test.firstActivity.logview.append("调用了myService的onBind方法！\n");
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		//iflab.test.firstActivity.logview.append("调用了myService的onCreate方法！\n");
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		//iflab.test.firstActivity.logview.append("调用了myService的onDestroy方法！\n");
		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		//iflab.test.firstActivity.logview.append("调用了myService的onStart方法！\n");
		super.onStart(intent, startId);
	}

}
