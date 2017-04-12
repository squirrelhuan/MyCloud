package cells;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.mycloud.MyAdapter;
import com.example.mycloud.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class listdong extends Activity{
public Bundle mListViews;
private List<? extends Map<String, ?>> herolist_wu;
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
	   View view1 = inflater.inflate(R.layout.layout2, null);
	   View view2 = inflater.inflate(R.layout.layout2, null);
	   ListView listview1 = (ListView) view1.findViewById(R.id.list);
	   ListView listview2 = (ListView) view1.findViewById(R.id.list);
	   ArrayList<HashMap<String,Object>>
	   item=new ArrayList<HashMap<String,Object>>();
	   HashMap<String,Object>item_web=new HashMap<String,Object>();
	   item_web.put("username","456465489");
		item_web.put("age","11");
		item.add(item_web);
	   SimpleAdapter simpleAdapter_Wu = new SimpleAdapter(this, item,
			    R.layout.cell_listview_item_image,
			    new String[]{"img", "username"},
			    new int[]{R.id.img, R.id.name});
	   listview1.setAdapter(simpleAdapter_Wu);

	   //添加分页到list中
	   viewList.add(view1);
	   viewList.add(view2);
	   viewPager.setAdapter(new MyAdapter(viewList));	
}
public class MyAdapter extends PagerAdapter{

    List<View> viewLists;
    
    public MyAdapter(List<View> lists)
    {
        viewLists = lists;
    }

    @Override
    public int getCount() {                                                                 //获得size
        // TODO Auto-generated method stub
        return viewLists.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {                         
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }
    
    @Override
    public void destroyItem(View view, int position, Object object)                       //销毁Item
    {
        ((ViewPager) view).removeView(viewLists.get(position));
    }
    
    @Override
    public Object instantiateItem(View view, int position)                                //实例化Item
    {
        ((ViewPager) view).addView(viewLists.get(position), 0);
        return viewLists.get(position);
    }
    
}
}