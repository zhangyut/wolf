package com.bn.d2.bill;
 
import java.util.ArrayList;

public class BallGoThread extends Thread 
{
	GameView gameView;

	private boolean flag=true;		

	ArrayList<Ball> ballsToDelete=new ArrayList<Ball>();
	private int sleepSpan=7;
	public BallGoThread(GameView gameView)
	{
		this.gameView=gameView;		
	}
	public void run()
	{
		while(flag)
		{
			ballsToDelete.clear();

			for(Ball b:gameView.alBalls){
				b.go();

				if(b.isInHole()){					
					if(b==gameView.alBalls.get(0)){
						b.hide();
					}
					else{
						ballsToDelete.add(b);
					}					
				}
			}
			gameView.alBalls.removeAll(ballsToDelete);

			boolean allBallsStoppedFlag=true;
			for(Ball b:gameView.alBalls){
				if(!b.isStoped()){
					allBallsStoppedFlag=false;
					break;
				}
			}
			if(allBallsStoppedFlag){
				if(gameView.alBalls.get(0).isHided()){
					gameView.alBalls.get(0).reset();
				}
				gameView.cue.setShowCueFlag(true);

				if(gameView.alBalls.size()<=1){
					gameView.overGame();
				}
			}

			try {
				Thread.sleep(sleepSpan);
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}