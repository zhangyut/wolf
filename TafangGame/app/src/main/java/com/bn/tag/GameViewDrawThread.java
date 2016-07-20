package com.bn.tag;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameViewDrawThread extends Thread{
	boolean flag = true;
	int sleepSpan = 50;
	GameView gameView;
	SurfaceHolder surfaceHolder;
	public GameViewDrawThread(GameView gameView){
		this.gameView = gameView;
		this.surfaceHolder = gameView.getHolder();
	}
	public void run(){
		Canvas c;
        while (flag) {
            c = null;
            try {

                c = this.surfaceHolder.lockCanvas(null);
                synchronized (this.surfaceHolder) {
                	gameView.onDraw(c);//����
                }
            } finally {
                if (c != null) {
                	//���ͷ���
                    this.surfaceHolder.unlockCanvasAndPost(c);
                }
            }
            try{
            	Thread.sleep(sleepSpan);//˯��ָ��������
            }
            catch(Exception e){
            	e.printStackTrace();//��ӡ��ջ��Ϣ
            }
        }
	}
	
	public void setFlag(boolean flag)
	{
		this.flag=flag;
	}
}
