package com.android.moviebox;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by sangeetha_gsk on 10/27/18.
 */

public class MoviesLoader extends AsyncTaskLoader<List<Movie>> {
    private String mUrl;

    public MoviesLoader(Context context, String url){
        super(context);
        this.mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        // Perform the network request, parse the response, and extract a list of movies.
        List<Movie> newsList = QueryUtils.fetchMovieData(mUrl);
        return newsList;
    }
}
