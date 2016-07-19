package com.bn.d2.bill;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;


public class Table {
	static final float x=Constant.TABLE_X;
	static final float y=Constant.TABLE_Y;
	Bitmap[] bitmaps;

	static final float bottomWidth=Constant.BOTTOM_WIDTH;
	static final float bottomHeight=Constant.BOTTOM_HEIGHT;
	static final float edgeBig=Constant.EDGE_BIG;
	static final float edgeSmall=Constant.EDGE_SMALL;
	static final float middle=Constant.MIDDLE;
	static final float disCorner=Constant.DIS_CORNER;
	static final float disMiddle=Constant.DIS_MIDDLE;

	static final float tableAreaWidth=bottomWidth-edgeBig*2;
	static final float TableAreaHeight=bottomHeight-edgeBig*2;

	static final float ab=(tableAreaWidth-middle)/2-edgeSmall;
	static final float ef=TableAreaHeight-edgeSmall*2;

	static final float lkx=x+edgeBig;
	static final float efx=lkx+tableAreaWidth;
	static final float ady=y+edgeBig;
	static final float jgy=ady+TableAreaHeight;

	static final float A[]={lkx+edgeSmall-disCorner, ady};
	static final float B[]={lkx+edgeSmall+ab+disMiddle, ady};
	static final float C[]={lkx+edgeSmall+ab+middle-disMiddle, ady};
	static final float D[]={lkx+edgeSmall+ab*2+middle+disCorner, ady};
	static final float E[]={efx, ady+edgeSmall-disCorner};
	static final float F[]={efx, ady+edgeSmall+ef+disCorner};
	static final float G[]={lkx+edgeSmall+ab*2+middle+disCorner, jgy};
	static final float H[]={lkx+edgeSmall+ab+middle-disMiddle, jgy};
	static final float I[]={lkx+edgeSmall+ab+disMiddle, jgy};
	static final float J[]={lkx+edgeSmall-disCorner, jgy};
	static final float K[]={lkx, ady+edgeSmall+ef+disCorner};
	static final float L[]={lkx, ady+edgeSmall-disCorner};
	static final float collisionPoints[][]={A,B,C,D,E,F,G,H,I,J,K,L};

	static final float holeCenterRevise=Constant.HOLE_CENTER_REVISE;
	static final float M[]={lkx,ady};
	static final float N[]={lkx+edgeSmall+ab+middle/2, ady-holeCenterRevise};
	static final float O[]={efx,ady};
	static final float P[]={efx,jgy};
	static final float Q[]={lkx+edgeSmall+ab+middle/2, jgy+holeCenterRevise-2};
	static final float R[]={lkx,jgy};
	static final float holeCenterPoints[][]={M,N,O,P,Q,R};
	static final float cornerHoleR=Constant.CORNER_HOLE_R;
	static final float middleHoleR=Constant.MIDDLE_HOLE_R;

	static final float xBall1=lkx+Constant.X_OFFESET_BALL1;
	static final float yBall1=ady+TableAreaHeight/2-Ball.r;
	static final float xBallDis=(float) ((Ball.d+Constant.GAP_BETWEEN_BALLS)*Math.sin(Math.PI/3));
	static final float yBallDis=(float) ((Ball.d+Constant.GAP_BETWEEN_BALLS)*Math.cos(Math.PI/3));
	static final float yDis=Ball.d+Constant.GAP_BETWEEN_BALLS;

