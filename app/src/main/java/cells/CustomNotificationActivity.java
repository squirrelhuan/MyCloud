package cells;

import musicplayer.MainActivity;

import com.example.mycloud.R;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

public class CustomNotificationActivity extends Activity {
	NotificationManager notificationManager;  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.noti_layout);  
        //获取到系统的notificationManager  
        notificationManager =  (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);  
    }  
      
    public void click(View view ){  
        //实例化一个notification   
         String tickerText = "IP号码 设置完毕";  
         long when = System.currentTimeMillis();  
         Notification notification = new Notification(R.drawable.audio, tickerText, when);  
           
         //不能手动清理  
         //notification.flags= Notification.FLAG_NO_CLEAR;  
         //添加音乐  
         //notification.sound = Uri.parse("/sdcard/haha.mp3");   
           
         //设置用户点击notification的动作   
         // pendingIntent 延期的意图   
         Intent intent = new Intent(this,MainActivity.class);  
         PendingIntent pendingIntent  = PendingIntent.getActivity(this, 0, intent, 0);  
         notification.contentIntent = pendingIntent;  
          
         //自定义界面   
         RemoteViews rv = new RemoteViews(getPackageName(), R.layout.noti_layout);  
         rv.setTextViewText(R.id.tv_rv, "正在播放：");  
         rv.setProgressBar(R.id.pb_rv, 80, 20, false);  
         notification.contentView = rv;  
           
         //把定义的notification 传递给 notificationmanager   
         notificationManager.notify(0, notification);  
    }
}
