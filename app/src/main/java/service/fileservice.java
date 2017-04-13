package service;

import android.content.Context;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class fileservice {
	private Context context;
	
public fileservice(Context context) {
		super();
		this.context = context;
	}

	public void save(String filename,String filecontent) throws Exception{
	  FileOutputStream outStream=context.openFileOutput(filename, context.MODE_PRIVATE);
		outStream.write(filecontent.getBytes());
		outStream.close();
	}
	public void savetoSDcard(String filename,String filecontent)throws Exception{
		File file =new File(Environment.getExternalStorageDirectory(), filename);
		FileOutputStream outStream=new FileOutputStream(file);
		outStream.write(filecontent.getBytes());
		outStream.close();
	}
	public String readfile(String filename)throws Exception{
		FileInputStream inStream=context.openFileInput(filename);
		ByteArrayOutputStream outStream=new ByteArrayOutputStream();
		byte[] buffer =new byte[1024];
		int len =0;
		while((len=inStream.read(buffer))!=-1){
			outStream.write(buffer, 0, len);
		}
		byte[]data =outStream.toByteArray();
		return new String(data);
	}
}
