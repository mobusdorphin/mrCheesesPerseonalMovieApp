package com.clan707.mobusdorphin.mrcheesespersonalmovieapp.model;

/**
 * Created by mobus on 5/2/18.
 */

public class Movie {
    private int id;
    private String title;
    private String posterUrl;
    private String rating;
    private String overview;
    private String date;
    private static final String POSTER_URL_PREFIX = "http://image.tmdb.org/t/p/w185/";

    public Movie(int id, String title, String posterUrl, String rating, String overview, String date) {
        this.id = id;
        this.title = title;
        this.posterUrl = POSTER_URL_PREFIX + posterUrl;
        this.rating = rating;
        this.overview = overview;
        this.date = date;
    }

    public void setId(int id) {this.id = id;}
    public void setTitle(String title) {this.title = title;}
    public void setPosterUrl(String posterUrl) {this.posterUrl = POSTER_URL_PREFIX + posterUrl;}
    public void setRating(String rating) {this.rating = rating;}
    public void setOverview(String overview) {this.overview = overview;}
    public void setDate(String date) {this.date = date;}

    public int getId() {return this.id;}
    public String getTitle() {return this.title;}
    public String getPosterUrl() {return this.posterUrl;}
    public String getRating() {return this.rating;}
    public String getOverview() {return this.overview;}
    public String getDate() {return this.date;}
}
