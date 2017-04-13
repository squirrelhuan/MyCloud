package userscenter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.mycloud.R;

import java.io.File;
import java.util.Calendar;

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
        innerIntent.putExtra("crop", "true");
        innerIntent.putExtra("aspectX", 1);
        innerIntent.setType("image/*"); //com.google.android.mms.ContentType
          
        tempFile=new File("/sdcard/ll1x/"+Calendar.getInstance().getTimeInMillis()+".jpg");
        File temp = new File("/sdcard/ll1x/");
        if (!temp.exists()) {  
            temp.mkdir();  
        }  
        innerIntent.putExtra("output", Uri.fromFile(tempFile));
        innerIntent.putExtra("outputFormat", "JPEG");
          
        Intent wrapperIntent = Intent.createChooser(innerIntent, "����ͼƬ");
        startActivityForResult(wrapperIntent, 1);
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
