package com.android.moviebox;

/**
 * Created by sangeetha_gsk on 10/17/18.
 */

public class Movie {

    private String title;
    private String releaseDate;
    private String posterPath;
    private String backDropPath;
    private String averageVoting;
    private  String plotSynopsis;
    private String category;
    private int imageId;
    private String movieId;

    private String[] movieTrailerIds;
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

}
