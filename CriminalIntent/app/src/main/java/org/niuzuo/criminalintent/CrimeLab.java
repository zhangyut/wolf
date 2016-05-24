package org.niuzuo.criminalintent;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by zdns on 16/5/11.
 */
public class CrimeLab {
    private static final String TAG = "CrimeLab";
    private static final String FILENAME= "crimes.json";
    private static CrimeLab sCrimeLab;
    private CriminalIntentJSONSerializer mSerializer;
    private Context mAppContext;
    private ArrayList<Crime> mCrimes;

    private CrimeLab(Context appContext) {
        mAppContext = appContext;
        mSerializer = new CriminalIntentJSONSerializer(mAppContext, FILENAME);
        //mCrimes = new ArrayList<Crime>();
        try {
            mCrimes = mSerializer.loadCrimes();
        } catch (Exception e) {
            mCrimes = new ArrayList<Crime>();
            Log.d(TAG, "error load crimes", e);
        }
    }
    public static CrimeLab get(Context c) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }

        return sCrimeLab;
    }

    public ArrayList<Crime> getmCrimes() {
        return mCrimes;
    }

    public void setmCrimes(ArrayList<Crime> mCrimes) {
        this.mCrimes = mCrimes;
    }
    public Crime getCrime(UUID id) {
        for (Crime c : mCrimes) {
            Log.d("uuid", c.getmId().toString(), null);
            if (c.getmId().toString().equals(id.toString())) {
                Log.d("get-crime", "true", null);
                return c;
            }
        }
        Log.d("get-crime", "false", null);
        return null;
    }
    public void addCrime(Crime c) {
        mCrimes.add(c);
    }

    public boolean saveCrimes() {
        try {
            mSerializer.saveCrimes(mCrimes);
            Log.d(TAG, "crimes save to files");
            return true;
        } catch (Exception e) {
            Log.d(TAG, "error saving crimes", e);
            return false;
        }
    }
}
