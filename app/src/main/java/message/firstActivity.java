package message;



import java.util.List;

import com.example.mycloud.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.content.IntentFilter;
import message.Receiver_message;


public class firstActivity extends Activity implements OnClickListener {
public SQLiteDatabase mydb=null;
private final static String DATABASE_NAME="FirstDataBase.db";
private final static String TABLE_NAME="firstTable";
private final static String ID="_id";
private final static String NAME="name";
private final static String AGE="age";
private final static String HOME="home";
private final static String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+ID+" INTEGER PRIMARY KEY,"+ NAME+" TEXT,"+AGE+" TEXT,"+HOME+" TEXT)";
private static final int PICK_CONTACT_SUBACTIVITY = 2;
//private EditText editText=null;
private EditText edit1=null,contact_number=null,content=null;
private EditText edit2=null;
private TextView contanct_name;
private Button add_contact;
private ImageView delete_contact;
private ListView sms_lv;
private Receiver_message mSMSBroadcastReceiver; 
private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
/** Called when the activity is first created. */

@Override
public void onCreate(Bundle savedInstanceState) {
	
	super.onCreate(savedInstanceState);
    setContentView(R.layout.message_main);

    Button btn1=(Button)findViewById(R.id.send);
  
   // editText=(EditText)findViewById(R.id.editText1);
    edit1=(EditText)findViewById(R.id.contact_number);
    edit2=(EditText)findViewById(R.id.content);
    contact_number=(EditText)findViewById(R.id.contact_number);
    content=(EditText)findViewById(R.id.content);
    contanct_name=(TextView) findViewById(R.id.contanct_name);
    add_contact=(Button) findViewById(R.id.add_contact);
    add_contact.setOnClickListener(this);
    delete_contact=(ImageView) findViewById(R.id.delete_contact);
    delete_contact.setOnClickListener(this);
    sms_lv=(ListView) findViewById(R.id.sms_lv);

    edit1.setText("");
    edit2.setText("");
    
    mydb=openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
    try
    {
    	mydb.execSQL(CREATE_TABLE);
    }
    catch(Exception e) {
    }    
    ContentValues cv=new ContentValues();
    //cv.put(NAME, "张三");
    //cv.put(AGE, "18");
    //cv.put(HOME, "北京");
   // mydb.insert(TABLE_NAME, null, cv);
  
    showData(0,"",""); 
    mydb.close();
    
    btn1.setOnClickListener(this);
    
    
}
@Override
	protected void onStart() {
		// TODO Auto-generated method stub
	super.onStart();
	//生成广播处理  
    mSMSBroadcastReceiver = new Receiver_message();
  //实例化过滤器并设置要过滤的广播  
    IntentFilter intentFilter = new IntentFilter(ACTION);  
    intentFilter.setPriority(Integer.MAX_VALUE);
	this.registerReceiver(mSMSBroadcastReceiver, intentFilter);  
	  
    mSMSBroadcastReceiver.setOnReceivedMessageListener(new Receiver_message.MessageListener() {  
        @Override  
        public void onReceived(String message) {  

        	//contanct_name.setText(message);
         
        	mydb=openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
    		ContentValues cv=new ContentValues();
    		cv.put(NAME, message);
    		cv.put(AGE, edit2.getText().toString());
    		mydb.insert(TABLE_NAME, null, cv);
    		showData(0,"",""); 
    		mydb.close();
        }});
       
		
	}

public void showData(int v,String contact_name,String contact_content)
{
   // editText.setText("数据库内容：\n");
   // editText.append("姓名\t\t年龄\t\t籍贯\n");
   
    Cursor cur=mydb.query(TABLE_NAME, new String[] {ID,NAME,AGE,HOME}, null, null, null, null, null);
    int count=cur.getCount();
    if(cur!=null && count>=0)
    {
    	if(cur.moveToFirst())
    	{
    		do
    		{
    			if(v==0){
    				String name=cur.getString(1);
        			String age=cur.getString(2);
    			}else{
    			String name=contact_name;
    			String age=contact_content;
    			}
    		//	editText.append(""+name+"\t\t"+age+"\t\t"+home+"\n");
    			SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.cell_sms_item,
    					cur, new String[] { "name", "age" }, new int[] {R.id.laixin, R.id.name });
    			sms_lv.setAdapter(sca);
    		}while(cur.moveToNext());
    	}	
    }
}

