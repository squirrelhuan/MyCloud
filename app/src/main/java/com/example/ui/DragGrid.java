package com.example.ui;

import java.util.Currency;

import com.example.laucher.LauncherItem;
import com.example.mycloud.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

public class DragGrid extends GridView {

	private int dragPosition;
	private int dropPosition;
	private int initScreen;

	// private ImageView dragImageView;

	ViewGroup fromView;
	Animation AtoB, BtoA, DelDone;
	int stopCount = 0;
	private G_PageListener pageListener;
	public static int moveNum;
	private G_ItemChangeListener itemListener;

	private int mLastX, xtox;
	boolean isCountXY = false;
	private int mLastY, ytoy;

	private WindowManager windowManager;
	private WindowManager.LayoutParams windowParams;

	// private int itemHeight, itemWidth;
	private ImageView iv_drag;

	public DragGrid(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DragGrid(Context context) {
		super(context);
	}

	boolean flag = false;

	public void setLongFlag(boolean temp) {
		flag = temp;
	}

	public boolean setOnItemLongClickListener(final MotionEvent ev) {
		this.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (!Configure.isDelVisible) {
					Configure.isDelVisible = true;
					Configure.scrollLayout.changeGridDelVisible(true);
					return true;
				}
				if (Configure.currentPage == 0 && arg2 < 2)
					return false;

				Configure.isMove = true;
				int x = (int) ev.getX();
				int y = (int) ev.getY();
				mLastX = x;
				mLastY = y;
				dragPosition = dropPosition = arg2;

				if (dragPosition == AdapterView.INVALID_POSITION) {
					return false;
				}
				fromView = (ViewGroup) getChildAt(dragPosition
						- getFirstVisiblePosition());
				fromView.destroyDrawingCache();
				fromView.setDrawingCacheEnabled(true);
				fromView.setDrawingCacheBackgroundColor(0xff6DB7ED);
				Bitmap bm = Bitmap.createBitmap(fromView.getDrawingCache());

				Bitmap bitmap = Bitmap.createBitmap(bm, 8, 8,
						bm.getWidth() - 16, bm.getHeight() - 8);
				startDrag(bitmap, x, y);
				return false;
			};
		});
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			return setOnItemLongClickListener(ev);
		}
		return super.onInterceptTouchEvent(ev);
	}

	private void startDrag(final Bitmap bm, final int x, final int y) {

		windowManager = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);// "window"
		Animation disappear = AnimationUtils.loadAnimation(getContext(),
				R.anim.out);
		disappear.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub

				fromView.setVisibility(8);
				stopDrag();
				windowParams = new WindowManager.LayoutParams();
				windowParams.gravity = Gravity.TOP | Gravity.LEFT;
				windowParams.x = fromView.getLeft() + 28;
				if (Configure.screenDensity == 1.5) {
					windowParams.y = fromView.getTop() + fromView.getHeight()
							/ 2;
				} else if (Configure.screenDensity == 2.0) {
					windowParams.y = (int) (fromView.getTop()
							+ fromView.getHeight() / 2 + (int) 40
							* Configure.screenDensity);
				} else {
					windowParams.y = (y - mLastY - ytoy) + fromView.getTop()
							+ (int) (40 * Configure.screenDensity) + 8;
				}

				windowParams.alpha = 0.8f;
				windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
				windowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;

				iv_drag = new ImageView(getContext());
				iv_drag.setImageBitmap(bm);
				windowManager.addView(iv_drag, windowParams);
				iv_drag.startAnimation(AnimationUtils.loadAnimation(
						getContext(), R.anim.del_done));

			}
		});
		fromView.startAnimation(disappear);
		pageListener.page(1, -100);
		// 璁板綍褰撳墠item鎵�鍦ㄧ殑page
		initScreen = Configure.currentPage;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		try {
			if (iv_drag != null && dragPosition != AdapterView.INVALID_POSITION) {
				int x = (int) ev.getX();
				int y = (int) ev.getY();
				switch (ev.getAction()) {
				case MotionEvent.ACTION_MOVE:
					if (!isCountXY) {
						xtox = x - mLastX;
						ytoy = y - mLastY;
						isCountXY = true;
					}
					onDrag(x, y);
					break;
				case MotionEvent.ACTION_UP:
					stopDrag();
					onDrop(x, y);
					break;
				}

			}
		} catch (Exception e) {

		}
		return super.onTouchEvent(ev);
	}

	private void onDrag(int x, int y) {
		if (iv_drag != null) {
			windowParams.alpha = 0.8f;
			// Log.i("kkk", (int) 40 * Configure.screenDensity + "");
			windowParams.x = (x - mLastX - xtox) + fromView.getLeft() + 28
					- moveNum * Configure.screenWidth;
			windowParams.y = (y - mLastY - ytoy) + fromView.getTop()
					+ fromView.getHeight() / 2;
			if (Configure.screenDensity == 1.5) {
				windowParams.y = (y - mLastY - ytoy) + fromView.getTop()
						+ fromView.getHeight() / 2;
			} else if (Configure.screenDensity == 2.0) {
				windowParams.y = (int) ((y - mLastY - ytoy) + fromView.getTop()
						+ fromView.getHeight() / 2 + (int) 40
						* Configure.screenDensity);
			} else {
				windowParams.y = (y - mLastY - ytoy) + fromView.getTop()
						+ (int) (40 * Configure.screenDensity) + 8;
			}
			windowManager.updateViewLayout(iv_drag, windowParams);
		}
		if (moveNum > 0) {
			if ((x >= (moveNum + 1) * Configure.screenWidth
					- iv_drag.getWidth() / 2 * Configure.screenDensity || x <= moveNum
					* Configure.screenWidth)
					&& !Configure.isChangingPage)
				stopCount++;
			else
				stopCount = 0;
			if (stopCount > 5) {
				stopCount = 0;
				if (x >= (moveNum + 1) * Configure.screenWidth
						- iv_drag.getWidth() / 2 * Configure.screenDensity
						&& Configure.currentPage < Configure.countPages - 1) {
					Configure.isChangingPage = true;
					pageListener.page(0, ++Configure.currentPage);
					moveNum++;
				} else if (x <= moveNum * Configure.screenWidth
						+ iv_drag.getWidth() / 2
						&& Configure.currentPage > 0) {
					Configure.isChangingPage = true;
					pageListener.page(0, --Configure.currentPage);
					moveNum--;
				}
			}
		} else {
			if ((x >= (moveNum + 1) * Configure.screenWidth
					- iv_drag.getWidth() / 2 * Configure.screenDensity || x <= moveNum
					* Configure.screenWidth)
					&& !Configure.isChangingPage)
				stopCount++;
			else
				stopCount = 0;
			if (stopCount > 5) {
				stopCount = 0;
				if (x >= (moveNum + 1) * Configure.screenWidth
						- iv_drag.getWidth() / 2 * Configure.screenDensity
						&& Configure.currentPage < Configure.countPages - 1) {
					Configure.isChangingPage = true;
					pageListener.page(0, ++Configure.currentPage);
					moveNum++;
				} else if (x <= moveNum * Configure.screenWidth
						+ iv_drag.getWidth() / 2
						&& Configure.currentPage > 0) {
					Configure.isChangingPage = true;
					pageListener.page(0, --Configure.currentPage);
					moveNum--;
				}
			}
		}
	}

	public void setPageListener(G_PageListener pageListener) {
		this.pageListener = pageListener;
	}

	public interface G_PageListener {
		void page(int cases, int page);
	}

	public void setOnItemChangeListener(G_ItemChangeListener pageListener) {
		this.itemListener = pageListener;
	}

	public interface G_ItemChangeListener {
		void change(int from, int to, int count);
	}

	private void onDrop(int x, int y) {
		fromView.setDrawingCacheBackgroundColor(0);
		Configure.isMove = false;
		pageListener.page(4, -300);
		int tempPosition = 0;
		Log.i("count", getCount() + "");
		//姝ゅ闇�瑕佽缃负鍙锛宲ointToPosition鏂规硶鏄厛鍒ゆ柇鍙鎵嶄細鍘诲垽鏂叾鏄惁鍦ㄦ寚瀹氬尯鍩�
		fromView.setVisibility(View.VISIBLE);
		tempPosition = pointToPosition(x - moveNum * Configure.screenWidth, y);
		System.out.println("tempPosition" + tempPosition);
		System.out.println("Configure.currentPage" + Configure.currentPage);
		System.out.println("moveNum" + moveNum);
		final DateAdapter adapter = (DateAdapter) this.getAdapter();
		if (tempPosition != AdapterView.INVALID_POSITION) {
			if (Configure.currentPage == 0 && tempPosition < 2) {
				adapter.notifyDataSetChanged();
				return;
			}
			dropPosition = tempPosition;
		} else {

			Log.i("dragposition,dropPosition", dragPosition + ","
					+ dropPosition + "," + initScreen + ","
					+ Configure.currentPage);
			if (dragPosition == dropPosition
					&& initScreen != Configure.countPages && tempPosition != -1) {
				adapter.exchange(dragPosition, dropPosition);
				adapter.notifyDataSetChanged();
				itemListener.change(dragPosition, dropPosition, moveNum);
				moveNum = 0;
				return;
			} else {
				itemListener.change(dragPosition, -1, moveNum);
				moveNum = 0;
				return;
			}
		}
		System.out.println("dragPosition" + dragPosition);
		System.out.println("dropPosition" + dropPosition);
		if (moveNum != 0) {
			itemListener.change(dragPosition, dropPosition, moveNum);
			moveNum = 0;
			return;
		}
		moveNum = 0;
		if (((LauncherItem) adapter.getItem(dropPosition)).isNone()) {
			adapter.notifyDataSetChanged();
			return;
		}
		ViewGroup toView = (ViewGroup) getChildAt(dropPosition
				- getFirstVisiblePosition());
		if (dragPosition % 2 == 0) {
			AtoB = getDownAnimation((dropPosition % 2 == dragPosition % 2) ? 0
					: 1, (dropPosition / 2 - dragPosition / 2));
			if (dropPosition != dragPosition)
				toView.startAnimation(getMyAnimation(
						(dragPosition % 2 == dropPosition % 2) ? 0 : -1,
						(dragPosition / 2 - dropPosition / 2)));
		} else {
			AtoB = getDownAnimation((dropPosition % 2 == dragPosition % 2) ? 0
					: -1, (dropPosition / 2 - dragPosition / 2));
			if (dropPosition != dragPosition)
				toView.startAnimation(getMyAnimation(
						(dragPosition % 2 == dropPosition % 2) ? 0 : 1,
						(dragPosition / 2 - dropPosition / 2)));
		}
		fromView.startAnimation(AtoB);
		AtoB.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation arg0) {
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				adapter.exchange(dragPosition, dropPosition);
				adapter.notifyDataSetChanged();
			}
		});
	}

	private void stopDrag() {
		if (iv_drag != null) {
			windowManager.removeView(iv_drag);
			iv_drag = null;
		}
	}

	public Animation getMyAnimation(float x, float y) {
		TranslateAnimation go = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, x,
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, y);
		go.setFillAfter(true);
		go.setDuration(400);
		return go;
	}

	public Animation getDownAnimation(float x, float y) {
		AnimationSet set = new AnimationSet(true);
		TranslateAnimation go = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, x, Animation.RELATIVE_TO_SELF, x,
				Animation.RELATIVE_TO_SELF, y, Animation.RELATIVE_TO_SELF, y);
		go.setFillAfter(true);
		go.setDuration(400);

		AlphaAnimation alpha = new AlphaAnimation(0.1f, 1.0f);
		alpha.setFillAfter(true);
		alpha.setDuration(400);

		ScaleAnimation scale = new ScaleAnimation(1.2f, 1.0f, 1.2f, 1.0f);
		scale.setFillAfter(true);
		scale.setDuration(400);

		set.addAnimation(go);
		set.addAnimation(alpha);
		set.addAnimation(scale);
		return set;
	}

	public Animation getDelAnimation(int x, int y) {
		AnimationSet set = new AnimationSet(true);
		// TranslateAnimation go = new TranslateAnimation(Animation.ABSOLUTE,
		// x-itemWidth/2, Animation.ABSOLUTE, x-itemWidth/2,
		// Animation.ABSOLUTE, y-itemHeight/2, Animation.ABSOLUTE,
		// y-itemHeight/2);
		// go.setFillAfter(true);go.setDuration(1400);
		RotateAnimation rotate = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		rotate.setFillAfter(true);
		rotate.setDuration(400);
		AlphaAnimation alpha = new AlphaAnimation(1.0f, 0.0f);
		alpha.setFillAfter(true);
		alpha.setDuration(400);

		// ScaleAnimation scale = new
		// ScaleAnimation(1.0f,0.0f,1.0f,0.0f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		// scale.setFillAfter(true);scale.setDuration(550);

		// set.addAnimation(rotate);
		set.addAnimation(alpha);
		set.addAnimation(rotate);
		return set;
	}

	public void deleteView(final int position) {
		View delView = (ViewGroup) getChildAt(position
				- getFirstVisiblePosition());
		AnimationSet set = new AnimationSet(true);
		RotateAnimation rotate = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		rotate.setFillAfter(true);
		rotate.setDuration(550);
		AlphaAnimation alpha = new AlphaAnimation(1.0f, 0.0f);
		alpha.setFillAfter(true);
		alpha.setDuration(550);
		set.addAnimation(alpha);
		set.addAnimation(rotate);
		set.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				/*
				 * 锟斤拷锟斤拷锟狡筹拷去执锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟揭伙拷锟街皇�1锟斤拷item时锟斤拷
				 * 删锟斤拷前锟斤拷页锟斤拷item锟斤拷锟斤拷锟阶筹拷NullPointerException
				 */
				new Handler().post(new Runnable() {
					public void run() {
						Configure.removeItem = position;
						pageListener.page(5, -300);
					}
				});
			}
		});
		delView.setVisibility(0);
		delView.startAnimation(set);
	}

	/**
	 * 浜ゆ崲item浣嶇疆
	 */
	private void changeItem(final View dragView, final int dropPosition,
			final View changeView, final DateAdapter adapter) {
		dragView.setVisibility(View.VISIBLE);
		fromView.setVisibility(View.VISIBLE);
		// final View changeView = getChildAt(dragPosition);
		// 鐩爣item绉诲姩鍒版粦鍔╥tem
		TranslateAnimation changePositionAnim = new TranslateAnimation(0,
				dragView.getLeft() - changeView.getLeft(), 0, dragView.getTop()
						- changeView.getTop());
		changePositionAnim.setFillAfter(true);
		changePositionAnim.setDuration(340);
		changeView.startAnimation(changePositionAnim);
		final TranslateAnimation changePositionAnim1 = new TranslateAnimation(
				0, changeView.getLeft() - dragView.getLeft(), 0,
				changeView.getTop() - dragView.getTop());
		// 婊戝姩item绉诲姩鍒扮洰鏍噄tem

		changePositionAnim.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation animation) {

				changePositionAnim1.setFillAfter(true);
				changePositionAnim1.setDuration(300);
				dragView.startAnimation(changePositionAnim1);
			}

			public void onAnimationRepeat(Animation animation) {
			}

			public void onAnimationEnd(Animation animation) {
				changeView.clearAnimation();

			}
		});
		changePositionAnim1.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation animation) {
			}

			public void onAnimationRepeat(Animation animation) {
			}

			public void onAnimationEnd(Animation animation) {
				fromView.clearAnimation();
				adapter.exchange(dragPosition, dropPosition);
				adapter.notifyDataSetChanged();

			}
		});
	}
}
