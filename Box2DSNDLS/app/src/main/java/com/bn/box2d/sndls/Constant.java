package com.bn.box2d.sndls;

import android.content.res.Resources;
import android.graphics.Bitmap;

public class Constant 
{
	public static float RATE = 10;
	public static boolean DRAW_THREAD_FLAG=true;
	public static boolean START_SCORE=false;
	
	public static final float TIME_STEP = 1f/60.0f;
	public static final int ITERA = 10;
	
	public static int SCREEN_WIDTH;
	public static int SCREEN_HEIGHT;
	   
	public static float SCREEN_WIDTH_STANDARD=854;
	public static float SCREEN_HEIGHT_STANDARD=480;
	
	public static float xMainRatio;
	public static float yMainRatio;
	

	public static boolean MAIN_DRAW_THREAD_FLAG=false;
	public static boolean TJ_CONTROL_FLAG=false;
	

	public static float xOffset=0;
	public static float yOffset=0;
	

	public static float CJ_WIDTH=2000;
	

	public static int SCORE=0;
	

	public static int[] PIC_ID=
	{
		R.drawable.ground,
		R.drawable.ls_zy,
		R.drawable.xm,
		R.drawable.wood_hen,
		R.drawable.wood_hen_1,
		R.drawable.wood_hen_2,
		R.drawable.stone_shu,
		R.drawable.ice_hen,
		R.drawable.ice_hen_1,
		R.drawable.ice_hen_2,
		R.drawable.ice_hen_3,
		R.drawable.ice_shu,
		R.drawable.ice_shu_1,
		R.drawable.ice_shu_2,
		R.drawable.ice_shu_3,
		R.drawable.bottom_left_ntz,
		R.drawable.bottom_right_ntz,
		R.drawable.bg,
		R.drawable.stone_hen,
		R.drawable.wood_shu,
		R.drawable.wood_shu_1,
		R.drawable.wood_shu_2,
		R.drawable.dg,
		R.drawable.ls_by,
		R.drawable.pj,
		R.drawable.c_xd,
		R.drawable.c_dd,
	};
	//Ԥ���ذ�ɫ����ͼƬID�����б�
	public static int[] NUM_BS_ID=    
	{
		R.drawable.num_bs_0,//��ɫ����0
		R.drawable.num_bs_1,//��ɫ����1  
		R.drawable.num_bs_2,//��ɫ����2
		R.drawable.num_bs_3,//��ɫ����3
		R.drawable.num_bs_4,//��ɫ����4
		R.drawable.num_bs_5,//��ɫ����5
		R.drawable.num_bs_6,//��ɫ����6
		R.drawable.num_bs_7,//��ɫ����7
		R.drawable.num_bs_8,//��ɫ����8
		R.drawable.num_bs_9//��ɫ����9
	};
	
	//���ػ�ɫ����ͼƬID�����б�
	public static int[] NUM_HS_ID=
	{
		R.drawable.num_hs_0,//��ɫ����0
		R.drawable.num_hs_1,//��ɫ����1
		R.drawable.num_hs_2,//��ɫ����2
		R.drawable.num_hs_3,//��ɫ����3
		R.drawable.num_hs_4,//��ɫ����4
		R.drawable.num_hs_5,//��ɫ����5
		R.drawable.num_hs_6,//��ɫ����6
		R.drawable.num_hs_7,//��ɫ����7
		R.drawable.num_hs_8,//��ɫ����8
		R.drawable.num_hs_9//��ɫ����9
	};
	
	//������ɫ����ͼƬID�����б�
	public static int[] NUM_ZS_ID=
	{
		R.drawable.num_zs_0,//��ɫ����0
		R.drawable.num_zs_1,//��ɫ����1
		R.drawable.num_zs_2,//��ɫ����2
		R.drawable.num_zs_3,//��ɫ����3
		R.drawable.num_zs_4,//��ɫ����4
		R.drawable.num_zs_5,//��ɫ����5
		R.drawable.num_zs_6,//��ɫ����6
		R.drawable.num_zs_7,//��ɫ����7
		R.drawable.num_zs_8,//��ɫ����8
		R.drawable.num_zs_9//��ɫ����9
	};
	//�����Լ��Զ���ͼƬ��ID�����б�
	public static int[] OTHER_PIC_ID=
	{
		R.drawable.gk_1,	//0��һ��
		R.drawable.gk_2,	//1�ڶ���
		R.drawable.gk_3,	//2������
		R.drawable.dhk,		//3�Ի���   
		R.drawable.v_zjm,	//4������
		R.drawable.v_xyg,	//5��һ��
		R.drawable.fs_back,	//6��ʼ�����з����ĶԻ���
		R.drawable.level_cleared,	//7���غ�Ľ�����ʾ
		R.drawable.level_failure	//8δ���غ�Ľ�����ʾ
	};
	//�����Լ��Զ����ͼƬ����
	public static Bitmap[] OTHER_PIC_ARRAY;
	
