package com.bn.d2.bill;
import android.view.SurfaceHolder;

public class GameViewDrawThread extends Thread{
	private boolean flag = true;	
	private int sleepSpan = 10;
	GameView gameView;
	SurfaceHolder surfaceHolder;
	public GameViewDrawThread(GameView gameView){
		this.gameView = gameView;
		this.surfaceHolder = gameView.getHolder();
	}
	public void run(){	
        while (this.flag) {
            gameView.repaint();
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
