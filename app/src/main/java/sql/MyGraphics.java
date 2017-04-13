package sql;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

public class MyGraphics extends View implements Runnable{
	private Paint paint=null;
	public MyGraphics(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		paint=new Paint();	//��������
		new Thread(this).start();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		paint.setAntiAlias(true);
		paint.setColor(Color.BLACK);
		paint.setTextSize((float) 30.0);
		canvas.drawColor(Color.WHITE);
		
		//canvas.clipRect(100, 100, 360, 400);
		canvas.clipRect(50, 50, 400, 700);
		canvas.save();
		canvas.rotate(45, 230, 250);
		paint.setColor(Color.BLUE);
		canvas.drawText("Hello Android!", 130, 250, paint);
		canvas.restore();
		
		//canvas.clipRect(0, 0, 480, 800);
		paint.setColor(Color.RED);
		canvas.drawText("Hello Android!", 130, 250, paint);
		//canvas.drawText("Hello Android!", 130, 600, paint);
		RectF oval=new RectF();
		oval.left=150;
		oval.top=500;
		oval.right=350;
		oval.bottom=600;
		canvas.drawOval(oval, paint);

		
//		String str="AndroidӦ�ó��򿪷�";
//		char[] ch={'H','e','l','l','o',' ','A','n','d','r','o','i','d'};
//		
//		canvas.drawText(str, 50, 200, paint);
//		canvas.drawText(ch, 0, ch.length, 50, 300, paint);
//		canvas.drawText(str+" API���", 0, str.length()+6, 50, 400, paint);
//		canvas.drawText(str, 7, str.length(), 50, 500, paint);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!Thread.currentThread().isInterrupted())
		{
			try
			{
				Thread.sleep(100);
			}
			catch(InterruptedException e)
			{
				Thread.currentThread().interrupt();
			}
			postInvalidate();
		}
	}

}
