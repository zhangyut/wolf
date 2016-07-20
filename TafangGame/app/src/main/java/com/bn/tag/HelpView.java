package com.bn.tag;

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
public class HelpView extends SurfaceView 
implements SurfaceHolder.Callback  //ʵ���������ڻص��ӿ�
{
	TafangGameActivity activity;
	Paint paint;//����
	Bitmap helppic;
	DrawThread drawThread;
	
	
	public HelpView(TafangGameActivity activity) {
		super(activity);
		this.activity = activity;
		this.getHolder().addCallback(this);//�����������ڻص��ӿڵ�ʵ����
		paint = new Paint();//��������
		paint.setAntiAlias(true);//�򿪿����
		
		//����ͼƬ
		helppic=BitmapFactory.decodeResource(activity.getResources(), R.drawable.help); 
	}
	public void onDraw(Canvas canvas){	
		//���ƺ��������屳��
		paint.setColor(Color.BLACK);//���û�����ɫ
		paint.setAlpha(255);		
		//����ƽ����ͼ
		//if(currentLogo==null)return;				
		canvas.drawBitmap(helppic, 0, 0, paint);	
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
	
	//����SurfaceView���߳�
	private class DrawThread extends Thread{
		private boolean flag = true;	
		private int sleepSpan = 100;
		HelpView fatherView;
		SurfaceHolder surfaceHolder;
		public DrawThread(HelpView helpView){
			this.fatherView = helpView;
			this.surfaceHolder = helpView.getHolder();
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