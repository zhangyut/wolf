package com.bn.box2d.sndls;

import static com.bn.box2d.sndls.Constant.*;

public class MainMenuDrawThread extends Thread
{
	static float syoffset;
	static float xyoffset;
	static float zyoffset;
	static float yyoffset;
	static float bdxoffset;
	static float bgxoffset;
	MainMenuView mmv;
	float degrees;
	public MainMenuDrawThread(MainMenuView tjview)
	{
		this.mmv=tjview;
	}
	@Override
	public void run()
	{
		while(MAIN_DRAW_THREAD_FLAG)
		{
			bdxoffset+=2f*yMainRatio;
			bgxoffset+=1f*yMainRatio;
			mmv.repaint();
			try
			{
				Thread.sleep(SLEEPTIME);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			

			if(mmv.flag==1)
			{  
				syoffset+=60*yMainRatio;
				degrees=(float)(Math.toDegrees(syoffset))/1.3f;
			}

			if(mmv.flag==2)
			{
				xyoffset+=60*yMainRatio;
				degrees=(float)(Math.toDegrees(-xyoffset))/1.3f;
			}

			if(mmv.ckisTouch)
			{
				zyoffset+=30*yMainRatio;
			}
			if(mmv.isClose)
			{
				yyoffset+=30*yMainRatio;
			}
			try
			{
				Thread.sleep(15);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}