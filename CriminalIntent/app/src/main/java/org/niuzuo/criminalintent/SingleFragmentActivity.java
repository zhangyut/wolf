package org.niuzuo.criminalintent;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by zdns on 16/5/11.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {
    protected abstract Fragment createFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.framentContainer);
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction().add(R.id.framentContainer, fragment).commit();
        }
    }
}
