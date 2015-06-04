package com.nancy.android.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by nan.zhang on 5/5/15.
 * Singleton class
 */
public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private Context mAppContext;        // Context for what?
    private ArrayList<Crime> mCrimes;

    private CrimeLab(Context appContext) {
        mAppContext = appContext;
        mCrimes = new ArrayList<Crime>();

        // temporary for test
        for (int i = 0; i < 100; ++i) {
            Crime c = new Crime();
            c.setTitle("Crime #" + i);
            c.setSolved(i % 2 == 0);
            mCrimes.add(c);
        }
    }

    public static CrimeLab get(Context c) {
        if (null == sCrimeLab) {
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }
        return sCrimeLab;
    }

    public ArrayList<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id) {
        for (Crime c : mCrimes) {
            if (c.getId().equals(id)) {
                return c;
            }
        }

        return null;
    }
}
