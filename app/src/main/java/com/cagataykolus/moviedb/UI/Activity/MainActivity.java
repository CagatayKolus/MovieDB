package com.cagataykolus.moviedb.UI.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cagataykolus.moviedb.R;
import com.cagataykolus.moviedb.UI.Fragment.MoviePageFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, new MoviePageFragment()).commit();
    }
}
