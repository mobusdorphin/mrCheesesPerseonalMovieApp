package com.clan707.mobusdorphin.mrcheesespersonalmovieapp;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.clan707.mobusdorphin.mrcheesespersonalmovieapp.databinding.ActivityDetailBinding;
import com.clan707.mobusdorphin.mrcheesespersonalmovieapp.model.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
//        Is there any particular reason not to set the bundle key as public and call like this?
//        It seems to me that it would be better to declare once and not repeat declarations
//        However, I don't usually see this being done in general. Something I don't know?
        Movie movie = getIntent().getExtras().getParcelable(MovieAdapter.INTENT_MOVIE_ID);

        // convert rating to the Brendon Sullivan scale
        double sullivanRating = Double.parseDouble(movie.getRating());
        sullivanRating = sullivanRating / 2;
        String sullivanText = sullivanRating + "/7";

        if (movie.getId() == 550) {
            sullivanText = "5/7";
            // Because Brendon Sullivan, the man himself, rates Fight Club a perfect 5/7
        }
        Picasso.with(this).load(movie.getPosterUrl()).into(binding.ivDetailMoviePoster);
        binding.tvDetailMovieTitle.setText(movie.getTitle());
        binding.tvDetailUserRating.setText(sullivanText);
        binding.tvDetailReleaseDate.setText(movie.getDate());
        binding.tvDetailPlotSynopsis.setText(movie.getOverview());
    }
}
