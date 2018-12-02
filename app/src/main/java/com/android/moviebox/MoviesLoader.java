package com.android.moviebox;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by sangeetha_gsk on 10/27/18.
 */

public class MoviesLoader extends AsyncTaskLoader<List<Movie>> {
    private String mUrl;
    private int mRequetType;

    public MoviesLoader(Context context, String url,int requestType){
        super(context);
        this.mUrl = url;
        this.mRequetType = requestType;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {
        List<Movie> moviesList = null;
        if (mUrl == null) {
            return null;
        }
        if(mRequetType == 1) {
            // Perform the network request, parse the response, and extract a list of movies details.
            moviesList = QueryUtils.fetchMovieData(mUrl);
        } else if (mRequetType == 2){
            // Perform the network request, parse the response, and extract a list of movie trailers.
            moviesList = QueryUtils.fetchMovieTrailer(mUrl);
        }else if (mRequetType == 3){
            // Perform the network request, parse the response, and extract a list of movie reviews.
            moviesList = QueryUtils.fetchMovieReview(mUrl);
        }
        return moviesList;
    }
}
