package com.example.movieapp.Repository;

import android.support.annotation.NonNull;

import com.example.movieapp.MoviesResponse;
import com.example.movieapp.OnGetMoviesCallBack;
import com.example.movieapp.retrofit.TMDBApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String LANGUAGE = "en-US";
    private static final String API_KEY="5b735153b47af02b48a28b4c0ab033ac";

    private static MovieRepository repository;

    private TMDBApi api;

    private MovieRepository(TMDBApi api) {
        this.api=api;
    }

    public static MovieRepository getInstance() {
        if (repository == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            repository = new MovieRepository(retrofit.create(TMDBApi.class));
        }

        return repository;
    }

    public void getMovies(final OnGetMoviesCallBack callback, int ReleaseYear) {
        api.getPopularMovies(API_KEY, ReleaseYear, LANGUAGE, 1 )
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(@NonNull Call
                            <MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                        if (response.isSuccessful()) {
                            MoviesResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMovies() != null) {
                                callback.onSucces(moviesResponse.getMovies());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        callback.onError();
                    }
                });
    }
}