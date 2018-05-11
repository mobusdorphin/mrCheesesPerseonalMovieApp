package com.clan707.mobusdorphin.mrcheesespersonalmovieapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
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

    public Movie(Parcel p) {
        String[] movieData = new String[6];
        p.readStringArray(movieData);

        this.id = Integer.parseInt(movieData[0]);
        this.title = movieData[1];
        this.posterUrl = movieData[2];
        this.rating = movieData[3];
        this.overview = movieData[4];
        this.date = movieData[5];
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

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.id + "",
                this.title,
                this.posterUrl,
                this.rating,
                this.overview,
                this.date} );
    }
}
