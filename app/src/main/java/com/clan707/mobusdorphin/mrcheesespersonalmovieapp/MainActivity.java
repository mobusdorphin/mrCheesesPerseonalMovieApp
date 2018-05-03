package com.clan707.mobusdorphin.mrcheesespersonalmovieapp;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;

import com.clan707.mobusdorphin.mrcheesespersonalmovieapp.data.JsonUtils;
import com.clan707.mobusdorphin.mrcheesespersonalmovieapp.data.TMDBConnector;
import com.clan707.mobusdorphin.mrcheesespersonalmovieapp.databinding.ActivityMainBinding;
import com.clan707.mobusdorphin.mrcheesespersonalmovieapp.model.Movie;

import org.json.JSONException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Movie[]> {

    ActivityMainBinding binding;
    private MovieAdapter movieAdapter;

    private static final int MOVIE_LOADER_ID = 0;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        GridLayoutManager layoutManager
                = new GridLayoutManager(this,2);
        binding.rvMainActivity.setHasFixedSize(true);
        binding.rvMainActivity.setLayoutManager(layoutManager);
        movieAdapter = new MovieAdapter();
        binding.rvMainActivity.setAdapter(movieAdapter);

        LoaderManager.LoaderCallbacks<Movie[]> callback = MainActivity.this;
        getSupportLoaderManager().initLoader(MOVIE_LOADER_ID, null, callback);
    }

    @Override
    public Loader<Movie[]> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Movie[]>(this) {
            Movie[] movieList = null;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                forceLoad();
            }

            @Override
            public Movie[] loadInBackground() {
                try {
                    try {
                        TMDBConnector.SortMethod method = TMDBConnector.SortMethod.SORT_POPULAR;
                        String movieJson = TMDBConnector.getMovieJSON(method);
                        Log.d(TAG, "loadInBackground: " + movieJson);
                        movieList = JsonUtils.parseMovieJson(movieJson);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return movieList;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Movie[]> loader, Movie[] data) {
        movieAdapter.setMovieList(data);
    }

    @Override
    public void onLoaderReset(Loader<Movie[]> loader) {

    }
}
