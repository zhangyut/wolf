package com.bn.d2.bill;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


class WhatMessage{
	public static final int GOTO_WELLCOME_VIEW=0;
	public static final int GOTO_MAIN_MENU_VIEW=1;
	public static final int GOTO_GAME_VIEW=2;
	public static final int GOTO_SOUND_CONTORL_VIEW=3;
	public static final int GOTO_WIN_VIEW=4;
	public static final int GOTO_FAIL_VIEW=5;
	public static final int GOTO_HIGH_SCORE_VIEW=6;
	public static final int GOTO_HELP_VIEW=7;
	public static final int GOTO_ABOUT_VIEW=8;
	public static final int GOTO_CHOICE_VIEW=9;
	public static final int OVER_GAME=20;
}
public class GameActivity extends Activity {
	int currentView;
	WellcomeView wellcomeView;
	MainMenuView mainMenuView;
	HelpView helpView;
	AboutView aboutView;
    GameView gameView;
    WinView winView;
	FailView failView;
	SoundControlView soundControlView;
	HighScoreView highScoreView;
	ChoiceView choiceView;
	
	boolean coundDownModeFlag=true;
	private boolean backGroundMusicOn=false;
	private boolean soundOn=true;
	static int initTime=0;
    int currScore;
	int highestScore;
    SQLiteDatabase sld;
    Handler myHandler = new Handler(){
        public void handleMessage(Message msg) {
        	switch(msg.what)
        	{
        	case WhatMessage.GOTO_MAIN_MENU_VIEW:
        		gotoMainMenuView();
        		break;
        	case WhatMessage.GOTO_GAME_VIEW:
        		gotoGameView();
        		break;
        	case WhatMessage.GOTO_SOUND_CONTORL_VIEW:
        		gotoSoundControlView();
        		break;
        	case WhatMessage.GOTO_WIN_VIEW:
        		gotoWinView();
        		break;
        	case WhatMessage.GOTO_FAIL_VIEW:
        		gotoFailView();
        		break;
        	case WhatMessage.GOTO_HIGH_SCORE_VIEW:
        		gotoHighScoreView();
        		break;
        	case WhatMessage.GOTO_WELLCOME_VIEW:
        		gotoWellcomeView();
        		break;
        	case WhatMessage.GOTO_HELP_VIEW:
        		gotoHelpView();
        		break;
        	case WhatMessage.GOTO_ABOUT_VIEW:
        		gotoAboutView();
        		break;
        	case WhatMessage.GOTO_CHOICE_VIEW:
        		gotoChoiceView();
        		break;
        	case WhatMessage.OVER_GAME:
        		goToOverView();
        		break;
        	}
        }
	};
	@Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);    

		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);    
        if(initTime==0){
        	Constant.initConst(dm.widthPixels, dm.heightPixels);
        	initTime++;
        }
        gotoWellcomeView();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent e)
    {
    	switch(keyCode)
    	{
    	    case 4:
		    	switch(currentView)
		    	{
		    	case WhatMessage.GOTO_WELLCOME_VIEW:
		    		break;
		    	case WhatMessage.GOTO_MAIN_MENU_VIEW:
		    		System.exit(0);
		    		break;
		    	case WhatMessage.GOTO_HIGH_SCORE_VIEW:
		    		gotoChoiceView();
		    		break;
		    	case WhatMessage.GOTO_GAME_VIEW:
		    	case WhatMessage.GOTO_SOUND_CONTORL_VIEW:
		    	case WhatMessage.GOTO_WIN_VIEW:
		    	case WhatMessage.GOTO_FAIL_VIEW: 
		    	case WhatMessage.GOTO_HELP_VIEW:
		    	case WhatMessage.GOTO_ABOUT_VIEW:
		    	case WhatMessage.GOTO_CHOICE_VIEW:
		    		gotoMainMenuView();
		    		break;
		    	}
		    return true;	    	
    	}
		return false;
    }

    public void sendMessage(int what)
    {
    	Message msg1 = myHandler.obtainMessage(what); 
    	myHandler.sendMessage(msg1);
    }  
    public boolean isBackGroundMusicOn() {
		return backGroundMusicOn;
	}
	public void setBackGroundMusicOn(boolean backGroundMusicOn) {
		this.backGroundMusicOn = backGroundMusicOn;
	}
	
	public boolean isSoundOn() {
		return soundOn;
	}
	public void setSoundOn(boolean soundOn) {
		this.soundOn = soundOn;
	}

    private void gotoWellcomeView()
    {
    	if(wellcomeView==null)
    	{
    		 wellcomeView = new WellcomeView(this);
    	}
    	this.setContentView(wellcomeView);
    	currentView=WhatMessage.GOTO_WELLCOME_VIEW;
    } 

    private void gotoMainMenuView()
    {

    	if(gameView!=null){
    		gameView.stopAllThreads();
    	}
    	if(mainMenuView==null)
    	{
    		mainMenuView = new MainMenuView(this);
    	}
    	this.setContentView(mainMenuView);
    	currentView=WhatMessage.GOTO_MAIN_MENU_VIEW;
    }   

    private void gotoGameView()
    {
    	if(gameView==null)
    	{
    		gameView = new GameView(this);
    	}
    	this.setContentView(gameView);
    	currentView=WhatMessage.GOTO_GAME_VIEW;
    }

    private void gotoHelpView()
    {
    	if(helpView==null)
    	{
    		helpView = new HelpView(this);
    	}
    	this.setContentView(helpView);
    	currentView=WhatMessage.GOTO_HELP_VIEW;
    } 

    private void gotoAboutView()
    {
    	if(aboutView==null)
    	{
    		aboutView = new AboutView(this);
    	}
    	this.setContentView(aboutView);
    	currentView=WhatMessage.GOTO_ABOUT_VIEW;
    } 

    private void gotoSoundControlView()
    {
    	if(soundControlView==null)
    	{
    		soundControlView = new SoundControlView(this);
    	}
    	this.setContentView(soundControlView);
    	currentView=WhatMessage.GOTO_SOUND_CONTORL_VIEW;
    }

    private void gotoWinView()
    {
    	if(winView==null)
    	{
    		winView=new WinView(this);
    	}
    	this.setContentView(winView);
    	currentView=WhatMessage.GOTO_WIN_VIEW;
    }

    private void gotoFailView()
    {
    	if(failView==null)
    	{
    		failView=new FailView(this);
    	}
    	this.setContentView(failView);
    	currentView=WhatMessage.GOTO_FAIL_VIEW;
    }

    private void gotoChoiceView()
    {
    	if(choiceView==null)
    	{
    		choiceView=new ChoiceView(this);
    	}
    	this.setContentView(choiceView);
    	currentView=WhatMessage.GOTO_CHOICE_VIEW;
    }

    private void gotoHighScoreView()
    {
    	if(highScoreView==null)
    	{
    		highScoreView = new HighScoreView(this);
    	}
    	this.setContentView(highScoreView);
    	currentView=WhatMessage.GOTO_HIGH_SCORE_VIEW;
    }

    public void openOrCreateDatabase()
    {
    	try
    	{
	    	sld=SQLiteDatabase.openDatabase
	    	(
	    			"/data/data/com.bn.d2.bill/mydb",
	    			null, 								//CursorFactory
	    			SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.CREATE_IF_NECESSARY
	    	);
	    	String sql="create table if not exists highScore" +
	    			"( " +
	    			"score integer," +
	    			"date varchar(20)" +
	    			");";
	    	sld.execSQL(sql);
    	}
    	catch(Exception e)
    	{
    		Toast.makeText(this, "���ݿ����"+e.toString(), Toast.LENGTH_SHORT).show();
    	}
    }

    public void closeDatabase()
    {
    	try
    	{
	    	sld.close();
    	}
		catch(Exception e)
		{
			Toast.makeText(this, "���ݿ����"+e.toString(), Toast.LENGTH_SHORT).show();;
		}
    }

    public void insert(int score,String date)
    {
    	try
    	{
        	String sql="insert into highScore values("+score+",'"+date+"');";
        	sld.execSQL(sql);
        	sld.close();
    	}
		catch(Exception e)
		{
			Toast.makeText(this, "���ݿ����"+e.toString(), Toast.LENGTH_SHORT).show();;
		}
    }

    public String query(int posFrom,int length)
    {
    	StringBuilder sb=new StringBuilder();
    	Cursor cur=null;
    	openOrCreateDatabase();
        String sql="select score,date from highScore order by score desc;";
        cur=sld.rawQuery(sql, null);
    	try
    	{    		
        	cur.moveToPosition(posFrom);
        	int count=0;
        	while(cur.moveToNext()&&count<length)
        	{
        		int score=cur.getInt(0);
        		String date=cur.getString(1);        		
        		sb.append(score);
        		sb.append("/");
        		sb.append(date);
        		sb.append("/");
        		count++;
        	}        			
    	}
		catch(Exception e)
		{
			Toast.makeText(this, "���ݿ����"+e.toString(), Toast.LENGTH_SHORT).show();
		}
		finally
		{
			cur.close();
			closeDatabase();
		}

		return sb.toString();
    }

    public int getRowCount()
    {
    	int result=0;
    	Cursor cur=null;
    	openOrCreateDatabase();
    	try
    	{
    		String sql="select count(score) from highScore;";    	
            cur=sld.rawQuery(sql, null);
        	if(cur.moveToNext())
        	{
        		result=cur.getInt(0);
        	}
    	}
    	catch(Exception e)
		{
			Toast.makeText(this, "���ݿ����"+e.toString(), Toast.LENGTH_SHORT).show();
		}
		finally
		{
			cur.close();
			closeDatabase();
		}
        
    	return result;
    }

    private void goToOverView()
    {
    	Cursor cur=null;
    	openOrCreateDatabase();
    	try{

        	String sql="select max(score) from highScore;";   	
        	cur=sld.rawQuery(sql, null);
        	if(cur.moveToNext())
        	{
        		this.highestScore=cur.getInt(0);
        	}
        	insert(currScore,DateUtil.getCurrentDate());
    	}
		catch(Exception e)
		{
			Toast.makeText(this, "���ݿ����"+e.toString(), Toast.LENGTH_SHORT).show();
		}
		finally
		{
			cur.close();
			closeDatabase();
		}		
		if(currScore>highestScore)
		{    	
	    	this.gotoWinView();
		}
		else
		{
			this.gotoFailView();
		}
    	
    }
}