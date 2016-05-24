package org.niuzuo.mydemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button mNextButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private TrueFalse[] mQuestionBank = new TrueFalse[] {
        new TrueFalse(R.string.question_ocean, true),
        new TrueFalse(R.string.question_mideast, false),
    };
    private int mCurrentIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        if (mQuestionTextView != null) {
            mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getmQuestion());
        }
        mNextButton = (Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mCurrentIndex == 0) {
                    mCurrentIndex = 1;
                } else {
                    mCurrentIndex = 0;
                }
                mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getmQuestion());
            }
        });

        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CheatActive.class);
                //Intent i = new Intent();
                i.putExtra("org.niuzuo.mydemo.main_activity", "朋友");
                Log.d("cheat", "debug :  start cheat active", null);
                startActivityForResult(i, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String ret = data.getStringExtra("org.niuzuo.mydemo.cheat_active");
            mQuestionTextView.setText(ret);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 为ActionBar扩展菜单项
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.menu_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 处理动作按钮的点击事件
        switch (item.getItemId()) {
            case R.id.action_search:
                mQuestionTextView.setText("search");
                Log.d("item", "debug : search 111", null);
                return true;
            case R.id.action_settings:
                mQuestionTextView.setText("setting");
                Log.d("item", "debug : settings 222", null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
