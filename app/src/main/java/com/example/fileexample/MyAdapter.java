package com.example.fileexample;

import java.io.File;
import java.util.List;

import com.example.mycloud.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author yuanhaiq
 * @version 1.0
 * @since 2014-11-29
 * @category
  
  
 * */
public class MyAdapter extends BaseAdapter {
	private String TAG = "MyAdapter";

	private LayoutInflater mInflater;

	private Bitmap mIcon1;

	private Bitmap mIcon2;

	private Bitmap mIcon3;

	private Bitmap mIcon4;
	private Bitmap mIcon5;
	private Bitmap mIcon6;
	private Bitmap mIcon7;
	private Bitmap mIcon8;
	private Bitmap mIcon9;
	private Bitmap mIcon10;
	private Bitmap mIcon11;
	private Bitmap mIcon12;
	private Bitmap mIcon13;
	private Bitmap mIcon14;

	private List<String> items;

	private List<String> paths;

	public MyAdapter(Context context, List<String> it, List<String> pa)

	{

		mInflater = LayoutInflater.from(context);

		items = it;

		paths = pa;

		mIcon1 = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.file_icon_folder_fav);

		mIcon2 = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.file_icon_folder);

		mIcon3 = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.file_icon_folder);

		mIcon4 = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.file_icon_txt);
		mIcon5 = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.file_icon_doc);
		mIcon6 = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.file_icon_xls);
		mIcon7 = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.iconfont_c);
		mIcon8 = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.file_icon_pdf);
		mIcon9 = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.file_icon_zip);
		mIcon10 = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.file_icon_picture);
		mIcon11 = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.file_icon_mp3);
		mIcon12 = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.file_icon_video);
		mIcon13 = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.file_icon_apk);
		mIcon14 = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.file_icon_default);

	}

	public int getCount()

	{

		return items.size();

	}

	public Object getItem(int position)

	{

		return items.get(position);

	}

	public long getItemId(int position)

	{

		return position;

	}

	public View getView(int position, View convertView, ViewGroup parent)

	{

		ViewHolder holder;

		if (convertView == null)

		{

			convertView = mInflater.inflate(R.layout.file_row, null);

			holder = new ViewHolder();

			holder.text = (TextView) convertView.findViewById(R.id.text);

			holder.icon = (ImageView) convertView.findViewById(R.id.icon);

			convertView.setTag(holder);
		} else

		{
			holder = (ViewHolder) convertView.getTag();
		}

		File f = new File(paths.get(position).toString());
		MyFileManager file = new MyFileManager();

		if (items.get(position).toString().equals("b1"))

		{

			holder.text.setText("杩斿洖鏍圭洰褰�..");

			holder.icon.setImageBitmap(mIcon1);

		}

		else if (items.get(position).toString().equals("b2"))

		{

			holder.text.setText("杩斿洖涓婁竴灞�..");

			holder.icon.setImageBitmap(mIcon2);

		}

		else

		{

			holder.text.setText(f.getName());
			String type = checkShapeFile(f);
			if (f.isDirectory()) {

				holder.icon.setImageBitmap(mIcon3);

			}

			else if (checkFileIsMyNeedType(type)) {

				if (type.equals("txt")) {
					Log.d(TAG, "now type..txt." + type);
					holder.icon.setImageBitmap(mIcon4);
				}
				if (type.equals("doc") || type.equals("wps")) {
					Log.d(TAG, "now type..doc." + type);
					holder.icon.setImageBitmap(mIcon5);
				}
				if (type.equals("html") || (type.equals("xml"))) {
					Log.d(TAG, "now type..html." + type);
					holder.icon.setImageBitmap(mIcon6);
				}
				if (type.equals("c")) {
					Log.d(TAG, "now type..c." + type);
					holder.icon.setImageBitmap(mIcon7);
				}
				if (type.equals("pdf")) {
					Log.d(TAG, "now type..pdf." + type);
					holder.icon.setImageBitmap(mIcon8);
				}
				if (type.equals("zip") || type.equals("rar")) {
					Log.d(TAG, "now type..zip." + type);
					holder.icon.setImageBitmap(mIcon9);
				}
				if (type.equals("jpg") || (type.equals("png"))
						|| (type.equals("jpeg")) || (type.equals("bmp"))) {
					Log.d(TAG, "now type..jpg." + type);
					holder.icon.setImageBitmap(mIcon10);
				}
				if (type.equals("mp3")) {
					Log.d(TAG, "now type..mp3." + type);
					holder.icon.setImageBitmap(mIcon11);
				}
				if (type.equals("mp4") || type.equals("wav")
						|| type.equals("swf")) {
					Log.d(TAG, "now type..mp4." + type);
					holder.icon.setImageBitmap(mIcon12);
				}
				if (type.equals("exe") || type.equals("apk")) {
					Log.d(TAG, "now type..exe." + type);
					holder.icon.setImageBitmap(mIcon13);
				}
			} else {

				Log.d(TAG, "now type..pdf.last");
				holder.icon.setImageBitmap(mIcon14);
			}
		}

		return convertView;

	}

	private class ViewHolder

	{
		TextView text;
		ImageView icon;

	}

	public String checkShapeFile(File file) {

		String fileNameString = file.getName();

		String endNameString = fileNameString.substring(

		fileNameString.lastIndexOf(".") + 1, fileNameString.length())
				.toLowerCase();

		return endNameString;

	}

	public boolean checkFileIsMyNeedType(String strtype) {
		boolean flags = false;
		for (int i = 0; i < MyFileManager.FILESTYPE_CONTENTS.length; i++) {
			if (strtype.equals(MyFileManager.FILESTYPE_CONTENTS[i])) {
				flags = true;
				break;
			} else {
				flags = false;
			}
		}
		return flags;

	}

}
