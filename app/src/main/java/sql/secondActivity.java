package sql;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mycloud.R;


public class secondActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);
		TextView tv=(TextView)findViewById(R.id.textView3);
		
		Intent intent=this.getIntent();
		String name=intent.getStringExtra("name");
		String key=intent.getStringExtra("key");
		//ComponentName componentName=intent.getComponent();
		String action=intent.getAction();
		tv.setText("");
		tv.append(""+name+"\n");
		tv.append(""+key);
		//String packageName=componentName.getPackageName();
		//String className=componentName.getClassName();
		//tv.setText("�����ƣ�"+packageName+"\n");
		//tv.append("�����ƣ�"+className);
				
		
	}

}
