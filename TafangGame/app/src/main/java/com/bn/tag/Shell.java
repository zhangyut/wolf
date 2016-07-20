package com.bn.tag;

import java.util.List;
import java.util.Vector;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import static com.bn.tag.Constant.*;

public class Shell {
	GameView gameView;
	private Bitmap bitmap;//λͼ
	float shellx;
	float shelly;
	SingleJianta jianta;
	static List<Shell> shl=new Vector<Shell>();
	static List<Target> tas=new Vector<Target>();
	
	Target tartee;
	double direction;
	public Shell(GameView gameView,Bitmap bitmap,float shellx,float shelly,Target tartee,SingleJianta jianta)
	{
		this.gameView=gameView;
		this.bitmap=bitmap;	
		this.shellx=shellx;
		this.shelly=shelly;
		this.tartee=tartee;
		this.jianta=jianta;
		//this.targett=targett;
	}

	public void drawSelf(Canvas canvas,Paint paint)
	{
		go();
		float dnX=shellx;
		float dnY=shelly;		
		canvas.drawBitmap(bitmap, dnX, dnY,paint);
		
	}
	//ǰ���ķ���
	public void go()
	{	
		direction=calDirection1(shellx,shelly,tartee.ballx,tartee.bally);
		float llx=(float) (SPEED*Math.sin(direction*Math.PI/180)+shellx);//x����
		float lly=(float) (SPEED*Math.cos(direction*Math.PI/180)+shelly);//y����	
		if(tas.contains(tartee))
		{
			shl.add(this);
		}		
		if(IsTwoRectCross//һ�����ε��ĸ�����֮һ�Ƿ�����һ��������
			(
					llx,lly,JIAN_TOU_WEIGHT,JIAN_TOU_HEIGHT,//���ϵ�x,y���꣬������
					tartee.ballx-SINGLE_PIC/2+20,tartee.bally-SINGLE_PIC/2,SINGLE_PIC-15,SINGLE_PIC-15
			))
			{
				
				//ÿ��ɱһ�������ý�Ǯ����			   
				tartee.bloodsum-=1;
				if(tartee.bloodsum==0)
				{					
					if(!tas.contains(tartee))
					{
						gameView.doller+=5;
						gameView.shaNUM+=1;
						gameView.shuijingMiddleNum+=1;
						tas.add(tartee);
					}				
						
							
				}
				shl.add(this);
							
				if(gameView.activity.isSoundOn())
				{
					gameView.playSound(2, 0);
				}
				
				
				
			}
			else if(getlength(llx,lly,jianta.clo*SINGLE_RODER,jianta.row*SINGLE_RODER)>R_LENGTH*R_LENGTH)
			{
				shl.add(this);				
			}
			else 
			{				
				shellx=llx;//(float) (SPEED*Math.sin(direction*Math.PI/180)+shellx);//x����
				shelly=lly;//(float) (SPEED*Math.cos(direction*Math.PI/180)+shelly);//y����
			}
	}
	
	public float getlength(float x1,float y1,float x2,float y2)
	{
		float result=(x1-x2)*(x1-x2)+(y1-y2)*(y1-y2);
		return result;
		
	}

	
	
	
	//���㷽��
    public double calDirection1(float x1,float y1,float x2,float y2)
    {
    	double direction = 0;				
		float dx=x1-x2;
		float dy=y1-y2;          
		if(dx!=0||dy!=0)
		{
				if(dx>0&&dy>0)
				{
				direction=180+Math.toDegrees(Math.atan(dx/dy));
    			}
				else if(dx<0&&dy>0)
				{
					direction=180-Math.toDegrees(Math.atan(-dx/dy));
				} 
				else if(dx<0&&dy<0)
				{
					direction=Math.toDegrees(Math.atan(dx/dy));
				}
				else if(dx>0&&dy<0)
				{
					direction=360-Math.toDegrees(Math.atan(dx/-dy));
				}	
				else if(dx==0)
				{
					if(dy>0)
					{
						direction=180;
					}
					else
					{
						direction=0;
					}
				}
				else if(dy==0)
				{
					if(dx>0)
					{
						direction=270;
					}
					else
					{
						direction=90;
					}
				}
			}		
		return direction;
		
    }
    
    public static boolean IsTwoRectCross//һ�����ε��ĸ�����֮һ�Ƿ�����һ��������
	(
			float xLeftTop1,float yLeftTop1,float length1,float width1,//���ϵ�x,y���꣬������
			float xLeftTop2,float yLeftTop2,float length2,float width2
	)
	{
		if
		(
				isPointInRect(xLeftTop1,yLeftTop1,xLeftTop2,yLeftTop2,length2,width2)||	//���϶���
				isPointInRect(xLeftTop1+length1,yLeftTop1,xLeftTop2,yLeftTop2,length2,width2)||	//���϶���
				isPointInRect(xLeftTop1,yLeftTop1+width1,xLeftTop2,yLeftTop2,length2,width2)||	//���¶���
				isPointInRect(xLeftTop1+length1,yLeftTop1+width1,xLeftTop2,yLeftTop2,length2,width2)||	//���¶���
				
				isPointInRect(xLeftTop2,yLeftTop2,xLeftTop1,yLeftTop1,length1,width1)||	//���϶���
				isPointInRect(xLeftTop2+length2,yLeftTop2,xLeftTop1,yLeftTop1,length1,width1)||	//���϶���
				isPointInRect(xLeftTop2,yLeftTop2+width2,xLeftTop1,yLeftTop1,length1,width1)||	//���¶���
				isPointInRect(xLeftTop2+length2,yLeftTop2+width2,xLeftTop1,yLeftTop1,length1,width1)	//���¶���
		)
		{
			return true;
		}
		return false;
	}
	public static boolean isPointInRect//һ�����Ƿ��ھ����ڣ������߽磩
	(
			float pointx,float pointy,
			float xLeftTop,float yLeftTop,float length,float width
	)
	{
		if(
				pointx>=xLeftTop&&pointx<=xLeftTop+length&&
				pointy>=yLeftTop&&pointy<=yLeftTop+width
		  )
		  {
			  return true;
		  }
		return false;
	}		
}
