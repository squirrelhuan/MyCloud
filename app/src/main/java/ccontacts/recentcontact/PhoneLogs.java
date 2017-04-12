package ccontacts.recentcontact;

import java.util.Date;

import com.example.mycloud.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import ccontacts.recentcontact.adapter.CallCursorAdapter;

@SuppressWarnings("unused")
public class PhoneLogs extends Activity {
	private RadioButton timeRadio = null;
	private RadioButton typeRadio = null;
	private ToggleButton sortButton = null;
	private String order = null;
	private String sort = "DESC";
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.recentcontact_main);
		setAdapter(CallLog.Calls.DEFAULT_SORT_ORDER);
		
		sortButton = (ToggleButton)findViewById(R.id.SortButton);
		timeRadio = (RadioButton)findViewById(R.id.RadioTime);
		typeRadio = (RadioButton)findViewById(R.id.RadioType);
		RadioGroup group = (RadioGroup)findViewById(R.id.RadioGroup01);
		group.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				RadioButton view = (RadioButton)group.findViewById(checkedId);
				if (view.getId() == R.id.RadioTime){
					order = "date "+sort;
					setAdapter(order);
				}else if (view.getId() == R.id.RadioType){
					order = "type "+sort;
					setAdapter(order);
				}
			}
		});
		
		sortButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (PhoneLogs.this.getString(R.string.desc).equals(buttonView.getText())){
					sort = "ASC";
				}else if (PhoneLogs.this.getString(R.string.asc).equals(buttonView.getText())){
					sort = "DESC";
				}
			}
		});
	}
	
	public void setAdapter(String order){
		ListView list = (ListView)findViewById(R.id.CallList);
		
		Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI,
				null, null, null, order);
		startManagingCursor(cursor);
		CallCursorAdapter adapter = new CallCursorAdapter(this,
				R.layout.recentcontact_callinfo, cursor,
				new String[] { "number", "name", "date", "type"},
				new int[] { R.id.TextNumber,R.id.TextName, R.id.TextDuration, R.id.TextType });
		list.setAdapter(adapter);
		list.setOnItemClickListener(itemClick);
	}
	
	OnItemClickListener itemClick = new OnItemClickListener(){
		public void onItemClick(AdapterView<?> parent, View view,
			int position, long id) {
			LinearLayout layout = (LinearLayout)view;
			TextView numberText = (TextView)layout.findViewById(R.id.TextNumber);
			Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel://"+numberText.getText()));
			startActivity(callIntent);
		}
	};
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}