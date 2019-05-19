package com.cagataykolus.moviedb.UI.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cagataykolus.moviedb.Model.Movie;
import com.cagataykolus.moviedb.Model.MoviePageResult;
import com.cagataykolus.moviedb.Network.MovieService;
import com.cagataykolus.moviedb.Network.RetrofitInstance;
import com.cagataykolus.moviedb.R;
import com.cagataykolus.moviedb.UI.Adapter.MovieAdapter;
import com.cagataykolus.moviedb.UI.Util.EndlessRecyclerViewScrollListener;
import com.cagataykolus.moviedb.UI.Util.Util;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cagataykolus.moviedb.UI.Util.Util.API_KEY;
import static com.cagataykolus.moviedb.UI.Util.Util.MOVIES_FIRST_PAGE;
import static com.cagataykolus.moviedb.UI.Util.Util.MOVIES_TOTAL_PAGES;

public class MovieFragmentTopRated extends Fragment {
    public MovieFragmentTopRated() {
    }

    Context context;
    RecyclerView rvRecyclerView;
    ProgressBar pbProgressBar;
    EditText etSearch;
    ImageView ivSearch;
    ImageView ivCancel;
    MovieAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    private int currentPage = MOVIES_FIRST_PAGE;
    private boolean isSearchActive = false;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_fragment_top_rated, container, false);
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        context = v.getContext();
        rvRecyclerView = v.findViewById(R.id.rv_recyclerView);
        pbProgressBar = v.findViewById(R.id.pb_progressBar);
        etSearch = v.findViewById(R.id.et_search);
        ivSearch = v.findViewById(R.id.iv_search);
        ivCancel = v.findViewById(R.id.iv_cancel);
        adapter = new MovieAdapter(context);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

        rvRecyclerView.setLayoutManager(linearLayoutManager);
        rvRecyclerView.setItemAnimator(new DefaultItemAnimator());
        rvRecyclerView.setAdapter(adapter);
        rvRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if(!isSearchActive){
                    currentPage += 1;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadPage(false);
                        }
                    }, 1000);
                }
            }
        });
        loadPage(true);

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.closeKeyboard(v);
                isSearchActive = true;
                searchAndLoad(etSearch.getText().toString());
            }
        });

        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.closeKeyboard(v);
                isSearchActive = false;
                etSearch.setText("");
                adapter.clear();
                currentPage = 1;
                loadPage(true);
            }
        });

        etSearch.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    ivSearch.performClick();
                    Util.closeKeyboard(v);
                    return true;
                }
                return false;
            }
        });
    }

    private void searchAndLoad(String movieName){
        if(!movieName.isEmpty()) {
            MovieService movieDataService = RetrofitInstance.getRetrofitInstance().create(MovieService.class);
            Call<MoviePageResult> call = movieDataService.getSearchResultMovies(API_KEY, movieName);

            call.enqueue(new Callback<MoviePageResult>() {
                @Override
                public void onResponse(Call<MoviePageResult> call, Response<MoviePageResult> response) {
                    assert response.body() != null;
                    adapter.clear();
                    currentPage = 1;
                    List<Movie> results = response.body().getResults();
                    adapter.addAll(results);
                }

                @Override
                public void onFailure(Call<MoviePageResult> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    private void loadPage(final boolean isFirstPage) {
        MovieService movieDataService = RetrofitInstance.getRetrofitInstance().create(MovieService.class);
        Call<MoviePageResult> call = movieDataService.getTopRatedMovies(currentPage, API_KEY);

        call.enqueue(new Callback<MoviePageResult>() {
            @Override
            public void onResponse(Call<MoviePageResult> call, Response<MoviePageResult> response) {
                assert response.body() != null;
                if (isFirstPage) {
                    List<Movie> results = response.body().getResults();
                    adapter.addAll(results);
                    pbProgressBar.setVisibility(View.GONE);
                    if (currentPage <= MOVIES_TOTAL_PAGES)
                        adapter.addLoadingFooter();
                } else {
                    adapter.removeLoadingFooter();
                    List<Movie> results = response.body().getResults();
                    adapter.addAll(results);
                    if (currentPage != MOVIES_TOTAL_PAGES)
                        adapter.addLoadingFooter();
                }
            }

            @Override
            public void onFailure(Call<MoviePageResult> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
