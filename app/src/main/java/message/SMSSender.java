package message;

import android.app.Activity;
import android.content.Context;
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
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycloud.R;

import java.util.List;

public class SMSSender extends Activity implements OnClickListener{
    private static final Context context = null;
	private static final String phoneNum = null;
	private static final int PICK_CONTACT_SUBACTIVITY = 2;
	public Button button,button2;
     EditText mobiletext,messagetext;
    private TextView contanct_name;
    private ListView sms_lv;
    public String mobile,message;
    public static String zh_sms_tv;
	private int COUNT;
	


	SQLiteDatabase db = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_main);
		button=(Button)findViewById(R.id.send);
		button.setOnClickListener(this);
		button2=(Button)findViewById(R.id.add_contact);
		button2.setOnClickListener(this);
		mobiletext=(EditText) findViewById(R.id.contact_number);
		messagetext=(EditText) findViewById(R.id.content);
		contanct_name=(TextView) findViewById(R.id.contanct_name);
		sms_lv = (ListView) findViewById(R.id.sms_lv);
		db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()+"/my.db3",
				null);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.send:
			String mobile=mobiletext.getText().toString();
			String message=messagetext.getText().toString();
			if(message!=null){
				SmsManager sms=SmsManager.getDefault();
				List<String>texts=sms.divideMessage(message);
		
			
			//PendingIntent sentIntent=PendingIntent.getBroadcast(SMSSender.this, 0, new Intent(),0);
			//if(message.length()>70){
				
				for(String text:texts){
					sms.sendTextMessage(mobile,null,text,null,null);
					Toast.makeText(SMSSender.this, "", Toast.LENGTH_LONG).show();
					break;
					//smsManager.sendTextMessage(mobile, null, msg, sentIntent, null);
				}
			}

				try {
					// TODO Auto-generated method stub
					insertData(db, mobiletext.getText().toString(), messagetext
							.getText().toString());
					Cursor cursor = db.rawQuery("select * from mydb", null);
					inflateCursor(cursor);
				} catch (Exception e) {
					db.execSQL("create table mydb(_id integer primary key autoincrement,mobiletext varchar(255),messagetext varchar(255))");
					insertData(db, mobiletext.getText().toString(), messagetext
							.getText().toString());
					Cursor cursor = db.rawQuery("select * from mydb", null);
					inflateCursor(cursor);
				}

			/*else{
				smsManager.sendTextMessage(mobile, null, menussage, sentIntent, null);
			}*/
			
		/*case R.id.button2:
			mobile=mobile+"3";
			mobiletext.setText(mobile);
			break;*/
		case R.id.add_contact:	
			Uri uri = Uri.parse("content://contacts/people");
			Intent intent = new Intent(Intent.ACTION_PICK, uri);
			startActivityForResult(intent, PICK_CONTACT_SUBACTIVITY);
			break;
		default:
			break;
		}	
	}
	
	public void insertData(SQLiteDatabase db, String mobiletext, String messagetext) {
		db.execSQL("insert into mydb(_id,mobiletext,messagetext) values(null,?,?)",
				new String[] { mobiletext, messagetext });
	}

	public void inflateCursor(Cursor cursor) {
		SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.cell_sms_item,
				cursor, new String[] { "mobiletext", "messagetext" }, new int[] {
						R.id.name, R.id.name });
		sms_lv.setAdapter(sca);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(db != null && db.isOpen()){
			db.close();
		}
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

							mobiletext.setText(phoneNumber);
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
						}
					}
				}
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	
		
	
	

}
