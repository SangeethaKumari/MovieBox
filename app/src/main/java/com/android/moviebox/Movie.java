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

    public Movie(String title, String releaseDate, String posterPath,
                 String backDropPath, String averageVoting, String plotSynopsis) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.backDropPath = backDropPath;
        this.averageVoting = averageVoting;
        this.plotSynopsis = plotSynopsis;
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

}