	//Ԥ���ص�ͼƬ����
	public static Bitmap[] PIC_ARRAY;   
	//Ԥ���صİ�ɫ����ͼƬ����
	public static Bitmap[] NUM_ARRAY;
	//Ԥ���صĻ�ɫ����ͼƬ����
	public static Bitmap[] Y_NUM_ARRAY;
	//Ԥ���ص���ɫ����ͼƬ����
	public static Bitmap[] P_NUM_ARRAY;
	//����ͼƬ�ķ���
	public static void loadPic(Resources res)
	{
		 PIC_ARRAY=new Bitmap[PIC_ID.length];
		 NUM_ARRAY=new Bitmap[NUM_BS_ID.length];
		 Y_NUM_ARRAY=new Bitmap[NUM_HS_ID.length];
		 P_NUM_ARRAY=new Bitmap[NUM_ZS_ID.length];
		 OTHER_PIC_ARRAY=new Bitmap[OTHER_PIC_ID.length];
		 for(int i=0;i<PIC_ID.length;i++)
		 {
			 PIC_ARRAY[i]=PicLoadUtil.loadBM(res, PIC_ID[i]);			 
		 }
		 for(int i=0;i<NUM_BS_ID.length;i++)
		 {
			 NUM_ARRAY[i]=PicLoadUtil.loadBM(res, NUM_BS_ID[i]);
			 NUM_ARRAY[i]=PicLoadUtil.scaleToFitXYRatio(NUM_ARRAY[i], yMainRatio,yMainRatio);
		 }
		 for(int i=0;i<NUM_HS_ID.length;i++)
		 {
			 Y_NUM_ARRAY[i]=PicLoadUtil.loadBM(res, NUM_HS_ID[i]);
			 Y_NUM_ARRAY[i]=PicLoadUtil.scaleToFitXYRatio(Y_NUM_ARRAY[i], yMainRatio,yMainRatio);
		 }
		 for(int i=0;i<NUM_ZS_ID.length;i++)
		 {
			 P_NUM_ARRAY[i]=PicLoadUtil.loadBM(res, NUM_ZS_ID[i]);
			 P_NUM_ARRAY[i]=PicLoadUtil.scaleToFitXYRatio(P_NUM_ARRAY[i], yMainRatio,yMainRatio);
		 }
		 //�Լ��Զ����ͼƬ����
		 for(int i=0;i<OTHER_PIC_ID.length;i++)
		 {
			 OTHER_PIC_ARRAY[i]=PicLoadUtil.loadBM(res, OTHER_PIC_ID[i]);
			 OTHER_PIC_ARRAY[i]=PicLoadUtil.scaleToFitXYRatio(OTHER_PIC_ARRAY[i], yMainRatio,yMainRatio);
		 }
		 
		 //����ͼ���ո߶�����
		 PIC_ARRAY[17]=PicLoadUtil.scaleToFitXYRatio(PIC_ARRAY[17], yMainRatio,yMainRatio);
		 //����ͼƬ���ո߶�����
		 PIC_ARRAY[22]=PicLoadUtil.scaleToFitXYRatio(PIC_ARRAY[22], yMainRatio*0.7f,yMainRatio*0.7f);
		 //��ͷͼƬ���ո߶�����
		 PIC_ARRAY[1]=PicLoadUtil.scaleToFitXYRatio(PIC_ARRAY[1], yMainRatio,yMainRatio);
		 //���ֲ�����
		 PIC_ARRAY[25]=PicLoadUtil.scaleToFitXYRatio(PIC_ARRAY[25], yMainRatio,yMainRatio);
		 PIC_ARRAY[26]=PicLoadUtil.scaleToFitXYRatio(PIC_ARRAY[26], yMainRatio,yMainRatio);
	}	 
	
