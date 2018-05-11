package com.clan707.mobusdorphin.mrcheesespersonalmovieapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.clan707.mobusdorphin.mrcheesespersonalmovieapp.model.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by mobus on 5/2/18.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Movie[] movieList;
//    public static final String INTENT_STRING_ID = "id";
    public static final String INTENT_MOVIE_ID = "movie";

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
//        public int id;

        public ViewHolder(ImageView imageView) {
            super(imageView);
            mImageView = imageView;
        }
    }

    public MovieAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView moviePoster = (ImageView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_poster, parent, false);
        return new ViewHolder(moviePoster);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.with(holder.mImageView.getContext())
            .load(movieList[position].getPosterUrl())
            .into(holder.mImageView);
//        final int id = movieList[position].getId();
        final Movie movie = movieList[position];
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Intent detailIntent = new Intent(v.getContext(), DetailActivity.class);
//                     detailIntent.putExtra(INTENT_STRING_ID, id);
                     detailIntent.putExtra(INTENT_MOVIE_ID, movie);
                     v.getContext().startActivity(detailIntent);
//                     Toast.makeText(v.getContext(),
//                             "Would pop up an intent with id " + id,
//                             Toast.LENGTH_SHORT).show();
                 }
             }

        );
    }

    @Override
    public int getItemCount() {
        if (movieList == null) return 0;
        return movieList.length;
    }

    public void setMovieList(Movie[] movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }
}
