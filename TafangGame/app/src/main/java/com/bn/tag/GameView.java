package com.bn.tag;


import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import static com.bn.tag.Constant.*;


public class GameView extends SurfaceView implements SurfaceHolder.Callback{

	TafangGameActivity activity;
	SingleJianta tartar;
	
	SurfaceHolder holder;
	TargetThread targetTH;
	IfGameOverThread ifgameoverTh;
	TimeThread timeTh;//ˮ������CD���߳�
	ShuijingThread shuijingTh;//ˮ������
	ShellThread shellTh;
	CaidanThread caidanth;//�����˵��ĶԻ����߳�
	homeThread homeTh;	
	
	float caidan_x=CAIDAN_GAME_START_X;
	float caidan_y=-340;
	float scale=1.0f;
	ShellNumThread shellNuTh;
	Paint paint;//��������
	Bitmap background; //����
	Bitmap shengming;
	Bitmap shengming01;//����Ѫ�۵ĵ�ɫ
	Bitmap gonglu;
	Bitmap smalltree;
	Bitmap shuijing;
	Bitmap bigtree;
	Bitmap flower;
	Bitmap jianta;
	Bitmap jianta1;
	Bitmap money;
	Bitmap blood;
	Bitmap shadipic;//ɱ����ͼƬ
	Bitmap tibiao1;
	Bitmap tibiao2;
	Bitmap tubiao1;
	Bitmap canlocationpic;		
	Bitmap shuijing_cover;
	Bitmap shuijingZCZ;//ˮ������
	Bitmap candoit;//������ǰ�����Ĵ���
	boolean candoiflag=false;
	Bitmap homelo;//��Ϸ�ҵ�λ��
	Bitmap candan;//��Ϸ���������½ǵĲ˵�ͼ��
	Bitmap caidanbi;//�����˵���ͼƬ
	GameViewDrawThread gameViewDrawThread;
	List<Target> alTarget;//Ŀ�����
	Bitmap target1Bitmap;
	Bitmap[] iscore=new Bitmap[10];
	Bitmap[][] targettop=new Bitmap[3][4];
	Bitmap[][] targetbottom=new Bitmap[3][4];
	Bitmap[][] targetstraight=new Bitmap[3][4];
	int score=20;
	int doller=45;
	int bloodNUM=10;//��ǰѪ��
	int shuijingNUM=1;
	int shuijingMiddleNum=25;
	int shaNUM=0;
	int alpha=200;//��ǰˮ�����е�ͼƬ��͸����	
	float scoreWidth = 16;//�������ֵ�ͼƬ�Ŀ��
	float shuijing_change_x=SHUIJIAN_STARTX;
	float shuijing_change_y=SHUIJIAN_STARTY;
	TargetNumThread TargetNum;
	List<Target> alTarget1=new Vector<Target>();//�����б�
	List<SingleJianta> jiantaList=new Vector<SingleJianta>();
	List<SingleJianta> jiantamidd=new Vector<SingleJianta>();
	
	boolean taflag1;//����1�İ��±�־λ
	boolean taflag2;//����2�İ��±�־λ
	boolean caidanflag;//��Ϸ�����в˵�ͼ�갴�±�־λ
	boolean shuijing_Flag=false;//����ˮ��CD�ı�־λ	
	boolean shuijing_D_Z_Flag=false;
	float middleballx;
	float middlebally;
	Shell shell;
	//���϶������߶������еĶ�������
	//�����϶�
	SingleGoJianta sg;
	SingleGoJianta sgsg;
	//�жϵ�ǰλ��Ϊ�ɷźͲ��ɷŵı�־λ
	CanOrIndexPic canLocation;
	CanOrIndexPic candoitit;
	//�������
	List<Shell> shellsjian=new Vector<Shell>();
	//������ر���
	SoundPool soundPool;//����
	HashMap<Integer, Integer> soundPoolMap; 
	MediaPlayer mMediaPlayer;
	int angle=0;

	public GameView(TafangGameActivity activity) {
		super(activity);
		getHolder().addCallback(this);//ע��ص��ӿ�
		this.activity = activity;
		initBitmap();
		
		sg=new SingleGoJianta(this, jianta);
		sgsg=new SingleGoJianta(this,jianta1);
		canLocation=new CanOrIndexPic(this,canlocationpic);		
		candoitit=new CanOrIndexPic(this,candoit);
		paint = new Paint();//��������
    	paint.setAntiAlias(true);//�򿪿����
    	paint.setStrokeWidth(1);//����·�߻��ƿ��
    	if(activity.isSoundOn())
    	{
    		initSounds(activity);
    	}
    	//homelo=((BitmapDrawable)getResources().getDrawable(R.drawable.home)).getBitmap();
	}
	
