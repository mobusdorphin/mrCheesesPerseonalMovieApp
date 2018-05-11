package com.clan707.mobusdorphin.mrcheesespersonalmovieapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.clan707.mobusdorphin.mrcheesespersonalmovieapp.data.JsonUtils;
import com.clan707.mobusdorphin.mrcheesespersonalmovieapp.data.TMDBConnector;
import com.clan707.mobusdorphin.mrcheesespersonalmovieapp.databinding.ActivityMainBinding;
import com.clan707.mobusdorphin.mrcheesespersonalmovieapp.model.Movie;

import org.json.JSONException;

import java.io.IOException;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Movie[]>,
            SharedPreferences.OnSharedPreferenceChangeListener {

    ActivityMainBinding binding;
    private MovieAdapter movieAdapter;


    private static final int MOVIE_LOADER_ID = 4912;
    private static final String SORT_METHOD_KEY = "sort_method";
    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        GridLayoutManager layoutManager
                = new GridLayoutManager(this, 2);
        binding.rvMainActivity.setHasFixedSize(true);
        binding.rvMainActivity.setLayoutManager(layoutManager);
        movieAdapter = new MovieAdapter();
        binding.rvMainActivity.setAdapter(movieAdapter);



        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        setAppMode(prefs.getBoolean(getString(R.string.show_mr_cheese), false));
        String sortMethod = prefs.getString(
                getString(R.string.sort_method_key),
                getString(R.string.method_popular_val)
        );

        prefs.registerOnSharedPreferenceChangeListener(this);

        loadMovies(sortMethod);
    }

    private void loadMovies(String sortMethod) {
        Bundle loaderBundle = new Bundle();
        loaderBundle.putString(SORT_METHOD_KEY, sortMethod);
        LoaderManager.LoaderCallbacks<Movie[]> callback = MainActivity.this;
        getSupportLoaderManager().restartLoader(
                MOVIE_LOADER_ID,
                loaderBundle,
                callback);


    }

    @Override
    public Loader<Movie[]> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<Movie[]>(this) {
            Movie[] movieList = null;
            String sortMethod = args.getString(SORT_METHOD_KEY);

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                forceLoad();
            }

            @Override
            public Movie[] loadInBackground() {
                TMDBConnector.SortMethod method = TMDBConnector.SortMethod.SORT_POPULAR;
                if (sortMethod.equals(getString(R.string.method_popular_val))) {
                    method = TMDBConnector.SortMethod.SORT_POPULAR;
                } else if (sortMethod.equals(getString(R.string.method_top_val))) {
                    method = TMDBConnector.SortMethod.SORT_TOP;
                }
                try {
                    String movieJson = TMDBConnector.getMovieJSON(method);
                    Log.d(TAG, "loadInBackground: " + movieJson);
                    movieList = JsonUtils.parseMovieJson(movieJson);
                } catch (JSONException e) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_prefs:
                Intent startPrefsActivity = new Intent(this, SettingsActivity.class);
                startActivity(startPrefsActivity);
                return true;
            default:
                return false;
        }
    }

    private void setAppMode(boolean showMrCheese) {
        if (showMrCheese == true) {
            binding.rvMainActivity.setVisibility(GONE);
            binding.ivMrCheese.setVisibility(View.VISIBLE);
        } else {
            binding.rvMainActivity.setVisibility(View.VISIBLE);
            binding.ivMrCheese.setVisibility(GONE);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.show_mr_cheese))) {
            setAppMode(sharedPreferences.getBoolean(
                    key,
                    getResources().getBoolean(R.bool.show_cheese_def_value)
            ));
        } else if (key.equals(getString(R.string.sort_method_key))) {
            loadMovies(sharedPreferences.getString(
                    getString(R.string.sort_method_key),
                    getString(R.string.method_popular_val)
            ));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.unregisterOnSharedPreferenceChangeListener(this);
    }
}
