package com.bbeny.astroweather;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Handler;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

public class MainActivity extends FragmentActivity  {
    private ViewPager mPager;
    private ScreenSlidePagerAdapter mPagerAdapter;
    private AstroCalculator astroCalculator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPagerAdapter.addFragment(new SunFragment());
        mPagerAdapter.addFragment(new MoonFragment());
        mPager.setAdapter(mPagerAdapter);
        astroInit();
       Dupa();
    }

    private void astroInit() {
        astroCalculator = AstroTools.getAstroCalculator();
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0)
            super.onBackPressed();
        else
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
    }

    public void Dupa() {
      //  setText.setText("b");
        final Handler h = new Handler();
        final int delay = 1000; //milliseconds
        h.postDelayed(new Runnable(){
            public void run(){
                IAstroFragment a = (IAstroFragment) mPagerAdapter.getItem(0);
             //   a.update();
                h.postDelayed(this, delay);
            }
        }, delay);
    }

}