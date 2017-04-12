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
		tv.setText("您输入的信息如下：\n");
		tv.append("用户名："+name+"\n");
		tv.append("密码："+key);
		//String packageName=componentName.getPackageName();
		//String className=componentName.getClassName();
		//tv.setText("包名称："+packageName+"\n");
		//tv.append("类名称："+className);
				
		
	}

}
