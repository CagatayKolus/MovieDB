package com.cagataykolus.moviedb.UI.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cagataykolus.moviedb.UI.Fragment.MovieFragmentPopular;
import com.cagataykolus.moviedb.UI.Fragment.MovieFragmentTopRated;
import com.cagataykolus.moviedb.UI.Fragment.MovieFragmentUpcoming;
import com.cagataykolus.moviedb.UI.Fragment.MoviePageFragment;

public class MoviePageAdapter extends FragmentPagerAdapter {
    public MoviePageAdapter(FragmentManager fm)
    {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new MovieFragmentPopular();
            case 1:
                return new MovieFragmentTopRated();
            case 2:
                return new MovieFragmentUpcoming();
        }
        return null;
    }

    @Override
    public int getCount() {
        return MoviePageFragment.int_items;
    }

    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "Popular";
            case 1:
                return "Top Rated";
            case 2:
                return "Upcoming";
        }
        return null;
    }
}
