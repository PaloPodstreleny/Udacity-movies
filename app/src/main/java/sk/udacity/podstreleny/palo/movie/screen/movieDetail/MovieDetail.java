package sk.udacity.podstreleny.palo.movie.screen.movieDetail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import sk.udacity.podstreleny.palo.movie.R;

public class MovieDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        setTitle(R.string.movie_detail_screen_title);


    }
}
