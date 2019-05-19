package com.cagataykolus.moviedb.Network;

import com.cagataykolus.moviedb.Model.MoviePageResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

@SuppressWarnings("ALL")
public interface MovieService {
    @GET("movie/popular")
    Call<MoviePageResult> getPopularMovies(@Query("page") int page, @Query("api_key") String userkey);

    @GET("movie/top_rated")
    Call<MoviePageResult> getTopRatedMovies(@Query("page") int page, @Query("api_key") String userkey);

    @GET("movie/upcoming")
    Call<MoviePageResult> getUpcomingMovies(@Query("page") int page, @Query("api_key") String userkey);

    @GET("search/movie")
    Call<MoviePageResult> getSearchResultMovies(@Query("api_key") String userkey, @Query("query") String query);
}