package com.example.movieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieapp.Adapter.MovieAdapter;
import com.example.movieapp.Adapter.RecyclerOnTouchListener;
import com.example.movieapp.Model.Movie;
import com.example.movieapp.Repository.MovieRepository;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MovieRepository moviesRepository;
   MovieAdapter adapter;
    RecyclerView recyclerView;
    private List<Movie> movieList;
    TextView yearView;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesRepository = MovieRepository.getInstance();

        yearView = findViewById(R.id.yearInput);


        submitButton = findViewById(R.id.submitButton);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = yearView.getText().toString();
                int releases = Integer.valueOf(value);
                Toast.makeText(MainActivity.this, String.valueOf(releases), Toast.LENGTH_SHORT).show();

                moviesRepository.getMovies(new OnGetMoviesCallBack() {


                    @Override
                    public void onSucces(List<Movie> movies) {
                        movieList = movies;
                        updateUI();

                    }

                    @Override
                    public void onError() {
                        Toast.makeText(MainActivity.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    }

                }, releases);
            }
        });

        recyclerView = findViewById(R.id.recyclerv_view);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));


        recyclerView.addOnItemTouchListener(new RecyclerOnTouchListener(this, recyclerView, new RecyclerOnTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Intent intent = new Intent(MainActivity.this, MovieDetail.class);
                Movie movie = movieList.get(position);
                intent.putExtra("movie", movie);

                startActivityForResult(intent, 1);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }




    private void updateUI() {
        if (adapter == null) {
            adapter = new MovieAdapter(movieList);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.swapList(movieList);
        }
    }
}
