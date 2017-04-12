package com.example.laucher;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.example.mycloud.R;
import com.example.ui.Configure;
import com.example.ui.DateAdapter;
import com.example.ui.DragGrid;
import com.example.ui.DragGrid.G_PageListener;
import com.example.ui.ScrollLayout;
import com.example.ui.ScrollLayout.PageListener;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	private DragGrid gridView;
	TextView tv_page;// int oldPage=1;
	private ScrollLayout lst_views;
	LinearLayout.LayoutParams param;
	private RelativeLayout relate;
	ArrayList<DragGrid> gridviews = new ArrayList<DragGrid>();
	private LinearLayout linear;
	Animation up, down;
	// private ImageView delImage;
	// 鍏ㄩ儴鏁版嵁鐨勯泦鍚堥泦lists.size()==countpage;
	// 淇濆瓨姣忎竴椤典笂闈㈢殑item
	ArrayList<ArrayList<LauncherItem>> lists = new ArrayList<ArrayList<LauncherItem>>();
	ArrayList<LauncherItem> lstDate;// 姣忎竴椤电殑鏁版嵁,瀛樺偍鐨勬槸澶氭湁鐨刬tem鐨勯厤缃俊鎭�
	public static final int PAGE_SIZE = 8;// 姣忛〉鐨刬tem

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.laucher_main);
		initUI();
		getLauncherData();
		initData();
	}

	private void initUI() {
		relate = (RelativeLayout) findViewById(R.id.relate);
		lst_views = (ScrollLayout) findViewById(R.id.views);
		tv_page = (TextView) findViewById(R.id.tv_page);// 褰撳墠椤垫暟
		tv_page.setText("1");
		gridView = new DragGrid(this);
		Configure.init(this);
		Configure.scrollLayout = lst_views;
		param = new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.FILL_PARENT);
		param.rightMargin = CommonUtil.dip2px(this, 70);
		param.leftMargin = CommonUtil.dip2px(this, 10);
		if (gridView != null) {
			lst_views.removeAllViews();
		}
		lst_views.setPageListener(new PageListener() {

			@Override
			public void page(int page) {
				setCurPage(page);
				// TODO Auto-generated method stub

			}
		});

		relate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Configure.isDelVisible) {
					Configure.isDelVisible = false;
					lst_views.changeGridDelVisible(false);
				}
				// TODO Auto-generated method stub

			}
		});
		//
		// if (CommonUtil.readSharedPreferences(this, "layer1", "").equals(""))
		// {
		// relate.addView(getLayerView(R.drawable.layer1, "layer1"));
		// }
	}

	public void initData() {
		// 淇濆瓨鎬婚〉鏁�
		Configure.countPages = (int) Math.ceil(lstDate.size()
				/ (float) PAGE_SIZE);
		lists.clear();
		for (int i = 0; i < Configure.countPages; i++) {
			lists.add(new ArrayList<LauncherItem>());
			for (int j = PAGE_SIZE * i; j < ((PAGE_SIZE * (i + 1) > lstDate
					.size() ? lstDate.size() : PAGE_SIZE * (i + 1))); j++)
				lists.get(i).add(lstDate.get(j));
		}
		// 鍔犺浇鏈�鍚庝竴椤�
		for (int i = lists.get(Configure.countPages - 1).size(); i < PAGE_SIZE; i++) {
			lists.get(Configure.countPages - 1).add(new LauncherItem());
		}
		lst_views.removeAllViews();
		gridviews = new ArrayList<DragGrid>();
		for (int i = 0; i < Configure.countPages; i++) {
			lst_views.addView(addGridView(i));// 鏍规嵁椤垫暟锛屽湪姣忎竴涔熶笂娣诲姞gridview

		}
	}

	// 椤甸潰鎿嶄綔浜嬩欢
	private DragGrid.G_PageListener launcherPageListener = new G_PageListener() {

		@Override
		public void page(int cases, int page) {
			switch (cases) {
			case 0:// 椤甸潰婊戝姩
					// 婊戝姩鍒扮鍑犲睆骞�
				Log.i("huadong", "0");
				lst_views.snapToScreen(page);
				setCurPage(page);
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						Configure.isChangingPage = false;
					}
				}, 800);
				break;
			case 5:// 鏉炬墜鍔ㄤ綔
				Log.i("pageListener", "5");
				lists.get(Configure.currentPage).get(Configure.removeItem).isShow = false;
				lists.get(Configure.currentPage)
						.remove(Configure.removeItem/* + 1 */);
				// ((DateAdapter)
				// ((gridviews.get(Configure.curentPage)).getAdapter())).notifyDataSetChanged();
				refreshLaucher(Configure.currentPage);
				break;

			}
			// TODO Auto-generated method stub

		}
	};
	// 妗岄潰鏍煎瓙璺ㄥ睆骞曠Щ鍔ㄤ簨浠�
	private DragGrid.G_ItemChangeListener launcherGridChangeListener = new DragGrid.G_ItemChangeListener() {
		@Override
		public void change(int from, int to, int count) {// 3 5 -1
			System.out.println("Configure.curentPage" + Configure.currentPage);
			System.out.println("count" + count);

			if (to != -1) {
				Log.i("from,to", Configure.currentPage + "," + from + "," + to+","
						+ count);
				// 绗�2椤电殑item
				LauncherItem fromString = (LauncherItem) lists.get(
						Configure.currentPage - count).get(from);
				System.out.println(fromString.id);

				// 绗�1椤电殑item
				LauncherItem toString = (LauncherItem) lists.get(
						Configure.currentPage).get(to);
				Log.i("G_ItemChangeListener", "G_ItemChangeListener"
						+ toString.id);
				// System.out.println("G_ItemChangeListener"+toString.id);
				lists.get(Configure.currentPage - count).add(from, toString);
				lists.get(Configure.currentPage - count).remove(from + 1);
				lists.get(Configure.currentPage).add(to, fromString);
				lists.get(Configure.currentPage).remove(to + 1);
			}

			refreshLaucher(Configure.currentPage);
		}

	};

	public ImageView getLayerView(int resId, final String key) {
		ImageView iv = new ImageView(this);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		iv.setLayoutParams(params);
		iv.setBackgroundResource(resId);
		iv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				v.setVisibility(View.GONE);
				CommonUtil.writeSharePreferences(MainActivity.this, key, key);
			}
		});
		return iv;
	}

	/**
	 * 鍒锋柊妗岄潰
	 */
	public void refreshLaucher(int page) {
		lstDate = new ArrayList<LauncherItem>();
		for (int i = 0; i < lists.size(); i++) {
			for (int j = 0; j < lists.get(i).size(); j++) {
				if (lists.get(i).get(j) != null
						&& !lists.get(i).get(j).isNone()) {
					lstDate.add(lists.get(i).get(j));
				}
			}
		}
		initData();
	}

	/**
	 * 鑾峰彇绗竴涓负None鏍囪鐨�
	 */
	public int getFristNonePosition(ArrayList<LauncherItem> array) {
		for (int i = 0; i < array.size(); i++) {
			if (array.get(i) != null && array.get(i).isNone()) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 鍒濆鍖栨瘡涓晫闈㈢殑gridview
	 */
	private LinearLayout addGridView(int i) {
		// if (lists.get(i).size() < PAGE_SIZE)
		// lists.get(i).add(null);

		linear = new LinearLayout(this);
		gridView = new DragGrid(this);
		DateAdapter adapter = new DateAdapter(this, lists.get(i));
		adapter.setDelVisible(Configure.isDelVisible);
		gridView.setAdapter(adapter);
		gridView.setNumColumns(2);
		int dp10 = CommonUtil.dip2px(this, 10);
		gridView.setHorizontalSpacing(dp10);
		gridView.setVerticalSpacing(dp10);

		final int ii = i;
		gridView.setOnItemClickListener(new DragGrid.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (Configure.isDelVisible) {
					Configure.isDelVisible = false;
					lst_views.changeGridDelVisible(false);
					return;
				}
				// TODO GridView鏍煎瓙鐐瑰嚮浜嬩欢
				LauncherItem item = lists.get(ii).get(position);
				if (item != null) {
					if (item.isNone())
						return;
					switch (item.type) {
					case 0:// 闇�瑕佺櫥褰曢獙璇佺殑

					case -1:

						break;
					}

				}
			}

		});
		gridView.setSelector(R.anim.grid_light);
		gridView.setPageListener(launcherPageListener);
		gridView.setOnItemChangeListener(launcherGridChangeListener);
		gridviews.add(gridView);
		linear.addView(gridviews.get(i), param);
		return linear;
	}

	/**
	 * 璁剧疆褰撳墠椤�
	 */
	private void setCurPage(final int page) {
		tv_page.setText((page + 1) + "");
	}

	/**
	 * 鑾峰彇妗岄潰鍥炬爣椤哄簭
	 */
	public void getLauncherData() {
		String launcher = CommonUtil.readLauncherSharedPreferences(this);
		if (launcher == null || launcher.equals("")) {
			launcher = CommonUtil.DEFAULT_DINGYUE_ORDER;
			CommonUtil.saveLauncherSharedPreferences(this, launcher);
		}
		String[] strArray = launcher.split(",");
		lstDate = new ArrayList<LauncherItem>();
		for (int i = 0; i < strArray.length; i++) {
			CommonUtil.launcherItems.get(Integer.valueOf(strArray[i])).isShow = true;
			lstDate.add(CommonUtil.launcherItems.get(Integer
					.valueOf(strArray[i])));

		}

	}

	/**
	 * 淇濆瓨妗岄潰鏁版嵁
	 */
	public void saveLauncherData(Context context) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < lists.size(); i++) {
			ArrayList<LauncherItem> lst = lists.get(i);
			// 鏈�鍚庝负null锛岃闄ゆ帀
			for (int j = 0; j < lst.size(); j++) {
				if (!lst.get(j).isNone())
					sb.append(lst.get(j).id).append(",");
			}
		}
		if (sb.length() > 1)
			sb.setLength(sb.length() - 1);

		CommonUtil.saveLauncherSharedPreferences(context, sb.toString());
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			onBack(null);
		}
		return false;
	}

	private static Boolean isExit = false;

	// 鎹曟崏鍥為��閿�
	public void onBack(View v) {
		if (Configure.isDelVisible) {
			Configure.isDelVisible = false;
			lst_views.changeGridDelVisible(false);
			return;
		}
		Timer tExit = null;
		if (isExit == false) {
			isExit = true;// 鍑嗗閫�鍑�
			tExit = new Timer();
			tExit.schedule(new TimerTask() {

				@Override
				public void run() {
					isExit = false;// 鍙栨秷閫�鍑�
				}
			}, 2000);// 濡傛灉2绉掗挓鍐呮病鏈夋寜涓嬭繑鍥為敭锛屽垯鍚姩瀹氭椂鍣ㄥ彇娑堟帀鍒氭墠鎵ц鐨勪换鍔�
		} else {
			saveLauncherData(this);
			finish();
		}

	}

}
