package com.bn.box2d.sndls;

import java.util.HashMap;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundUtil
{
	SoundPool soundPool;
	HashMap<Integer, Integer> soundPoolMap;
	MyBox2dActivity activity;
	public SoundUtil(MyBox2dActivity activity)
	{
		this.activity=activity;
	}

    public void initSounds()
    {
    	 //�������������
	     soundPool = new SoundPool
	     (
	    		 6, 							//ͬʱ����ಥ�ŵĸ���
	    		 AudioManager.STREAM_MUSIC,     //��Ƶ������
	    		 100							//�����Ĳ���������Ŀǰ��Ч
	     );
	     
	     //����������ԴMap	     
	     soundPoolMap = new HashMap<Integer, Integer>();   
	     //�����ص�������Դid�Ž���Map
	     soundPoolMap.put(0, soundPool.load(activity, R.raw.pijin, 1));//Ƥ�����������
	     soundPoolMap.put(1, soundPool.load(activity, R.raw.ls_fx, 1));//�����
	     soundPoolMap.put(2, soundPool.load(activity, R.raw.wood, 1));//ľͷ
	     soundPoolMap.put(3, soundPool.load(activity, R.raw.boli, 1));//����
	     soundPoolMap.put(4, soundPool.load(activity, R.raw.stond, 1));//ʯͷ
	     soundPoolMap.put(5, soundPool.load(activity, R.raw.cat, 1));//Сè
	     //�м�����Ч���е�ǰ�������  R.raw.gamestart���ر�� ����     �����1Ϊ���ȼ� Ŀǰ������
	} 
       
   //���������ķ���
   public void playSound(int sound, int loop) {
	   if(!activity.mmv.syisTouch)
	   {
		   AudioManager mgr = (AudioManager)activity.getSystemService(Context.AUDIO_SERVICE);
		    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);//��ǰ����   
		    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//�������       
		    float volume = streamVolumeCurrent / streamVolumeMax;   
		    
		    soundPool.play
		    (
	    		soundPoolMap.get(sound), //������Դid
	    		volume, 				 //����������
	    		volume, 				 //����������
	    		1, 						 //���ȼ�				 
	    		loop, 					 //ѭ������ -1������Զѭ��
	    		1f					 //�ط��ٶ�0.5f��2.0f֮��
		    );
	   }
	}
}