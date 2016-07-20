package com.bn.tag;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import static com.bn.tag.Constant.*;
/**
 * 
 *
 *
 */
public class MusicSurfaceView extends SurfaceView 
implements SurfaceHolder.Callback  //ʵ���������ڻص��ӿ�
{
	TafangGameActivity activity;
	Paint paint;//����
	Bitmap musicback;
	Bitmap backMusicoff;//�������ֹ�
	Bitmap backMusicon;//�������ֿ�
	Bitmap Yinxiaooff;//�ر���Ϸ��Ч
	Bitmap Yinxiaoon;//������Ϸ��Ч
	boolean backmusicFlag01=true;
	boolean backmusicFlag02=true;
	boolean yinxiaoFlag01=true;
	boolean yinxiaoFlag02=true;
	float pic_x=200;
	float pic_y=130;
	
	DrawThread drawThread;
	
	
	public MusicSurfaceView(TafangGameActivity activity) {
		super(activity);
		this.activity = activity;
		this.getHolder().addCallback(this);//�����������ڻص��ӿڵ�ʵ����
		paint = new Paint();//��������
		paint.setAntiAlias(true);//�򿪿����
		
		//����ͼƬ
		backMusicoff=BitmapFactory.decodeResource(activity.getResources(), R.drawable.backmusicoff);
		backMusicon=BitmapFactory.decodeResource(activity.getResources(), R.drawable.backmusicon);
		Yinxiaooff=BitmapFactory.decodeResource(activity.getResources(), R.drawable.yinxiaooff);
		Yinxiaoon=BitmapFactory.decodeResource(activity.getResources(), R.drawable.yinxiaoon);
		musicback=BitmapFactory.decodeResource(activity.getResources(), R.drawable.musicbackground);
	}
	public void onDraw(Canvas canvas){	
		//���ƺ��������屳��
		paint.setColor(Color.BLACK);//���û�����ɫ
		paint.setAlpha(255);		
		//����ƽ����ͼ
		//if(currentLogo==null)return;				
		canvas.drawBitmap(musicback, 0, 0, paint);	
		if(backmusicFlag01)
		{
			
			canvas.drawBitmap(backMusicoff, pic_x, pic_y, paint);	
		}
		else if(!backmusicFlag01) 
		{
			canvas.drawBitmap(backMusicon, pic_x, pic_y, paint);
		}
		if(yinxiaoFlag01)
		{
			float x1=pic_x;
			float y1=pic_y+MUSIC_HEIGHT+25;
			canvas.drawBitmap(Yinxiaooff, x1, y1, paint);
		}
		else if(!yinxiaoFlag01)
		{
			float x1=pic_x;
			float y1=pic_y+MUSIC_HEIGHT+25;
			canvas.drawBitmap(Yinxiaoon, x1, y1, paint);
		}
		
//		canvas.save();
//    	canvas.clipPath(makePathDash());
//    	canvas.drawBitmap(Yinxiaoon, 0,0, null);
//    	//canvas.drawBitmap(Yinxiaooff, 0, 0, null);
//    	canvas.restore();	
//    	canvas.drawBitmap(Yinxiaooff, 0, 120, null);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();				
    	switch(event.getAction())
    	{
    	case MotionEvent.ACTION_DOWN:
    		
    		//�������ֵĿ��ƿ���
    		if(x>pic_x&&x<pic_x+MUSIC_WEIGHT&&y>pic_y&&y<pic_y+MUSIC_HEIGHT)
    		{
    			backmusicFlag02=!backmusicFlag02;
    			activity.setBackGroundMusicOn(backmusicFlag02);
    			backmusicFlag01=!backmusicFlag01;
    		}
    		if(x>pic_x&&x<pic_x+MUSIC_WEIGHT&&y>pic_y+MUSIC_HEIGHT+25&&y<pic_y+MUSIC_HEIGHT+25+MUSIC_HEIGHT)
    		{
    			yinxiaoFlag02=!yinxiaoFlag02;
    			activity.setSoundOn(yinxiaoFlag02);
    			yinxiaoFlag01=!yinxiaoFlag01;
    		}
    		break;
    	}
		return true;
	}	

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		
	}
	public void surfaceCreated(SurfaceHolder holder) {//����ʱ������		
		drawThread=new DrawThread(this);
		drawThread.start();
		
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {//����ʱ������

		boolean retry = true;
		drawThread.setFlag(false);
	        while (retry) {
	            try {
	            	drawThread.join();
	                retry = false;
	            } 
	            catch (InterruptedException e) {e.printStackTrace();}//���ϵ�ѭ����ֱ��ˢ֡�߳̽���
	        }
	}
	
	
//	
	//����SurfaceView���߳�
	private class DrawThread extends Thread{
		private boolean flag = true;	
		private int sleepSpan = 100;
		MusicSurfaceView fatherView;
		SurfaceHolder surfaceHolder;
		public DrawThread(MusicSurfaceView MusicSurfaceView){
			this.fatherView = MusicSurfaceView;
			this.surfaceHolder = MusicSurfaceView.getHolder();
		}
		public void run(){
			Canvas c;
	        while (this.flag) {
	            c = null;
	            try {
	            	// �����������������ڴ�Ҫ��Ƚϸߵ�����£����������ҪΪnull
	                c = this.surfaceHolder.lockCanvas(null);
	                synchronized (this.surfaceHolder) {
	                	fatherView.onDraw(c);//����
	                }
	            } finally {
	                if (c != null) {
	                	//���ͷ���
	                    this.surfaceHolder.unlockCanvasAndPost(c);
	                }
	            }
	            try{
	            	Thread.sleep(sleepSpan);//˯��ָ��������
	            }
	            catch(Exception e){
	            	e.printStackTrace();//��ӡ��ջ��Ϣ
	            }
	        }
		}
		public void setFlag(boolean flag) {
			this.flag = flag;
		}
	}
}