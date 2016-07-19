package com.bn.d2.bill;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
/**
 * 
 *
 *
 */
public class WinView extends SurfaceView implements SurfaceHolder.Callback{
	GameActivity activity;
	Paint paint;

	Bitmap bgBitmap;
	Bitmap winBitmap;		

	int bmpx;
	int bmpy;
	
	public WinView(GameActivity activity) {
		super(activity);
		this.activity=activity;

		this.requestFocus();
        this.setFocusableInTouchMode(true);
		getHolder().addCallback(this);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);  	

		canvas.drawColor(Color.WHITE);
		canvas.drawBitmap(bgBitmap, 0, 0, paint);
		canvas.drawBitmap(winBitmap,bmpx, bmpy, paint);
	}	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder){
		paint=new Paint();
		paint.setAntiAlias(true);
		initBitmap();

		bmpx=(Constant.SCREEN_WIDTH-winBitmap.getWidth())/2;
		bmpy=(Constant.SCREEN_HEIGHT-winBitmap.getHeight())/2;
		new Thread()
		{
			int sleepSpan=100;
			int totalSleepTime=5000;
			public void run(){
				Canvas c;
		        for(int i=0;i<totalSleepTime/sleepSpan;i++) 
		        {
		        	c = null;
		        	SurfaceHolder myholder=WinView.this.getHolder();
		        	try {
		                c = myholder.lockCanvas(null);
		                synchronized (myholder) {
		                	WinView.this.onDraw(c);
		                }
		            } finally {
		                if (c != null) {

		                	myholder.unlockCanvasAndPost(c);
		                }
		            }
		            try{
		            	Thread.sleep(sleepSpan);
		            }
		            catch(Exception e){
		            	e.printStackTrace();
		            }
		        }

				activity.sendMessage(WhatMessage.GOTO_MAIN_MENU_VIEW);
			}
		}.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	}

	public void initBitmap(){
		winBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.win);
		bgBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.help);

		winBitmap=PicLoadUtil.scaleToFitFullScreen(winBitmap, Constant.wRatio, Constant.hRatio);
		bgBitmap=PicLoadUtil.scaleToFitFullScreen(bgBitmap, Constant.wRatio, Constant.hRatio);
	}	
}
