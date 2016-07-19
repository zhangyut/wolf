package com.bn.d2.bill;
/**
 *
 */
public class KeyThread extends Thread {
	private boolean flag=true;	
	GameView gameView;
	private int sleepSpan=40;
	private float changeSpeedTime=80f;
	public KeyThread(GameView gameView)
	{
		this.gameView=gameView;
	}
	@Override
	public void run()
	{
		while(flag)
		{
			if(!((gameView.keyState&0x20)==0))
			{
				gameView.btnPressTime+=3.5f;
			}
			if(!((gameView.keyState&0x1)==0))
			{
				if(gameView.btnPressTime<changeSpeedTime)
				{
					gameView.cue.rotateLeftSlowly();
				}
				else
				{
					gameView.cue.rotateLeftFast();
				}		
			}
			else if(!((gameView.keyState&0x2)==0))
			{
				if(gameView.btnPressTime<changeSpeedTime)
				{
					gameView.cue.rotateRightSlowly();
				}
				else
				{
					gameView.cue.rotateRightFast();
				}
			}
			try{
            	Thread.sleep(sleepSpan);
            }
            catch(Exception e){
            	e.printStackTrace();
            }
		}
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
