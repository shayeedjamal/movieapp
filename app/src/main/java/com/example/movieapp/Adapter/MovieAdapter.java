package com.example.movieapp.Adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieapp.Model.Movie;
import com.example.movieapp.R;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500";

    private List<Movie> mData;

    public MovieAdapter(List<Movie> data) {

        this.mData = data;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout._list_item_layout, parent, false);
        MovieAdapter.ViewHolder viewHolder = new MovieAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mData.get(position),position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView numberTextView;
        LinearLayout parentLayout;
        ImageView movieImage;

        public ViewHolder(View itemView) {
            super(itemView);

            movieImage = itemView.findViewById(R.id.movieImageView);
            numberTextView = itemView.findViewById(R.id.numberTextView);
            parentLayout = itemView.findViewById(R.id.parentalLayout);
            parentLayout.setClickable(true);

        }

        @SuppressLint("ClickableViewAccessibility")
        public void bind(Movie movie, int position) {

            numberTextView.setText(String.valueOf(position + 1) + ".");
            String url = IMAGE_BASE_URL + movie.getPosterPath();
            Glide.with(itemView).load(url).into(movieImage);
        }

    }


    public void swapList(List<Movie> newList) {
        mData = newList;
        if (newList != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }
}
