package org.niuzuo.mydemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

/**
 * Created by zdns on 16/5/4.
 */
public class CheatActive extends android.app.Activity {
    TextView m1;
    TextView m2;
    TextView m3;
    Button mWho;

    String mMethod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_cheat);
        m1 = (TextView) findViewById(R.id.answerTextView);
        m1.setText("敌人");
        mMethod = getIntent().getStringExtra("org.niuzuo.mydemo.main_activity");
        Log.d("0000000", mMethod, null);
        mWho = (Button)findViewById(R.id.who);
        mWho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("111111111", mMethod, null);
                m1.setText(mMethod);
                Intent data = new Intent();
                if (mMethod.equals("朋友")) {
                    data.putExtra("org.niuzuo.mydemo.cheat_active", "好酒好肉");
                } else{
                    data.putExtra("org.niuzuo.mydemo.cheat_active", "给你吃枪子");
                }
                setResult(0, data);
            }
        });
    }
}
