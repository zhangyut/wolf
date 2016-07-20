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
public class GameOverView extends SurfaceView 
implements SurfaceHolder.Callback  //ʵ���������ڻص��ӿ�
{
	TafangGameActivity activity;
	Paint paint;//����
	int currentAlpha=0;//��ǰ�Ĳ�͸��ֵ
	
	float screenWidth=Constant.SCREEN_HEIGHT;//��Ļ���
	float screenHeight=Constant.SCREEN_WIDTH;//��Ļ�߶�
	int sleepSpan=50;//������ʱ��ms
		
	Bitmap bitmapGameOver;//��ǰlogoͼƬ����
	int currentX;
	int currentY;
	
	public GameOverView(TafangGameActivity activity) {
		super(activity);
		this.activity = activity;
		this.getHolder().addCallback(this);//�����������ڻص��ӿڵ�ʵ����
		paint = new Paint();//��������
		paint.setAntiAlias(true);//�򿪿����
		
		//����ͼƬ
		bitmapGameOver=BitmapFactory.decodeResource(activity.getResources(), R.drawable.gameover); 
		
	}
	public void onDraw(Canvas canvas){	
		//���ƺ��������屳��
		paint.setColor(Color.BLACK);//���û�����ɫ
		paint.setAlpha(255);
		canvas.drawRect(0, 0, screenWidth, screenHeight, paint);
		
		//����ƽ����ͼ
		if(bitmapGameOver==null)return;
		paint.setAlpha(currentAlpha);		
		canvas.drawBitmap(bitmapGameOver, currentX, currentY, paint);	
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		
	}
	public void surfaceCreated(SurfaceHolder holder) {//����ʱ������		
		new Thread()
		{
			public void run()
			{									
					//����ͼƬλ��
					currentX=(int) (activity.screenWidth/2-bitmapGameOver.getWidth()/2);
					currentY=(int) (activity.screenHeight/2-bitmapGameOver.getHeight()/2);
					
					for(int i=255;i>-10;i=i-5)
					{//��̬����ͼƬ��͸����ֵ�������ػ�	
						currentAlpha=i;
						if(currentAlpha<0)
						{
							currentAlpha=0;
						}
						SurfaceHolder myholder=GameOverView.this.getHolder();
						Canvas canvas = myholder.lockCanvas();//��ȡ����
						try{
							synchronized(myholder){
								onDraw(canvas);//����
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
							{//������ͼƬ����ȴ�һ��
								Thread.sleep(1000);
							}
							Thread.sleep(sleepSpan);
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
					}
				
				//����������Ϻ�ȥ���˵�����				
				 activity.sendMessage(1);
			}
		}.start();
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {//����ʱ������

	}
}