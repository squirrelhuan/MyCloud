package sql;



import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.mycloud.R;


public class firstActivity extends Activity {
private SQLiteDatabase mydb=null;
private final static String DATABASE_NAME="FirstDataBase.db";
private final static String TABLE_NAME="firstTable";
private final static String ID="_id";
private final static String NAME="name";
private final static String AGE="age";
private final static String HOME="home";
private final static String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+ID+" INTEGER PRIMARY KEY,"+ NAME+" TEXT,"+AGE+" TEXT,"+HOME+" TEXT)";
//private EditText editText=null;
private EditText edit1=null;
private EditText edit2=null;
private ListView sms_lv;

/** Called when the activity is first created. */

@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.sql_main);

    Button btn1=(Button)findViewById(R.id.button1);
    Button btn2=(Button)findViewById(R.id.button2);
    Button btn3=(Button)findViewById(R.id.button3);
    Button btn4=(Button)findViewById(R.id.button4);
   // editText=(EditText)findViewById(R.id.editText1);
    edit1=(EditText)findViewById(R.id.edit1);
    edit2=(EditText)findViewById(R.id.edit2);
    sms_lv=(ListView) findViewById(R.id.sms_lv);

    edit1.setText("");
    edit2.setText("");
    

    mydb=openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
    try
    {
    	mydb.execSQL(CREATE_TABLE);
    }
    catch(Exception e)
    {
    }
    
    ContentValues cv=new ContentValues();
    cv.put(NAME, "");
    cv.put(AGE, "18");
    cv.put(HOME, "");
    mydb.insert(TABLE_NAME, null, cv);
    
   
    
    showData();
    
    mydb.close();
    
    btn1.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mydb=openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
			ContentValues cv=new ContentValues();
			cv.put(NAME, edit1.getText().toString());
			cv.put(AGE, edit2.getText().toString());
			mydb.insert(TABLE_NAME, null, cv);
			showData();
			mydb.close();
		}
	});
    btn2.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mydb=openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
			String whereClause="name=?";
			String[] whereArgs={edit1.getText().toString()};
			mydb.delete(TABLE_NAME, whereClause, whereArgs);
			showData();
			mydb.close();			
		}
	});
    btn3.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mydb=openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);	
			String selection="name=?";
			String[] selectionArgs={edit1.getText().toString()};
			Cursor cur=mydb.query(TABLE_NAME, new String[] {ID,NAME,AGE,HOME}, selection, selectionArgs, null, null, null);
		    if(cur!=null)
		    {
		    	if(cur.moveToFirst())
		    	{
		    			String name=cur.getString(1);
		    			String age=cur.getString(2);
		    			String home=cur.getString(3);
		    			edit1.setText(name);
		    			edit2.setText(age);
		    	}	
		    }
		    else
		    {
		    }
			showData();
			mydb.close();
		}
	});
    btn4.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mydb=openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
			ContentValues cv=new ContentValues();
			cv.put(NAME, edit1.getText().toString());
			cv.put(AGE, edit2.getText().toString());
		
			String whereClause="name=?";
			String[] whereArgs={edit1.getText().toString()};
			mydb.update(TABLE_NAME, cv, whereClause, whereArgs);
			showData();
			mydb.close();			
		}
	});
  
    
}

public void showData()
{
   
    Cursor cur=mydb.query(TABLE_NAME, new String[] {ID,NAME,AGE,HOME}, null, null, null, null, null);
    int count=cur.getCount();
    if(cur!=null && count>=0)
    {
    	if(cur.moveToFirst())
    	{
    		do
    		{
    			String name=cur.getString(1);
    			String age=cur.getString(2);
    			String home=cur.getString(3);
    		//	editText.append(""+name+"\t\t"+age+"\t\t"+home+"\n");
    			SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.cell_sms_item,
    					cur, new String[] { "name", "age" }, new int[] {
    							R.id.laixin, R.id.name });
    			sms_lv.setAdapter(sca);
    		}while(cur.moveToNext());
    	}	
    }
}

}