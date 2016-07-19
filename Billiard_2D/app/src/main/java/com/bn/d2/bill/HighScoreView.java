package com.bn.d2.bill;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
/**
 * 
 *
 *
 */
public class HighScoreView extends SurfaceView implements SurfaceHolder.Callback{
	GameActivity activity;
	Paint paint;
	DrawThread drawThread;
	Bitmap bgBitmap;
	Bitmap bmp;
	Bitmap defenBitmap;
	Bitmap riqiBitmap;
	Bitmap[] numberBitmaps;
	Bitmap gangBitmap;
	int bmpx;
	String queryResultStr;
	String[] splitResultStrs;
	private int numberWidth;
	private int posFrom=-1;
	private int length=5;
	int downY=0;
	int upY=0;
	public HighScoreView(GameActivity activity) {
		super(activity);
		this.activity=activity;

		this.requestFocus();
        this.setFocusableInTouchMode(true);
		getHolder().addCallback(this);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.drawColor(Color.GRAY);
		canvas.drawBitmap(bgBitmap, 0, 0, paint);

		canvas.drawBitmap(bmp, bmpx+Constant.X_OFFSET, Constant.BMP_Y+Constant.Y_OFFSET, paint);
		canvas.drawBitmap(defenBitmap, Constant.DE_FEN_X+Constant.X_OFFSET, Constant.DE_FEN_Y+Constant.Y_OFFSET, paint);
		canvas.drawBitmap(riqiBitmap, Constant.RI_QI_X+Constant.X_OFFSET, Constant.DE_FEN_Y+Constant.Y_OFFSET, paint);

		float x;
		float y;
		for(int i=0;i<splitResultStrs.length;i++)
		{
			if(i%2==0)
			{
				x=Constant.SCREEN_WIDTH*3/4;				
			}
			else
			{
				x=Constant.SCREEN_WIDTH/2;
			}
			y=Constant.BMP_Y+defenBitmap.getHeight()+(numberBitmaps[0].getHeight()+10)*(i/2+1);

			drawDateBitmap(splitResultStrs[i],x+Constant.X_OFFSET,y+Constant.Y_OFFSET,canvas,paint);
		}
	}

	public void drawDateBitmap(String numberStr,float endX,float endY,Canvas canvas,Paint paint)
	{
		for(int i=0;i<numberStr.length();i++)
		{
			char c=numberStr.charAt(i);
			if(c=='-')
			{
				canvas.drawBitmap(gangBitmap,endX-numberWidth*(numberStr.length()-i), endY, paint);
			}
			else
			{
				canvas.drawBitmap
				(
						numberBitmaps[c-'0'], 
						endX-numberWidth*(numberStr.length()-i), 
						endY, 
						paint
				);
			}			
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int y = (int) event.getY();		
    	switch(event.getAction())
    	{
    	case MotionEvent.ACTION_DOWN:
    		downY=y;
    		break;
    	case MotionEvent.ACTION_UP:
    		upY=y;    		
        	if(Math.abs(downY-upY)<20)
        	{
        		return true;
        	}
        	else if(downY<upY)
        	{	

        		if(this.posFrom-this.length>=-1)
        		{
        			this.posFrom-=this.length;        			
        		}
        	}
        	else
        	{	

        		if(this.posFrom+this.length<activity.getRowCount()-1)
        		{
        			this.posFrom+=this.length;        			
        		}
        	}
        	queryResultStr=activity.query(posFrom,length);
			splitResultStrs=queryResultStr.split("/", 0);
    		break;    	
    	}
		return true;
	}	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder){
		paint=new Paint();
		paint.setAntiAlias(true);
		createAllThreads();
		initBitmap();
		numberWidth=numberBitmaps[0].getWidth()+3;

		bmpx=(Constant.SCREEN_WIDTH-bmp.getWidth())/2;
		posFrom=-1;
		queryResultStr=activity.query(posFrom,length);
		splitResultStrs=queryResultStr.split("/", 0);
		startAllThreads();
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		  boolean retry = true;
	        stopAllThreads();
	        while (retry) {
	            try {
	            	drawThread.join();
	                retry = false;
	            } 
	            catch (InterruptedException e) {e.printStackTrace();}
	        }
	}

	public void initBitmap(){
		bmp=BitmapFactory.decodeResource(this.getResources(), R.drawable.bmp);	
		bgBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.help);
		numberBitmaps=new Bitmap[]{
				BitmapFactory.decodeResource(this.getResources(), R.drawable.number0),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.number1),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.number2),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.number3),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.number4),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.number5),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.number6),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.number7),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.number8),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.number9),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.number0),
		};
		gangBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.gang);
		defenBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.defen);
		riqiBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.riqi);

		bmp=PicLoadUtil.scaleToFit(bmp, Constant.ssr.ratio);
		gangBitmap=PicLoadUtil.scaleToFit(gangBitmap, Constant.ssr.ratio);
		riqiBitmap=PicLoadUtil.scaleToFit(riqiBitmap, Constant.ssr.ratio);
		defenBitmap=PicLoadUtil.scaleToFit(defenBitmap, Constant.ssr.ratio);				
		for(int i=0;i<numberBitmaps.length;i++){
			numberBitmaps[i]=PicLoadUtil.scaleToFit(numberBitmaps[i], Constant.ssr.ratio);
		}
		bgBitmap=PicLoadUtil.scaleToFitFullScreen(bgBitmap, Constant.wRatio, Constant.hRatio);
	}
	void createAllThreads()
	{
		drawThread=new DrawThread(this);
	}
	void startAllThreads()
	{
		drawThread.setFlag(true);     
		drawThread.start();
	}
	void stopAllThreads()
	{
		drawThread.setFlag(false);       
	}
	private class DrawThread extends Thread{
		private boolean flag = true;	
		private int sleepSpan = 100;
		HighScoreView fatherView;
		SurfaceHolder surfaceHolder;
		public DrawThread(HighScoreView fatherView){
			this.fatherView = fatherView;
			this.surfaceHolder = fatherView.getHolder();
		}
		public void run(){
			Canvas c;
	        while (this.flag) {
	            c = null;
	            try {

	                c = this.surfaceHolder.lockCanvas(null);
	                synchronized (this.surfaceHolder) {
	                	fatherView.onDraw(c);
	                }
	            } finally {
	                if (c != null) {

	                    this.surfaceHolder.unlockCanvasAndPost(c);
	                }
	            }
	            try{
	            	Thread.sleep(sleepSpan);
	            }
	            catch(Exception e){
	            	e.printStackTrace();
	            }
	        }
		}
		public void setFlag(boolean flag) {
			this.flag = flag;
		}
	}
}
