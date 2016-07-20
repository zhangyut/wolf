package com.bn.tag;

public class Constant {
	public static final float  SCREEN_WIDTH=480;
	public static final float  SCREEN_HEIGHT=800;

	public static final int[][] MAP=
	{
		{3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
		{3,2,2,2,0,0,0,4,4,4,0,0,0,0,0,2,3,3,3,3},
		{3,4,4,4,0,0,1,1,1,1,1,0,0,0,0,2,3,3,3,3},
		{3,2,0,0,0,4,1,0,0,0,1,0,0,0,0,2,4,4,4,3},
		{1,1,1,1,0,0,1,0,0,0,1,0,3,0,0,0,0,0,0,0},
		{2,2,0,1,0,2,1,0,0,0,1,0,3,0,1,1,1,1,1,0},
		{2,0,0,1,0,2,1,0,3,3,1,0,2,2,1,0,0,0,0,3},
		{2,0,0,1,0,2,1,0,3,3,1,0,2,2,1,0,0,0,0,3},
		{2,0,0,1,0,0,1,0,2,3,1,0,0,0,1,0,0,0,0,3},
		{2,0,0,1,1,1,1,0,2,0,1,1,1,1,1,0,2,2,3,3},
		{3,3,0,0,0,0,0,0,2,0,0,0,0,0,0,2,2,3,3,3},
		{3,3,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3}
	};
	
	public static final int[][] MIDDLE_MAP=
	{
		{20,180},
		{140,180},
		{140,380},
		{260,380},
		{260,100},
		{420,100},
		{420,380},
		{580,380},
		{580,220},
		{780,220}
	};


	public static final float JIAN_TA_WEIGHT=40;
	public static final float JIAN_TA_HEIGHT=79;
	
	public static final float SINGLE_RODER=40;

	public static final float BACK_WIDTH=480;
	public static final float BACK_HEIGHT=800;
	
	public static final float GONGLU_WEIGHT=40;
	public static final float GONGLU_HEIGHT=40;
	
	public static final float SHUIJIAN_STARTX=500;
	public static final float SHUIJIAN_STARTY=5;
	public static final float SHUIJING_WEIGHT=40;
	public static final float SHUIJIAN_HEIGHT=40;

	public static final float SHUIJING_CENTER_X=520;
	public static final float SHUIJING_CENTER_Y=25;

	public static final float MONEY_WEIGHT=40;
	public static final float MONRY_HEIGHT=40;

	public static final float BLOOD_WEIGHT=45;
	public static final float BLOOD_HEIGHT=40;

	public static final float LEVEL_WEIGHT=89;
	public static final float LEVEL_HEIGH=29;

	public static final float TIBIAO_WEIGHT=70;
	public static final float TIBIAO_HEIGHT=70;
	

	public static final float SINGLE_FOOT=0.625f;
	public static final float HALF_SINGLE_RODER=SINGLE_RODER/2+0.01f;
	

	public static final float SINGLE_PIC=38;

	public static final float GUAI_BLOOD_WEIGHT=34;
	public static final float GUAI_BLOOD_HEIGHT=5;

	public static final float JIAN_TOU_WEIGHT=11;
	public static final float JIAN_TOU_HEIGHT=11;

	public static final float TARGET_MAX_NUM=10;
	


	public static final float TREE_WEIGHT=40;
	public static final float TREE_HEIGHT=40;

	public static final float BIG_TREE_WEIGHT=40;
    public static final float BIG_TREE_HEIGHT=80;

    public static final float FLOWER_WEIGHT=40;
    public static final float FLOWER_HEIGHT=40;

    public static final float R_LENGTH=130;

    public static final float JIANTOU_WEIGHT=40;
    public static final float JIANTOU_HEIGHT=7;

    public static final float SPEED=1.25f;
    

    public static final float MAIN_LENGTH=170;
    public static final float MAIN_WEIGHT=40;


    public static final float GOON_X=50;
    public static final float GOON_Y=60;

    public static final float NEW_X=50;
    public static final float NEW_Y=110;

    public static final float JIFEN_X=50;
    public static final float JIFEN_Y=160;

    public static final float YINXIAO_X=50;
    public static final float YINXIAO_Y=210;

    public static final float HELP_X=50;
    public static final float HELP_Y=260;

    public static final float EXIT_X=50;
    public static final float EXIT_Y=410;

    public static final float CANDAN_WEIGHT=80;
    public static final float CANDAN_HEIGHT=80; 


    public static final float SAVE_GAME_X=50;
    public static final float SAVE_GAME_Y=130;

    public static final float GOBACK_GAME_X=50;
    public static final float GOBACK_GAME_Y=200;

    public static final float EXIT_GAME_X=50;
    public static final float EXIT_GAME_Y=270;

    public static final float TANCHU_CAIDAN_WEIGHT=170;
    public static final float TANCHU_CAIDAN_HEIGHT=40;

    public static final float CAIDAN_GAME_START_X=120;

    public static final float MUSIC_WEIGHT=220;
    public static final float MUSIC_HEIGHT=50;

    public static final int CUN_DANG_DIALOG_ID=0;
    public static final int GO_ONGAME_DIALOG_ID=1;
	

    public static final float HOME_WEIGHT=75;
    public static final float HOME_HEIGHT=106;
    

    public static final float HOME_X=720;
    public static final float HOME_Y=138;
 

	public static int [][] homeLocations={
			{5,18}
	};

	public static final int GW_STATE01=0;
	public static final int GW_STATE02=1;
	public static final int GW_STATE03=2;
	
	public static final int JT_STATE01=1;
	public static final int JT_STATE02=2;
	
	
	
	public static int[] getHomeLocationByxy(int stage){
		return homeLocations[stage];
	}
}
