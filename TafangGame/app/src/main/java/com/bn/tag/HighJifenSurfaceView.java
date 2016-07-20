package com.bn.tag;

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
public class HighJifenSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
	TafangGameActivity activity;//activity������
	Paint paint;//��������
	DrawThread drawThread;//�����߳�����		
	Bitmap bgBitmap;//����ͼƬ	
//	Bitmap highScoreBitmap;//���ֵ�ͼƬ
//	Bitmap defenBitmap;
//	Bitmap riqiBitmap;
	Bitmap[] numberBitmaps;//����ͼƬ	
	Bitmap gangBitmap;//����"-"��Ӧ��ͼƬ
	int bmpx;//����λ��	
	String queryResultStr;//��ѯ���ݿ�Ľ��
	String[] splitResultStrs;//����ѯ����зֺ������
	private int numberWidth;//����ͼƬ�Ŀ��
	private int posFrom=-1;//��ѯ�Ŀ�ʼλ��
	private int length=6;//��ѯ������¼����
	int downY=0;//���º�̧���y����
	int upY=0;
	public HighJifenSurfaceView(TafangGameActivity activity) {
		super(activity);
		this.activity=activity;
		//��ý��㲢����Ϊ�ɴ���
		this.requestFocus();
        this.setFocusableInTouchMode(true);
		getHolder().addCallback(this);//ע��ص��ӿ�	
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//���Ʊ���
		canvas.drawColor(Color.WHITE);
		canvas.drawBitmap(bgBitmap, 0, 0, paint);		
		//���Ƶ÷ֺ�ʱ��
		int x,y;
		for(int i=0;i<splitResultStrs.length;i++)
		{
			if(i%2==0)//����÷ֵ�λ��
			{
				x=400;				
			}
			else//����ʱ���λ��
			{
				x=230;
			}
			y=20+40+20+(numberBitmaps[0].getHeight()+10)*(i/2+1)+10;
			//�����ַ���
			drawDateBitmap(splitResultStrs[i],x,y,canvas,paint);
		}
	}
	//������ͼƬ�ķ���
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
        	if(Math.abs(downY-upY)<20)//����ֵ��Χ�ڣ�����ҳ
        	{
        		return true;
        	}
        	else if(downY<upY)//����Ĩ
        	{	
        		//���Ĩ����ǰҳ��������Ĩ
        		if(this.posFrom-this.length>=-1)
        		{
        			this.posFrom-=this.length;        			
        		}
        	}
        	else//����Ĩ
        	{	
        		//���Ĩ�����ҳ��������Ĩ
        		if(this.posFrom+this.length<activity.getRowCount()-1)
        		{
        			this.posFrom+=this.length;        			
        		}
        	}
        	queryResultStr=activity.query(posFrom,length);//�õ����ݿ��е�����
			splitResultStrs=queryResultStr.split("/", 0);//��"/"�з֣�������մ�
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
		paint=new Paint();//��������
		paint.setAntiAlias(true);//�򿪿����	
		createAllThreads();//���������߳�
		initBitmap();//��ʼ��λͼ��Դ	
		numberWidth=numberBitmaps[0].getWidth()+3;//�õ�����ͼƬ�Ŀ��
		//��ʼ��ͼƬ��λ��
		//bmpx=(int) ((Constant.SCREEN_WIDTH-40)/2);
		posFrom=-1;//��ѯ�Ŀ�ʼλ����-1			
		queryResultStr=activity.query(posFrom,length);//�õ����ݿ��е�����
		splitResultStrs=queryResultStr.split("/", 0);//��"/"�з֣�������մ�		
		startAllThreads();//���������߳�
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
	            catch (InterruptedException e) {e.printStackTrace();}//���ϵ�ѭ����ֱ��ˢ֡�߳̽���
	        }
	}
	//��ͼƬ����
	public void initBitmap(){
			
		bgBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.jifenbackground);
		numberBitmaps=new Bitmap[]{
				BitmapFactory.decodeResource(this.getResources(), R.drawable.d0),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.d1),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.d2),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.d3),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.d4),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.d5),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.d6),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.d7),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.d8),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.d9),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.d0),
		};
		gangBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.gang);		
	}
	void createAllThreads()
	{
		drawThread=new DrawThread(this);//���������߳�		
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
		HighJifenSurfaceView fatherView;
		SurfaceHolder surfaceHolder;
		public DrawThread(HighJifenSurfaceView fatherView){
			this.fatherView = fatherView;
			this.surfaceHolder = fatherView.getHolder();
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
