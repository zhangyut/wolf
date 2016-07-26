package com.bn.box2d.sndls;
import static com.bn.box2d.sndls.Constant.*;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Pijin
{
	public static float[][] lcon=
	{
		{54,20},
		{93,20}
	};
	//�����X����
	float lx;
	//�����Y����
	float ly;
	//������˩סƤ���X����
	float dx;
	//������˩סƤ���Y����
	float dy;
	static boolean flag=false;
	public Pijin(float lx,float ly,float dx,float dy)
	{
		this.lx=lx;
		this.ly=ly;
		this.dx=dx;
		this.dy=dy;
	}
	public float[] getDegrees(float arg0,float arg1,float arg2,float arg3)
	{
		float[] degrees=new float[2];
		float yoffset=arg3-arg1;
		float xoffset=arg2-arg0;
		degrees[0]=(float) Math.sqrt(xoffset*xoffset+yoffset*yoffset);
		degrees[1]=(float) Math.toDegrees(Math.asin(yoffset/degrees[0]));
		return degrees;
	}
	//�Զ���Ļ��Ʒ���
	public void drawSelf(Canvas canvas,Paint paint)
	{
		if(lx<150*yMainRatio&&!flag)
		{
			float[] degrees=getDegrees(lx,ly,dx,dy);
			setMatrix(canvas,paint,degrees);
		}    
		else
		{
			flag=true;
			float[] degrees=getDegrees(lx,ly,dx,dy);
			setMatrix(canvas,paint,degrees);
		}
	}
	//���þ������ת
	public void setMatrix(Canvas canvas,Paint paint,float[] degrees)
	{
		canvas.save();//���滭��״̬
		//ƽ�ƾ���
		Matrix m1=new Matrix();
		m1.setTranslate(lx+PIC_ARRAY[1].getWidth(), ly+PIC_ARRAY[1].getHeight()/2);
		//��ת����
		Matrix m2=new Matrix();
		m2.setRotate(degrees[1]);
		Matrix m3=new Matrix();
		m3.setConcat(m1, m2);
		Matrix m4=new Matrix();
		float xrotate=(float) (degrees[0])/PIC_ARRAY[24].getWidth();
		m4.setScale(xrotate, 1);
		Matrix m5=new Matrix();
		m5.setConcat(m3, m4);
		canvas.drawBitmap(PIC_ARRAY[24], m5, paint);
		canvas.restore();//�ָ�����״̬
	}
}