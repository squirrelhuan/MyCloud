package com.example.ui;

import java.util.ArrayList;
import java.util.Collections;

import com.example.laucher.CommonUtil;
import com.example.laucher.LauncherItem;
import com.example.mycloud.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DateAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<LauncherItem> lstDate;
	RelativeLayout layoutItem;
	ImageView ivItemIcon, ivDeleteIcon;
	TextView tvItemText, tvCloudCount;
	int dp80 = 80;
	private boolean delVisible = false;

	public DateAdapter(Context mContext, ArrayList<LauncherItem> list) {
		this.context = mContext;
		lstDate = list;
		dp80 = CommonUtil.dip2px(mContext, 103);
	}

	public void setDelVisible(boolean b) {
		this.delVisible = b;
	}

	public boolean getDelvisible() {
		return this.delVisible;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lstDate.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lstDate.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void exchange(int startPosition, int endPosition) {
		Collections.swap(lstDate, startPosition, endPosition);
	}

	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(R.layout.laucher_item,
				null);
		LauncherItem item = (LauncherItem) getItem(position);
		layoutItem = (RelativeLayout) convertView
				.findViewById(R.id.layout_gridItem);
		ivItemIcon = (ImageView) convertView.findViewById(R.id.iv_itemIcon);
		tvItemText = (TextView) convertView.findViewById(R.id.tv_itemText);
		tvCloudCount = (TextView) convertView.findViewById(R.id.tv_itemCloud);
		ivDeleteIcon = (ImageView) convertView.findViewById(R.id.iv_delItem);
		if (!item.isNone()) {
			ivItemIcon.setImageResource(item.iconId);
			tvItemText.setText(context.getText(item.textId));
			layoutItem
					.setBackgroundResource(item.type <= 0 ? R.drawable.lm_index1_x
							: R.drawable.lm_index2_x);
			//姝ゅ璁剧疆绗竴绗簩椤逛笉鍙垹闄�
			if (item.id != 1 && item.id != 2) {
				ivDeleteIcon.setVisibility(delVisible ? View.VISIBLE
						: View.GONE);
			} else
				ivDeleteIcon.setVisibility(View.GONE);
			// 杩欐槸鏍囩ず鏄惁鏈夋皵娉�
			if (item.isCloud) {
				tvCloudCount.setVisibility(View.VISIBLE);
				tvCloudCount.setText(item.cloudCount + "");
			}
		}
		ivDeleteIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((DragGrid) parent).deleteView(position);
				// TODO Auto-generated method stub

			}
		});
		// TODO Auto-generated method stub
		return convertView;
	}

}
