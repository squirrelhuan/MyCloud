package cells;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.example.mycloud.MyAdapter;
import com.example.mycloud.R;

import java.util.ArrayList;

public class list extends Activity{
public Bundle mListViews;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.cell_viewpage);
	//LayoutInflater����
	   LayoutInflater inflater = getLayoutInflater();
	   //1�ID�ȡViewPager
	   ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
	   //һ��viewlist��ҳ������
	   ArrayList<View> viewList = new ArrayList<View>();
	   //LayoutInflater�ҳ
	   View view1 = inflater.inflate(R.layout.layout1, null);
	   View view2 = inflater.inflate(R.layout.layout3, null);
	   //ӷ�ҳ��list�
	   viewList.add(view1);
	   viewList.add(view2);
	   viewPager.setAdapter(new MyAdapter(viewList));	
}

}
