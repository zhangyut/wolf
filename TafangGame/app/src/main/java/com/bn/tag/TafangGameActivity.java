package com.bn.tag;


import java.util.List;
import java.util.Vector;





import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import static com.bn.tag.Constant.*;

enum WhichView {WELCOME_VIEW,GAME_VIEW,MUSIC_VIEW,HELP_VIEW,JIFEN_VIEW,MAINMENU_VIEW};
public class TafangGameActivity extends Activity {
	GameView gameView;	
	WelcomeView welcomeview;
	MainMenuSurfaceView MainMenuView;//������
	HighJifenSurfaceView ScoreView;//���ֽ���
	MusicSurfaceView musicView;//�������ý���
	GameOverView gameoverView;
	HelpView helpview;
	static SQLiteDatabase sldd;
	Dialog Inputdialog;//��Ϸ�浵�Ի���
	Dialog GoonGamedialog;//������Ϸ�Ի���
	String name;//������Ϸ�Ի�����ѡ���name
	int screenWidth;
	int screenHeight;
	List<String> nearlist=new Vector<String>();
	//�������ű�־λ
	private boolean backGroundMusicOn=true;//���������Ƿ����ı�־
	private boolean soundOn=true;//��Ч�Ƿ����ı�־
	boolean cundang_Flag=false;
	
