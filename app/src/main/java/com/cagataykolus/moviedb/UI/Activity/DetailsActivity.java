package com.cagataykolus.moviedb.UI.Activity;

import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cagataykolus.moviedb.R;

import static com.cagataykolus.moviedb.UI.Util.Util.*;

public class DetailsActivity extends AppCompatActivity {
    private SharedPreferences myPrefs;
    TextView tvMovieTitle, tvMovieYear, tvMovieDescription;
    ImageView ivMoviePoster, ivMovieFavorite;
    boolean isFavorited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initView();
        getDeatilsData();

        ivMovieFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorited) {
                    deleteFavorites(tvMovieTitle.getText().toString());
                    ivMovieFavorite.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
                    isFavorited = false;
                    Toast.makeText(DetailsActivity.this, "Removed from favorites.", Toast.LENGTH_SHORT).show();
                } else {
                    setFavorites(tvMovieTitle.getText().toString());
                    ivMovieFavorite.setColorFilter(getResources().getColor(R.color.colorFavorite), PorterDuff.Mode.SRC_ATOP);
                    isFavorited = true;
                    Toast.makeText(DetailsActivity.this, "Added to favorites.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void initView() {
        tvMovieTitle = findViewById(R.id.tv_movie_title);
        tvMovieYear = findViewById(R.id.tv_movie_year);
        tvMovieDescription = findViewById(R.id.tv_movie_description);
        ivMoviePoster = findViewById(R.id.iv_movie_poster);
        ivMovieFavorite = findViewById(R.id.iv_movie_favorite);
    }

    public void getDeatilsData() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            tvMovieTitle.setText(extras.getString("mMovieTitle"));
            tvMovieYear.setText(extras.getString("mYear"));
            tvMovieDescription.setText(extras.getString("mMovieDesc"));
            Glide.with(this)
                    .load(BASE_URL_IMG_W500 + extras.getString("mPosterImgURL"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    //.centerCrop()
                    .into(ivMoviePoster);
            getFavorites(extras.getString("mMovieTitle"));
        }
    }

    private void setFavorites(String s) {
        myPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.putString(s, "favorite");
        editor.apply();
    }

    private void getFavorites(String s) {
        myPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (myPrefs.getString(s, "not_favorite").equals("favorite")) {
            ivMovieFavorite.setColorFilter(getResources().getColor(R.color.colorFavorite), PorterDuff.Mode.SRC_ATOP);
            isFavorited = true;
        } else {
            ivMovieFavorite.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
            isFavorited = false;
        }
    }

    private void deleteFavorites(String s) {
        myPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.putString(s, "not_favorite");
        editor.apply();
    }
}
