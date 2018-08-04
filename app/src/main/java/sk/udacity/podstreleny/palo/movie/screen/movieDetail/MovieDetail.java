package sk.udacity.podstreleny.palo.movie.screen.movieDetail;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

    @BindView(R.id.movie_title) TextView movieTitle;
    @BindView(R.id.movie_overview) TextView movieOverview;
    @BindView(R.id.movie_image) ImageView moviePoster;
    @BindView(R.id.movie_release_date) TextView movieReleaseDate;
    @BindView(R.id.movie_rating) TextView movieRating;
    @BindView(R.id.include) Toolbar toolbar;
    @BindView(R.id.favoriteImage) ImageView movieFavorite;
    @BindView(R.id.parentLayout) ConstraintLayout parentLayout;
    @BindView(R.id.recycler_reviews) RecyclerView reviewRecycler;
    @BindView(R.id.reviewImage) ImageView reviewImage;
    @BindView(R.id.reviews) TextView reviewTextView;
    @BindView(R.id.reviewProgressBar) ProgressBar reviewProgressBar;

    private Movie mMovie;
    private ReviewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        //Set recyclerView and adapter
        mAdapter = new ReviewAdapter();
        reviewRecycler.setLayoutManager(new LinearLayoutManager(this));
        reviewRecycler.setHasFixedSize(true);
        reviewRecycler.setAdapter(mAdapter);


        //Set toolbar
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.movie_detail_screen_title);

        final MovieDetailViewModel viewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);

        //Set movie
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

        //Set review
        viewModel.getReviews().observe(this, new Observer<Resource<List<Review>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Review>> listResource) {
                if(listResource != null) {
                    List<Review> reviews = listResource.getData();
                    switch (listResource.getStatus()){
                        case LOADING:
                            changeVisibilityReviewImage(View.GONE);
                            changeVisibilityReviewProgressBar(View.VISIBLE);
                            break;
                        case SUCCESS:
                            changeVisibilityReviewProgressBar(View.GONE);
                            if(reviews == null || reviews.isEmpty()){
                                changeVisibilityReviewImage(View.GONE);
                                reviewTextView.setText(getString(R.string.movie_detail_screen_reviews_no_review));
                            }else {
                                reviewTextView.setText(getString(R.string.movie_detail_screen_reviews_title));
                                changeVisibilityReviewImage(View.VISIBLE);
                                mAdapter.setData(reviews);
                            }
                            break;
                        case UNAUTHORIZED:
                            reviewTextView.setText(getString(R.string.movie_detail_screen_reviews_unauthorized));
                            changeVisibilityReviewProgressBar(View.GONE);
                            changeVisibilityReviewImage(View.GONE);
                            break;
                        case ERROR:
                            changeVisibilityReviewProgressBar(View.GONE);
                            changeVisibilityReviewImage(View.GONE);
                            reviewTextView.setText(getString(R.string.movie_detail_screen_reviews_error));
                            break;
                        default:
                            throw new IllegalArgumentException("There is wrong status!");
                    }
                }
            }
        });

        movieFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFavorite = mMovie.isFavorite();
                mMovie.setFavorite(!isFavorite);
                showSnackBar((isFavorite)? R.drawable.ic_star_yellow_36dp : R.drawable.ic_star_border_yellow_36dp);
                viewModel.updateMovie(mMovie);
            }
        });


        reviewTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reviewRecycler.getVisibility() == View.GONE){
                    reviewRecycler.setVisibility(View.VISIBLE);
                }else {
                    reviewRecycler.setVisibility(View.GONE);
                }

            }
        });




        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)){
            viewModel.setMovieId(intent.getIntExtra(Intent.EXTRA_TEXT,-1));
        }


    }

    private void changeVisibilityReviewProgressBar(int visible) {
        reviewProgressBar.setVisibility(visible);
    }


    private void changeVisibilityReviewImage(int visibility) {
        reviewImage.setVisibility(visibility);
    }

    private void setFavoriteIcon(boolean isFavorite){
        if(isFavorite){
            showImageResource(R.drawable.ic_star_yellow_36dp);
        }else {
            showImageResource(R.drawable.ic_star_border_yellow_36dp);
        }
    }

    private void showImageResource(int resource){
        movieFavorite.setImageResource(resource);
    }

    private void showSnackBar(int resource){
        switch (resource){
            case R.drawable.ic_star_border_yellow_36dp:
                Snackbar.make(parentLayout,R.string.movie_detail_screen_favorite,Snackbar.LENGTH_LONG).show();
                break;
            case R.drawable.ic_star_yellow_36dp:
                Snackbar.make(parentLayout,R.string.movie_detail_screen_favorite_unfollow,Snackbar.LENGTH_LONG).show();
                break;
                default:
                    throw new IllegalArgumentException("Wrong resource id!");

        }

    }



}
