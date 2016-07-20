package com.bn.tag;

import com.bn.tag.MainMenuSurfaceView;;

public class SelectThread extends Thread{
	MainMenuSurfaceView father;
	boolean flag;
	int sleepSpan = 100;//����ʱ��		
	public SelectThread(MainMenuSurfaceView father){
		this.father = father;
		//flag = true;
	}
	//�̵߳�ִ�з���
	public void run(){
		while(true){
			
			father.ballY+=30;
			if(father.ballY>=0){
				//father.status = 2;
				father.ballY=0;				
				break;
			}			
			try{
				Thread.sleep(sleepSpan);
			}										//�߳�����
			catch(Exception e){
				e.printStackTrace();
			}										//���񲢴�ӡ�쳣
		}
	}
	
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}