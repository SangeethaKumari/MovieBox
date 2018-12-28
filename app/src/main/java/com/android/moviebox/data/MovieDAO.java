package com.android.moviebox.data;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.android.moviebox.Movie;

import java.util.List;

/**
 * Created by sangeetha_gsk on 12/27/18.
 */

@Dao
public interface MovieDAO {

    @Query("SELECT * FROM favorite_movies")
    LiveData<List<Movie>> getAllFavoriteMovies();

    @Insert
    void insertFavoriteMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Query("SELECT COUNT (*) FROM favorite_movies")
    int getMovieCount();
}
