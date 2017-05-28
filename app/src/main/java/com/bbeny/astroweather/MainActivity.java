package com.bbeny.astroweather;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Handler;

import com.astrocalculator.AstroCalculator;

public class MainActivity extends FragmentActivity  {
    private ViewPager mPager;
    private ScreenSlidePagerAdapter mPagerAdapter;
    private AstroCalculator astroCalculator;
    private IAstroFragment sun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(findViewById(R.id.astroCalc).getTag().equals("small_screen")) {
            mPager = (ViewPager) findViewById(R.id.astroCalc);
            mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
            mPagerAdapter.addFragment(new SunFragment());
            mPagerAdapter.addFragment(new MoonFragment());
            mPagerAdapter.addFragment(new TimeFragment());
            mPager.setAdapter(mPagerAdapter);
        } else {
            sun = (IAstroFragment) getSupportFragmentManager().findFragmentById(R.id.sunFragment);
        }
        astroInit();
      //  Dupa();

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
             //   IAstroFragment a = (IAstroFragment) mPagerAdapter.getItem(0);
             //   a.update();
                sun.update();
                h.postDelayed(this, delay);
            }
        }, delay);
    }

}