	public static float DMGD=40.0f;//����߶�
	
	public static int currStage=0;//��ǰ�ؿ� 0-��һ��
	
	public static int[] GG_SCORE={10000,15000,20000};//���ط�
	
	public static int[] HH_SCORE={0,0,0};
	
	//=======================================================================================
	//��ͼ�����Ƿ�ֹ�б�	true��ʾ���Ǿ�̬false��ʾ���Ƕ�̬
	public static boolean[][] IS_MOVE=
	{
		//��һ�ص��Ƿ�ֹ�б�
		{	
			true,true,true,
			false,false,false,false,false,
			false,false,false,false,false,
			false,false,false,false,false,
			false,false,false,false,false,
			false,false,false
		},
		//�ڶ��ص��Ƿ�ֹ�б�
		{
			true,true,true,
			false,false,false,false,false,false,
			false,false,false,false,
			false,false,false,false,false,
			false,false,false
		},
		//�����ص��Ƿ�ֹ�б�
		{
			true,true,
			true,true,false,false,
			false,false,false,false,false,false,
			true
		}
	};
	
	//��ͼ������ͼ��ͼƬ���������б�[����0 ][���۾�������1][Сè2][��ľͷ345][��ʯͷ6]
	public static int[][][] IMG_ID=
	{
		//��һ�ص�������ͼ����
		{
			{0},//����
			{15},//������̨��   
			{16},//�Ҳ����̨��
			{6},//�������е�ʯ��
			{2},//���Сè
			{11,12,13,14},//������ŵı���
			{3,4,5},//�����ŵ�ľ��
			{18},//����Ϸ����ŵ�ʯͷ
			{6},//�Ҳ�����е�ʯ��
			{2},//�Ҳ�Сè
			{19,20,21},//�Ҳ����ŵ�ľ��
			{7,8,9,10},//�Ҳ���ŵı���
			{18},//�Ҳ��Ϸ����ŵ�ʯͷ
			{18},//�Ҳ��Ϸ����ŵ�ʯͷ
			{18},//����Ϸ����ŵ�ʯͷ
			{18},//�Ҳ��Ϸ����ŵ�ʯͷ
			{18},//����Ϸ����ŵ�ʯͷ
			{6},//������ŵ�ʯ��
			{6},//������ŵ�ʯ��
			{18},//������ŵ�ʯ��
			{6},//������ŵ�ʯ��
			{6},//������ŵ�ʯ��
			{18},//������ŵ�ʯ��
			{19,20,21},//�Ҳ����ŵ�ľ��
			{19,20,21},//�Ҳ����ŵ�ľ��
			{7,8,9,10},//�Ҳ���ŵı���
		},
		//�ڶ��ص�������ͼ����
		{
			{0},//����
			{15},//������̨��   
			{16},//�Ҳ����̨��
			{11,12,13,14},//������ŵı���
			{2},//���Сè
			{19,20,21},//�����ֱ��ľ��
			{7,8,9,10},//�����ŵı���
			{19,20,21},//����Ϸ����ŵ�ľ��
			{6},//������ŵ�ʯ��
			{2},//��϶�е�Сè
			{6},//�Ҳ����ŵ�ʯ��
			{2},//�Ҳ��Сè
			{6},//������ŵ�ʯ��
			{6},//������ŵ�ʯ��
			{18},//������ŵ�ʯ��
			{6},//������ŵ�ʯ��
			{6},//������ŵ�ʯ��
			{18},//������ŵ�ʯ��
			{19,20,21},//�Ҳ����ŵ�ľ��
			{19,20,21},//�Ҳ����ŵ�ľ��
			{7,8,9,10},//�Ҳ���ŵı���
		},
		//�����ص�������ͼ����
		{
			{0},//����
			{15},//������̨��   
			{6},//���ŵ�ʯ��
			{18},//����Ϸ����ŵ�ʯͷ
			{2},//Сè
			{2},//Сè
			{19,20,21},//����Ϸ����ŵ�ľ��
			{11,12,13,14},//����Ϸ����ŵı���
			{7,8,9,10},//����Ϸ����ŵı���
			{6},//������ŵ�ʯ��
			{2},//Сè
			{2},//Сè
			{6},//�Ҳ����ŵ�ʯ��
		}
	};  
	
