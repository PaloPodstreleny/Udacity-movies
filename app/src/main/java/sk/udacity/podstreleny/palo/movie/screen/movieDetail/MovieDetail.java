package sk.udacity.podstreleny.palo.movie.screen.movieDetail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import butterknife.BindView;
import butterknife.ButterKnife;
import sk.udacity.podstreleny.palo.movie.R;
import sk.udacity.podstreleny.palo.movie.db.entity.Movie;
import sk.udacity.podstreleny.palo.movie.util.MovieUrlUtil;

public class MovieDetail extends AppCompatActivity {

    @BindView(R.id.movie_title)
    TextView movieTitle;

    @BindView(R.id.movie_overview)
    TextView movieOverview;

    @BindView(R.id.movie_image)
    ImageView moviePoster;

    @BindView(R.id.movie_release_date)
    TextView movieReleaseDate;

    @BindView(R.id.movie_rating)
    TextView movieRating;

    @BindView(R.id.include)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        //Set toolbar
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.movie_detail_screen_title);


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)){
            Movie movie = intent.getParcelableExtra(Intent.EXTRA_TEXT);
            movieTitle.setText(movie.getTitle());
            movieOverview.setText(movie.getOverview());
            Glide.with(this).load(MovieUrlUtil.IMAGE_BASE_URL + movie.getPoster_path()).into(moviePoster);
            movieReleaseDate.setText(movie.getRelease_date().replace("-","/"));
            movieRating.setText(String.valueOf(movie.getVote_average()));
        }


    }



}
