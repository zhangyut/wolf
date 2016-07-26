package com.bn.box2d.sndls;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.BodyType;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import static com.bn.box2d.sndls.Constant.*;

public class MyPolygonImg
{
	Bitmap bma[];//���ڻ��Ƶ�ͼƬ
	int bmIndex=0;//��ǰͼƬ����
	Body body;//��Ӧ���������еĸ���
	float width;//Ŀ����
	float height;//Ŀ��߶�
	float xScale;
	float yScale;
	boolean isLive=true;//�Ƿ����
	boolean isDeleted=false;//�Ƿ��Ѿ�ɾ��
	GameView gv;
	long timeStamp=0;
	
	public MyPolygonImg(Body body,Bitmap[] bma,float width,float height,GameView gv)
	{
		this.body=body;
		this.bma=bma;
		this.width=width;
		this.height=height;
		xScale=width/bma[0].getWidth();
		yScale=height/bma[0].getHeight();
		this.gv=gv;
	}
	
	public void drawSelf(Canvas canvas,Paint paint)
	{ 		
		if(!isLive){return;}
		 
		float x=body.getPosition().x*RATE;
		float y=body.getPosition().y*RATE;
		float angle=body.getAngle();
	    canvas.save();
	    Matrix m1=new Matrix();
	    m1.setRotate((float)Math.toDegrees(angle),x+xOffset, y+yOffset);
	    canvas.setMatrix(m1);	   
	    Matrix m2=new Matrix();
	    m2.setScale(xScale, yScale);
	    Matrix m3=new Matrix();
	    m3.setTranslate(x+xOffset, y+yOffset);
	    Matrix mz=new Matrix();
	    mz.setConcat(m3, m2);
	    canvas.drawBitmap(bma[bmIndex], mz, paint);
        canvas.restore();
	}  
	
	//��ײ��ִ�в�ͬ�Ķ���
	public void doAction(float x,float y)
	{
		if(body.getType() == BodyType.DYNAMIC &&gv.hero!=this)
		{
			long tempST=System.currentTimeMillis();
			if(tempST-timeStamp>800)
			{
				gv.activity.soundutil.playSound(4, 0);
				timeStamp=tempST;
			}
		}  
	}
}