@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()) {
	case R.id.send:
		String mobile=contact_number.getText().toString();
		String message=content.getText().toString();
		if(message!=null){
			SmsManager sms=SmsManager.getDefault();
			List<String>texts=sms.divideMessage(message);
		
		mydb=openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
		ContentValues cv=new ContentValues();
		cv.put(NAME, edit1.getText().toString());
		cv.put(AGE, edit2.getText().toString());
		mydb.insert(TABLE_NAME, null, cv);
		showData(0,"",""); 
		mydb.close();
		for(String text:texts){
			sms.sendTextMessage(mobile,null,text,null,null);
			Toast.makeText(firstActivity.this, "短信发送完成", Toast.LENGTH_LONG).show();
			break;
			//smsManager.sendTextMessage(mobile, null, msg, sentIntent, null);
		}
	}
	
		break;
	case R.id.add_contact:
		Uri uri = Uri.parse("content://contacts/people");
		Intent intent = new Intent(Intent.ACTION_PICK, uri);
		startActivityForResult(intent, PICK_CONTACT_SUBACTIVITY);
		break;
	case R.id.delete_contact:
		contact_number.setText("");
		contanct_name.setText("");					    
		break;
	default:
		break;
	}
}
public  void oncontent_deleteClick(View v){
	switch (v.getId()) {
	case R.id.content_delete:
		mydb=openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
		String whereClause="name=?";
		String[] whereArgs={edit1.getText().toString()};
		mydb.delete(TABLE_NAME, whereClause, whereArgs);
		showData(0,"",""); 
		mydb.close();	
		break;
	default:
		break;
	}
}

public void comeforomsms(String contact_name,String contact_content) {
	// TODO Auto-generated method stub
	mydb=openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
	ContentValues cv=new ContentValues();
	cv.put(NAME, contact_name);
	cv.put(AGE, contact_content);
	mydb.insert(TABLE_NAME, null, cv);
	mydb.close();
}
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	if (resultCode == Activity.RESULT_OK) {
		switch (requestCode) {
		case PICK_CONTACT_SUBACTIVITY:
			Cursor cursor = null;
			Cursor phones = null;
			String name=null,phoneNumber = null;
			Uri uri = data.getData();
			if (uri != null) {
				cursor = getContentResolver().query(uri, null, null, null, "name desc");
				if (cursor != null && cursor.moveToFirst()) {
					try {
						String id = cursor.getString(cursor.getColumnIndexOrThrow(Contacts.People._ID));
						phones = getContentResolver().query(Contacts.Phones.CONTENT_URI, null, Contacts.Phones.PERSON_ID + "=" + id,
								null, null);
						while (phones.moveToNext()) {
							phoneNumber = phones.getString(phones.getColumnIndexOrThrow(Contacts.Phones.NUMBER));
							name=phones.getString(phones.getColumnIndexOrThrow(Contacts.People.NAME));
							phoneNumber = phoneNumber.replaceAll("\\D", "");
							if (phoneNumber.startsWith("86")) {
								phoneNumber = phoneNumber.substring(2);
							}
						}
						contact_number.setText(phoneNumber);
						contanct_name.setText(name);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if (phones != null) {
							phones.close();
							phones = null;
						}
						if (cursor != null) {
							cursor.close();
							cursor = null;
						}
					}}
			}
			break;
		}
	}
	super.onActivityResult(requestCode, resultCode, data);
}
}