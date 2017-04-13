package cells;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.example.mycloud.R;

import musicplayer.MainActivity;

public class CustomNotificationActivity extends Activity {
	NotificationManager notificationManager;  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.noti_layout);  
        //notificationManager
        notificationManager =  (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);  
    }  
      
    public void click(View view ){  
        //notification
         String tickerText = "IP���� �������";  
         long when = System.currentTimeMillis();  
         Notification notification = new Notification(R.drawable.audio, tickerText, when);  

         //notification.flags= Notification.FLAG_NO_CLEAR;
         //notification.sound = Uri.parse("/sdcard/haha.mp3");   
           
         //notification�Ķ���
         // pendingIntent ���ڵ���ͼ   
         Intent intent = new Intent(this,MainActivity.class);  
         PendingIntent pendingIntent  = PendingIntent.getActivity(this, 0, intent, 0);  
         notification.contentIntent = pendingIntent;  

         RemoteViews rv = new RemoteViews(getPackageName(), R.layout.noti_layout);  
         rv.setTextViewText(R.id.tv_rv, "���ڲ��ţ�");  
         rv.setProgressBar(R.id.pb_rv, 80, 20, false);  
         notification.contentView = rv;  
           
         //notification notificationmanager
         notificationManager.notify(0, notification);  
    }
}
