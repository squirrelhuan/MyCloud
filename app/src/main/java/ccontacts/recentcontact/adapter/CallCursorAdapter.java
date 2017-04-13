package ccontacts.recentcontact.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.example.mycloud.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CallCursorAdapter extends ResourceCursorAdapter {
	final int DAY = 1440;				//һ��ķ���ֵ
	private int[] mTo;
	private String[] mOriginalFrom;

	public CallCursorAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to) {
		super(context, layout, c);
		mOriginalFrom = from;
		mTo = to;
	}
	
	/**
	 * �����ݵ���ͼ��
	 */
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		View[] views = new View[mTo.length];
		for (int i=0; i<views.length; i++){
			views[i] = view.findViewById(mTo[i]);
			String value = cursor.getString(cursor.getColumnIndex(mOriginalFrom[i]));
			
			if (views[i] instanceof TextView){
				if ("date".equals(mOriginalFrom[i])){
					long callTime = Long.parseLong(value);
					long newTime = new Date().getTime();
					long duration = (newTime - callTime) / (1000*60);
					
					if (duration < 60){
						value = duration+"����ǰ";
					}else if (duration >= 60 && duration < DAY){
						value = (duration/60)+"Сʱǰ";
					}else if (duration >= DAY && duration < DAY*2){
						value = "����";
					}else if (duration >= DAY*2 && duration < DAY*3){
						value = "ǰ��";
					}else if (duration >= DAY*7){
						SimpleDateFormat sdf = new SimpleDateFormat("M��dd��");
						value = sdf.format(new Date(callTime));
					}else{
						value = (duration/DAY)+"��ǰ";
					}
				}else if ("type".equals(mOriginalFrom[i])){
					int type = Integer.parseInt(value);
					if (CallLog.Calls.INCOMING_TYPE == type){
						view.setBackgroundResource(R.color.incoming);	//��������ɫ
						value = "�ѽӵ绰";
					}else if (CallLog.Calls.OUTGOING_TYPE == type){
						view.setBackgroundResource(R.color.outgoing);
						value = "�Ѳ��绰";
					}else if (CallLog.Calls.MISSED_TYPE == type){
						view.setBackgroundResource(R.color.missed);
						value = "δ�ӵ绰";
					}
				}else if ("name".equals(mOriginalFrom[i])){
					if (null == value || "".equals(value)){
						value = cursor.getString(cursor.getColumnIndex("number"));
					}
				}
				
				setText((TextView)views[i], value);
			}
		}
		
		final Context mContext = context;
		final TextView mNumber = (TextView)view.findViewById(R.id.TextNumber);
		ImageView mailButton = (ImageView)view.findViewById(R.id.MailButton);
		if (mailButton != null){
			//Ϊ�������ͼ����Ӵ����¼���ʹ����뷢�Ͷ��Ž���
			mailButton.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					Uri smsToUri = Uri.parse("smsto://"+mNumber.getText());
					Intent smsIntent = new Intent(Intent.ACTION_SENDTO, smsToUri);
					mContext.startActivity(smsIntent);
				}
			});
		}
	}
	
	public void setText(TextView v, String text){
		v.setText(text);
	}
}