	//��ͼ���������
	public static BodyType[][] typeA=
	{
		{
			BodyType.DM,BodyType.HNTZ,BodyType.HNTZ,
			BodyType.ST,BodyType.XM,BodyType.BK,BodyType.MT,BodyType.ST,		
			BodyType.ST,BodyType.XM,BodyType.MT,BodyType.BK,BodyType.ST,
			BodyType.ST,BodyType.ST,BodyType.ST,BodyType.ST,BodyType.ST,
			BodyType.ST,BodyType.ST,BodyType.ST,BodyType.ST,BodyType.ST,
			BodyType.MT,BodyType.MT,BodyType.BK,
		},
		{
			BodyType.DM,BodyType.HNTZ,BodyType.HNTZ,
			BodyType.BK,BodyType.XM,BodyType.MT,BodyType.BK,BodyType.MT,BodyType.ST,
			BodyType.XM,BodyType.ST,BodyType.XM,BodyType.ST,
			BodyType.ST,BodyType.ST,BodyType.ST,BodyType.ST,BodyType.ST,
			BodyType.MT,BodyType.MT,BodyType.BK,
		},
		{
			BodyType.DM,BodyType.HNTZ,
			BodyType.ST,BodyType.ST,BodyType.XM,BodyType.XM,
			BodyType.MT,BodyType.BK,BodyType.BK,BodyType.ST,BodyType.XM,BodyType.XM,
			BodyType.ST
		}
	};

	public static float[][][] SPEED=
	{
		{
			{0,0},{0,0},{0,0},
			{0,0},{0,0},{0,0},{0,0},{0,0},
			{0,0},{0,0},{0,0},{0,0},{0,0},
			{0,0},{0,0},{0,0},{0,0},{0,0},
			{0,0},{0,0},{0,0},{0,0},{0,0},
			{0,0},{0,0},{0,0}
		},
		{
			{0,0},{0,0},{0,0},
			{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},
			{0,0},{0,0},{0,0},{0,0},
			{0,0},{0,0},{0,0},{0,0},{0,0},
			{0,0},{0,0},{0,0}
		},
		{
			{0,0},{0,0},
			{0,0},{0,0},{0,0},{0,0},
			{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},
			{0,0}
		}
	};

	public static float[][][] LOCATION=
	{
		{
			{0,440},{760,400},{1000,360},
			{840,340},{870,360},{920,340},{840,320},{920,300},		
			{1000,300},{1030,320},{1080,300},{1000,280},{1080,260},
			{840,300},{1000,260},{880,300},{1040,260},{1240,260},
			{1340,260},{1240,240},{1240,60},{1340,60},{1240,40},			
			{1120,300},{1180,300},{1120,280}
		},
		{
			{0,440},{760,400},{1000,400},
			{820,340},{850,360},{900,340},{820,320},{860,300},{940,340},
			{960,400},{1010,320},{1030,360},{1240,260},
			{1340,260},{1240,240},{1240,60},{1340,60},{1240,40},
			{1080,300},{1140,300},{1080,280}
		},
		{
			{0,440},{760,400},
			{730,320},{730,300},{920,360},{880,360},
			{770,200},{800,260},{800,240},{840,260},{800,200},{860,260},
			{960,100}
		}
	};

	public static float[][][] SIZE=
	{
		{  
			{2000,40},{200,40},{240,80},
			{20,60},{40,40},{20,60},{100,20},{20,20},
			{20,60},{40,40},{20,60},{100,20},{20,20},
			{20,20},{20,20},{20,20},{20,20},{20,180},
			{20,180},{120,20},{20,180},{20,180},{120,20},			
			{20,60},{20,60},{80,20}
		},
		{
			{2000,40},{200,40},{200,40},
			{20,60},{40,40},{20,60},{100,20},{20,20},{20,60},
			{40,40},{20,80},{40,40},{20,180},
			{20,180},{120,20},{20,180},{20,180},{120,20},
			{20,60},{20,60},{80,20}
		},
		{
			{2000,40},{300,40},
			{20,120},{170,20},{40,40},{40,40},
			{20,100},{20,40},{60,20},{20,40},{40,40},{40,40},
			{20,300}
		}
	};

