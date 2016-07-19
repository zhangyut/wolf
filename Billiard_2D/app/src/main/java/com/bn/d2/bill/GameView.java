package com.bn.d2.bill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 
 *
 *
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback{
	GameActivity activity;
	Paint paint;
	Bitmap[] tableBmps;
	Bitmap cueBmp;
	Bitmap[][] ballBmps;
	Bitmap barDownBmp;
	Bitmap barUpBmp;	
	Bitmap goDownBmp;
	Bitmap goUpBmp;
	Bitmap leftDownBmp;
	Bitmap leftUpBmp;
	Bitmap rightDownBmp;
	Bitmap rightUpBmp;
	Bitmap aimDownBmp;
	Bitmap aimUpBmp;
	Bitmap bgBmp;
	Bitmap[] numberBitmaps;
	Bitmap breakMarkBitmap;

	List<Ball> alBalls;
	Table table;
	Cue cue;
	StrengthBar strengthBar;
	VirtualButton goBtn;
	VirtualButton leftBtn;
	VirtualButton rightBtn;
	VirtualButton aimBtn;
	Timer timer;

	GameViewDrawThread drawThread;
	BallGoThread ballGoThread;
	KeyThread keyThread;
	TimeRunningThread timeRunningThread;
	//״ֵ̬
	int keyState=0;
	float btnPressTime=0;

	SoundPool soundPool;
	HashMap<Integer, Integer> soundPoolMap; 
	MediaPlayer mMediaPlayer;	
	public static final int SHOOT_SOUND=0;
	public static final int HIT_SOUND=1;
	public static final int BALL_IN_SOUND=2;
	public GameView(GameActivity activity) {
		super(activity);
		this.activity=activity;

		this.requestFocus();
        this.setFocusableInTouchMode(true);
		getHolder().addCallback(this);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.BLACK);
		canvas.drawBitmap(bgBmp, 0, 0, paint);
		table.drawSelf(canvas, paint);

		List<Ball> alBallsTemp=new ArrayList<Ball>(alBalls);
		for(Ball b:alBallsTemp){
			b.drawSelf(canvas, paint);
		}	
		cue.drawSelf(canvas, paint);
		strengthBar.drawSelf(canvas,paint);
		goBtn.drawSelf(canvas, paint);
		leftBtn.drawSelf(canvas, paint);
		rightBtn.drawSelf(canvas, paint);
		aimBtn.drawSelf(canvas, paint);
		if(activity.coundDownModeFlag){
			timer.drawSelf(canvas, paint);
		}		
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();

		if(cue.isShowingAnimFlag() || !cue.isShowCueFlag()){
			return true;
		}
    	switch(event.getAction())
    	{
    	case MotionEvent.ACTION_DOWN:     		
    		if(goBtn.isActionOnButton(x, y))
    		{
    			goBtn.pressDown();
    		}
    		else if(leftBtn.isActionOnButton(x, y))
    		{
    			leftBtn.pressDown();
    			keyState=keyState|0x20;
    			keyState=keyState|0x1;
    		}
    		else if(rightBtn.isActionOnButton(x, y))
    		{
    			rightBtn.pressDown();
    			keyState=keyState|0x20;
    			keyState=keyState|0x2;
    		}
    		else if(aimBtn.isActionOnButton(x, y))
    		{

    			cue.setAimFlag(!cue.isAimFlag());

    			if(cue.isAimFlag()){
    				aimBtn.releaseUp();
    			}
    			else{
    				aimBtn.pressDown();
    			}
    		}
    		else if(strengthBar.isActionOnBar(x, y)){
    			strengthBar.changeCurrHeight(x, y);
    		}
    		else
    		{    			
        		cue.calcuAngle(x, y);
    		}
    		break;
    	case MotionEvent.ACTION_MOVE: 
    		if(strengthBar.isActionOnBar(x, y)){
    			strengthBar.changeCurrHeight(x, y);
    		}
    		else if(!goBtn.isActionOnButton(x, y) && 
    				!leftBtn.isActionOnButton(x, y) && 
    				!rightBtn.isActionOnButton(x, y) &&
    				!aimBtn.isActionOnButton(x, y)
    		)
    		{
    			goBtn.releaseUp();
        		keyState=keyState&0xFFEF;
        		leftBtn.releaseUp();
        		keyState=keyState&0xFFFE;
        		rightBtn.releaseUp();
        		keyState=keyState&0xFFFD;
        		btnPressTime=0;
        		keyState=keyState&0xFFDF;
        		cue.calcuAngle(x, y);
    		}
    		break;
    	case MotionEvent.ACTION_UP:
    		if(goBtn.isActionOnButton(x, y))
    		{
    			goBtn.releaseUp();
        		new CueAnimateThread(this).start();
    		}
    		else if(leftBtn.isActionOnButton(x, y))
    		{
    			leftBtn.releaseUp();
        		keyState=keyState&0xFFFE;
        		btnPressTime=0;
        		keyState=keyState&0xFFDF;
    		}
    		else if(rightBtn.isActionOnButton(x, y))
    		{
    			rightBtn.releaseUp();
        		keyState=keyState&0xFFFD;
        		btnPressTime=0;
        		keyState=keyState&0xFFDF;
    		}
    		break;
    	}
		return true;
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder){
		paint=new Paint();
		paint.setAntiAlias(true);
		createAllThreads();
		initBitmap();
		changeBmpsRatio(Constant.ssr.ratio);
		changeBmpsRatioFullScreen(Constant.wRatio,Constant.hRatio);
		initSounds();

		mMediaPlayer = MediaPlayer.create(activity, R.raw.backsound);
		mMediaPlayer.setLooping(true);
		table=new Table(tableBmps);

		alBalls=new ArrayList<Ball>();
		for(int i=0;i<Table.AllBallsPos.length;i++)
		{
			alBalls.add(new Ball(ballBmps[i],this,0,0,Table.AllBallsPos[i]));
		}
		cue=new Cue(cueBmp,alBalls.get(0));
		strengthBar=new StrengthBar(barDownBmp,barUpBmp);
		goBtn=new VirtualButton(goDownBmp,goUpBmp,Constant.GO_BTN_X,Constant.GO_BTN_Y);
		leftBtn=new VirtualButton(leftDownBmp,leftUpBmp,Constant.LEFT_BTN_X,Constant.LEFT_BTN_Y);
		rightBtn=new VirtualButton(rightDownBmp,rightUpBmp,Constant.RIGHT_BTN_X,Constant.RIGHT_BTN_Y);
		aimBtn=new VirtualButton(aimDownBmp,aimUpBmp,Constant.AIM_BTN_X,Constant.AIM_BTN_Y);
		timer=new Timer(this,breakMarkBitmap,numberBitmaps);
		if(activity.isBackGroundMusicOn())
		{
			mMediaPlayer.start();
		}
		startAllThreads();
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {		
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {	
		boolean retry = true;        
        while (retry){
        	joinAllThreads();	      
            retry = false;
        }

        if(mMediaPlayer.isPlaying()){
        	mMediaPlayer.stop();
        }
	}	

	public void initBitmap(){		
		tableBmps=new Bitmap[]{
				BitmapFactory.decodeResource(this.getResources(), R.drawable.table0),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.table1),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.table2),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.table3),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.table4),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.table5),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.table6),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.table7),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.table8),				
				BitmapFactory.decodeResource(this.getResources(), R.drawable.table9),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.table10),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.table11),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.table12),
			};				
		ballBmps=new Bitmap[][]{
			{
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball00),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball01),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball02),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball00),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball01),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball02),
			},
			{
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball10),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball11),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball12),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball10),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball11),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball12),
			},
			{
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball20),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball21),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball22),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball20),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball21),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball22),
			},
			{
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball30),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball31),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball32),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball30),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball31),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball32),
			},
			{
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball40),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball41),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball42),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball40),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball41),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball42),
			},
			{
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball50),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball51),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball52),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball50),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball51),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball52),
			},
			{
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball60),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball61),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball62),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball60),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball61),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball62),
			},
			{
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball70),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball71),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball72),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball70),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball71),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball72),
			},
			{
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball80),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball81),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball82),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball80),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball81),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball82),
			},
			{
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball90),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball91),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball92),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball90),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball91),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball92),
			},
			{
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball100),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball101),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball102),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball100),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball101),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball102),
			},			
			{
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball110),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball111),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball112),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball110),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball111),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball112),
			},
			{
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball120),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball121),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball122),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball120),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball121),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball122),
			},
			{
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball130),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball131),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball132),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball130),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball131),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball132),
			},
			{
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball140),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball141),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball142),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball140),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball141),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball142),
			},
			{
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball150),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball151),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball152),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball150),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball151),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.ball152),
			},
		};		
		cueBmp=BitmapFactory.decodeResource(this.getResources(), R.drawable.qiu_gan);
		barDownBmp=BitmapFactory.decodeResource(this.getResources(), R.drawable.ruler);
		barUpBmp=BitmapFactory.decodeResource(this.getResources(), R.drawable.pointer);		
		goDownBmp=BitmapFactory.decodeResource(this.getResources(), R.drawable.go_down);
		goUpBmp=BitmapFactory.decodeResource(this.getResources(), R.drawable.go_up);
		
		leftDownBmp=BitmapFactory.decodeResource(this.getResources(), R.drawable.left_down);
		leftUpBmp=BitmapFactory.decodeResource(this.getResources(), R.drawable.left_up);
		rightDownBmp=BitmapFactory.decodeResource(this.getResources(), R.drawable.right_down);
		rightUpBmp=BitmapFactory.decodeResource(this.getResources(), R.drawable.right_up);
		aimDownBmp=BitmapFactory.decodeResource(this.getResources(), R.drawable.aim_down);
		aimUpBmp=BitmapFactory.decodeResource(this.getResources(), R.drawable.aim_up);
		
		bgBmp=BitmapFactory.decodeResource(this.getResources(), R.drawable.bg);
		numberBitmaps=new Bitmap[]{
				BitmapFactory.decodeResource(this.getResources(), R.drawable.number0),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.number1),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.number2),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.number3),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.number4),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.number5),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.number6),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.number7),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.number8),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.number9),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.number0),
		};
		breakMarkBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.breakmark);
	}
	public void changeBmpsRatio(float ratio){
		for(int i=0;i<tableBmps.length;i++){
			tableBmps[i]=PicLoadUtil.scaleToFit(tableBmps[i], ratio);
		}
		for(int i=0;i<ballBmps.length;i++){
			for(int j=0;j<ballBmps[i].length;j++){
				ballBmps[i][j]=PicLoadUtil.scaleToFit(ballBmps[i][j], ratio);
			}			
		}
		cueBmp=PicLoadUtil.scaleToFit(cueBmp, ratio);
		barDownBmp=PicLoadUtil.scaleToFit(barDownBmp, ratio);
		barUpBmp=PicLoadUtil.scaleToFit(barUpBmp, ratio);
		goDownBmp=PicLoadUtil.scaleToFit(goDownBmp, ratio);
		goUpBmp=PicLoadUtil.scaleToFit(goUpBmp, ratio);
		
		leftDownBmp=PicLoadUtil.scaleToFit(leftDownBmp, ratio);
		leftUpBmp=PicLoadUtil.scaleToFit(leftUpBmp, ratio);
		rightDownBmp=PicLoadUtil.scaleToFit(rightDownBmp, ratio);
		rightUpBmp=PicLoadUtil.scaleToFit(rightUpBmp, ratio);
		aimDownBmp=PicLoadUtil.scaleToFit(aimDownBmp, ratio);		
		aimUpBmp=PicLoadUtil.scaleToFit(aimUpBmp, ratio);	
		for(int i=0;i<numberBitmaps.length;i++){
			numberBitmaps[i]=PicLoadUtil.scaleToFit(numberBitmaps[i], ratio);
		}
		breakMarkBitmap=PicLoadUtil.scaleToFit(breakMarkBitmap, ratio);	
	}
	public void changeBmpsRatioFullScreen(float wRatio,float hRatio){
		bgBmp=PicLoadUtil.scaleToFitFullScreen(bgBmp, wRatio, hRatio);		
	}
	void createAllThreads()
	{
		drawThread=new GameViewDrawThread(this);
		ballGoThread=new BallGoThread(this);	
		keyThread=new KeyThread(this);
		if(activity.coundDownModeFlag){
			timeRunningThread=new TimeRunningThread(this);
		}
	}
	void startAllThreads()
	{
		drawThread.setFlag(true);  
		drawThread.start();
		ballGoThread.setFlag(true);
		ballGoThread.start();
		keyThread.setFlag(true);
		keyThread.start();
		if(activity.coundDownModeFlag){
			timeRunningThread.setFlag(true);		
		    timeRunningThread.start();
		}		
	}
	void stopAllThreads()
	{
		drawThread.setFlag(false);   
		ballGoThread.setFlag(false);
		keyThread.setFlag(false);
		if(activity.coundDownModeFlag){
			timeRunningThread.setFlag(false);
		}
	}
	void joinAllThreads()
	{
		try {
			drawThread.join();		
			keyThread.join();
			ballGoThread.join();
			if(activity.coundDownModeFlag){
				timeRunningThread.join();
			}			
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}

	public void overGame()
	{
        stopAllThreads();

        if(mMediaPlayer.isPlaying()){
        	mMediaPlayer.stop();
        }
        if(activity.coundDownModeFlag){
        	activity.currScore=timer.getLeftSecond();
    		activity.sendMessage(WhatMessage.OVER_GAME);
        } 
        else{
        	activity.sendMessage(WhatMessage.GOTO_CHOICE_VIEW);
        }
	}	

    public void repaint()
	{
		Canvas canvas=this.getHolder().lockCanvas();
		try
		{
			synchronized(canvas)
			{
				onDraw(canvas);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(canvas!=null)
			{
				this.getHolder().unlockCanvasAndPost(canvas);
			}
		}
	}

	public void initSounds(){
	     soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
	     soundPoolMap = new HashMap<Integer, Integer>();   
	     soundPoolMap.put(SHOOT_SOUND, soundPool.load(getContext(), R.raw.shoot, 1));
	     soundPoolMap.put(HIT_SOUND, soundPool.load(getContext(), R.raw.hit, 1));
	     soundPoolMap.put(BALL_IN_SOUND, soundPool.load(getContext(), R.raw.ballin, 1));
	}

	public void playSound(int sound, int loop) {
	    AudioManager mgr = (AudioManager)getContext().getSystemService(Context.AUDIO_SERVICE);   
	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   
	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       
	    float volume = streamVolumeCurrent / streamVolumeMax;   	    
	    soundPool.play(soundPoolMap.get(sound), volume, volume, 1, loop, 1f);
	}
}
