package ccontacts;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.Contacts;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ContactsAdapter2 extends CursorAdapter{
	private ContentResolver mContent;

	public ContactsAdapter2(Context context, Cursor c) {
		super(context, c);
		// TODO Auto-generated constructor stub
		mContent=(ContentResolver) context.getContentResolver();
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		((TextView)view).setText(cursor.getString(cursor.getColumnIndexOrThrow(Contacts.People.NAME)));
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		final LayoutInflater inflater=LayoutInflater.from(context);
		final TextView view=(TextView)inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent,false);
		view.setText(cursor.getString(cursor.getColumnIndexOrThrow(Contacts.People.NAME)));
		
		return view;
	}
	public String convertToString(Cursor cursor){
		return cursor.getString(cursor.getColumnIndexOrThrow(Contacts.People.NAME));
	}
	public Cursor runQueryOnBackgroundThread(CharSequence constraint){
		if(getFilterQueryProvider()!=null){
		return(getFilterQueryProvider().runQuery(constraint));
	}
		StringBuilder buffer= null;
	String[] args=null;
	if(constraint!=null){
		buffer=new StringBuilder();
		buffer.append("UPPER(");
		buffer.append(Contacts.ContactMethods.NAME);
		buffer.append(")GLOB(");
		args =new String[]{
				constraint.toString().toUpperCase()+"*"};
	}
		return mContent.query(Contacts.People.CONTENT_URI,
				Contacts_main.PEOPLE_PROJECTION,buffer==null ? null:buffer.toString(),
				args,Contacts.People.DEFAULT_SORT_ORDER);
	
	}

	}
