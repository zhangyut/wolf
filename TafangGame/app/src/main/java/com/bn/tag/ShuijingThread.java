package com.bn.tag;

public class ShuijingThread extends Thread{
	GameView gv;			//GameView���������	
	boolean flag;			//�߳��Ƿ�ִ�б�־λ		//��Ϸ�Ƿ���б�־λ
	boolean whileflag=true;
	//int sleepSpan = AI_THREAD_SLEEP_SPAN;	//��Ϸ����ʱ�߳�ִ��ʱ��	
	public ShuijingThread(GameView gv){
		this.gv = gv;		
		flag = false;
		//isGameOn = true;
	}
	public void run(){		//�������߳�ִ�зŷ�
		while(whileflag){
			
			if(flag){
				gv.alpha=gv.alpha-10;
				if(gv.alpha<0)
				{
					gv.alpha=200;
					gv.shuijing_D_Z_Flag=false;
					this.setFlag(false);
					
				}
			}
			try{
				
				Thread.sleep(50);					//�߳̿�תʱ��
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public void setwhileflag(boolean whileflag)
	{
		this.whileflag=whileflag;
	}		
}