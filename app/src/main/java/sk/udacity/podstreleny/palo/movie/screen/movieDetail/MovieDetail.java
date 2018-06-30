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
import sk.udacity.podstreleny.palo.movie.util.IntentStrings;

public class MovieDetail extends AppCompatActivity {

    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/";

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


        //TODO change it later :)
        Intent intent = getIntent();
        if (intent != null){
            if(intent.hasExtra(IntentStrings.MOVIE_TITLE)){
                movieTitle.setText(intent.getStringExtra(IntentStrings.MOVIE_TITLE));
            }
            if(intent.hasExtra(IntentStrings.MOVIE_OVERVIEW)){
                movieOverview.setText(intent.getStringExtra(IntentStrings.MOVIE_OVERVIEW));
            }
            if(intent.hasExtra(IntentStrings.MOVIE_POSTER)){
                Glide.with(this).load(IMAGE_BASE_URL + intent.getStringExtra(IntentStrings.MOVIE_POSTER)).into(moviePoster);
            }
            if (intent.hasExtra(IntentStrings.MOVIE_RELEASE_DATE)){
                movieReleaseDate.setText(intent.getStringExtra(IntentStrings.MOVIE_RELEASE_DATE).replace("-","/"));
            }
            if (intent.hasExtra(IntentStrings.MOVIE_VOTE_AVERAGE)){
                final float value = intent.getFloatExtra(IntentStrings.MOVIE_VOTE_AVERAGE,0.0f);
                movieRating.setText(String.valueOf(value));
            }
        }


    }



}
