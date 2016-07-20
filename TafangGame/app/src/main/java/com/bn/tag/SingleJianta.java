package com.bn.tag;



import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import static com.bn.tag.Constant.*;

public class SingleJianta {
	GameView gameView;
	Bitmap bitmap;
	//Bitmap[]
	int clo;
	int row;
	int state;//��������ư뾶 �뾶��ͬ ����ͬ

	public SingleJianta(GameView gameView,Bitmap bitmap,int x,int y,int state)
	{
		this.gameView=gameView;
		this.bitmap=bitmap;	
		this.clo=x;
		this.row=y;
		this.state=state;		
	}
	public void drawSelf(Canvas canvas,Paint paint)
	{	
		float x1=SINGLE_RODER*clo;
		float y1=SINGLE_RODER*row-JIAN_TA_HEIGHT+SINGLE_RODER;
		canvas.drawBitmap(bitmap, x1,y1,paint);
	}

}
