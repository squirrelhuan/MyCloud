package message;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;  
  
public class Receiver_message extends BroadcastReceiver {  
  
    private static final String strRes = "android.provider.Telephony.SMS_RECEIVED";  
    private static MessageListener mMessageListener; 
    public  String zh_sms_tv;
	private Context context;
      
	 public Receiver_message() {  
	       
	    }  
    @Override  
    public void onReceive(Context arg0, Intent arg1) {  
        // TODO Auto-generated method stub 
    	 StringBuilder sb = new StringBuilder();
        if(strRes.equals(arg1.getAction())){  

            Bundle bundle = arg1.getExtras();  
            if(bundle!=null){  
                Object[] pdus = (Object[])bundle.get("pdus");  
                SmsMessage[] msg = new SmsMessage[pdus.length];  
                for(int i = 0 ;i<pdus.length;i++){  
                    msg[i] = SmsMessage.createFromPdu((byte[])pdus[i]);  
                }  
                  
                for(SmsMessage curMsg:msg){  
                    sb.append("You got the message From:");
                    sb.append(curMsg.getDisplayOriginatingAddress());  
                    sb.append("Content");
                    sb.append(curMsg.getDisplayMessageBody());       
                }  
                Toast.makeText(arg0,
                        "Got The Message:" + sb.toString(),  
                        Toast.LENGTH_SHORT).show();  
               
                }  
            mMessageListener.onReceived(sb.toString());  
        } 
     

       // firstActivity a=new firstActivity();
       // a.comeforomsms("laixin",zh_sms_tv);
        
    }  
  //
    public interface MessageListener {  
        public void onReceived(String message);  
    }  
      
    public void setOnReceivedMessageListener(MessageListener messageListener) {  
        this.mMessageListener = messageListener;  
    }  
  
}  