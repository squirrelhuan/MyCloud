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
        innerIntent.putExtra("crop", "true");// 才能出剪辑的小方框，不然没有剪辑功能，只能选取图片  
        innerIntent.putExtra("aspectX", 1); // 出现放大和缩小  
        innerIntent.setType("image/*"); // 查看类型 详细的类型在 com.google.android.mms.ContentType   
          
        tempFile=new File("/sdcard/ll1x/"+Calendar.getInstance().getTimeInMillis()+".jpg"); // 以时间秒为文件名  
        File temp = new File("/sdcard/ll1x/");//自已项目 文件夹  
        if (!temp.exists()) {  
            temp.mkdir();  
        }  
        innerIntent.putExtra("output", Uri.fromFile(tempFile));  // 专入目标文件     
        innerIntent.putExtra("outputFormat", "JPEG"); //输入文件格式    
          
        Intent wrapperIntent = Intent.createChooser(innerIntent, "先择图片"); //开始 并设置标题  
        startActivityForResult(wrapperIntent, 1); // 设返回 码为 1  onActivityResult 中的 requestCode 对应  
    }  
      
    //调用成功反回方法  
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
