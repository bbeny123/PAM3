package com.bbeny.astroweather.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.bbeny.astroweather.R;
import com.bbeny.astroweather.fragment.ForecastFragment;
import com.bbeny.astroweather.fragment.NonweatherFragment;
import com.bbeny.astroweather.fragment.TodayFragment;
import com.bbeny.astroweather.util.ScreenSlidePagerAdapter;

public class MainActivity extends FragmentActivity  {
    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(findViewById(R.id.weather).getTag().equals("small_screen")) {
            mPager = (ViewPager) findViewById(R.id.weather);
            ScreenSlidePagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
            mPagerAdapter.addFragment(new TodayFragment());
            mPagerAdapter.addFragment(new NonweatherFragment());
            mPagerAdapter.addFragment(new ForecastFragment());
            mPager.setAdapter(mPagerAdapter);
        }
    }

    @Override
    public void onBackPressed() {
        if (findViewById(R.id.weather).getTag().equals("small_screen")) {
            if (mPager.getCurrentItem() == 0)
                super.onBackPressed();
            else
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        } else {
            super.onBackPressed();
        }
    }

}