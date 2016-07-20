package com.bn.tag;


import java.util.List;
public class ShellThread extends Thread{
	GameView gv;			//GameView���������
	List<Shell> shells;	//Moster��������
	boolean flag;			//�߳��Ƿ�ִ�б�־λ
	boolean isGameOn;		//��Ϸ�Ƿ���б�־λ
	boolean whileflag=true;
	//int sleepSpan = AI_THREAD_SLEEP_SPAN;	//��Ϸ����ʱ�߳�ִ��ʱ��	
	public ShellThread(GameView gv,List<Shell> shellsjian){
		this.gv = gv;
		this.shells = shellsjian;
		flag = true;
		//isGameOn = true;
	}
	public void run(){		//�������߳�ִ�зŷ�
		while(whileflag){
			
			
			if(flag){
				synchronized(gv.shellsjian){
				for(Shell m:gv.shellsjian){		
					
					m.go();
				}				
				gv.shellsjian.removeAll(Shell.shl);
			
//				try{
//					Thread.sleep(20);
//				}
//				catch(Exception e){
//					e.printStackTrace();
//				}
			}
			}
			try{
				
				Thread.sleep(5);					//�߳̿�תʱ��
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