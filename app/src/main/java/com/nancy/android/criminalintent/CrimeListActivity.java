package com.nancy.android.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by nan.zhang on 5/6/15.
 */
public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
