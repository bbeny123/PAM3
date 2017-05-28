package com.bbeny.astroweather;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class MainActivity extends FragmentActivity  {
    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(findViewById(R.id.astroCalc).getTag().equals("small_screen")) {
            mPager = (ViewPager) findViewById(R.id.astroCalc);
            ScreenSlidePagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
            mPagerAdapter.addFragment(new SunFragment());
            mPagerAdapter.addFragment(new MoonFragment());
            mPagerAdapter.addFragment(new TimeFragment());
            mPager.setAdapter(mPagerAdapter);
        }
    }

    @Override
    public void onBackPressed() {
        if (findViewById(R.id.astroCalc).getTag().equals("small_screen")) {
            if (mPager.getCurrentItem() == 0)
                super.onBackPressed();
            else
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        } else {
            super.onBackPressed();
        }
    }

}