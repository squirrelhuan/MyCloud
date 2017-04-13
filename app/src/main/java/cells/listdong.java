package cells;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.mycloud.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class listdong extends Activity{
public Bundle mListViews;
private List<? extends Map<String, ?>> herolist_wu;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.cell_viewpage);
	//LayoutInflater����
	   LayoutInflater inflater = getLayoutInflater();
	   //viewPager
	   ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
	   //viewlist���������������ҳ������
	   ArrayList<View> viewList = new ArrayList<View>();
	   //LayoutInflater��ʵ����������ҳ
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

	   //list��
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
    public int getCount() {                                                                 //���size
        // TODO Auto-generated method stub
        return viewLists.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {                         
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }
    
    @Override
    public void destroyItem(View view, int position, Object object)                       //����Item
    {
        ((ViewPager) view).removeView(viewLists.get(position));
    }
    
    @Override
    public Object instantiateItem(View view, int position)                                //ʵ����Item
    {
        ((ViewPager) view).addView(viewLists.get(position), 0);
        return viewLists.get(position);
    }
    
}
}