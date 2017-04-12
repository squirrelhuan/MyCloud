package ccontacts.recentcontact.contact;

import android.os.Bundle;

import com.example.mycloud.R;
import ccontacts.recentcontact.contact.base.BaseContactList;

public class MyContactList extends BaseContactList {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recentcontact_list_layout);
		setListAdapter("name is not null", null);
	}
}
