package ccontacts.recentcontact.portal;

import com.example.mycloud.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;


import ccontacts.recentcontact.contact.AlwaysContactList;
import ccontacts.recentcontact.contact.MyContactList;
import ccontacts.recentcontact.contact.StrangerContactList;

@SuppressWarnings("deprecation")
public class CalllogTabs extends TabActivity {
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		@SuppressWarnings("deprecation")
		final TabHost host = getTabHost();
		
		host.addTab(host.newTabSpec("myContact")
					.setIndicator("我的联系人", getResources().getDrawable(R.drawable.mycontact))
					.setContent(new Intent(this, MyContactList.class)));
		
		host.addTab(host.newTabSpec("stranger")
				.setIndicator("陌生人", getResources().getDrawable(R.drawable.strangercontact))
				.setContent(new Intent(this, StrangerContactList.class)));
		
		host.addTab(host.newTabSpec("alwaysContact")
				.setIndicator("经常联系人", getResources().getDrawable(R.drawable.alwayscontact))
				.setContent(new Intent(this, AlwaysContactList.class)));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		finish();
	}
}
