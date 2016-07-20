package com.bn.tag;


public class TimeThread extends Thread{
		GameView father;
		boolean flag=false;
		boolean whileflag=true;
		int sleepSpan=100;
		
		public TimeThread(GameView father){
			this.father = father;
			
		}
		//�̵߳�ִ�з���
		public void run(){
			while(whileflag){
				if(flag)
				{
					father.angle+=1;
					if(father.angle>=360)
					{
						father.angle=0;
						this.flag=false;
						father.shuijing_Flag=false;
					}
					
					
					father.changeShuijingXY(father.angle);
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
		public void setwhileflag(boolean whileflag)
		{
			this.whileflag=whileflag;
		}
	}