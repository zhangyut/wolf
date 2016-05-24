package org.niuzuo.criminalintent;

import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import java.util.UUID;
import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

public class CrimeActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeId);
    }
}
