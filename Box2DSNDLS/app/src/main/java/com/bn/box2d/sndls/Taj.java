package com.bn.box2d.sndls;
import static com.bn.box2d.sndls.Constant.*;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Taj 
{
	int tajIndex;
	float xoffset;//xλ��
	float yoffset;//yλ��
	float vx;//�ٶ�   
	float vy;
	int timeSpan=0;
	float maxOffset=1f;//��ͷ��ʼ�Ŀ��
	public Taj(int tajIndex,float xoffset,float yoffset,float vx,float vy)
	{
		this.tajIndex=tajIndex;
		this.xoffset=xoffset;
		this.yoffset=yoffset;
		this.vx=vx;
		this.vy=vy;
	}
	//���Ʒ���
	public void drawSelf(Canvas canvas,Paint paint)
	{
		float x=xoffset+vx*timeSpan;
		float y=yoffset-vy*timeSpan+0.5f*timeSpan*timeSpan*1.0f;
		float offset=0.3f;
		Matrix m1=new Matrix();
		m1.setTranslate(x, y);
		Matrix m2=new Matrix();
		if(vx>=5&&vy>=5)
		{
			m2.setScale(maxOffset-offset, maxOffset-offset);
		}
		else if(vx<5&&vy<5)
		{
			m2.setScale(maxOffset+offset, maxOffset+offset);
		}
		else
		{
			m2.setScale(maxOffset, maxOffset);
		} 
		Matrix m3=new Matrix();
		m3.setConcat(m1, m2);
		//�ڷ�Χ֮��Ͳ��ٻ���
		if(xoffset<0||xoffset>SCREEN_WIDTH||yoffset>SCREEN_HEIGHT)
		{
			return;
		}
		else
		{
			canvas.drawBitmap(PE_ARRAY[tajIndex], m3, paint);
		}
	}
}