package org.niuzuo.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by zdns on 16/5/11.
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