	//��
	Vibrator mVibrator;//��������
	boolean shakeflag=true;//�Ƿ���
	
	
	WhichView curr;
	Handler myHandler = new Handler(){//�������SurfaceView���͵���Ϣ
		@Override
		public void handleMessage(Message msg) {
        	switch(msg.what)
        	{
        	case 0:
        		gotoGameView();
        		break;
        	case 1:
        		gotoMainMenuView();
        		break;
        	case 2:
        		gotoHelpView();
        		break;
        	case 3:
        		gotoJifenView();
        		break;
        	case 4:
        		gotoMusicView();
        		break;
        	case 5:
        		gotoGameOverView();
        		break;
        	}
        }
	};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth=dm.widthPixels;
        screenHeight=dm.heightPixels;
		//ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//����Ϊ����
		collisionShake();
		gotoWelcomeView();      
    }   
    
    //��ӭ����
    private void gotoWelcomeView()
    {
    	if(welcomeview==null)
    	{
    		 welcomeview = new WelcomeView(this);
    	}
    	this.setContentView(welcomeview);
    	curr=WhichView.WELCOME_VIEW;
    }
    
    private void gotoGameOverView()
    {
    	if(gameoverView==null)
    	{
    		gameoverView=new GameOverView(this);
    	}
    	this.setContentView(gameoverView);
    }
    //��Ϸ����
    private void gotoGameView()
    {
    	gameView=new GameView(this);   	
    	this.setContentView(gameView);
    	curr=WhichView.GAME_VIEW;
    	
    }
    //������
    private void gotoMainMenuView()
    {
    	MainMenuView=new MainMenuSurfaceView(this);
    	this.setContentView(MainMenuView);
    	curr=WhichView.MAINMENU_VIEW;
    }
    //��������
    public void gotoHelpView()
    {
    	if(helpview==null)
    	{
    		helpview=new HelpView(this);
    	}
    	this.setContentView(helpview);
    	curr=WhichView.HELP_VIEW;
    }
    //���ֽ���
    public void gotoJifenView()
    {
    	if(ScoreView==null)
    	{
    		ScoreView=new HighJifenSurfaceView(this);
    	}
    	this.setContentView(ScoreView);
    	curr=WhichView.JIFEN_VIEW;
    }
    //�������ý���
    public void gotoMusicView()
    {
    	if(musicView==null)
    	{
    		musicView=new MusicSurfaceView(this);
    	}
    	this.setContentView(musicView);
    	curr=WhichView.MUSIC_VIEW;
    }
    //�����Ի���
    public void gototachu()
    {
    	showDialog(CUN_DANG_DIALOG_ID);
    }
    //������Ϸ�����Ի���
    public void gotoTaChuGame()
    {
    	
    	nearlist=DBUtil.getcundang();
    	showDialog(GO_ONGAME_DIALOG_ID);
    }
    //������Ϣ
    public void sendMessage(int what)
    {
    	Message msg1 = myHandler.obtainMessage(what); 
    	myHandler.sendMessage(msg1);
    } 
    //����������ű�־λ�ĺ���
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
    
	
	//�ֻ���
    public void collisionShake()
    {
    		mVibrator=(Vibrator)getApplication().getSystemService
            (Service.VIBRATOR_SERVICE);	
    }
    //��
    public void shake()
    {
    	if(shakeflag)
    	{
    		mVibrator.vibrate( new long[]{0,80},-1);
    	}
    }
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent e) 
    {
    	if(keyCode==4)//������һ������ļ�
    	{
    		if(curr==WhichView.HELP_VIEW)
    		{
    			gotoMainMenuView();
    			return true;
    		}
    		if(curr==WhichView.JIFEN_VIEW||curr==WhichView.MUSIC_VIEW)
    		{
    			gotoMainMenuView();
    			return true;
    		}
    	}
		return false;
    		
    }
    
    public static void openOrCreateDatabase()
    {
    	try
    	{
	    	sldd=SQLiteDatabase.openDatabase
	    	(
	    			"/data/data/com.bn.tag/ttdb", //���ݿ�����·��
	    			null, 								//CursorFactory
	    			SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.CREATE_IF_NECESSARY //��д�����������򴴽�
	    	);
	    	String sql="create table if not exists highScore" +
	    			"( " +
	    			"score integer," +
	    			"date varchar(20)" +
	    			");";
	    	sldd.execSQL(sql);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
	
	//�ر����ݿ�ķ���
    public void closeDatabase()
    {
    	try
    	{
	    	sldd.close();
    	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
    
  //�����¼�ķ���
    public void insert(int score,String date)
    {
    	try
    	{
    		openOrCreateDatabase();
        	String sql="insert into highScore values("+score+",'"+date+"');";
        	sldd.execSQL(sql);
        	sldd.close();
    	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
    //��ѯ�ķ���
    public String query(int posFrom,int length)//��ʼ��λ�ã�Ҫ��Ѱ�ļ�¼����
    {
    	StringBuilder sb=new StringBuilder();//Ҫ���صĽ��
    	Cursor cur=null;
    	openOrCreateDatabase();
        String sql="select score,date from highScore order by score desc;";    	
        cur=sldd.rawQuery(sql, null);
    	try
    	{
    		
        	cur.moveToPosition(posFrom);//���α��ƶ���ָ���Ŀ�ʼλ��
        	int count=0;//��ǰ��ѯ��¼����
        	while(cur.moveToNext()&&count<length)
        	{
        		int score=cur.getInt(0);
        		String date=cur.getString(1);        		
        		sb.append(score);
        		sb.append("/");
        		sb.append(date);
        		sb.append("/");//����¼��"/"�ָ���
        		count++;
        	}        			
    	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			cur.close();
			closeDatabase();
		}
		//ת�����ַ���������
		return sb.toString();
    }
    //�õ����ݿ��м�¼�����ķ���
    public int getRowCount()
    {
    	int result=0;
    	Cursor cur=null;
    	openOrCreateDatabase();
    	try
    	{
    		String sql="select count(score) from highScore;";    	
            cur=sldd.rawQuery(sql, null);
        	if(cur.moveToNext())
        	{
        		result=cur.getInt(0);
        		
        	}
    	}
    	catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			cur.close();
			closeDatabase();
		}
        
    	return result;
    }
    //���÷ֺ�ʱ��������ݿ⣬����ת����Ӧ�Ľ�������
    public void insertScoreAndDate(int currScore)
    {
    	//Cursor cur=null;
    	//openOrCreateDatabase();//�򿪻򴴽����ݿ�
    	try
    	{	
        	insert(currScore,DateUtil.getCurrentDate());//��õ�ǰ���������ڲ��������ݿ�        	
    	}
		catch(Exception e)
		{
			Toast.makeText(this, "���ݿ����"+e.toString(), Toast.LENGTH_SHORT).show();
		}
		finally
		{
			//closeDatabase();
			//cur.close();
			
		}		
    }
    
    @Override
    public Dialog onCreateDialog(int id)//�����Ի���
    {    	
        Dialog result=null;
    	switch(id)
    	{
	    	case CUN_DANG_DIALOG_ID://��������Ի���
		    	Inputdialog=new MyDialogue(this); 	    
				result=Inputdialog;				
			break;
	    	case GO_ONGAME_DIALOG_ID:	    		
	    		AlertDialog.Builder b2=new AlertDialog.Builder(this); 
				  b2.setItems(
					null, 
				    null
				   );
				  GoonGamedialog=b2.create();       //����Dialog
				  result=GoonGamedialog;	    		
		    	break;
    	}   
		return result;
    }
    
    //ÿ�ε����Ի���ʱ���ص��Զ�̬���¶Ի������ݵķ���
    @Override
    public void onPrepareDialog(int id, final Dialog dialog)
    {
    	//�����ǵȴ��Ի����򷵻�
    	switch(id)
    	{
    	   case CUN_DANG_DIALOG_ID://��������Ի���
    		   //ȷ����ť
    		   Button bjhmcok=(Button)Inputdialog.findViewById(R.id.bjhmcOk);
    		   //ȡ����ť
       		   Button bjhmccancel=(Button)Inputdialog.findViewById(R.id.bjhmcCancle);
       		   //��ȷ����ť��Ӽ�����
       		   bjhmcok.setOnClickListener
               (
    	          new OnClickListener()
    	          {
    				@Override
    				public void onClick(View v) 
    				{
    					//��ȡ�Ի���������ݲ���Toast��ʾ
    					EditText et=(EditText)Inputdialog.findViewById(R.id.etname);
    					String name=et.getText().toString();
    					 String currentDate=DateUtil.getCurrentDate();
    					 String sql1="insert into save values('"+name+"','"+currentDate+"')";
    					 DBUtil.updatetable(sql1);    					 
    					 //�õ���������
    					 for(int i=0;i<gameView.jiantaList.size();i++)
    					 {    						
    						 String sql="insert into jiant values ('"+name+"','"+gameView.jiantaList.get(i).clo+"','"+gameView.jiantaList.get(i).row+"','"+gameView.jiantaList.get(i).state+"')";
    						 DBUtil.updatetable(sql);
    					 }
    					 //�õ���������
    					 for(int i=0;i<gameView.alTarget1.size();i++)
    					 {    						 
    						 String sql="insert into guaiw values ('"+name+"','"+gameView.alTarget1.get(i).ballx+"','"+gameView.alTarget1.get(i).bally+"','"+gameView.alTarget1.get(i).state+"','"+gameView.alTarget1.get(i).direction*180/Math.PI+"','"+gameView.alTarget1.get(i).ii+"','"+gameView.alTarget1.get(i).bloodsum+"','"+gameView.alTarget1.get(i).bloodsumNO+"')";
    						 DBUtil.updatetable(sql);
    					 }
    					 String sql="insert into nochange values ('"+name+"','"+gameView.doller+"','"+gameView.bloodNUM+"','"+gameView.shaNUM+"','"+gameView.shuijingMiddleNum+"')";
    					 DBUtil.updatetable(sql);
    					//�رնԻ���
    					Inputdialog.cancel();
    				}        	  
    	          }
    	        );   
       		    //��ȡ����ť��Ӽ�����
       		    bjhmccancel.setOnClickListener
	            (
	 	          new OnClickListener()
	 	          {
	 				@Override
	 				public void onClick(View v) 
	 				{
	 					//�رնԻ���
	 					Inputdialog.cancel();					
	 				}        	  
	 	          }
	 	        );   
    	   break;
    	   case GO_ONGAME_DIALOG_ID:
    			//�Ի����Ӧ���ܴ�ֱ����LinearLayout
    		   	LinearLayout ll=new LinearLayout(TafangGameActivity.this);
    			ll.setOrientation(LinearLayout.VERTICAL);		//���ó���	
    			ll.setGravity(Gravity.CENTER_HORIZONTAL);   			
    			ll.setBackgroundResource(R.drawable.dialog);
    			
    			//�����е�ˮƽLinearLayout
    			LinearLayout lln=new LinearLayout(TafangGameActivity.this);
    			lln.setOrientation(LinearLayout.HORIZONTAL);		//���ó���	
    			lln.setGravity(Gravity.CENTER);    		
    			
    			//�����е�����
    			TextView tvTitle=new TextView(TafangGameActivity.this);
    			tvTitle.setText("ѡ����Ϸ��Ӧ�浵");
    			tvTitle.setTextSize(20);//���������С
    			tvTitle.setTextColor(Color.WHITE);//����������ɫ
    			lln.addView(tvTitle);
    			
    			//����������ӵ���LinearLayout
    			ll.addView(lln);		
    		   	
    		   	//Ϊ�Ի����е���ʷ��¼��Ŀ����ListView
    		    //��ʼ��ListView
    	        ListView lv=new ListView(this);
    	        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);         		   
    		        BaseAdapter ba=new BaseAdapter()
    		        {
    					@Override
    					public int getCount() {
    						return nearlist.size();//�ܹ�����ѡ��
    					}

    					@Override
    					public Object getItem(int arg0) { return null; }

    					@Override
    					public long getItemId(int arg0) { return 0; }

    					@Override
    					public View getView(int arg0, View arg1, ViewGroup arg2) {
    						//��̬����ÿ����ʷ��¼��Ӧ��TextView
    						TextView tv=new TextView(TafangGameActivity.this);
    						tv.setGravity(Gravity.CENTER);
    						tv.setText(nearlist.get(arg0));//��������
    						tv.setTextSize(20);//���������С
    						tv.setTextColor(Color.WHITE);//����������ɫ
    						tv.setPadding(0,0,0,0);//������������				
    						return tv;
    					}        	
    		        };       
    		        lv.setAdapter(ba);//ΪListView��������������
    		        
    		        //����ѡ������ļ�����
    		        lv.setOnItemClickListener(
    		           new OnItemClickListener()
    		           {
    					@Override
    					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
    							long arg3) {//��дѡ������¼��Ĵ�����   						
    						name=nearlist.get(arg2);
    						cundang_Flag=true;
    						gotoGameView();			
    						GoonGamedialog.cancel();
    					}        	   
    		           }
    		        );             
    		        //����ʷ��¼����ListView������LinearLayout
    		        ll.addView(lv);
    		        dialog.setContentView(ll);
    		   	}
    	}
}    
    
    

	
