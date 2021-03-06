package ccontacts.recentcontact.contact.base;

import com.example.mycloud.R;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import ccontacts.recentcontact.adapter.CallCursorAdapter;

public class BaseContactList extends ListActivity {

	protected String sort = "DESC";

	protected void setListAdapter(String where, String order){
		if (order == null){
			order = CallLog.Calls.DEFAULT_SORT_ORDER;
		}
		
		Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI,
				null, where, null, order);
		//��cursor�������ڽ���activity������
		startManagingCursor(cursor);
		CallCursorAdapter adapter = new CallCursorAdapter(this,
				R.layout.recentcontact_callinfo, cursor,
				new String[] { "number", "name", "date", "type"},
				new int[] { R.id.TextNumber,R.id.TextName, R.id.TextDuration, R.id.TextType});
		setListAdapter(adapter);
	}
	
	/* (non-Javadoc)
	 * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		LinearLayout layout = (LinearLayout)v;
		TextView numberText = (TextView)layout.findViewById(R.id.TextNumber);
		Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel://"+numberText.getText()));
		startActivity(callIntent);
	}
}