	@Override
	public void onDraw(final Canvas canvas) {
		super.onDraw(canvas); 
		canvas.drawBitmap(background, 0,0, null);		
		if(activity.cundang_Flag)
		{
			List<Integer> list01=new Vector<Integer>();
			list01=DBUtil.searjianta(activity.name);
			int[] nochange1=new int[4];
			nochange1=DBUtil.searchnochange(activity.name);
			doller=nochange1[0];//��Ǯ
			bloodNUM=nochange1[1];//����
			shaNUM=nochange1[2];
			shuijingMiddleNum=nochange1[3];
			
									
			for(int i=0;i<list01.size();i+=3)
			{
				if(list01.get(i+2)==130)
				{
					jiantaList.add(new SingleJianta(this,jianta,list01.get(i),list01.get(i+1),list01.get(i+2)));
				}
				if(list01.get(i+2)==100)
				{
					jiantaList.add(new SingleJianta(this,jianta1,list01.get(i),list01.get(i+1),list01.get(i+2)));
				}
			}
			list01.clear();
			list01=DBUtil.searchguaiwu(activity.name);
			for(int i=0;i<list01.size();i+=7)
			{				
				if(list01.get(i+2)==GW_STATE01)
				{
					alTarget1.add(new Target(this,target1Bitmap,list01.get(i),list01.get(i+1),list01.get(i+5),list01.get(i+6),0,list01.get(i+3)*Math.PI/180,list01.get(i+4)));
				}
				if(list01.get(i+2)==GW_STATE02)
				{
					alTarget1.add(new Target(this,target1Bitmap,list01.get(i),list01.get(i+1),list01.get(i+5),list01.get(i+6),1,list01.get(i+3)*Math.PI/180,list01.get(i+4)));
				}
				if(list01.get(i+2)==GW_STATE03)
				{
					alTarget1.add(new Target(this,target1Bitmap,list01.get(i),list01.get(i+1),list01.get(i+5),list01.get(i+6),2,list01.get(i+3)*Math.PI/180,list01.get(i+4)));
				}				
				
			}
			activity.cundang_Flag=false;
			
		}
		else 
		{
			
		}
		
		for(int i=0;i<12;i++)
		{
			for(int j=0;j<20;j++)
			{
				if(MAP[i][j]==1)
        		{
        			canvas.drawBitmap(gonglu, GONGLU_WEIGHT*j,GONGLU_HEIGHT*i, null);
        		}
			}
		}
		//����Matrix����
		Matrix mMatrix=new Matrix();
		mMatrix.reset();
		mMatrix.postScale(1,scale);
		//scale=1.5f;
		Bitmap homelolo=Bitmap.createBitmap(homelo, 0, 0, homelo.getWidth(), homelo.getHeight(), mMatrix, true);
		//���Ƽ�
		canvas.drawBitmap(homelolo, HOME_X,HOME_Y-(scale-1)*HOME_HEIGHT, null);
		for(int i=0;i<12;i++)
        {
        	for(int j=0;j<20;j++)
        	{       		
        		if(MAP[i][j]==2)
        		{
        			canvas.drawBitmap(smalltree, TREE_WEIGHT*j,TREE_HEIGHT*i, null);
        		}
        		else if(MAP[i][j]==3)
        		{
        			canvas.drawBitmap(bigtree, BIG_TREE_WEIGHT*j,GONGLU_HEIGHT*i-BIG_TREE_HEIGHT+GONGLU_HEIGHT, null);
        		}
        		else if(MAP[i][j]==4)
        		{
        			canvas.drawBitmap(flower, FLOWER_WEIGHT*j,FLOWER_HEIGHT*i, null);
        		}
        		//���Ƽ���
        		synchronized (jiantaList){
        		for(SingleJianta sj:jiantaList)
    			{
    				if(sj.clo==j&&sj.row==i)
    				{
    					sj.drawSelf(canvas, paint);    					
    				}       			
    			}
        		}
        		//���ƹ���
        		synchronized (alTarget1)
        		{
        		for(Target tar:alTarget1)
    			{
        			if((int)(tar.ballx/SINGLE_RODER)==j&&(int)(tar.bally/SINGLE_RODER)==i)
        			{
        				
        				tar.drawSelf(canvas, paint);
        			}
    			}
        		}
        	}
        }

    	
		//�������
		
		//List<Shell> shellsjian1=new Vector<Shell>(shellsjian);
		//shellsjian1.addAll(shellsjian);
		synchronized (shellsjian){
			for(Shell sh:shellsjian)
			{
				sh.drawSelf(canvas, paint);
			}  
		}	
		 	
    	//���ƽ�Ǯ
		//doller=doller+(10-alTarget11.size());
    	canvas.drawBitmap(money, 40,5, null);
    	String dollerStr=doller+"";
    	int length=dollerStr.length();   	
    	for(int i=0;i<length;i++){
    		int temp=dollerStr.charAt(i)-'0';
    		canvas.drawBitmap(iscore[temp], 50+MONEY_WEIGHT+i*scoreWidth,15, null);
    	}
    	
    	//��������
    	canvas.drawBitmap(blood, 70+MONEY_WEIGHT+5*scoreWidth,5, null);
    	String bloodStr=bloodNUM+"";
    	int lengthbl=bloodStr.length();   	
    	for(int i=0;i<lengthbl;i++){
    		int temp=bloodStr.charAt(i)-'0';
    		canvas.drawBitmap(iscore[temp], BLOOD_WEIGHT+80+MONEY_WEIGHT+(5+i)*scoreWidth,15, null);
    	}
    	//����ɱ����
    	canvas.drawBitmap(shadipic, 170+MONEY_WEIGHT+5*scoreWidth+BLOOD_WEIGHT,5, null);
    	String shadiStr=shaNUM+"";
    	int lengthsha=shadiStr.length();   	
    	for(int i=0;i<lengthsha;i++){
    		int temp=shadiStr.charAt(i)-'0';
    		canvas.drawBitmap(iscore[temp], 150+BLOOD_WEIGHT+80+MONEY_WEIGHT+(5+i)*scoreWidth,15, null);
    	}
    	
    	//����ˮ��
    	canvas.drawBitmap(shuijing, SHUIJIAN_STARTX,SHUIJIAN_STARTY, null);
    	shuijingNUM=shuijingMiddleNum/25;
    	String shuijingStr=shuijingNUM+"";
    	int lengthshui=shuijingStr.length();   	
    	for(int i=0;i<lengthshui;i++){
    		int temp=shuijingStr.charAt(i)-'0';
    		canvas.drawBitmap(iscore[temp], SHUIJIAN_STARTX+(4+i)*scoreWidth,15, null);
    	}
    	//����ˮ��CD����
    	if(shuijing_Flag)
    	{
    		canvas.save();
        	canvas.clipPath(makePathDash(shuijing_change_x,shuijing_change_y));
        	canvas.drawBitmap(shuijing_cover, SHUIJIAN_STARTX,SHUIJIAN_STARTY, null);
        	//canvas.drawBitmap(Yinxiaooff, 0, 0, null);
        	canvas.restore();	
    	}
    	
    	
    	//����ͼ����������ͼ��
    	canvas.drawBitmap(tibiao2, SCREEN_HEIGHT-LEVEL_WEIGHT-100,SCREEN_WIDTH-TIBIAO_HEIGHT-10, null);
    	canvas.drawBitmap(tibiao1, SCREEN_HEIGHT-LEVEL_WEIGHT-10,SCREEN_WIDTH-TIBIAO_HEIGHT-10, null);
    	//��Ϸ���������½ǵĲ˵�ͼ��
    	canvas.drawBitmap(candan, 20,SCREEN_WIDTH-CANDAN_HEIGHT-10, null);
    	//���Ʋ���������ͼ��
    	if(candoiflag)
    	{
    		candoitit.drawSelf(canvas, paint);
    	}
    	//���Ƶ����˵�
    	if(caidanflag)
    	{
    		caidanth.setFlag(true); //�˵�ѡ���־λ��true
    		canvas.drawBitmap(caidanbi, caidan_x,caidan_y, null);
    		//caidanflag=false;
    	} 
    	
    	
    	//�������϶�����ʱ��������
    	if(taflag1||taflag2)
    	{
    		for(int i=0;i<20;i++)
    		{
    				float xStart=SINGLE_RODER*i;
    				float yStart=0;
    				float xEnd1=SINGLE_RODER*i;
    				float yEnd1=SINGLE_RODER*12;
    				paint.setARGB(255,255,255,255);
    				canvas.drawLine(xStart, yStart, xEnd1, yEnd1, paint);   			
    		}
    		for(int i=0;i<12;i++)
    		{
    			float yStart=SINGLE_RODER*i;
				float xStart=0;
				float yEnd1=SINGLE_RODER*i;
				float xEnd1=SINGLE_RODER*20;
				paint.setARGB(255,255,255,255);
				canvas.drawLine(xStart, yStart, xEnd1, yEnd1, paint); 
    		}
    		if(canLocation.ifdraw==true)
    		{
    			canLocation.drawSelf(canvas, paint);
    		}    		
    		if(taflag1)
    		{
    			sg.drawSelf(canvas, paint);
    		}
    		else if(taflag2)
    		{
    			sgsg.drawSelf(canvas, paint);
    		}
    	}
    	
    	if(shuijing_D_Z_Flag)
    	{
    		paint.setAlpha(alpha);
    		canvas.drawBitmap(shuijingZCZ, 0,0, paint);
    		paint.setAlpha(255);
    	}
    	
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
			
		if(event.getAction() == MotionEvent.ACTION_DOWN)
        {  
			int x = (int) event.getX();
			int y = (int) event.getY();	
			int temppx=(int) (x/SINGLE_RODER);
			int temppy=(int) (y/SINGLE_RODER);
			//SingleJianta tartar = null;
			//�Ƿ�ȡ����ǰ����
			for(int i=0;i<jiantaList.size();i++)
			{
				if(jiantaList.get(i).clo==temppx&&jiantaList.get(i).row==temppy&&candoiflag==false)
				{
					candoiflag=true;
					tartar=jiantaList.get(i);
//					System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
//					System.out.println()
					
					candoitit.getXY(x, y);
					return true;
				}
			}
			//ˮ��
			if(shuijingNUM>0&&x>SHUIJIAN_STARTX&&x<SHUIJIAN_STARTX+SHUIJING_WEIGHT&&y>SHUIJIAN_STARTY&&y<SHUIJIAN_STARTY+SHUIJIAN_HEIGHT)
			{
				alTarget1.clear();
				shuijingMiddleNum-=25;
				timeTh.setFlag(true);				
				this.shuijing_Flag=true;//	
				this.shuijing_D_Z_Flag=true;
				shuijingTh.setFlag(true);
				//shuijingNUM-=1;
			}
			//ȡ��ѡ�еļ���
			if(tartar!=null&&candoiflag==true&&x>tartar.clo*SINGLE_RODER-40&&x<(tartar.clo+1)*SINGLE_RODER&&y>tartar.row*SINGLE_RODER-40&&y<(tartar.row+1)*SINGLE_RODER+40)
			{				
				jiantaList.remove(tartar);
				this.doller+=5;
				candoiflag=false;
				return true;
			}
			if(doller>=25&&x>SCREEN_HEIGHT-LEVEL_WEIGHT-100&&x<SCREEN_HEIGHT-LEVEL_WEIGHT-100+TIBIAO_WEIGHT
        	  &&y>SCREEN_WIDTH-TIBIAO_HEIGHT-10&&y<SCREEN_WIDTH-TIBIAO_HEIGHT-10+TIBIAO_HEIGHT&&caidanflag==false)
        	{
        		//����ͼ��1
        		taflag1=true;
        		sg.getXY(x, y);        		
        		return true;
        	}
        	if(doller>=15&&x>SCREEN_HEIGHT-LEVEL_WEIGHT-10&&x<SCREEN_HEIGHT-LEVEL_WEIGHT-10+TIBIAO_WEIGHT
              	  &&y>SCREEN_WIDTH-TIBIAO_HEIGHT-10&&y<SCREEN_WIDTH-TIBIAO_HEIGHT-10+TIBIAO_HEIGHT&&caidanflag==false)
              	{
              		//����ͼ��2
              		taflag2=true;
              		sgsg.getXY(x, y);
              		//return true;
              		return true;
              	}
        	if(x>CANDAN_WEIGHT-30&&x<CANDAN_WEIGHT-30+CANDAN_WEIGHT&&y>SCREEN_WIDTH-CANDAN_HEIGHT-10&&y<SCREEN_WIDTH-10)
        	{
        		caidanflag=true;
        		stopworkAllThreads();
        		stophuanzhen();
        		return true;
        	}
        	//�����˵��ı�����Ϸ��ͼ��λ��
        	if(caidanflag==true&&x>CAIDAN_GAME_START_X+SAVE_GAME_X&&x<SAVE_GAME_X+TANCHU_CAIDAN_WEIGHT+CAIDAN_GAME_START_X&&
        	   y>SAVE_GAME_Y&&y<SAVE_GAME_Y+TANCHU_CAIDAN_HEIGHT	
        	)
        	{
        		activity.gototachu();
        	}
        	//�����˵��Ļص���Ϸ��ͼ��λ��
        	if(caidanflag==true&&x>CAIDAN_GAME_START_X+GOBACK_GAME_X&&x<GOBACK_GAME_X+TANCHU_CAIDAN_WEIGHT+CAIDAN_GAME_START_X&&
               y>GOBACK_GAME_Y&&y<GOBACK_GAME_Y+TANCHU_CAIDAN_HEIGHT	
           	)
          	{
        		caidanflag=false;
        		startworkAllThreads();
        		caidanth.setFlag(false);
        		starthuanzhen();//����������֡
        		
        		caidan_x=CAIDAN_GAME_START_X;
        		caidan_y=-340;
        		return true;
          	}
        	//�����˵����˳���Ϸ��ͼ��λ��
        	if(caidanflag==true&&x>CAIDAN_GAME_START_X+EXIT_GAME_X&&x<EXIT_GAME_X+TANCHU_CAIDAN_WEIGHT+CAIDAN_GAME_START_X&&
             	y>EXIT_GAME_Y&&y<EXIT_GAME_Y+TANCHU_CAIDAN_HEIGHT	
         	)
        	{      		
        		try
        		{
        			activity.insertScoreAndDate(shaNUM);
        			stopAllThreads();
        			if(mMediaPlayer.isPlaying())
    		    	{
    		        	mMediaPlayer.stop();
    		    	}
        			activity.sendMessage(1);//(2);
        			
        		}catch(Exception e)
        		{
        		   e.printStackTrace();
        		}        		        		
        		caidanflag=false;
        		 return true;      		
         	}        	
        	candoiflag=false;        	
        }
    	else if (event.getAction() == MotionEvent.ACTION_MOVE) 
    	{
    		int x = (int) event.getX();
    		int y = (int) event.getY();	
    		if(taflag1==true||taflag2==true)
    		{
    			middleballx=x;
    			middlebally=y;
    			sg.getXY(x, y);
    			sgsg.getXY(x, y);
    			if(MAP[(int)(y/SINGLE_RODER)][(int)(x/SINGLE_RODER)]==0)
    			{
    				canLocation.ifdraw=true;
    				//notLocation.ifdraw=false;
    				canLocation.getXY(x, y);        			
    			}
    			else 
    			{
    				//notLocation.ifdraw=true;
    				canLocation.ifdraw=false;
    				//notLocation.getXY(x, y);
    			}
    			return true;   			
    		}
    		else 
    		{
    			return true;
    		}
    		
    	}    		
        else if (event.getAction() == MotionEvent.ACTION_UP) 
        {
        	int x = (int) event.getX();
    		int y = (int) event.getY();	
        	int xx=(int) (x/SINGLE_RODER);
        	int yy=(int)(y/SINGLE_RODER);       
        	if(MAP[yy][xx]==0&&taflag1==true||MAP[yy][xx]==0&&taflag2==true)
        	{
        		if(taflag1==true)
        		{
        			jiantaList.add(new SingleJianta(this,jianta,xx,yy,130));
        			doller=doller-25;
                	taflag1=false;
        		}
        		else if(taflag2==true)
        		{
        			jiantaList.add(new SingleJianta(this,jianta1,xx,yy,100));
        			doller=doller-15;
        			taflag2=false;
        		}        		           	
            	//return true;
        	}        	
         	taflag1=false;
         	taflag2=false;
          	CanOrIndexPic.ifdraw=false;
         	return true;
        }
		return false;
	}	

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}
	public void surfaceCreated(SurfaceHolder holder) {//����ʱ������Ӧ����
        //gameViewDrawThread.setFlag(true);
		
		gameViewDrawThread = new GameViewDrawThread(this);        
        gameViewDrawThread.start();
        timeTh=new TimeThread(this);
        timeTh.start();
        alTarget=new Vector<Target>();
        TargetNum=new TargetNumThread(this);
        TargetNum.start();
        targetTH=new TargetThread(this);
        targetTH.start();
//        middTh=new MiddleThread(this);
//        middTh.start();
        shellTh=new ShellThread(this,shellsjian);
        shellTh.start();
        shellNuTh=new ShellNumThread(this);
        shellNuTh.start();
        caidanth=new CaidanThread(this);
        caidanth.start();
        homeTh=new homeThread(this);
        homeTh.start();
        ifgameoverTh=new IfGameOverThread(this);
        ifgameoverTh.start();      
        shuijingTh=new ShuijingThread(this);
        shuijingTh.start();
        
      //��ʼ����������
		mMediaPlayer = MediaPlayer.create(activity, R.raw.background);
		mMediaPlayer.setLooping(true);
		if(activity.isBackGroundMusicOn())//������������
		{
			mMediaPlayer.start();
		}       
	}
	public void surfaceDestroyed(SurfaceHolder holder) {//�ݻ�ʱ�ͷ���Ӧ����
        boolean retry = true;
        //gameViewDrawThread.setFlag(false);// =  false;
        stopAllThreads();
      //ֹͣ��������
        if(mMediaPlayer.isPlaying())
        {
        	mMediaPlayer.stop();
        }
	}
	
   //ֹͣ���й��ﶯ����֡
	void stophuanzhen()
	{
		for(int i=0;i<alTarget1.size();i++)
		{
			alTarget1.get(i).setWorkFlag(false);
		}
	}
	//�������й��ﻻ֡������־λ
	void starthuanzhen()
	{
		for(int i=0;i<alTarget1.size();i++)
		{
			alTarget1.get(i).setWorkFlag(true);
		}
	}
	void stopAllThreads()
	{
		gameViewDrawThread.setFlag(false);
		TargetNum.setwhileflag(false);//(false);
		targetTH.setwhileflag(false);
		shellTh.setwhileflag(false);
		shellNuTh.setwhileflag(false);
		caidanth.setwhileflag(false);//(false);
		homeTh.setwhileflag(false);
		ifgameoverTh.setFlag(false);
		timeTh.setwhileflag(false);
		shuijingTh.setwhileflag(false);
		
	}
	//ֹͣ�������߳�
	void stopworkAllThreads()
	{
		//gameViewDrawThread.setFlag(false);
		TargetNum.setFlag(false);
		targetTH.setFlag(false);
		shellTh.setFlag(false);
		shellNuTh.setFlag(false);
		homeTh.setFlag(false);
		timeTh.setFlag(false);
		
		//caidanth.setFlag(false);
	}
	//���������߳�
	void startworkAllThreads()
	{
		//gameViewDrawThread.setFlag(false);
		TargetNum.setFlag(true);
		targetTH.setFlag(true);
		shellTh.setFlag(true);
		shellNuTh.setFlag(true);
		homeTh.setFlag(true);
		timeTh.setFlag(true);
		//caidanth.setFlag(true);
	}
	
	//��ʼ�������ķ���
	public void initSounds(TafangGameActivity activity){
	     soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
	     soundPoolMap = new HashMap<Integer, Integer>();   	     
	     soundPoolMap.put(2, soundPool.load(getContext(), R.raw.explode, 1));
	}
	//���������ķ���
	public void playSound(int sound, int loop) {
	    AudioManager mgr = (AudioManager)getContext().getSystemService(Context.AUDIO_SERVICE);   
	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   
	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       
	    float volume = streamVolumeCurrent / streamVolumeMax;   	    
	    soundPool.play(soundPoolMap.get(sound), volume, volume, 1, loop, 1f);
	}
	
	public void initBitmap(){
		background=BitmapFactory.decodeResource(this.getResources(), R.drawable.back);
		gonglu=BitmapFactory.decodeResource(this.getResources(), R.drawable.gonglu);
		jianta=BitmapFactory.decodeResource(this.getResources(), R.drawable.jianta);		
		iscore[0] = BitmapFactory.decodeResource(getResources(), R.drawable.d0);
		iscore[1] = BitmapFactory.decodeResource(getResources(), R.drawable.d1);
		iscore[2] = BitmapFactory.decodeResource(getResources(), R.drawable.d2);
		iscore[3] = BitmapFactory.decodeResource(getResources(), R.drawable.d3);
		iscore[4] = BitmapFactory.decodeResource(getResources(), R.drawable.d4);
		iscore[5] = BitmapFactory.decodeResource(getResources(), R.drawable.d5);
		iscore[6] = BitmapFactory.decodeResource(getResources(), R.drawable.d6);
		iscore[7] = BitmapFactory.decodeResource(getResources(), R.drawable.d7);
		iscore[8] = BitmapFactory.decodeResource(getResources(), R.drawable.d8);
		iscore[9] = BitmapFactory.decodeResource(getResources(), R.drawable.d9);
		money=BitmapFactory.decodeResource(this.getResources(), R.drawable.money);
		blood=BitmapFactory.decodeResource(this.getResources(), R.drawable.blood);
		tibiao1=BitmapFactory.decodeResource(this.getResources(), R.drawable.biao1);
		tibiao2=BitmapFactory.decodeResource(this.getResources(), R.drawable.biao2);
		
		targetbottom[0][0]=BitmapFactory.decodeResource(this.getResources(), R.drawable.b01);
		targetbottom[0][1]=BitmapFactory.decodeResource(this.getResources(), R.drawable.b02);
		targetbottom[0][2]=BitmapFactory.decodeResource(this.getResources(), R.drawable.b03);
		targetbottom[0][3]=BitmapFactory.decodeResource(this.getResources(), R.drawable.b04);
		targetbottom[1][0]=BitmapFactory.decodeResource(this.getResources(), R.drawable.c01);
		targetbottom[1][1]=BitmapFactory.decodeResource(this.getResources(), R.drawable.c02);
		targetbottom[1][2]=BitmapFactory.decodeResource(this.getResources(), R.drawable.c03);
		targetbottom[1][3]=BitmapFactory.decodeResource(this.getResources(), R.drawable.c04);
		targetbottom[2][0]=BitmapFactory.decodeResource(this.getResources(), R.drawable.d01);
		targetbottom[2][1]=BitmapFactory.decodeResource(this.getResources(), R.drawable.d02);
		targetbottom[2][2]=BitmapFactory.decodeResource(this.getResources(), R.drawable.d03);
		targetbottom[2][3]=BitmapFactory.decodeResource(this.getResources(), R.drawable.d04);
		
		
		targetstraight[0][0]=BitmapFactory.decodeResource(this.getResources(), R.drawable.b05);
		targetstraight[0][1]=BitmapFactory.decodeResource(this.getResources(), R.drawable.b06);
		targetstraight[0][2]=BitmapFactory.decodeResource(this.getResources(), R.drawable.b07);
		targetstraight[0][3]=BitmapFactory.decodeResource(this.getResources(), R.drawable.b08);
		targetstraight[1][0]=BitmapFactory.decodeResource(this.getResources(), R.drawable.c05);
		targetstraight[1][1]=BitmapFactory.decodeResource(this.getResources(), R.drawable.c06);
		targetstraight[1][2]=BitmapFactory.decodeResource(this.getResources(), R.drawable.c07);
		targetstraight[1][3]=BitmapFactory.decodeResource(this.getResources(), R.drawable.c08);
		targetstraight[2][0]=BitmapFactory.decodeResource(this.getResources(), R.drawable.d05);
		targetstraight[2][1]=BitmapFactory.decodeResource(this.getResources(), R.drawable.d06);
		targetstraight[2][2]=BitmapFactory.decodeResource(this.getResources(), R.drawable.d07);
		targetstraight[2][3]=BitmapFactory.decodeResource(this.getResources(), R.drawable.d08);
		
		targettop[0][0]=BitmapFactory.decodeResource(this.getResources(), R.drawable.b09);
		targettop[0][1]=BitmapFactory.decodeResource(this.getResources(), R.drawable.b10);
		targettop[0][2]=BitmapFactory.decodeResource(this.getResources(), R.drawable.b11);
		targettop[0][3]=BitmapFactory.decodeResource(this.getResources(), R.drawable.b12);
		targettop[1][0]=BitmapFactory.decodeResource(this.getResources(), R.drawable.c09);
		targettop[1][1]=BitmapFactory.decodeResource(this.getResources(), R.drawable.c10);
		targettop[1][2]=BitmapFactory.decodeResource(this.getResources(), R.drawable.c11);
		targettop[1][3]=BitmapFactory.decodeResource(this.getResources(), R.drawable.c12);
		targettop[2][0]=BitmapFactory.decodeResource(this.getResources(), R.drawable.d09);
		targettop[2][1]=BitmapFactory.decodeResource(this.getResources(), R.drawable.d10);
		targettop[2][2]=BitmapFactory.decodeResource(this.getResources(), R.drawable.d11);
		targettop[2][3]=BitmapFactory.decodeResource(this.getResources(), R.drawable.d12);
		
		bigtree=BitmapFactory.decodeResource(this.getResources(), R.drawable.tree02);
		smalltree=BitmapFactory.decodeResource(this.getResources(), R.drawable.tree01);
		flower=BitmapFactory.decodeResource(this.getResources(), R.drawable.flower01);
		tubiao1=BitmapFactory.decodeResource(this.getResources(), R.drawable.tubiao);
		canlocationpic=BitmapFactory.decodeResource(this.getResources(), R.drawable.canlocate);		
		candan=BitmapFactory.decodeResource(this.getResources(), R.drawable.caidan);
		caidanbi=BitmapFactory.decodeResource(this.getResources(), R.drawable.caidanbig);
		shadipic=BitmapFactory.decodeResource(this.getResources(), R.drawable.shidishu);
		jianta1=BitmapFactory.decodeResource(this.getResources(), R.drawable.jianta1);
		homelo=BitmapFactory.decodeResource(this.getResources(), R.drawable.home);
		shuijing=BitmapFactory.decodeResource(this.getResources(), R.drawable.shuijing);
		shuijingZCZ=BitmapFactory.decodeResource(this.getResources(), R.drawable.dazhao);
		//��ù��������Ѫ��
		shengming=BitmapFactory.decodeResource(this.getResources(), R.drawable.shengming);
		shengming01=BitmapFactory.decodeResource(this.getResources(), R.drawable.shengming01);
		candoit=BitmapFactory.decodeResource(this.getResources(), R.drawable.cando);
		shuijing_cover=BitmapFactory.decodeResource(this.getResources(), R.drawable.shuijingcover);						
		
	}	
	
	//��Ϸ�����ķ���
	public void GameOver()
	{
		
		if(bloodNUM==0)
		{
			stopAllThreads();
			 if(mMediaPlayer.isPlaying())
		    	{
		        	mMediaPlayer.stop();
		    	}
			activity.insertScoreAndDate(shaNUM);
			activity.sendMessage(5);
			
		}
	}
	
	//�������ڻ������ߵĵ�Ԫ·���ķ���
	private  Path makePathDash(float x6,float y6) {
        Path p = new Path();
        //����δӾ����м�㿪ʼ˳ʱ��
        float x1=SHUIJIAN_STARTX+SHUIJING_WEIGHT/2;
        float y1=SHUIJIAN_STARTY+SHUIJIAN_HEIGHT/2;
        
        float x2=SHUIJIAN_STARTX+SHUIJING_WEIGHT;
        float y2=SHUIJIAN_STARTY;
        
        float x3=SHUIJIAN_STARTX+SHUIJING_WEIGHT;
        float y3=SHUIJIAN_STARTY+SHUIJIAN_HEIGHT;
        
        float x4=SHUIJIAN_STARTX;
        float y4=SHUIJIAN_STARTY+SHUIJIAN_HEIGHT;
        
        float x5=SHUIJIAN_STARTX;
        float y5=SHUIJIAN_STARTY;
        if(y6==SHUIJIAN_STARTY)
        {
        	p.moveTo(x1, y1);
            p.lineTo(x6, y6);
            p.lineTo(x2, y2);
            p.lineTo(x3, y3);
            p.lineTo(x4, y4);
            p.lineTo(x5, y5);
            p.lineTo(x1, y1);
            return p;
        }
        else if(x6==SHUIJIAN_STARTX+SHUIJING_WEIGHT)
        {
        	p.moveTo(x1, y1);
            p.lineTo(x6, y6);            
            p.lineTo(x3, y3);
            p.lineTo(x4, y4);
            p.lineTo(x5, y5);
            p.lineTo(x1, y1);
            return p;
        }
        else if(y6==SHUIJIAN_STARTY+SHUIJIAN_HEIGHT)
        {
        	p.moveTo(x1, y1);
            p.lineTo(x6, y6);            
            p.lineTo(x4, y4);
            p.lineTo(x5, y5);
            p.lineTo(x1, y1);
            return p;
        }
        else if(x6==SHUIJIAN_STARTX)
        {
        	p.moveTo(x1, y1);
            p.lineTo(x6, y6);           
            p.lineTo(x5, y5);
            p.lineTo(x1, y1);
            return p;
        }
        //return p;
		return null;
    }
	
	//ˮ������ı仯����ͨ������ı仯�õ��仯������
	public void changeShuijingXY(int angle)
	{
		if(angle>=0&&angle<90)
		{
			shuijing_change_x=(float) (SHUIJIAN_STARTX+SHUIJING_WEIGHT/2-SHUIJIAN_HEIGHT/2*Math.tan((45-angle)*Math.PI/180));
			shuijing_change_y=SHUIJIAN_STARTY;
		}
		else if(angle>=90&&angle<180)
		{
			shuijing_change_x=SHUIJIAN_STARTX+SHUIJING_WEIGHT;
			shuijing_change_y=(float) (SHUIJIAN_STARTY+SHUIJIAN_HEIGHT/2-SHUIJING_WEIGHT/2*Math.tan((135-angle)*Math.PI/180));
		}
		else if(angle>=180&&angle<270)
		{
			shuijing_change_x=(float) (SHUIJIAN_STARTX+SHUIJING_WEIGHT/2+SHUIJIAN_HEIGHT/2*Math.tan((225-angle)*Math.PI/180));
			shuijing_change_y=SHUIJIAN_STARTY+SHUIJIAN_HEIGHT;
		}
		else if(angle>=270&&angle<=360)
		{
			shuijing_change_x=SHUIJIAN_STARTX;
			shuijing_change_y=(float) (SHUIJIAN_STARTY+SHUIJIAN_HEIGHT/2+SHUIJING_WEIGHT/2*Math.tan((315-angle)*Math.PI/180));
		}
		
	}
	
	
	private class CaidanThread extends Thread{
		GameView father;
		boolean flag=false;
		boolean whileflag=true;
		int sleepSpan = 100;//����ʱ��
		
		public CaidanThread(GameView father){
			this.father = father;
			//flag = true;
		}
		//�̵߳�ִ�з���
		public void run(){
			while(whileflag){
				if(flag)
				{
					father.caidan_y+=25;
					if(father.caidan_y>=0){
						father.caidan_y=0;
					}
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
	
	//HOME�ı�״̬���߳�
	class homeThread extends Thread{
		GameView father;
		boolean flag=false;
		boolean whileflag=true;
		int sleepSpan = 1;//����ʱ��
		
		public homeThread(GameView father){
			this.father = father;
			//flag = true;
		}
		//�̵߳�ִ�з���
		public void run(){
			while(whileflag){
				if(flag)
				{					
					father.scale+=0.1f;
					if(father.scale>1.3f)
					{
						this.flag=false;
					}
					try{
						Thread.sleep(20);
					}										//�߳�����
					catch(Exception e){
						e.printStackTrace();
					}					
				}
			   if(!flag)
				{					
						father.scale=1.0f;									
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
	
	


}
