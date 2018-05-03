package com.clan707.mobusdorphin.mrcheesespersonalmovieapp.data;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by mobus on 5/2/18.
 */

public class TMDBConnector {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String PATH_MOVIE = "movie";

    public enum SortMethod {
        SORT_TOP ("top_rated"),
        SORT_POPULAR ("popular");

        private final String method;
        SortMethod(String method) {
            this.method = method;
        }

        public String getMethod() {
            return this.method;
        }
    }

    public static String getMovieJSON(SortMethod sortMethod) throws IOException {
        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(PATH_MOVIE)
                .appendPath(sortMethod.getMethod())
                .appendQueryParameter("api_key", ApiKey.API_KEY)
                .build();
        URL movieUrl;
        try {
            movieUrl = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

        // Pulled from Sunshine NetworkUtils
        HttpURLConnection connection = (HttpURLConnection) movieUrl.openConnection();

        try {
            InputStream in = connection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {
            connection.disconnect();
        }
    }
}
