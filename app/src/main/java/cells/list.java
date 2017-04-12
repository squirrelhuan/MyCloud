package cells;

import java.util.ArrayList;

import com.example.mycloud.MyAdapter;
import com.example.mycloud.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

public class list extends Activity{
public Bundle mListViews;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.cell_viewpage);
	//实例化一个LayoutInflater对象
	   LayoutInflater inflater = getLayoutInflater();
	   //通过步骤1中声明的组件ID来获取ViewPager
	   ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
	   //新建一个viewlist对象来保存各个分页的内容
	   ArrayList<View> viewList = new ArrayList<View>();
	   //通过LayoutInflater来实例化各个分页
	   View view1 = inflater.inflate(R.layout.layout1, null);
	   View view2 = inflater.inflate(R.layout.layout3, null);
	   //添加分页到list中
	   viewList.add(view1);
	   viewList.add(view2);
	   viewPager.setAdapter(new MyAdapter(viewList));	
}

}