	static final float[][] AllBallsPos={
		new float[]{xBall1-Constant.DIS_WITH_MAIN_BALL,yBall1},
		new float[]{xBall1,yBall1},
		new float[]{xBall1+xBallDis,yBall1+yBallDis},
		new float[]{xBall1+xBallDis,yBall1+yBallDis-yDis},
		new float[]{xBall1+xBallDis*2,yBall1+yBallDis*2},
		new float[]{xBall1+xBallDis*2,yBall1+yBallDis*2-yDis},
		new float[]{xBall1+xBallDis*2,yBall1+yBallDis*2-yDis*2},
		new float[]{xBall1+xBallDis*3,yBall1+yBallDis*3},
		new float[]{xBall1+xBallDis*3,yBall1+yBallDis*3-yDis},
		new float[]{xBall1+xBallDis*3,yBall1+yBallDis*3-yDis*2},
		new float[]{xBall1+xBallDis*3,yBall1+yBallDis*3-yDis*3},
		new float[]{xBall1+xBallDis*4,yBall1+yBallDis*4},
		new float[]{xBall1+xBallDis*4,yBall1+yBallDis*4-yDis},
		new float[]{xBall1+xBallDis*4,yBall1+yBallDis*4-yDis*2},
		new float[]{xBall1+xBallDis*4,yBall1+yBallDis*4-yDis*3},
		new float[]{xBall1+xBallDis*4,yBall1+yBallDis*4-yDis*4},
	};	

	final float[][] bmpsPos={
			new float[]{lkx,ady},//0
			new float[]{x,y},//1
			new float[]{lkx+edgeSmall, y},//2
			new float[]{lkx+edgeSmall+ab, y},//3
			new float[]{lkx+edgeSmall+ab+middle, y},//4
			new float[]{lkx+edgeSmall+ab*2+middle, y},//5
			new float[]{efx, ady+edgeSmall},//6
			new float[]{lkx+edgeSmall+ab*2+middle, ady+edgeSmall+ef},//7
			new float[]{lkx+edgeSmall+ab+middle, jgy},//8
			new float[]{lkx+edgeSmall+ab, jgy},//9
			new float[]{lkx+edgeSmall, jgy},//10
			new float[]{x, ady+edgeSmall+ef},//11
			new float[]{x, ady+edgeSmall},//12
		};
	public Table(Bitmap[] bitmaps)
	{
		this.bitmaps=bitmaps;
	}
	public void drawSelf(Canvas canvas,Paint paint)
	{


		for(int i=0;i<bitmaps.length;i++){
			canvas.drawBitmap(bitmaps[i], bmpsPos[i][0]+Constant.X_OFFSET, bmpsPos[i][1]+Constant.Y_OFFSET, paint);
		}

//		paint.setColor(Color.YELLOW);paint.setAlpha(100);
//		canvas.drawRect(lkx+Constant.X_OFFSET, ady+Constant.Y_OFFSET, efx+Constant.X_OFFSET, jgy+Constant.Y_OFFSET, paint);
//		paint.setColor(Color.WHITE);paint.setAlpha(130);
//		canvas.drawRect(lkx+Constant.X_OFFSET, y+Constant.Y_OFFSET, lkx+edgeSmall+Constant.X_OFFSET, y+edgeBig+Constant.Y_OFFSET, paint);
//		canvas.drawRect(efx+Constant.X_OFFSET, jgy-edgeSmall+Constant.Y_OFFSET, efx+edgeBig+Constant.X_OFFSET, jgy+Constant.Y_OFFSET, paint);
//		canvas.drawRect(x+(bottomWidth-middle)/2+Constant.X_OFFSET, y+Constant.Y_OFFSET, x+(bottomWidth+middle)/2+Constant.X_OFFSET, jgy+Constant.Y_OFFSET, paint);
//		paint.setAlpha(255);
//		paint.setColor(Color.YELLOW);paint.setAlpha(100);		
//		for(float[] p:holeCenterPoints)
//		{

//			if(p==Table.N || p==Table.Q){
//				canvas.drawCircle(p[0]+Constant.X_OFFSET, p[1]+Constant.Y_OFFSET, middleHoleR, paint);
//			}else{
//				canvas.drawCircle(p[0]+Constant.X_OFFSET, p[1]+Constant.Y_OFFSET, cornerHoleR, paint);
//			}
//		}
//		paint.setAlpha(255);
	}
}