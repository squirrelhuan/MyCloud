package userscenter;

import java.io.File;
import java.util.Calendar;

import com.example.mycloud.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class editpicture extends Activity{
	private ImageView imageView;  
    private File tempFile;  
    
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.editpicture);  
        imageView = (ImageView) findViewById(R.id.showSelectImageId);  
    }  
  
    public void onclickFun(View view) {  
        Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT);  
        innerIntent.putExtra("crop", "true");// ���ܳ�������С���򣬲�Ȼû�м������ܣ�ֻ��ѡȡͼƬ  
        innerIntent.putExtra("aspectX", 1); // ���ַŴ����С  
        innerIntent.setType("image/*"); // �鿴���� ��ϸ�������� com.google.android.mms.ContentType   
          
        tempFile=new File("/sdcard/ll1x/"+Calendar.getInstance().getTimeInMillis()+".jpg"); // ��ʱ����Ϊ�ļ���  
        File temp = new File("/sdcard/ll1x/");//������Ŀ �ļ���  
        if (!temp.exists()) {  
            temp.mkdir();  
        }  
        innerIntent.putExtra("output", Uri.fromFile(tempFile));  // ר��Ŀ���ļ�     
        innerIntent.putExtra("outputFormat", "JPEG"); //�����ļ���ʽ    
          
        Intent wrapperIntent = Intent.createChooser(innerIntent, "����ͼƬ"); //��ʼ �����ñ���  
        startActivityForResult(wrapperIntent, 1); // �践�� ��Ϊ 1  onActivityResult �е� requestCode ��Ӧ  
    }  
      
    //���óɹ����ط���  
    @Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        super.onActivityResult(requestCode, resultCode, data);  
        switch (requestCode) {  
        case 1:  
            imageView.setImageDrawable(Drawable.createFromPath(tempFile.getAbsolutePath()));  
            break;  
        }  
    }  
}
