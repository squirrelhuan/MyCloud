package cells;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.mycloud.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class cell_listview extends Activity{

	private int[] imageId = new int[] { R.drawable.menu_bar_toggle_flight_mode, R.drawable.menu_bar_toggle_gps,
			R.drawable.menu_bar_toggle_bluetooth, R.drawable.menu_bar_toggle_screenshot, R.drawable.menu_bar_toggle_power,
			R.drawable.menu_bar_toggle_wifi, R.drawable.menu_bar_toggle_vibrate, R.drawable.menu_bar_toggle_torch,
			R.drawable.menu_bar_toggle_privacy_mode, R.drawable.menu_bar_toggle_data, R.drawable.menu_bar_toggle_brightness_manual,
			R.drawable.menu_bar_toggle_divider }; 
	private ListView lv_setting;
	private List<String>list;
	private ArrayAdapter<String>aa;
	private String[] array_dt = { };
	//
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.cell_listview_main);
		Resources res = this.getResources();
		array_dt=res.getStringArray(R.array.menuitems);
		ArrayList<HashMap<String,Object>>
        item=new ArrayList<HashMap<String,Object>>();		
        for(int i=0;i<6;i++){
        	HashMap<String,Object>item_web=new HashMap<String,Object>();
        	if(i==0){
        		item_web.put("username",array_dt[i]);
        		item_web.put("age","11");
        		item.add(item_web);
        	}
        	else{
        	item_web.put("img",imageId[i]);
        	item_web.put("username","("+i+")"+array_dt[i]);
        	item_web.put("age",(20+i)+"");
        	item.add(item_web);
        	}
        }
        SimpleAdapter saImageItems=new SimpleAdapter(this,item,R.layout.cell_listview_item_image,
        		new String[]{"img","username"},
        		new int[]{R.id.img,R.id.name});
        ((ListView)findViewById(R.id.list)).setAdapter(saImageItems);
/*
		lv_setting=new ListView(this);
		list=new ArrayList<String>();
		list.add("�����Ű�");
		list.add("���ȵ���");
		list.add("��������");
		list.add("���Ի�����");
		list.add("����Cbrowser");
		aa=new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1,list);
		lv_setting.setAdapter(aa);
		this.setContentView(lv_setting);
		lv_setting.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//Toast.makeText(MainSetting_list.this, "��ǰѡ�����б�����±�Ϊ��"+position, Toast.LENGTH_SHORT).show();
			}
			
	
	});*/
		}}
