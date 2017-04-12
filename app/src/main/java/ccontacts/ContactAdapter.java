package ccontacts;

import java.util.ArrayList;

import com.example.mycloud.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContactAdapter extends BaseAdapter {
	
	private Context mContext;
	private ArrayList<Contact> contactData;

	public ContactAdapter(Context context, ArrayList<Contact> contactDataList) {
		this.mContext = context;
		this.contactData = contactDataList;
	}
	
	static class ContactViewHolder {
		public TextView contactName;
		public TextView phoneNumber;
	}
	
	public void setRefreshData(ArrayList<Contact> refreshData) {
		this.contactData = refreshData;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return contactData.size();
	}

	@Override
	public Object getItem(int position) {
		return contactData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (contactData == null) {
			return null;
		}
		
		ContactViewHolder contactViewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.contact_row, null);
			contactViewHolder = new ContactViewHolder();
			contactViewHolder.contactName = (TextView)convertView.findViewById(R.id.contact_name);
			contactViewHolder.phoneNumber = (TextView)convertView.findViewById(R.id.contact_number);
			convertView.setTag(contactViewHolder);
		}else {
			contactViewHolder = (ContactViewHolder) convertView.getTag();
		}
		
		String phoneNumber = (String)contactData.get(position).getContactPhone();
		
		contactViewHolder.contactName.setText((String)contactData.get(position).getContactName());
		contactViewHolder.phoneNumber.setText(phoneNumber);
		
		return convertView;
	}
}
