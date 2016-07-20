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
public class MainMenuSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
	TafangGameActivity activity;//activity������
	Paint paint;//��������
	//�߳�����
	DrawThread drawThread;//�����߳�����
	SelectThread selectthread;
	Bitmap mainbackgroundpic;
	Bitmap selectpic;
	float ballX=0;
	float ballY=-400;
	//���ⰴťͼƬ
	
	public MainMenuSurfaceView(TafangGameActivity activity) {
		super(activity);
		this.activity=activity;
		//��ý��㲢����Ϊ�ɴ���
		this.requestFocus();
        this.setFocusableInTouchMode(true);
		getHolder().addCallback(this);//ע��ص��ӿ�	
		initBitmap();//��ʼ��λͼ��Դ	
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);  	
		//���Ʊ���
		//canvas.drawColor(Color.WHITE);
		try
		{
			canvas.drawBitmap(mainbackgroundpic,0,0, null);
			canvas.drawBitmap(selectpic, ballX, ballY, null);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
				
				
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();				
    	switch(event.getAction())
    	{
    	case MotionEvent.ACTION_DOWN:
    		
    		//������Ϸ
    		if(x>=GOON_X&&x<=GOON_X+MAIN_LENGTH&&y>=GOON_Y&&y<=GOON_Y+MAIN_WEIGHT)
    		{
    			activity.gotoTaChuGame();
    		}
    		//����Ϸ
    		if(x>=NEW_X&&x<=NEW_X+MAIN_LENGTH&&y>=NEW_Y&&y<=NEW_Y+MAIN_WEIGHT)
    		{
    			activity.myHandler.sendEmptyMessage(0);
    		}
    		//���ְ�
    		if(x>=JIFEN_X&&x<=JIFEN_X+MAIN_LENGTH&&y>=JIFEN_Y&&y<=JIFEN_Y+MAIN_WEIGHT)
    		{
    			activity.myHandler.sendEmptyMessage(3);
    		}
    		//��Ч����
    		if(x>=YINXIAO_X&&x<=YINXIAO_X+MAIN_LENGTH&&y>=YINXIAO_Y&&y<=YINXIAO_Y+MAIN_WEIGHT)
    		{
    			activity.myHandler.sendEmptyMessage(4);
    		}
    		//����
    		if(x>=HELP_X&&x<=HELP_X+MAIN_LENGTH&&y>=HELP_Y&&y<=HELP_Y+MAIN_WEIGHT)
    		{
    			activity.myHandler.sendEmptyMessage(2);
    		}
    		//�˳�
    		if(x>=EXIT_X&&x<=EXIT_X+MAIN_LENGTH&&y>=EXIT_Y&&y<=EXIT_Y+MAIN_WEIGHT)
    		{
    			System.exit(0);
    		}
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
		selectthread=new SelectThread(this);
		selectthread.start();
		createAllThreads();
		startAllThreads();
		
		
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		  boolean retry = true;
		  stopAllThreads();
	}
	//��ͼƬ����
	public void initBitmap(){
		mainbackgroundpic=BitmapFactory.decodeResource(this.getResources(), R.drawable.mainbackground);
		selectpic=BitmapFactory.decodeResource(this.getResources(), R.drawable.select);
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
		//selectthread.setFlag(false);
	}
	
	//����SurfaceView���߳�
	private class DrawThread extends Thread{
		private boolean flag = true;	
		private int sleepSpan = 100;
		MainMenuSurfaceView fatherView;
		SurfaceHolder surfaceHolder;
		public DrawThread(MainMenuSurfaceView fatherView){
			this.fatherView = fatherView;
			this.surfaceHolder = fatherView.getHolder();
		}
		public void run(){
			Canvas c;
	        while (flag) {
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
