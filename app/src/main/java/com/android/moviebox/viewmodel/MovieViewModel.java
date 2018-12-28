package com.android.moviebox.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.android.moviebox.Movie;
import com.android.moviebox.data.MovieRoomDatabase;

import java.util.List;

/**
 * Created by sangeetha_gsk on 12/27/18.
 */

public class MovieViewModel extends AndroidViewModel {

    private final LiveData<List<Movie>> movie;
    private MovieRoomDatabase movieRoomDatabase;

    public MovieViewModel(Application application) {
        super(application);
        movieRoomDatabase = MovieRoomDatabase.getDatabase(this.getApplication());
        movie = movieRoomDatabase.movieModel().getAllFavoriteMovies();
    }

    /* Getter for the movie variable, returns a LiveData<List<Movie>> object */
    public LiveData<List<Movie>> getMovie() {
        return movie;
    }


    public void addFavoriteMovie(final Movie movie) {
        new addFavoriteMovieAsyncTask(movieRoomDatabase).execute(movie);
    }

    public void deleteFavoriteMovie(final Movie movie) {
        new deleteFavoriteMovieAsyncTask(movieRoomDatabase).execute(movie);
    }

    private static class addFavoriteMovieAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieRoomDatabase db;

        addFavoriteMovieAsyncTask(MovieRoomDatabase movieRoomDatabase) {
            db = movieRoomDatabase;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            db.movieModel().insertFavoriteMovie(movies[0]);
            return null;
        }
    }

    private static class deleteFavoriteMovieAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieRoomDatabase db;

        deleteFavoriteMovieAsyncTask(MovieRoomDatabase movieRoomDatabase) {
            db = movieRoomDatabase;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            db.movieModel().deleteMovie(movies[0]);
            return null;
        }

    }
}
