package com.bn.tag;

import com.bn.tag.GameView;

/**
 * 
 *
 *
 */
public class IfGameOverThread extends Thread {
	GameView gameView;
	private boolean flag=true;
	private int sleepSpan=1000;		
	public IfGameOverThread(GameView gameView)
	{
		this.gameView=gameView;
	}
	@Override
	public void run()
	{
		while(flag)
		{			
			 try{
	            	Thread.sleep(sleepSpan);//˯��ָ��������
	            }
	            catch(Exception e){
	            	e.printStackTrace();//��ӡ��ջ��Ϣ
	            }
	            gameView.GameOver();
		}
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
