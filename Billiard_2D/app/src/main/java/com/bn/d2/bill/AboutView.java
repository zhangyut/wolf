package com.bn.d2.bill;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
/**
 * 
 *
 *
 */
public class AboutView extends SurfaceView implements SurfaceHolder.Callback{
	GameActivity activity;
	Paint paint;

	Bitmap bgBitmap;
	Bitmap bmp;
	float bmpx;
	public AboutView(GameActivity activity) {
		super(activity);
		this.activity=activity;

		this.requestFocus();
        this.setFocusableInTouchMode(true);
		getHolder().addCallback(this);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);  	

		canvas.drawBitmap(bgBitmap, 0, 0, paint);
		canvas.drawBitmap(bmp, bmpx+Constant.X_OFFSET, Constant.BMP_Y+Constant.Y_OFFSET, paint);
	}

    public void repaint()
	{
		Canvas canvas=this.getHolder().lockCanvas();
		try
		{
			synchronized(canvas)
			{
				onDraw(canvas);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(canvas!=null)
			{
				this.getHolder().unlockCanvasAndPost(canvas);
			}
		}
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

		bmpx=(Constant.SCREEN_WIDTH-bmp.getWidth())/2;
		
		repaint();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	}

	public void initBitmap(){
		bgBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.help);
		bmp=BitmapFactory.decodeResource(this.getResources(), R.drawable.bmp0);	
		bgBitmap=PicLoadUtil.scaleToFitFullScreen(bgBitmap, Constant.wRatio, Constant.hRatio);
		bmp=PicLoadUtil.scaleToFit(bmp, Constant.ssr.ratio);
		
	}	
}
