package com.bn.box2d.sndls;
import static com.bn.box2d.sndls.Constant.*;

import org.jbox2d.common.Vec2;
import android.graphics.Bitmap;

public class DrawThread extends Thread
{  
	GameView gv;
	boolean addTask=false;
	float xst;
	float yst;
	boolean flag=false;//���غ���ƶԻ���ı�־λ
	boolean levelFlag;	//level_cleared��level_Failure�ı�־λ   
	public DrawThread(GameView gv)
	{
		this.gv=gv; 
	}
	  
	@Override
	public void run()
	{
		while(DRAW_THREAD_FLAG)
		{
			if(!flag)
			{
				if(addTask)
				{
					doAddtask();
					addTask=false;
				}
			}
			gv.repaint();
			if(!flag)
			{	
				gv.world.step(TIME_STEP, ITERA, 2);//��ʼģ��
			}
			try 
			{
				Thread.sleep(10);
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			if(!flag)
			{
				for(MyPolygonImg mpi:gv.bl)
				{
					if(!mpi.isLive&&!mpi.isDeleted)
					{
						mpi.isDeleted=true;
						gv.world.destroyBody(mpi.body);
					}				
				} 	
			}
			//�ж��Ƿ����
			if(!flag)
			{
				if(SCORE>=GG_SCORE[currStage])
				{
					if(SCORE>HH_SCORE[currStage])
					{
						HH_SCORE[currStage]=SCORE;  
					}
					flag=true;				
					levelFlag=true;
				}
			}
			//�ж��Ƿ�����
			if(START_SCORE)   
			{
				boolean isStop=true;
				for(MyPolygonImg mpi:gv.bl)
				{					
					if(mpi.isLive&&mpi.body.getLinearVelocity().lengthSquared()>0.1f)
					{
						isStop=false;
						break;
					}
				}
				if(isStop)
				{
					flag=true;
					levelFlag=false;
				}
			}
			try 
			{
				Thread.sleep(5);
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}

	public void doAddtask()
	{
		//�����ͷ����
		Bitmap[] bma=new Bitmap[1];
        bma[0]=PIC_ARRAY[1];
        float[][] dataF=new float[][]
        {
  			{16,0},{32,4},{40,20},{32,38},{8,38},{0,28},{2,17},{8,5}
    	};
        for(int i=0;i<dataF.length;i++)
        {
        	dataF[i][0]=dataF[i][0]*yMainRatio;
        	dataF[i][1]=dataF[i][1]*yMainRatio;
        }
        gv.hero=Box2DUtil.createPolygonImg 
        (
        		xst, 
        		yst, 
        		dataF,
        		false, 
        		gv.world, 
        		bma,
        		42*yMainRatio,
        		42*yMainRatio,
        		BodyType.LS_ZY,
        		gv
         );
         float sdx=gv.ysXst-xst;
         float sdy=gv.ysYst-yst;
         double len=Math.sqrt(sdx*sdx+sdy*sdy);
         final float maxSpeed=50;
         float vx=(float) (sdx*maxSpeed/len);
         float vy=(float) (sdy*maxSpeed/len);	
         gv.hero.body.setLinearVelocity(new Vec2(vx,vy));
         gv.bl.add(gv.hero); 
	}
}