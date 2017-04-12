package ccontacts.recentcontact.contact;

import com.example.mycloud.R;

import android.os.Bundle;
import ccontacts.recentcontact.contact.base.BaseContactList;

public class StrangerContactList extends BaseContactList {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recentcontact_list_layout);
		setListAdapter("name is null", null);
	}
}
