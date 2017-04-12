package com.example.fileexample;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.mycloud.R;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyFileManager extends ListActivity {
	private String TAG = "MyFileManager";
	private List<String> items = null;

	private List<String> paths = null;
	public static final int FILE_RESULT_BACKCODE = 1;
	public static boolean IS_DIRECTORY = false;
	public static boolean IS_FILE = false;
	// 鍔ㄦ�佸垵濮嬪寲...
	public static String FILE_TYPE[] = new String[200];
	public static final String FILESTYPE_CONTENTS[] = { "txt", "doc", "wps",
			"html", "xml", "pdf", "zip", "rar", "bmp", "jpg", "png", "jpeg",
			"wav", "mp3", "mp4", "avi", "mov", "swf", "exe", "c", "asm", "lib",
			"bak", "dot", "bat" };

	private String rootPath = getSDDir();

	private String curPath = getSDDir();

	private TextView mPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout2);

		mPath = (TextView) findViewById(R.id.mPath);

		getFileDir(rootPath);

	}

	private void getFileDir(String filePath) {

		mPath.setText(filePath);

		items = new ArrayList<String>();

		paths = new ArrayList<String>();

		File f = new File(filePath);

		File[] files = f.listFiles();

		if (!filePath.equals(rootPath)) {

			items.add("b1");

			paths.add(rootPath);

			items.add("b2");

			paths.add(f.getParent());

		}

		for (int i = 0, j = 0; i < files.length; i++) {

			File file = files[i];

			String type = checkShapeFile(file).toString();
			Log.d(TAG, "now type.." + i + ".." + type);
			if (type != "isDirectory") {
				FILE_TYPE[j] = type;
				Log.d(TAG, "FILE_TYPE[ " + j + " ]" + FILE_TYPE[j]);
				j++;
			}

			if (IS_DIRECTORY || IS_FILE) {

				items.add(file.getName());

				paths.add(file.getPath());

			}

		}
		setListAdapter(new MyAdapter(this, items, paths));

	}

	// open allocate file
	// 鑾峰緱閫夋嫨鏂囦欢鐨勭洰褰曪紝骞剁敤bundle鎵撳寘锛屼紶閫�

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		File file = new File(paths.get(position));

		if (file.isDirectory()) {

			curPath = paths.get(position);
			Log.d(TAG, "curpath...." + curPath);

			getFileDir(paths.get(position));

		} else {

			Intent data = new Intent(MyFileManager.this,

			MainActivity.class);

			Bundle bundle = new Bundle();

			bundle.putString("file", file.getPath());

			data.putExtras(bundle);

			setResult(2, data);

			finish();

		}

	}

	public String checkShapeFile(File file) {

		String fileNameString = file.getName();

		String endNameString = fileNameString.substring(

		fileNameString.lastIndexOf(".") + 1, fileNameString.length())
				.toLowerCase();
		if (file.isDirectory()) {
		} else if (file.isFile()) {
			Log.d(TAG, "endnamestring....." + endNameString);

		}

		// file is directory or not

		if (file.isDirectory()) {

			IS_DIRECTORY = true;
			endNameString = "isDirectory";
			Log.d(TAG, " now endnamestring....." + endNameString);
			return endNameString;

		}
		if (file.isFile()) {
			for (int i = 0; i < FILESTYPE_CONTENTS.length; i++) {
				if (endNameString.equals(FILESTYPE_CONTENTS[i])) {
					IS_FILE = true;
					break;
				} else {
					IS_FILE = false;
				}
				Log.d(TAG, " file endnamestring....." + endNameString);
			}
		} else {
			IS_FILE = false;
			Log.d(TAG, "nxjdvndjvnkdf");
			return "";
		}
		return endNameString;
	}

	protected final String getSDDir() {

		if (!checkSDcard()) {

			Toast.makeText(this, "no sdcard", Toast.LENGTH_SHORT).show();

			return "";

		}

		try {

			String SD_DIR = Environment.getExternalStorageDirectory()
					.getAbsolutePath()

					.toString();
			// String sdpath=Context.getExternalCacheDir().toString();
			Log.d(TAG, "Environment.getExternalStorageDirectory()" + SD_DIR);

			return SD_DIR;

		} catch (Exception e) {

			return "";

		}

	}

	public boolean checkSDcard() {

		String sdStutusString = Environment.getExternalStorageState();

		if (sdStutusString.equals(Environment.MEDIA_MOUNTED)) {

			return true;

		} else {

			return false;

		}

	}

}