	public static float[][][][] STONE_VERTEX=
	{
		{
			{
		    	{0,0},{2000,0},{2000,40},{0,40}
			},
			{ 
				{20,0},{200,0},{200,40},{0,40}
			},
			{
				{0,0},{220,0},{240,80},{0,80}
			},
			{
				{0,0},{20,0},{20,60},{0,60}
			},
		    {
		    	{0,0},{38,0},{40,20},{32,36},{6,36},{0,20}
		    },
		    {
				{0,0},{20,0},{20,60},{0,60}
		    },
		    {
				{0,0},{100,0},{100,20},{0,20}
		    },
		    {
		    	{0,0},{20,0},{20,20},{0,20}
		    },
			{
				{0,0},{20,0},{20,60},{0,60}
			},
		    {
				{0,0},{38,0},{40,20},{32,36},{6,36},{0,20}
		    },
		    {
				{0,0},{20,0},{20,60},{0,60}
		    },
		    {
				{0,0},{100,0},{100,20},{0,20}
		    },
		    {
		    	{0,0},{20,0},{20,20},{0,20}
		    },
		    {
		    	{0,0},{20,0},{20,20},{0,20}
		    },
		    {
		    	{0,0},{20,0},{20,20},{0,20}
		    },
		    {
		    	{0,0},{20,0},{20,20},{0,20}
		    },
		    {
		    	{0,0},{20,0},{20,20},{0,20}
		    },
			{
				{0,0},{20,0},{20,180},{0,180}
			},
			//�Ҳ�ʯͷ
			{
				{0,0},{20,0},{20,180},{0,180}
			},
			//�Ҳ��Ϸ����ŵ�ʯͷ
		    {
		    	{0,0},{120,0},{120,20},{0,20}
		    },
		    //�Ҳ�ʯͷ
			{
				{0,0},{20,0},{20,180},{0,180}
			},
			//�Ҳ�ʯͷ
			{
				{0,0},{20,0},{20,180},{0,180}
			},
			//�Ҳ��Ϸ����ŵ�ʯͷ
		    {
		    	{0,0},{120,0},{120,20},{0,20}
		    },
		    //�Ҳ�����е�ľ��
			{
				{0,0},{20,0},{20,60},{0,60}
			}, 
			//�Ҳ�����е�ľ��
		    {
				{0,0},{20,0},{20,60},{0,60}
		    },
		    //�Ҳ�������Ϸ��ı���
		    {
				{0,0},{80,0},{80,20},{0,20}
		    }
		},
		//�ڶ�����ͼ������������б������Ķ���Ҫ˳ʱ��	��   
		{
			//����
			{
		    	{0,0},{2000,0},{2000,30},{0,30}
			},
			 //������̨�� 
			{ 
				{20,0},{200,0},{200,40},{0,40}
			},
			//������̨��
			{
				{0,0},{180,0},{200,40},{0,40}
			},
			//�����ֱ����
			{
				{0,0},{20,0},{20,60},{0,60}
			},
			//���Сè
			{
				{0,0},{38,0},{40,20},{32,36},{6,36},{0,20}
			},
			//���ľ��
			{
				{0,0},{20,0},{20,60},{0,60}
			},
			//����Ϸ�����
			{
				{0,0},{100,0},{100,20},{0,20}
			},
			//����Ϸ�ľ��
			{
				{0,0},{20,0},{20,20},{0,20}
			},
			//���ʯͷ
			{
				{0,0},{20,0},{20,60},{0,60}
			},
			//��϶�е�Сè
			{
				{0,0},{38,0},{40,20},{32,36},{6,36},{0,20}
			},
			//�Ҳ�ʯͷ
			{
				{0,0},{20,0},{20,80},{0,80}
			},
			//�Ҳ�Сè
			{
				{0,0},{38,0},{40,20},{32,36},{6,36},{0,20}
			},
			//�Ҳ�ʯͷ
			{
				{0,0},{20,0},{20,180},{0,180}
			},
			//�Ҳ�ʯͷ
			{
				{0,0},{20,0},{20,180},{0,180}
			},
			//�Ҳ��Ϸ����ŵ�ʯͷ
		    {
		    	{0,0},{120,0},{120,20},{0,20}
		    },
		    //�Ҳ�ʯͷ
			{
				{0,0},{20,0},{20,180},{0,180}
			},
			//�Ҳ�ʯͷ
			{
				{0,0},{20,0},{20,180},{0,180}
			},
			//�Ҳ��Ϸ����ŵ�ʯͷ
		    {
		    	{0,0},{120,0},{120,20},{0,20}
		    },
		  //�Ҳ�����е�ľ��
			{
				{0,0},{20,0},{20,60},{0,60}
			}, 
			//�Ҳ�����е�ľ��
		    {
				{0,0},{20,0},{20,60},{0,60}
		    },
		    //�Ҳ�������Ϸ��ı���
		    {
				{0,0},{80,0},{80,20},{0,20}
		    }
		},
		//��������ͼ������������б������Ķ���Ҫ˳ʱ��	��   
		{
			//����
			{
		    	{0,0},{2000,0},{2000,30},{0,30}
			},
			 //������̨�� 
			{ 
				{20,0},{300,0},{300,40},{0,40}
			},
			//�����ֱʯͷ
			{
				{0,0},{20,0},{20,120},{0,120}
			},
			//�����ŵ�ʯͷ
			{
		    	{0,0},{170,0},{170,20},{0,20}
		    },
			//Сè
		    {
				{0,0},{38,0},{40,20},{32,36},{6,36},{0,20}   
			},
			//Сè
		    {
				{0,0},{38,0},{40,20},{32,36},{6,36},{0,20}
			},
			//����Ϸ���ֱľ��
			{
				{0,0},{20,0},{20,100},{0,100}
			},
			//����Ϸ���ֱ����
			{
				{0,0},{20,0},{20,40},{0,40}
			},
			//����Ϸ����ŵı���
			{
				{0,0},{60,0},{60,20},{0,20}
			},
			//����Ϸ���ֱʯͷ
			{
				{0,0},{20,0},{20,40},{0,40}
			},
			//����Ϸ�Сè
		    {
				{0,0},{38,0},{40,20},{32,36},{6,36},{0,20}
			},
			//����Ϸ�Сè
		    {
				{0,0},{38,0},{40,20},{32,36},{6,36},{0,20}
			},
			//����Ϸ���ֱʯͷ
			{
				{0,0},{20,0},{20,300},{0,300}
			}
		}
	};
	
	
	//�Ƿ��Ѿ�ִ�й�changeRatio���� 
	public static boolean changeRatioOkFlag=false;
	//��̬����Ӧ��Ļ�ķ���
	public static void changeRatio()
	{
		if(changeRatioOkFlag)
		{
			return;
		}		
		changeRatioOkFlag=true;
		
		xMainRatio=SCREEN_WIDTH/SCREEN_WIDTH_STANDARD;
		yMainRatio=SCREEN_HEIGHT/SCREEN_HEIGHT_STANDARD;
		
		CJ_WIDTH=CJ_WIDTH*yMainRatio;	
		RATE=RATE*yMainRatio;
		DMGD=DMGD*yMainRatio;
		
		for(int k=0;k<SIZE.length;k++)
		{
			for(int i=0;i<SIZE[k].length;i++)
			{
				SIZE[k][i][0]=SIZE[k][i][0]*yMainRatio;
				SIZE[k][i][1]=SIZE[k][i][1]*yMainRatio;
			}
			
			for(int i=0;i<STONE_VERTEX[k].length;i++)
			{
				for(int j=0;j<STONE_VERTEX[k][i].length;j++)
				{
					STONE_VERTEX[k][i][j][0]=STONE_VERTEX[k][i][j][0]*yMainRatio;
					STONE_VERTEX[k][i][j][1]=STONE_VERTEX[k][i][j][1]*yMainRatio;
				}
			}
			
			for(int i=0;i<LOCATION[k].length;i++)
			{
				LOCATION[k][i][0]=LOCATION[k][i][0]*yMainRatio;
				LOCATION[k][i][1]=LOCATION[k][i][1]*yMainRatio;
			}
		}
	}
	
