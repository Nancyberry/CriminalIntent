package com.nancy.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by nan.zhang on 5/8/15.
 */
public class CrimePagerActivity extends FragmentActivity implements CrimeFragment.Callbacks {
    private ViewPager mViewPager;
    private ArrayList<Crime> mCrimes;

    @Override
    public void onCrimeUpdated(Crime crime) {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        mCrimes = CrimeLab.get(this).getCrimes();

        FragmentManager fm = getSupportFragmentManager();
        // set PagerAdapter
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                return CrimeFragment.newInstance(mCrimes.get(position).getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        // to show correct Crime title
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) { }

            public void onPageScrolled(int pos, float posOffset, int posOffsetPixels) { }

            public void onPageSelected(int pos) {
                Crime crime = mCrimes.get(pos);
                if (crime.getTitle() != null) {
                    setTitle(crime.getTitle());
                }
            }
        });

        // set index for ViewPager to show correct Crime
        UUID crimeId = (UUID) getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
        for (int i = 0; i < mCrimes.size(); ++i) {
            if (mCrimes.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}