package ccontacts.recentcontact.contact;

import android.os.Bundle;

import com.example.mycloud.R;
import ccontacts.recentcontact.contact.base.BaseContactList;

public class AlwaysContactList extends BaseContactList {
	//private final String duration = "1";		//通话时长一小时
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		Calendar cal = Calendar.getInstance();
//		long newTime = cal.getTimeInMillis();
//		cal.add(Calendar.DAY_OF_MONTH, -50);
//		long beforeTime = cal.getTimeInMillis();
//		String sql = "_id in(select total._id from (select _id,sum(duration) as duration from calls where date>="+beforeTime+" and date<="+newTime+" group by number) as total where total.duration>="+duration+")";
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recentcontact_list_layout);
	}
}
