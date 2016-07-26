package com.bn.box2d.sndls;         
import java.util.HashMap;
import android.app.Activity;    
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;   
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Window;   
import android.view.WindowManager;  
import static com.bn.box2d.sndls.Constant.*;

enum WhichView{GAME_VIEW,MAIN_MENU_VIEW};
public class MyBox2dActivity extends Activity 
{   
	WhichView curr;
	MainMenuView mmv;//���˵�����
	GameView gv;//��Ϸ����
	//��ȡSharedPreferences
	SharedPreferences sp;
	//��SharedPreferences��д������
    SharedPreferences.Editor editor;
    //������
    SoundUtil soundutil;
	HashMap<Integer, Integer> soundPoolMap;
	Handler hd=new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
				case 0:
					gotoGameView();					
				break;
				case 1:
					gotoMainMenuView();
				break;
			}
		}
	};
    public void onCreate(Bundle savedInstanceState) 
    {   
        super.onCreate(savedInstanceState);   
        //����Ϊȫ��
        requestWindowFeature(Window.FEATURE_NO_TITLE);   
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,   
        WindowManager.LayoutParams. FLAG_FULLSCREEN); 
        //����Ϊ����ģʽ
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		//��ȡ��Ļ�ߴ�
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);  
        if(dm.widthPixels>dm.heightPixels)
        {
        	 SCREEN_WIDTH=dm.widthPixels;
             SCREEN_HEIGHT=dm.heightPixels;
        }
        else
        {
        	SCREEN_WIDTH=dm.heightPixels;
            SCREEN_HEIGHT=dm.widthPixels;
        }
        //��ʼ��������Դ
        soundutil=new SoundUtil(this);
        soundutil.initSounds();   
        
        Constant.changeRatio();
        Constant.loadPic(this.getResources());
           
        //����ͼƬ��Դ
        Constant.initBitmap(getResources());
        //�õ�ͼƬ��λ��
        Constant.getLocaltionAndWH();  
        
        //�Ӽ�¼�м��ص÷�
        sp=this.getSharedPreferences("actm", Context.MODE_PRIVATE);
    	String lastStr=sp.getString("score", null);
    	if(lastStr!=null)
    	{
    		String[] sa=lastStr.split("\\|");
    		HH_SCORE=new int[sa.length];
    		HH_SCORE[0]=Integer.parseInt(sa[0]);
    		HH_SCORE[1]=Integer.parseInt(sa[1]);
    		HH_SCORE[2]=Integer.parseInt(sa[2]);
    	}    	
    	
        gotoMainMenuView();
    }
    //���뿪ʼ����
    public void gotoMainMenuView()
    {
		mmv=new MainMenuView(this);   
    	setContentView(mmv);
    	curr=WhichView.MAIN_MENU_VIEW;
    }
    //������Ϸ����
    public void gotoGameView()
    {
    	//ֹͣ�߳�
    	DRAW_THREAD_FLAG=true;
    	START_SCORE=false;
    	xOffset=0;
    	yOffset=0;
    	SCORE=0;
    	gv=new GameView(this);   
        setContentView(gv);
        curr=WhichView.GAME_VIEW;  
    }
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent e)
    {    	
    	if(keyCode==4)
    	{	
    		if(curr==WhichView.MAIN_MENU_VIEW)
    		{
    			sp=this.getSharedPreferences("actm", Context.MODE_PRIVATE);
    			SharedPreferences.Editor editor=sp.edit();
    			String str=HH_SCORE[0]+"|"+HH_SCORE[1]+"|"+HH_SCORE[2];
        		editor.putString("score", str);
        		editor.commit();
    			System.exit(0);
    		}
    		if(curr==WhichView.GAME_VIEW)
    		{
    			DRAW_THREAD_FLAG=false; 
    			gotoMainMenuView();
    		}
    	}    	
    	return true;
    }
}