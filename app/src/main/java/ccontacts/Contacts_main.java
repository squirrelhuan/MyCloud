package ccontacts;




import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mycloud.R;

import java.util.ArrayList;

public class Contacts_main extends Activity{
	private AutoCompleteTextView myAutoCompleteTextView;
	private TextView myTextView1;
	private Cursor contactCursor;
	private ContactsAdapter2 myContactsAdapter;
	public static final String[] PEOPLE_PROJECTION=new String[]{
		Contacts.People._ID,Contacts.People.PRIMARY_EMAIL_ID,
		Contacts.People.TYPE,Contacts.People.NUMBER,Contacts.People.LABEL,Contacts.People.NAME};
	
	public static final int PAGE_SIZE = 10;
	public static final int GET_OK = 0x100;
	public static final int GET_FAILE = 0x101;
	public final static int LISTVIEW_DATA_MORE = 1;
	public final static int LISTVIEW_DATA_LOADING = 2;
	public final static int LISTVIEW_DATA_FULL = 3;
	public final static int LISTVIEW_DATA_EMPTY = 4;
	
	private int index = 0;
	
	private ListView contactLv;
	private View footer;
	private ProgressBar footer_bar;
	private TextView footer_tv;
	private ContactAdapter adapter;
	private ArrayList<Contact> contactList = new ArrayList<Contact>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts_main);
		contactLv = (ListView) findViewById(R.id.contact_lv);
		footer = getLayoutInflater().inflate(R.layout.listview_footer, null);
		footer_bar = (ProgressBar) footer.findViewById(R.id.foot_progress);
		footer_tv = (TextView) footer.findViewById(R.id.foot_more);
		contactLv.addFooterView(footer);
		getContacts();
		/*myAutoCompleteTextView=(AutoCompleteTextView)findViewById(R.id.myAutoCompleteTextView);
		myTextView1=(TextView)findViewById(R.id.myTextView1);
		
		ContentResolver content=getContentResolver();
		contactCursor=content.query(Contacts.People.CONTENT_URI,
				PEOPLE_PROJECTION, null, null, Contacts.People.DEFAULT_SORT_ORDER);
		myContactsAdapter=new ContactsAdapter(this,contactCursor);
		myAutoCompleteTextView.setAdapter(myContactsAdapter);
		myAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				Cursor c=myContactsAdapter.getCursor();
				c.moveToPosition(arg2);
				String number=c.getString(c.getColumnIndexOrThrow(Contacts.People.NUMBER));
				
				if(number==null){
				number="ϵδӣ“;
				}
				String name=c.getString(c.getColumnIndexOrThrow(Contacts.People.NAME));
				myTextView1.setText(c.getString(c.getColumnIndexOrThrow(Contacts.People.NAME))+"�ĵ绰��"+number);
				
			}
		});*/
	}
	
	private void initAdapter(ArrayList<Contact> list) {
		if (adapter == null) {
			adapter = new ContactAdapter(this, list);
			contactLv.setAdapter(adapter);
		}else {
			adapter.setRefreshData(list);
		}
	}
	
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GET_OK:
				index++;
				ArrayList<Contact> temp = (ArrayList<Contact>) msg.obj;
				if (temp.size() > 0) {
					contactList.addAll(temp);
					contactLv.setTag(LISTVIEW_DATA_MORE);
					initAdapter(contactList);
					footer_bar.setVisibility(View.GONE);
					footer_tv.setText("�ɿ��鿴����");
				}else {
					footer_bar.setVisibility(View.GONE);
					footer_tv.setText("�������");
					contactLv.setTag(LISTVIEW_DATA_FULL);
					contactLv.removeFooterView(footer);
				}
				break;
			case GET_FAILE:
				break;

			default:
				break;
			}
		};
	};
	
		public void getContacts() {
			new Thread() {
				
				@Override
				public void run() {
					super.run();
					
					try {
						Thread.sleep(1000 * index);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; 
					String[] projection = { ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
											ContactsContract.CommonDataKinds.Phone.DATA1, "sort_key"};
					Cursor cursor = getContentResolver().query(uri, projection, null, null, "sort_key COLLATE LOCALIZED asc limit " + PAGE_SIZE + " offset " + index * PAGE_SIZE);
					if (cursor != null) {
						ArrayList<Contact> tempList = new ArrayList<Contact>();
						while (cursor.moveToNext()) {
							String contactName = cursor.getString(0);
							String phoneNumber = cursor.getString(1);
							if (TextUtils.isEmpty(phoneNumber)) {
								continue;
							}
							
							Contact contact = new Contact(contactName, phoneNumber);
							tempList.add(contact);
						}
						cursor.close();
						Log.d("C", tempList.toString());
						Message message = Message.obtain();
						message.what = GET_OK;
						message.obj = tempList;
						mHandler.sendMessage(message);
					}else {
						//faile
						Log.d("C", "Faile");
						mHandler.sendEmptyMessage(GET_FAILE);
					}
				}
			}.start();
		}
	

}
