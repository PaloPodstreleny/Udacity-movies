package sk.udacity.podstreleny.palo.movie.screen.movieDetail;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.udacity.podstreleny.palo.movie.R;
import sk.udacity.podstreleny.palo.movie.db.entity.Movie;
import sk.udacity.podstreleny.palo.movie.db.entity.Review;
import sk.udacity.podstreleny.palo.movie.model.Resource;
import sk.udacity.podstreleny.palo.movie.util.MovieUrlUtil;
import sk.udacity.podstreleny.palo.movie.viewModels.MovieDetailViewModel;

public class MovieDetail extends AppCompatActivity {

    private static final String TAG = MovieDetail.class.getSimpleName();

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

    @BindView(R.id.favoriteImage)
    ImageView movieFavorite;

    private Movie mMovie;

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

        final MovieDetailViewModel viewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);

        viewModel.getMovie().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                if(movie != null) {
                    mMovie = movie;
                    movieTitle.setText(movie.getTitle());
                    movieOverview.setText(movie.getOverview());
                    Glide.with(getBaseContext()).load(MovieUrlUtil.IMAGE_BASE_URL + movie.getPosterPath()).into(moviePoster);
                    movieReleaseDate.setText(movie.getReleaseDate().replace("-", "/"));
                    movieRating.setText(String.valueOf(movie.getVoteAverage()));
                    setFavoriteIcon(movie.isFavorite());
                }
            }
        });

        viewModel.getReviews().observe(this, new Observer<Resource<List<Review>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Review>> listResource) {
                if(listResource != null) {
                    if(listResource.getData() != null) {
                        Log.d(TAG, listResource.toString());
                    }
                }
            }
        });

        movieFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFavorite = mMovie.isFavorite();
                mMovie.setFavorite(!isFavorite);
                viewModel.updateMovie(mMovie);
            }
        });




        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)){
            viewModel.setMovieId(intent.getIntExtra(Intent.EXTRA_TEXT,-1));
        }


    }

    private void setFavoriteIcon(boolean isFavorite){
        if(isFavorite){
            movieFavorite.setImageResource(R.drawable.ic_star_yellow_36dp);
        }else {
            movieFavorite.setImageResource(R.drawable.ic_star_border_yellow_36dp);
        }
    }



}
