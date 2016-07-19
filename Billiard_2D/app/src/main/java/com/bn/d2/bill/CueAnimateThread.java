package com.bn.d2.bill;

public class CueAnimateThread extends Thread{
	GameView gameView;
	private boolean flag=true;
	private int sleepSpan=40;
	public CueAnimateThread(GameView gameView){
		this.gameView=gameView;
	}
	@Override
	public void run(){
		gameView.cue.setShowingAnimFlag(true);
		while(flag)
		{

			if(gameView.cue.changeDisWithBall() <= 0){
				gameView.cue.resetAnimValues();
				break;
			}
			try{
            	Thread.sleep(sleepSpan);
            }
            catch(Exception e){
            	e.printStackTrace();
            }
		}

		float v=Ball.vMax*(gameView.strengthBar.getCurrHeight()/gameView.strengthBar.getHeight());
		float angle=gameView.cue.getAngle();
		gameView.alBalls.get(0).changeVxy(v, angle);
		gameView.cue.setShowingAnimFlag(false);
		gameView.cue.setShowCueFlag(false);
		if(gameView.activity.isSoundOn()){
			gameView.playSound(GameView.SHOOT_SOUND, 0);
		}		
	}
}
