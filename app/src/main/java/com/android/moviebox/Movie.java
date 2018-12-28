package com.android.moviebox;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by sangeetha_gsk on 10/17/18.
 */

@Entity(tableName = "favorite_movies")

public class Movie {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "MOVIE_ID")
    private String movieId;

    @ColumnInfo(name = "TITLE")
    private String title;

    @ColumnInfo(name = "RELEASE_DATE")
    private String releaseDate;

    @ColumnInfo(name = "MOVIE_POSTER_PATH")
    private String posterPath;

    @ColumnInfo(name = "COLUMN_USER_RATING")
    private String averageVoting;

    @ColumnInfo(name = "MOVIE_PLOT_SYNOPSIS")
    private  String plotSynopsis;

    @Ignore
    private String category;
    @Ignore
    private int imageId;
    @Ignore
    private String backDropPath;
    @Ignore
    private byte[] movieImage;
    @Ignore
    private String[] movieTrailerIds;
    @Ignore
    private String[] movieReviews;

    public Movie() {
    }

    public Movie(String title, String releaseDate, String posterPath,
                 String backDropPath, String averageVoting, String plotSynopsis, String movieId) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.backDropPath = backDropPath;
        this.averageVoting = averageVoting;
        this.plotSynopsis = plotSynopsis;
        this.movieId = movieId;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackDropPath() {
        return backDropPath;
    }

    public void setBackDropPath(String backDropPath) {
        this.backDropPath = backDropPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getAverageVoting() {
        return averageVoting;
    }

    public void setAverageVoting(String averageVoting) {
        this.averageVoting = averageVoting;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String[] getMovieTrailerIds() {
        return movieTrailerIds;
    }

    public void setMovieTrailerIds(String[] movieTrailerIds) {
        this.movieTrailerIds = movieTrailerIds;
    }

    public String[] getMovieReviews() {
        return movieReviews;
    }

    public void setMovieReviews(String[] movieReviews) {
        this.movieReviews = movieReviews;
    }

    public byte[] getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(byte[] movieImage) {
        this.movieImage = movieImage;
    }

}
