package message;

import java.util.List;  

import com.example.mycloud.R;

import android.app.Activity;  
import android.app.PendingIntent;  
import android.content.BroadcastReceiver;  
import android.content.Context;  
import android.content.Intent;  
import android.content.IntentFilter;  
import android.os.Bundle;  
import android.telephony.SmsManager;  
import android.view.View;  
import android.widget.Button;  
import android.widget.EditText;  
import android.widget.Toast;  
public class Send_message extends Activity{
	String SENT_SMS_ACTION="SENT_SMS_ACTION";  
    String DELIVERED_SMS_ACTION="DELIVERED_SMS_ACTION";  
  
    /** Called when the activity is first created. */  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.smsssss);  
          
        Button btn = (Button)findViewById(R.id.btn);  
        btn.setOnClickListener(new View.OnClickListener() {  
              
            public void onClick(View arg0) {  
                // TODO Auto-generated method stub  
                EditText telNoText =(EditText)findViewById(R.id.telNo);  
                EditText contentText = (EditText)findViewById(R.id.content);  
                  
                String telNo = telNoText.getText().toString();  
                String content = contentText.getText().toString();  
                if (validate(telNo, content))  
                {  
                    sendSMS(telNo, content);  
                }else{  
//                  AlertDialog.Builder builder =  new AlertDialog.Builder(SMS.this);  
//                  builder.setMessage("Please enter both phone number and message.")  
//                  .setTitle("Error")  
//                          .setCancelable(false)  
//                          .setNegativeButton("OK",  
//                                  new DialogInterface.OnClickListener() {  
//                                      public void onClick(  
//                                              DialogInterface dialog,  
//                                              int id) {  
//                                          dialog.cancel();  
//                                      }  
//                                  });  
//                  AlertDialog alert = builder.create();  
//                  alert.show();  
                }  
            }  
        });  
    }  
      
    /** 
     * Send SMS 
     * @param phoneNumber 
     * @param message 
     */  
    private void sendSMS(String phoneNumber, String message) {  
          
        //create the sentIntent parameter  
        Intent sentIntent = new Intent(SENT_SMS_ACTION);  
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, sentIntent,  
                0);  
        // create the deilverIntent parameter  
        Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);  
        PendingIntent deliverPI = PendingIntent.getBroadcast(this, 0,  
                deliverIntent, 0);  
  
        SmsManager sms = SmsManager.getDefault();  
        if (message.length() > 70) {  
            List<String> msgs = sms.divideMessage(message);  
            for (String msg : msgs) {  
                sms.sendTextMessage(phoneNumber, null, msg, sentPI, deliverPI);  
            }  
        } else {  
            sms.sendTextMessage(phoneNumber, null, message, sentPI, deliverPI);  
        }  
        Toast.makeText(Send_message.this, R.string.message, Toast.LENGTH_LONG).show();  
          
        //register the Broadcast Receivers  
        registerReceiver(new BroadcastReceiver(){  
            @Override  
            public void onReceive(Context _context,Intent _intent)  
            {  
                switch(getResultCode()){  
                    case Activity.RESULT_OK:  
                        Toast.makeText(getBaseContext(),   
                                "SMS sent success actions",  
                                Toast.LENGTH_SHORT).show();  
                        break;  
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:  
                        Toast.makeText(getBaseContext(),   
                                "SMS generic failure actions",  
                                Toast.LENGTH_SHORT).show();  
                        break;  
                    case SmsManager.RESULT_ERROR_RADIO_OFF:  
                        Toast.makeText(getBaseContext(),  
                                "SMS radio off failure actions",  
                                Toast.LENGTH_SHORT).show();  
                        break;  
                    case SmsManager.RESULT_ERROR_NULL_PDU:  
                        Toast.makeText(getBaseContext(),   
                                "SMS null PDU failure actions",  
                                Toast.LENGTH_SHORT).show();  
                        break;  
                }  
            }  
        },  
        new IntentFilter(SENT_SMS_ACTION));  
        registerReceiver(new BroadcastReceiver(){  
            @Override  
            public void onReceive(Context _context,Intent _intent)  
            {  
                Toast.makeText(getBaseContext(),   
                        "SMS delivered actions",  
                        Toast.LENGTH_SHORT).show();               
            }  
        },  
        new IntentFilter(DELIVERED_SMS_ACTION));  
  
    }  
      
    public boolean validate(String telNo, String content){  
          
        if((null==telNo)||("".equals(telNo.trim()))){  
            Toast.makeText(this, "please input the telephone No.!",Toast.LENGTH_LONG).show();  
            return false;  
        }  
        if(!checkTelNo(telNo)){  
            Toast.makeText(this, "please input the right telephone No.!",Toast.LENGTH_LONG).show();  
            return false;  
        }  
        if((null==content)||("".equals(content.trim()))){  
            Toast.makeText(this, "please input the message content!",Toast.LENGTH_LONG).show();  
            return false;  
        }  
        return true;  
    }  
      
    public boolean checkTelNo(String telNo){  
        if("5556".equals(telNo)){  
            return true;  
        }else{  
            String reg ="^0{0,1}(13[0-9]|15[0-9])[0-9]{8}$";   
            return telNo.matches(reg);  
        }  
    }  
}
