package com.clan707.mobusdorphin.mrcheesespersonalmovieapp.data;

import com.clan707.mobusdorphin.mrcheesespersonalmovieapp.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mobus on 5/2/18.
 */

public class JsonUtils {
    public static Movie[] parseMovieJson(String json) throws JSONException {
        final String MOVIE_NAME = "title";
        final String MOVIE_ID = "id";
        final String MOVIE_TITLE = "title";
        final String MOVIE_POSTER = "poster_path";
        final String MOVIE_RATING = "vote_average";
        final String MOVIE_SYNOPSIS = "overview";
        final String MOVIE_DATE = "release_date";
        final String RESULTS_ROOT = "results";

        JSONObject movieJson = new JSONObject(json);
        JSONArray movieArray = movieJson.getJSONArray(RESULTS_ROOT);
        Movie[] movielist = new Movie[movieArray.length()];

        for (int i = 0; i < movieArray.length(); i++) {
            JSONObject movieObject = movieArray.getJSONObject(i);
            int id = movieObject.getInt(MOVIE_ID);
            String title = movieObject.getString(MOVIE_TITLE);
            String poster = movieObject.getString(MOVIE_POSTER);
            String rating = movieObject.getString(MOVIE_RATING);
            String overview = movieObject.getString(MOVIE_SYNOPSIS);
            String date = movieObject.getString(MOVIE_DATE);
            movielist[i] = new Movie(id, title, poster, rating, overview, date);
        }

        return movielist;

    }
}
