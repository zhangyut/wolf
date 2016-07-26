package com.bn.box2d.sndls;
import static com.bn.box2d.sndls.Constant.*;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.collision.AABB;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback,ContactListener
{
	MyBox2dActivity activity;
	Paint paint;//����		
	DrawThread dt;//�����߳�
    AABB worldAABB;//���� һ��������ײ������   
    World world;     
    Pijin pjc;//Ƥ��
    String hightScore;//��߷�
    //���ѡ��ؿ��˵���ȥ������ť���ƽ��ֵ
    float x_pj;
    //�����б�
    ArrayList<MyPolygonImg> bl=new ArrayList<MyPolygonImg>();
    //Ӣ��
    MyPolygonImg hero;
    //�÷��б�
    List<Score> scoreList=new ArrayList<Score>();
    
    //�Ƿ��Ѿ��ͷ���ͷ�ı�־λ
    boolean flagSf=false;
    //��ͷ��ʼλ��
    float xst=180*yMainRatio;
    float yst=SCREEN_HEIGHT-DMGD-PIC_ARRAY[22].getHeight();
    final float ysXst=xst;
    final float ysYst=yst;
	
	public GameView(MyBox2dActivity activity) 
	{
		super(activity);
		this.activity = activity;	
		this.loadGameData();
		this.initContactListener();
		
		//�����������ڻص��ӿڵ�ʵ����
		this.getHolder().addCallback(this);
		//��ʼ������
		paint = new Paint();//��������
		paint.setAntiAlias(true);//�򿪿����
		//���������߳�
		dt=new DrawThread(this);
		dt.start();
		
	} 
	
	//������Ϸ����
	public void loadGameData()
	{
		worldAABB = new AABB();   
        
        //���½磬����Ļ�����Ϸ�Ϊ ԭ�㣬��������ĸ��嵽����Ļ�ı�Ե�Ļ�����ֹͣģ��   
        worldAABB.lowerBound.set(0f,-100.0f);
        worldAABB.upperBound.set(200.0f, 100.0f);//ע������ʹ�õ�����ʵ����ĵ�λ   
           
        Vec2 gravity = new Vec2(0.0f,20.0f);
        boolean doSleep = true;     
        //�������� 
        world = new World(gravity);
        world.setSleepingAllowed(doSleep);
        //���������е�����
        for(int i=0;i<STONE_VERTEX[currStage].length;i++)
        {
            Bitmap[] bma=new Bitmap[IMG_ID[currStage][i].length];
            for(int j=0;j<IMG_ID[currStage][i].length;j++)
            {
            	bma[j]=PIC_ARRAY[IMG_ID[currStage][i][j]];
            }     
            MyPolygonImg myBodyTemp=Box2DUtil.createPolygonImg 
            (
              		LOCATION[currStage][i][0], 
              		LOCATION[currStage][i][1], 
              		STONE_VERTEX[currStage][i],
              		IS_MOVE[currStage][i], 
              		world, 
              		bma,
              		SIZE[currStage][i][0],
              		SIZE[currStage][i][1],
              		typeA[currStage][i],
              		this
            );
            myBodyTemp.body.setLinearVelocity(new Vec2(SPEED[currStage][i][0],SPEED[currStage][i][1]));
            bl.add(myBodyTemp); 
        }
	}

	//������ײ������
	public void initContactListener()
	{
		world.setContactListener(this);
	}

	public void onDraw(Canvas canvas)
	{		
		if(canvas==null)
		{
			return;
		}
		
		//���㳡����ƫ����
		if(hero!=null)
		{
			float hx=hero.body.getPosition().x*RATE;
			float tempXOffset=-(hx-SCREEN_WIDTH/2);	
			if(tempXOffset>0)
			{
				tempXOffset=0;
			}
			else if(tempXOffset<SCREEN_WIDTH-CJ_WIDTH)
			{
				tempXOffset=SCREEN_WIDTH-CJ_WIDTH;
			}
			if(tempXOffset<xOffset)
			{
				xOffset=tempXOffset;
			}
		}
		  
		canvas.save();
		canvas.clipRect(new RectF(0,0,SCREEN_WIDTH,SCREEN_HEIGHT));
		//���Ʊ���
		canvas.drawBitmap(PIC_ARRAY[17], 0+xOffset*0.6f, 0, paint);
		canvas.drawBitmap(PIC_ARRAY[17], 0+xOffset*0.6f+PIC_ARRAY[17].getWidth(), 0, paint);
		//���Ʊ�����
		canvas.drawBitmap(PIC_ARRAY[26], 0+xOffset,SCREEN_HEIGHT-DMGD-PIC_ARRAY[26].getHeight(), paint);
		canvas.drawBitmap(PIC_ARRAY[26], 0+xOffset+PIC_ARRAY[26].getWidth(),SCREEN_HEIGHT-DMGD-PIC_ARRAY[26].getHeight(), paint);
		
		//���Ƴ����е�����
		for(MyPolygonImg mb:bl)
		{
			mb.drawSelf(canvas, paint);
		}  
		 
		//�����ܵ÷�
		String scoreStr=SCORE+"";
		float xsStart=SCREEN_WIDTH-NUM_ARRAY[0].getWidth()*(scoreStr.length()+1);
		float ysStart=NUM_ARRAY[0].getHeight()/2;
		for(int i=0;i<scoreStr.length();i++)
		{
			canvas.drawBitmap(NUM_ARRAY[scoreStr.charAt(i)-'0'], xsStart+i*NUM_ARRAY[0].getWidth() ,ysStart, paint);
		}
		
		//�����϶���ͷ�Լ�Ƥ��
		if(!flagSf)
		{			
			pjc=new Pijin(xst-PIC_ARRAY[1].getWidth(),yst,180*yMainRatio,SCREEN_HEIGHT-DMGD-PIC_ARRAY[22].getHeight());
			pjc.drawSelf(canvas, paint);
			//���Ʊ�����������ͷ  
			canvas.drawBitmap(PIC_ARRAY[1], xst+xOffset,yst, paint);
			//������Ƥ��
			pjc=new Pijin(xst-PIC_ARRAY[1].getWidth(),yst,180*yMainRatio-PIC_ARRAY[1].getWidth(),SCREEN_HEIGHT-DMGD-PIC_ARRAY[22].getHeight());
			pjc.drawSelf(canvas, paint);
		} 
		if(flagSf&&!Pijin.flag&&hero!=null) 
		{ 
			pjc=new Pijin(hero.body.getPosition().x*RATE-PIC_ARRAY[1].getWidth(),hero.body.getPosition().y*RATE,180*yMainRatio,SCREEN_HEIGHT-DMGD-PIC_ARRAY[22].getHeight());
			pjc.drawSelf(canvas, paint);
			//������Ƥ��
			pjc=new Pijin(hero.body.getPosition().x*RATE-PIC_ARRAY[1].getWidth(),hero.body.getPosition().y*RATE,180*yMainRatio-PIC_ARRAY[1].getWidth(),SCREEN_HEIGHT-DMGD-PIC_ARRAY[22].getHeight());
			pjc.drawSelf(canvas, paint);			
		}
		if(flagSf&&Pijin.flag&&xOffset<=0&&xOffset>-190*yMainRatio)
		{
			pjc=new Pijin(180*yMainRatio-PIC_ARRAY[1].getWidth()+xOffset, SCREEN_HEIGHT-DMGD-PIC_ARRAY[22].getHeight(),180*yMainRatio+xOffset, SCREEN_HEIGHT-DMGD-PIC_ARRAY[22].getHeight());
			pjc.drawSelf(canvas, paint);
		}
		
		//���Ƶ���
		canvas.drawBitmap(PIC_ARRAY[22], 150*yMainRatio+xOffset,SCREEN_HEIGHT-DMGD-PIC_ARRAY[22].getHeight(), paint);		
		  
		//����ǰ����
		canvas.drawBitmap(PIC_ARRAY[25], 0+xOffset,SCREEN_HEIGHT-DMGD-PIC_ARRAY[25].getHeight(), paint);
		canvas.drawBitmap(PIC_ARRAY[25], 0+xOffset+PIC_ARRAY[25].getWidth(),SCREEN_HEIGHT-DMGD-PIC_ARRAY[25].getHeight(), paint);
		
		//����С�÷��б�
		for(Score sc:scoreList)
		{
			sc.drawSelf(canvas, paint);
		}
		canvas.restore();
		
		//���ƶԻ���
		if(dt.flag==true)
		{
			//���ƴ󱳾�
			canvas.drawBitmap(OTHER_PIC_ARRAY[3],(SCREEN_WIDTH-OTHER_PIC_ARRAY[3].getWidth())/2,
								(SCREEN_HEIGHT-OTHER_PIC_ARRAY[3].getHeight())/2, paint);
			//���Ƶ�ǰΪ�ڼ���   
			canvas.drawBitmap(OTHER_PIC_ARRAY[currStage], (SCREEN_WIDTH-OTHER_PIC_ARRAY[3].getWidth())/2, 
								(SCREEN_HEIGHT-OTHER_PIC_ARRAY[3].getHeight())/2, paint);
			//������߷�===================================================�������Ե�һ�صķ������Ƶ���߷֣��ǵû���
			hightScore=SCORE+"";
			xsStart=(SCREEN_WIDTH+OTHER_PIC_ARRAY[3].getWidth())/2-NUM_ARRAY[0].getWidth()*(hightScore.length()+4);
			ysStart=(SCREEN_HEIGHT-OTHER_PIC_ARRAY[3].getHeight())/2+NUM_ARRAY[0].getHeight()+10*yMainRatio;
			for(int i=0;i<scoreStr.length();i++)
			{
				canvas.drawBitmap(NUM_ARRAY[scoreStr.charAt(i)-'0'], xsStart+i*NUM_ARRAY[0].getWidth() ,ysStart, paint);
			}  
			//���Ƶ÷�=======================================================���ﲻ��Ҫ��
			xsStart=SCREEN_WIDTH/2-NUM_ARRAY[0].getWidth()*(scoreStr.length()+2);
			ysStart=SCREEN_HEIGHT/2-4*yMainRatio;
			for(int i=0;i<scoreStr.length();i++)
			{   
				canvas.drawBitmap(NUM_ARRAY[scoreStr.charAt(i)-'0'], xsStart+(i+2)*NUM_ARRAY[0].getWidth() ,ysStart+NUM_ARRAY[0].getHeight()+8*yMainRatio, paint);
			}
			//������ת�������˵���ť
			x_pj=(OTHER_PIC_ARRAY[3].getWidth()-OTHER_PIC_ARRAY[4].getWidth()*2)/3;
			canvas.drawBitmap(OTHER_PIC_ARRAY[4],(SCREEN_WIDTH-OTHER_PIC_ARRAY[3].getWidth())/2+x_pj,
								(SCREEN_HEIGHT+OTHER_PIC_ARRAY[3].getHeight())/2-OTHER_PIC_ARRAY[4].getHeight()/2, paint);
			//���ƽ�����һ�ذ�ť
			canvas.drawBitmap(OTHER_PIC_ARRAY[5],(SCREEN_WIDTH-OTHER_PIC_ARRAY[3].getWidth())/2+x_pj*2+OTHER_PIC_ARRAY[5].getWidth(),
								(SCREEN_HEIGHT+OTHER_PIC_ARRAY[3].getHeight())/2-OTHER_PIC_ARRAY[4].getHeight()/2, paint);
			//���Ƶ�ǰ����ʾ���磺level_Failure��level_cleared
			if(dt.levelFlag)  
			{//level_cleared
				canvas.drawBitmap(OTHER_PIC_ARRAY[7],(SCREEN_WIDTH-OTHER_PIC_ARRAY[3].getWidth()+OTHER_PIC_ARRAY[7].getWidth()/2)/2-25*yMainRatio,
									SCREEN_HEIGHT/2-OTHER_PIC_ARRAY[7].getHeight(), paint);
			}
			if(!dt.levelFlag)
			{//level_Failure
				canvas.drawBitmap(OTHER_PIC_ARRAY[8],(SCREEN_WIDTH-OTHER_PIC_ARRAY[3].getWidth()+OTHER_PIC_ARRAY[8].getWidth()/2)/2-25*yMainRatio,
									SCREEN_HEIGHT/2-OTHER_PIC_ARRAY[8].getHeight(), paint);
			}
		}
	}
	
	//��Ļ�����¼�	
	boolean isTouched=false;//�Ƿ���������ͷ
	float startX=0;
	float startY=0;
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		if(!flagSf)
		{
			int action=e.getAction();
			float x=e.getX();
			float y=e.getY();
			float w=PIC_ARRAY[1].getWidth();
			float h=PIC_ARRAY[1].getHeight();
			switch(action)
			{
			   case  MotionEvent.ACTION_DOWN:
				  if(x>xst&&x<xst+w&&y>yst&&y<yst+h)
				  {
					  if(!isTouched)
					  {
						  activity.soundutil.playSound(0, 0);
					  }
					  isTouched=true;
					  startX=x;
					  startY=y;
				  } 
			   break;
			   case  MotionEvent.ACTION_MOVE:
				  if(isTouched)
				  {
					  float dx=x-startX;
					  float dy=y-startY;
					  final float xYZ=-80*yMainRatio;
					  final float yYZ=60*yMainRatio;
					  
					  if(dx>0)
					  {
						  dx=0;
					  }
					  else if(dx<xYZ)
					  {
						  dx=xYZ;
					  }
					  
					  if(dy>yYZ)
					  {
						  dy=yYZ;
					  }
					  else if(dy<-yYZ)
					  {
						  dy=-yYZ;
					  }
					  
					  xst=ysXst+dx;
					  yst=ysYst+dy;
				  }
			   break;
			   case  MotionEvent.ACTION_UP:
				  if(isTouched)
				  {
					  isTouched=false;
					  flagSf=true;				  
					  //�����ͷ����
					  dt.xst=xst;  
					  dt.yst=yst;
					  dt.addTask=true;
					  activity.soundutil.playSound(1, 0);
			  }
			   break;
			}
			return true;
		}
		else
		{
			int action=e.getAction();
			float x=e.getX();
			float y=e.getY();
			float w=OTHER_PIC_ARRAY[4].getWidth();
			float h=OTHER_PIC_ARRAY[4].getHeight();
			switch(action)
			{
			   case  MotionEvent.ACTION_DOWN:
				  //������Ƿ������˵���ť
				  if(dt.flag==true&&x>(SCREEN_WIDTH-OTHER_PIC_ARRAY[3].getWidth())/2+x_pj&&
						  			x<(SCREEN_WIDTH-OTHER_PIC_ARRAY[3].getWidth())/2+x_pj+w&&
						  			y>(SCREEN_HEIGHT+OTHER_PIC_ARRAY[3].getHeight())/2-h/2&&
						  			y<(SCREEN_HEIGHT+OTHER_PIC_ARRAY[3].getHeight())/2
					 )
				  {
					  activity.hd.sendEmptyMessage(1);
				  }
				  //���������һ�ذ�ť
				  if(dt.flag==true&&x>(SCREEN_WIDTH-OTHER_PIC_ARRAY[3].getWidth())/2+x_pj*2+w&&
						  			x<(SCREEN_WIDTH-OTHER_PIC_ARRAY[3].getWidth())/2+x_pj*2+w*2&&
						  			y>(SCREEN_HEIGHT+OTHER_PIC_ARRAY[3].getHeight())/2-h/2&&
						  			y<(SCREEN_HEIGHT+OTHER_PIC_ARRAY[3].getHeight())/2
					 )   
				  {
					  if(currStage<2)
					  {
						  hero=null;//hero.body.getPosition().x*RATE;
						  DRAW_THREAD_FLAG=false;
						  flagSf=false;
						  currStage=currStage+1;
						  activity.hd.sendEmptyMessage(0);
					  }
				  }
			   break;
			}
		}
		return true;
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) 
	{
		
	}

	public void surfaceCreated(SurfaceHolder holder) {//����ʱ������
		repaint();
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {//����ʱ������
		
	}
	
	public void repaint()
	{
		SurfaceHolder holder=this.getHolder();
		Canvas canvas = holder.lockCanvas();//��ȡ����
		try{
			synchronized(holder){
				onDraw(canvas);//����
			}			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if(canvas != null){
				holder.unlockCanvasAndPost(canvas);
			}
		}
	}

	@Override
	public void beginContact(Contact contact) {
	}

	@Override
	public void endContact(Contact contact) {

	}

	@Override
	public void preSolve(Contact contact, Manifold manifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse contactImpulse) {

	}
}