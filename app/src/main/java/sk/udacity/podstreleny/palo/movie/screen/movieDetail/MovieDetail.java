package sk.udacity.podstreleny.palo.movie.screen.movieDetail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
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
import sk.udacity.podstreleny.palo.movie.db.entity.Video;
import sk.udacity.podstreleny.palo.movie.model.Resource;
import sk.udacity.podstreleny.palo.movie.util.MovieUrlUtil;
import sk.udacity.podstreleny.palo.movie.viewModels.MovieDetailViewModel;

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
    @BindView(R.id.parentLayout)
    ConstraintLayout parentLayout;
    @BindView(R.id.recycler_reviews)
    RecyclerView reviewRecycler;
    @BindView(R.id.recycler_videos)
    RecyclerView videoRecycler;
    @BindView(R.id.reviewImage)
    ImageView reviewImage;
    @BindView(R.id.videoImage)
    ImageView videoImage;
    @BindView(R.id.reviews)
    TextView reviewTextView;
    @BindView(R.id.videos)
    TextView videosTextView;
    @BindView(R.id.reviewProgressBar)
    ProgressBar reviewProgressBar;
    @BindView(R.id.videoProgressBar)
    ProgressBar videoProgressBar;
    @BindView(R.id.buttonFavorite)
    Button mButtonFavorite;

    private Movie mMovie;
    private ReviewAdapter mReviewAdapter;
    private VideoAdapter mVideoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        //Set recyclerViews and adapters
        mReviewAdapter = new ReviewAdapter(this);
        reviewRecycler.setLayoutManager(new LinearLayoutManager(this));
        reviewRecycler.setHasFixedSize(true);
        reviewRecycler.setAdapter(mReviewAdapter);

        mVideoAdapter = new VideoAdapter(this);
        videoRecycler.setLayoutManager(new LinearLayoutManager(this));
        videoRecycler.setHasFixedSize(true);
        videoRecycler.setAdapter(mVideoAdapter);


        //Set toolbar
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.movie_detail_screen_title);

        //Get ViewModel and observe movie
        final MovieDetailViewModel viewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
        viewModel.getMovie().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                if (movie != null) {
                    mMovie = movie;
                    movieTitle.setText(movie.getTitle());
                    movieOverview.setText(movie.getOverview());
                    Glide.with(getBaseContext()).load(MovieUrlUtil.IMAGE_BASE_URL + movie.getPosterPath()).into(moviePoster);
                    movieReleaseDate.setText(movie.getReleaseDate().replace("-", "/"));
                    movieRating.setText(String.valueOf(movie.getVoteAverage()));
                    if (mMovie.isFavorite()) {
                        movieRating.setTextColor(getResources().getColor(R.color.colorWhite));
                        movieRating.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_colorful));
                        mButtonFavorite.setTextAppearance(getApplicationContext(), R.style.RedButton);
                        mButtonFavorite.setText(R.string.btn_remove_from_favorite);
                        mButtonFavorite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_remove, 0, 0, 0);
                    } else {
                        movieRating.setTextColor(getResources().getColor(R.color.colorTextSecondary));
                        movieRating.setBackgroundDrawable(getResources().getDrawable(R.drawable.round));
                        mButtonFavorite.setTextAppearance(getApplicationContext(), R.style.GreenButton);
                        mButtonFavorite.setText(R.string.btn_add_to_favorite);
                        mButtonFavorite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add, 0, 0, 0);
                    }
                }
            }
        });

        //Set review
        viewModel.getReviews().observe(this, new Observer<Resource<List<Review>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Review>> listResource) {
                if (listResource != null) {
                    List<Review> reviews = listResource.getData();
                    switch (listResource.getStatus()) {
                        case LOADING:
                            changeVisibilityReviewImage(View.GONE);
                            changeVisibilityReviewProgressBar(View.VISIBLE);
                            break;
                        case SUCCESS:
                            changeVisibilityReviewProgressBar(View.GONE);
                            if (reviews == null || reviews.isEmpty()) {
                                changeVisibilityReviewImage(View.GONE);
                                reviewTextView.setText(getString(R.string.movie_detail_screen_reviews_no_review));
                            } else {
                                reviewTextView.setText(getString(R.string.movie_detail_screen_reviews_title));
                                changeVisibilityReviewImage(View.VISIBLE);
                                mReviewAdapter.setData(reviews);
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


        viewModel.getVideos().observe(this, new Observer<Resource<List<Video>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Video>> listResource) {
                if (listResource != null) {
                    List<Video> videos = listResource.getData();
                    switch (listResource.getStatus()) {
                        case LOADING:
                            changeVisibilityVideoImage(View.GONE);
                            changeVisibilityVideoProgressBar(View.VISIBLE);
                            break;
                        case SUCCESS:
                            changeVisibilityVideoProgressBar(View.GONE);
                            if (videos == null || videos.isEmpty()) {
                                changeVisibilityVideoImage(View.GONE);
                                videosTextView.setText(getString(R.string.movie_detail_screen_no_video));
                            } else {
                                videosTextView.setText(getString(R.string.movie_detail_screen_videos_title));
                                changeVisibilityVideoImage(View.VISIBLE);
                                mVideoAdapter.setVideoList(videos);
                            }
                            break;
                        case UNAUTHORIZED:
                            videosTextView.setText(getString(R.string.movie_detail_screen_reviews_unauthorized));
                            changeVisibilityVideoProgressBar(View.GONE);
                            changeVisibilityVideoImage(View.GONE);
                            break;
                        case ERROR:
                            changeVisibilityVideoProgressBar(View.GONE);
                            changeVisibilityVideoImage(View.GONE);
                            videosTextView.setText(getString(R.string.movie_detail_screen_reviews_error));
                            break;
                        default:
                            throw new IllegalArgumentException("There is wrong status!");
                    }
                }
            }
        });

        mButtonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFavorite = mMovie.isFavorite();
                mMovie.setFavorite(!isFavorite);
                viewModel.updateMovie(mMovie);
            }
        });


        reviewTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reviewRecycler.getVisibility() == View.GONE) {
                    reviewRecycler.setVisibility(View.VISIBLE);
                    changeArrowReviewPointerDrawable(getResources().getDrawable(R.drawable.ic_arrow_up));
                } else {
                    reviewRecycler.setVisibility(View.GONE);
                    changeArrowReviewPointerDrawable(getResources().getDrawable(R.drawable.ic_arrow_down));
                }

            }
        });

        videosTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (videoRecycler.getVisibility() == View.GONE) {
                    videoRecycler.setVisibility(View.VISIBLE);
                    changeArrowVideosPointerDrawable(getResources().getDrawable(R.drawable.ic_arrow_up));
                } else {
                    videoRecycler.setVisibility(View.GONE);
                    changeArrowVideosPointerDrawable(getResources().getDrawable(R.drawable.ic_arrow_down));
                }
            }
        });


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            viewModel.setMovieId(intent.getIntExtra(Intent.EXTRA_TEXT, -1));
        }

    }

    private void changeArrowVideosPointerDrawable(Drawable drawable) {
        videoImage.setImageDrawable(drawable);
    }

    private void changeArrowReviewPointerDrawable(Drawable drawable) {
        reviewImage.setImageDrawable(drawable);
    }


    private void changeVisibilityVideoProgressBar(int visible) {
        videoProgressBar.setVisibility(visible);
    }

    private void changeVisibilityVideoImage(int gone) {
        videoImage.setVisibility(gone);
    }

    private void changeVisibilityReviewProgressBar(int visible) {
        reviewProgressBar.setVisibility(visible);
    }

    private void changeVisibilityReviewImage(int visibility) {
        reviewImage.setVisibility(visibility);
    }

}
