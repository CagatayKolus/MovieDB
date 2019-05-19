package com.cagataykolus.moviedb.UI.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cagataykolus.moviedb.R;
import com.cagataykolus.moviedb.UI.Adapter.MoviePageAdapter;

public class MoviePageFragment extends Fragment {

    public TabLayout tabLayout;
    public ViewPager viewPager;
    public static int int_items = 3;

    Context context;
    public MoviePageFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movies_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        context = v.getContext();
        tabLayout = v.findViewById(R.id.tabs);
        viewPager = v.findViewById(R.id.viewpager);

        viewPager.setAdapter(new MoviePageAdapter(getChildFragmentManager()));

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
                tabLayout.getTabAt(0).setIcon(R.drawable.popular);
                tabLayout.getTabAt(1).setIcon(R.drawable.top_rated);
                tabLayout.getTabAt(2).setIcon(R.drawable.upcoming);
            }
        });
    }
}
