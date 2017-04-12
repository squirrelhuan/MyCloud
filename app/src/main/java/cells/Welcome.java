package cells;


import webview.Main_Browser;

import com.example.mycloud.AllAppList;
import com.example.mycloud.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.GestureDetector.OnGestureListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;



public class Welcome extends Activity implements OnGestureListener {
	private final int SPLASH_DISPLAY_LENGHT = 3000; // 延迟3秒  
	private GestureDetector detector;
	private ViewFlipper flipper;
	private Button button;
	ImageView []iamges=new ImageView[4];
	int i = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layoutwelcome);
		iamges[0]=(ImageView) findViewById(R.id.imageview1);
		iamges[1]=(ImageView) findViewById(R.id.imageview2);
		iamges[2]=(ImageView) findViewById(R.id.imageview3);
		iamges[3]=(ImageView) findViewById(R.id.imageview4);
		
		detector = new GestureDetector(this);
		
		flipper = (ViewFlipper) this.findViewById(R.id.ViewFlipper1);
		flipper.addView(addImageView(R.drawable.png1o));
		flipper.addView(addImageView(R.drawable.png2o));
		flipper.addView(addImageView(R.drawable.png3o));

		flipper.addView(addView());

	}

	private View addImageView(int id) {
		ImageView iv = new ImageView(this);
		iv.setImageResource(id);
		return iv;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return this.detector.onTouchEvent(event); 
	}

	private View addView() {
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.layoutwelcome2, null);
		button = (Button) view.findViewById(R.id.come_in);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Intent intent = new
				// Intent(getApplicationContext(),ActContent.class);
				// startActivity(intent);
				// finish();
				Intent mainIntent = new Intent(Welcome.this, AllAppList.class);  
                                
    			Bundle data1=new Bundle();
    			data1.putString("re_name", "desktop_setting");
    			mainIntent.putExtras(data1);
    			
    			Welcome.this.startActivity(mainIntent);  
                Welcome.this.finish(); 

				Toast.makeText(Welcome.this, "欢迎来到云世界！", 2000).show();
			}
		});
		return view;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		System.out.println("in------------>>>>>>>");
		if (e1.getX() - e2.getX() > 120) {
			if (i < 3) {
				i++;
				setImage(i);
				this.flipper.setInAnimation(AnimationUtils.loadAnimation(this,
						R.anim.animation_right_in));
				this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this,
						R.anim.animation_left_out));
				this.flipper.showNext();
			}
			return true;
		} 
		else if (e1.getX() - e2.getX() < -120) {
			if (i > 0) {
				i--;
				setImage(i);
				this.flipper.setInAnimation(AnimationUtils.loadAnimation(this,
						R.anim.animation_left_in));
				this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this,
						R.anim.animation_right_out));
				this.flipper.showPrevious();
			}
			return true;
		}
		return false;
		
	}
	
	void setImage(int i)
	{
		for(int j=0;j<4;j++)
		{
			if(j!=i)
			iamges[j].setImageResource(R.drawable.xiao);
			else
				iamges[j].setImageResource(R.drawable.da);
		}
	}
}