	//========================================================================================
	//�߳�����ʱ��
	public static int SLEEPTIME=15;
	
	//ͼƬ���Ӵ�������ֵ  
	public static float XOFFSET=1;
	
	//���ð�ť�������µ�λ��
	public static float SET_X_OFFSET=10;
	public static float SET_Y_OFFSET=10;
	//ƫ����
	public static float SET_BACK_CK_OFFSET=10;
	
	//�õ�ͼƬ�ı��
	public static int[] PIC_NUM=
	{
		R.drawable.bg,		//0����ͼƬ
		R.drawable.ground,	//1����ͼƬ
		R.drawable.c_dd,	//2����ǿ׳�Ĳ�
		R.drawable.c_xd,	//3С��
		R.drawable.ls_zy,	//4��������
		R.drawable.ls_by,	//5��������
		R.drawable.play,	//6��ʼ��ť
		R.drawable.set,		//7���ð�ť
		R.drawable.xm,		//8Сè
		R.drawable.set_back,	//9�����еı���
		R.drawable.set_sy,		//10�����е���ѡ�������
		R.drawable.set_xx,		//11�����е���ѡ�������Ϣ�鿴
		R.drawable.set_ck,		//12�����е���ѡ�����÷�������Ϣ
		R.drawable.bg_fg,		//13���Ǳ�����͸��ͼ��
		R.drawable.about,		//14���ڽ���   
		R.drawable.close,		//15 �رհ�ť
		R.drawable.set_close,	//16�ر�������ť
		R.drawable.set_cl,		//17����
		R.drawable.sndls		//18����
	};	
	//ͼƬ����
	public static Bitmap[] PIC_BITMAP;
	//���ź��ͼƬ����
	public static Bitmap[] PE_ARRAY;	
	//����ͼƬ����ķ���
	public static void initBitmap(Resources e)
	{
		//�������
		xMainRatio=SCREEN_WIDTH/SCREEN_WIDTH_STANDARD;
		yMainRatio=SCREEN_HEIGHT/SCREEN_HEIGHT_STANDARD;
		PIC_BITMAP=new Bitmap[PIC_NUM.length];
		//��������
		PE_ARRAY=new Bitmap[PIC_NUM.length];		
		
		for(int i=0;i<PIC_NUM.length;i++)
		{
			PIC_BITMAP[i]=PicLoadUtil.loadBM(e, PIC_NUM[i]);
		}		
		for(int j=0;j<PIC_NUM.length;j++)
		{
			PE_ARRAY[j]=PicLoadUtil.scaleToFitXYRatio(PIC_BITMAP[j], yMainRatio, yMainRatio);
		}
	}	
	//����Ķ�ά����
	public static float[][] LOCALTION_BUTTON;
	//�õ��������
	public static float[][] getLocaltionAndWH()
	{ 
		//���ð�ť�뵯����ť�ļ��
		float SET_SPACE=(PE_ARRAY[7].getWidth()-PE_ARRAY[9].getWidth())/2;
		//������ť������(�����Ӱ�ť)(��Ӧ��Ϊ����x,y,w,h)
		LOCALTION_BUTTON=new float[][]
		{
			//���ð�ť
			{
				SET_X_OFFSET,SCREEN_HEIGHT-PE_ARRAY[7].getHeight()-SET_Y_OFFSET,
				PE_ARRAY[7].getWidth(),PE_ARRAY[7].getHeight()
			},			 
			 //������ð�ť�󵯳���������ť
			{
				SET_X_OFFSET+SET_SPACE,SCREEN_HEIGHT-PE_ARRAY[1].getHeight()-PE_ARRAY[10].getHeight()*2,
				PE_ARRAY[10].getWidth(),PE_ARRAY[10].getHeight()
			},			 
			 //������ð�ť�󵯳��Ĳ鿴��˾��Ϣ��ť
			{
				SET_X_OFFSET+SET_SPACE,SCREEN_HEIGHT-PE_ARRAY[1].getHeight()-PE_ARRAY[11].getHeight()*3,
				PE_ARRAY[11].getWidth(),PE_ARRAY[11].getHeight()
			},			
			//������ð�ť�󵯳��Ĳ鿴������Ϣ��ť
			{
				SET_X_OFFSET+SET_SPACE,SCREEN_HEIGHT-PE_ARRAY[1].getHeight()-PE_ARRAY[12].getHeight()*4,
				PE_ARRAY[12].getWidth(),PE_ARRAY[12].getHeight()
			},			 
			 //������ǡ�PLAY����ť
			{
				(SCREEN_WIDTH-PE_ARRAY[6].getWidth())/2,(SCREEN_HEIGHT-PE_ARRAY[6].getHeight())/2,
				PE_ARRAY[6].getWidth(),PE_ARRAY[6].getHeight()
			}
		};
		return LOCALTION_BUTTON;
	}
}