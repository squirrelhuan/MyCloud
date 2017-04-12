package sql;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

public class MyGraphics extends View implements Runnable{	//�Զ���View
	private Paint paint=null;
	public MyGraphics(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		paint=new Paint();	//��������
		new Thread(this).start();	//�����߳�
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		paint.setAntiAlias(true);	//���û���Ϊ�޾��
		paint.setColor(Color.BLACK);	//���û�����ɫ
		paint.setTextSize((float) 30.0);
		canvas.drawColor(Color.WHITE);				//��ɫ����
		
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
		RectF oval=new RectF();						//RectF����
		oval.left=150;								//���
		oval.top=500;								//�ϱ�
		oval.right=350;								//�ұ�
		oval.bottom=600;							//�±�
		canvas.drawOval(oval, paint);					//������Բ

		
//		String str="AndroidӦ�ó��򿪷�";
//		char[] ch={'H','e','l','l','o',' ','A','n','d','r','o','i','d'};
//		
//		canvas.drawText(str, 50, 200, paint);
//		canvas.drawText(ch, 0, ch.length, 50, 300, paint);
//		canvas.drawText(str+" API���", 0, str.length()+6, 50, 400, paint);
//		canvas.drawText(str, 7, str.length(), 50, 500, paint);
	}

	@Override
	public void run() {									//����run����
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
			postInvalidate();							//���½���
		}
	}

}
