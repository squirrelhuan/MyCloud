package sql;

import com.example.mycloud.R;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


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
		tv.setText("���������Ϣ���£�\n");
		tv.append("�û�����"+name+"\n");
		tv.append("���룺"+key);
		//String packageName=componentName.getPackageName();
		//String className=componentName.getClassName();
		//tv.setText("�����ƣ�"+packageName+"\n");
		//tv.append("�����ƣ�"+className);
				
		
	}

}
