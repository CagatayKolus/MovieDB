package com.cagataykolus.moviedb.UI.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cagataykolus.moviedb.Model.Movie;
import com.cagataykolus.moviedb.R;
import com.cagataykolus.moviedb.UI.Activity.DetailsActivity;

import java.util.ArrayList;
import java.util.List;

import static com.cagataykolus.moviedb.UI.Util.Util.*;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private List<Movie> movieResults;
    private Context context;

    private boolean isLoadingAdded = false;

    public MovieAdapter(Context context) {
        this.context = context;
        movieResults = new ArrayList<>();
    }

    public List<Movie> getMovies() {
        return movieResults;
    }

    public void setMovies(List<Movie> movieResults) {
        this.movieResults = movieResults;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.movie_list_item, parent, false);
        viewHolder = new MovieVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Movie result = movieResults.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                final MovieVH movieVH = (MovieVH) holder;
                movieVH.tvMovieTitle.setText(result.getTitle());
                movieVH.tvMovieYear.setText(result.getReleaseDate().split("-")[0]);
                movieVH.tvMovieDescription.setText(result.getOverview());
                movieVH.moviePosterURL = result.getPosterPath();

                Glide.with(context)
                        .load(BASE_URL_IMG_W200 + result.getPosterPath())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(movieVH.ivMoviePoster);
                break;
            case LOADING:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return movieResults == null ? 0 : movieResults.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == movieResults.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    // HELPERS
    public void add(Movie r) {
        movieResults.add(r);
        notifyItemInserted(movieResults.size() - 1);
    }

    public void addAll(List<Movie> moveResults) {
        for (Movie result : moveResults) {
            add(result);
        }
    }

    public void remove(Movie r) {
        int position = movieResults.indexOf(r);
        if (position > -1) {
            movieResults.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Movie());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = movieResults.size() - 1;
        Movie result = getItem(position);

        if (result != null) {
            movieResults.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Movie getItem(int position) {
        return movieResults.get(position);
    }


    // VIEW HOLDER
    protected class MovieVH extends RecyclerView.ViewHolder {
        private TextView tvMovieTitle;
        private TextView tvMovieYear;
        private TextView tvMovieDescription;
        private ImageView ivMoviePoster;
        private String moviePosterURL;

        public MovieVH(View itemView) {
            super(itemView);

            tvMovieTitle = itemView.findViewById(R.id.movie_title);
            tvMovieDescription = itemView.findViewById(R.id.movie_desc);
            tvMovieYear = itemView.findViewById(R.id.movie_year);
            ivMoviePoster = itemView.findViewById(R.id.movie_poster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, DetailsActivity.class);
                    i.putExtra("mMovieTitle", tvMovieTitle.getText());
                    i.putExtra("mMovieDesc", tvMovieDescription.getText());
                    i.putExtra("mYear", tvMovieYear.getText());
                    i.putExtra("mPosterImgURL", moviePosterURL);
                    context.startActivity(i);
                }
            });
        }
    }

    protected class LoadingVH extends RecyclerView.ViewHolder {
        public LoadingVH(View itemView) {
            super(itemView);
        }
    }
}
