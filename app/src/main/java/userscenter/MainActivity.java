package userscenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycloud.R;

import java.io.File;
import java.util.Calendar;
import java.util.Map;

import service.PreferencesService;
import service.fileservice;

public class MainActivity extends Activity implements OnClickListener{
	SQLiteDatabase db = null;
	private ImageView ic_users,iv_01,iv_02;
	private Bitmap getbitmap;
	private File tempFile;
	private ListView sms_lv;
	EditText title, content;
	private PreferencesService service;
	private String filename,filecontent;
	private TextView user_name,user_content;
	private String filepath = "/sdcard/users_image.jpg";
	 final String[] photo = new String[] { "", ""};
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.users_center);
	iv_01=(ImageView) findViewById(R.id.iv_01);
	iv_02=(ImageView) findViewById(R.id.iv_02);
	ic_users=(ImageView) findViewById(R.id.ic_users);
	ic_users.setOnClickListener(this);
	Button button_save=(Button) findViewById(R.id.button_save);
	button_save.setOnClickListener(this);
	Button button_read=(Button) findViewById(R.id.button_read);
	button_read.setOnClickListener(this);
	user_name = (TextView) findViewById(R.id.users_name);
	user_content = (TextView) findViewById(R.id.user_content);
	sms_lv=(ListView) findViewById(R.id.sms_lv);
    title=(EditText) findViewById(R.id.title);
	content=(EditText) findViewById(R.id.content);
	
	service=new PreferencesService(this);
	Map<String,String> params=service.getPreferences();
	user_name.setText(params.get("username"));
	user_content.setText(params.get("usercontent"));
	
	Bitmap bitmap1=BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
	iv_01.setImageBitmap(bitmap1);
	
	Bitmap alterBitmap=Bitmap.createBitmap(bitmap1.getWidth(),bitmap1.getHeight(),bitmap1.getConfig());
	Canvas canvas=new Canvas(alterBitmap);
	Paint paint =new Paint();
	paint.setColor(Color.BLACK);
	Matrix matrix=new Matrix();
	/*matrix.setValues(new float[]{
			0.5f,0,0,
			0,1,0,
			0,0,1
	});*/
	matrix.setScale(0.5f, 1);
	canvas.drawBitmap(bitmap1, matrix, paint);
	iv_02.setImageBitmap(alterBitmap);
	
	//video();
	db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()+"/my.db",null);
    
}
public void show(){try {
	// TODO Auto-generated method stub
	insertData(db, title.getText().toString(), content
			.getText().toString());
	Cursor cursor = db.rawQuery("select * from mydb", null);
	inflateCursor(cursor);
} catch (Exception e) {
	db.execSQL("create table mydb(_id integer primary key autoincrement,title varchar(255),content varchar(255))");
	insertData(db, title.getText().toString(), content
			.getText().toString());
	Cursor cursor = db.rawQuery("select * from mydb", null);
	inflateCursor(cursor);
}}
public void insertData(SQLiteDatabase db, String title, String content) {
	db.execSQL("insert into mydb(_id,title,content) values(null,?,?)",
			new String[] { title, content });
}
public void inflateCursor(Cursor cursor) {
	SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.line,
			cursor, new String[] { "title", "content" }, new int[] {
					R.id.tv1, R.id.tv2 });
	sms_lv.setAdapter(sca);
}
public void video(){
	Intent intent =new Intent();
	intent.setAction("android.media.action.VIDEO_CAPTURE");
	intent.addCategory("android.intent.category.DEFAULT");
	File file =new File("/sdcard/myvideo.mp4");
	Uri uri=Uri.fromFile(file);
	intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
	startActivity(intent);
}
public void camra(){
	Intent intent =new Intent();
	intent.setAction("android.media.action.IMAGE_CAPTURE");
	intent.addCategory("android.intent.category.DEFAULT");
	File file =new File("/sdcard/users_image.jpg");
	Uri uri=Uri.fromFile(file);
	intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
	startActivity(intent);
}
public void getimage(){
    File file = new File(filepath);
    if (file.exists()) {
            getbitmap = BitmapFactory.decodeFile(filepath);
            //ImageView��
            ic_users.setImageBitmap(getbitmap);
    }
}
@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()) {
	case R.id.ic_users:
		AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
		ad.setTitle("");
		ad.setIcon(R.drawable.audio);
		ad .setItems(photo, new DialogInterface.OnClickListener() {	 
		     @Override
		     public void onClick(DialogInterface dialog, int which) {
		    	 if(which==1){
		    	 camra();
		    	 }else{selectpicture();}
		    	 
		     }
		    });
		ad.show();
		
		break;
	case R.id.button_read:
		readuser();
		break;
	case R.id.button_save:
		storage();
		privateparametersave();
		break;
	default:
		break;
	}
}
public void storage(){
	
	
    fileservice service=new fileservice(getApplicationContext());
    filename=title.getText().toString();
    filecontent=content.getText().toString();
    try {
    	if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){}
		service.save(filename,filecontent);
		user_name.setText(filename);
		user_content.setText(filecontent);
	    Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
	} catch (Exception e) {
	    Toast.makeText(getApplicationContext(), "",  Toast.LENGTH_SHORT).show();

		e.printStackTrace();
	}
}
public void readuser(){
	fileservice service=new fileservice(getApplicationContext());
	String name;
	try {
		filename=title.getText().toString();
	    filecontent=content.getText().toString();
		service.readfile(filename);
		user_name.setText(filename);
		user_content.setText(filecontent);
	    Toast.makeText(getApplicationContext(), "��ȡsuccess",  Toast.LENGTH_SHORT).show();
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		Toast.makeText(getApplicationContext(), "��ȡfail",  Toast.LENGTH_SHORT).show();
	}
	}
public void privateparametersave(){
	String username=title.getText().toString();
    String usercontent=content.getText().toString();
	service.save(username,usercontent);
	Toast.makeText(getApplicationContext(), "OK",  Toast.LENGTH_SHORT).show();
}

public void selectpicture() {  
    Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT);  
    innerIntent.putExtra("crop", "true");
    innerIntent.putExtra("aspectX", 1);
    innerIntent.setType("image/*"); //com.google.android.mms.ContentType
      
    tempFile=new File("/sdcard/ll1x/"+Calendar.getInstance().getTimeInMillis()+".jpg");
    File temp = new File("/sdcard/ll1x/");
    if (!temp.exists()) {  
        temp.mkdir();  
    }  
    innerIntent.putExtra("output", Uri.fromFile(tempFile));
    innerIntent.putExtra("outputFormat", "JPEG");
      
    Intent wrapperIntent = Intent.createChooser(innerIntent, "");
    startActivityForResult(wrapperIntent, 1);
}
@Override  
protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
    super.onActivityResult(requestCode, resultCode, data);  
    switch (requestCode) {  
    case 1:  
    	ic_users.setImageDrawable(Drawable.createFromPath(tempFile.getAbsolutePath()));  
    	
        break;  
    } 
}  
}
