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
public class WellcomeSurfaceView extends SurfaceView 
implements SurfaceHolder.Callback
{
	GameActivity activity;
	Paint paint;
	int currentAlpha=0;
	
	int screenWidth=Constant.SCREEN_WIDTH;
	int screenHeight=Constant.SCREEN_HEIGHT;
	int sleepSpan=50;
	
	Bitmap[] logos=new Bitmap[2];
	Bitmap currentLogo;
	int currentX;
	int currentY;
	
	public WellcomeSurfaceView(GameActivity activity) {
		super(activity);
		this.activity = activity;
		this.getHolder().addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);

		logos[0]=BitmapFactory.decodeResource(activity.getResources(), R.drawable.dukea); 
		logos[1]=BitmapFactory.decodeResource(activity.getResources(), R.drawable.dukeb);		
		for(int i=0;i<logos.length;i++){
			logos[i]=PicLoadUtil.scaleToFit(logos[i], Constant.ssr.ratio);
		}
	}
	public void onDraw(Canvas canvas){	

		paint.setColor(Color.BLACK);
		paint.setAlpha(255);
		canvas.drawRect(0, 0, screenWidth, screenHeight, paint);
		

		if(currentLogo==null)return;
		paint.setAlpha(currentAlpha);		
		canvas.drawBitmap(currentLogo, currentX, currentY, paint);	
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		
	}
	public void surfaceCreated(SurfaceHolder holder) {
		new Thread()
		{
			public void run()
			{
				for(Bitmap bm:logos)
				{
					currentLogo=bm;

					currentX=screenWidth/2-bm.getWidth()/2;
					currentY=screenHeight/2-bm.getHeight()/2;
					
					for(int i=255;i>-10;i=i-10)
					{
						currentAlpha=i;
						if(currentAlpha<0)
						{
							currentAlpha=0;
						}
						SurfaceHolder myholder=WellcomeSurfaceView.this.getHolder();
						Canvas canvas = myholder.lockCanvas();
						try{
							synchronized(myholder){
								onDraw(canvas);
							}
						}
						catch(Exception e){
							e.printStackTrace();
						}
						finally{
							if(canvas != null){
								myholder.unlockCanvasAndPost(canvas);
							}
						}						
						try
						{
							if(i==255)
							{
								Thread.sleep(1000);
							}
							Thread.sleep(sleepSpan);
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
					}
				}

				activity.sendMessage(WhatMessage.GOTO_MAIN_MENU_VIEW);
//				activity.sendMessage(WhatMessage.GOTO_GAME_VIEW);
			}
		}.start();
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {

	}
}