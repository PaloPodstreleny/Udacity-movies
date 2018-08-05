package sk.udacity.podstreleny.palo.movie.screen.dashboard;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.udacity.podstreleny.palo.movie.R;
import sk.udacity.podstreleny.palo.movie.db.entity.Movie;
import sk.udacity.podstreleny.palo.movie.model.MovieOrder;
import sk.udacity.podstreleny.palo.movie.model.Resource;
import sk.udacity.podstreleny.palo.movie.viewModels.DashBoardViewModel;

public class DashBoardActivity extends AppCompatActivity {


    private static final String MOVIE_TYPE = "movieTypeID";
    private int movieTypeID;
    private MovieOrder movieType;

    private DashBoardViewModel viewModel;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.main_rv)
    RecyclerView recyclerView;

    @BindView(R.id.actualOrdering)
    TextView mOrderingTv;

    @BindView(R.id.top_bar)
    Toolbar toolbar;

    @BindView(R.id.retry_btn)
    Button mRetryButton;

    @BindView(R.id.error_text_view)
    TextView errorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);


        //Setting recyclerView + Adapter
        final int NUMBER_OF_GRID_COLUMNS = getResources().getInteger(R.integer.number_of_grid_columns);
        final MovieAdapter adapter = new MovieAdapter(this);
        final GridLayoutManager manager = new GridLayoutManager(this, NUMBER_OF_GRID_COLUMNS);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        //Get instance of viewModel and observe movies
        viewModel = ViewModelProviders.of(this).get(DashBoardViewModel.class);
        viewModel.movies.observe(this, new Observer<Resource<List<Movie>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Movie>> movies) {
                if (movies != null) {
                    switch (movies.getStatus()) {
                        case LOADING:
                            changeRecyclerViewVisibility(View.GONE);
                            changeErrorTextViewVisibility(View.GONE, null, false);
                            changeProgressBarVisibility(View.VISIBLE);
                            break;
                        case SUCCESS:
                            if (movies.getData() != null) {
                                if (movies.getData().isEmpty()) {
                                    changeRecyclerViewVisibility(View.GONE);
                                    changeErrorTextViewVisibility(View.VISIBLE, getString(R.string.main_screen_no_favorite_movie), false);
                                } else {
                                    showData(adapter, movies.getData());
                                }
                            }
                            changeProgressBarVisibility(View.GONE);
                            break;
                        case ERROR:
                            if (movies.getData() == null || movies.getData().isEmpty()) {
                                changeRecyclerViewVisibility(View.GONE);
                                changeErrorTextViewVisibility(View.VISIBLE, getString(R.string.main_screen_internet_connection_problem), true);
                                changeProgressBarVisibility(View.GONE);
                            } else {
                                showData(adapter, movies.getData());
                            }
                            break;
                        case UNAUTHORIZED:
                            if (movies.getData() == null || movies.getData().isEmpty()) {
                                changeRecyclerViewVisibility(View.GONE);
                                changeErrorTextViewVisibility(View.VISIBLE, getString(R.string.main_screen_unauthorized), false);
                                changeProgressBarVisibility(View.GONE);
                            } else {
                                showData(adapter, movies.getData());
                            }
                            break;
                        default:
                            throw new IllegalArgumentException("Movie status contains not allowed value");
                    }
                }
            }
        });

        //Set listener for re-fetching data
        mRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retryRequest(movieType);
            }
        });


        if (savedInstanceState != null && savedInstanceState.containsKey(MOVIE_TYPE)) {
            movieType = convertMovieCategoryIDToMovieType(savedInstanceState.getInt(MOVIE_TYPE));
            setMovieOrder(movieType);
        } else {
            movieType = MovieOrder.POPULARITY;
            setMovieOrder(movieType);
        }

    }

    private void showData(MovieAdapter adapter, List<Movie> data) {
        adapter.swapData(data);
        changeRecyclerViewVisibility(View.VISIBLE);
        changeErrorTextViewVisibility(View.GONE, null, false);
    }


    /***
     *
     * Helper method convert movieCategoryID to MovieType object
     *
     * @param movieCategoryID int number of 0,1,2 representing movieCategoryID
     * @return MovieType object according to parameter value
     */
    private MovieOrder convertMovieCategoryIDToMovieType(int movieCategoryID) {
        switch (movieCategoryID) {
            case 0:
                return MovieOrder.POPULARITY;
            case 1:
                return MovieOrder.TOP_RATED;
            case 2:
                return MovieOrder.FAVORITE;
            default:
                throw new IllegalArgumentException("Movie ID can have just values 0, 1, 2");
        }
    }

    /***
     *
     * Helper method convert MovieOrder object to it's int representation
     *
     * @param movieType represents MovieType
     * @return int representation of MovieType
     */
    private int converMovieTypeToMovieCategoryID(MovieOrder movieType) {
        switch (movieType) {
            case POPULARITY:
                return 0;
            case FAVORITE:
                return 2;
            case TOP_RATED:
                return 1;
            default:
                throw new IllegalArgumentException("MovieType can contains: Popular, Favorite or TopRated categories");
        }
    }

    /***
     *
     * Helper method change visibility of ProgressBar according to visibility parameter
     *
     * @param visibility value of View visibility (VISIBLE,GONE..)
     */
    private void changeProgressBarVisibility(@NonNull int visibility) {
        mProgressBar.setVisibility(visibility);
    }


    /***
     *
     * Helper method change visibility of error TextView and set text according to "text" parameter.
     * Moreover if paramter "button" is equal to true, retry button is visible to re-fetch data.
     *
     * @param visibility int value of View visibiliy
     * @param text String representation of error message
     * @param button boolean value which represent button visibility
     */
    private void changeErrorTextViewVisibility(int visibility, @Nullable String text, boolean button) {
        if (visibility == View.VISIBLE && text != null) {
            errorTextView.setVisibility(View.VISIBLE);
            errorTextView.setText(text);
            if (button) {
                mRetryButton.setVisibility(View.VISIBLE);
            } else {
                mRetryButton.setVisibility(View.GONE);
            }
        } else if (visibility == View.GONE) {
            errorTextView.setVisibility(View.GONE);
            mRetryButton.setVisibility(View.GONE);
        }

    }


    /***
     *
     * Helper method change visibility of recyclerView according to visibility parameter
     *
     * @param visibility value of View visibility (VISIBLE,GONE..)
     */
    private void changeRecyclerViewVisibility(int visibility) {
        recyclerView.setVisibility(visibility);
    }

    /***
     *
     * Helper method set text in TextView and update ViewModel according to MovieType parameter
     *
     * @param movieType type of the movies which will be shown
     */

    private void setMovieOrder(MovieOrder movieType) {
        switch (movieType) {
            case POPULARITY:
                mOrderingTv.setText(getString(R.string.main_screen_order_text, getString(R.string.main_screen_menu_popularity)));
                viewModel.setMovieOrder(MovieOrder.POPULARITY, false);
                break;
            case FAVORITE:
                mOrderingTv.setText(getString(R.string.main_screen_order_text, getString(R.string.main_screen_menu_favorite)));
                viewModel.setMovieOrder(MovieOrder.FAVORITE, false);
                break;
            case TOP_RATED:
                mOrderingTv.setText(getString(R.string.main_screen_order_text, getString(R.string.main_screen_menu_highest_rating)));
                viewModel.setMovieOrder(MovieOrder.TOP_RATED, false);
                break;
            default:
                throw new IllegalArgumentException("You provided wrong MovieType!");
        }
    }

    /***
     *
     * Helper method which trying to get Movies according to movieType parameter
     *
     * @param movieType category of movies
     */
    private void retryRequest(MovieOrder movieType) {
        switch (movieType) {
            case POPULARITY:
                viewModel.setMovieOrder(MovieOrder.POPULARITY, true);
                break;
            case TOP_RATED:
                viewModel.setMovieOrder(MovieOrder.TOP_RATED, true);
                break;
            case FAVORITE:
                viewModel.setMovieOrder(MovieOrder.FAVORITE, true);
                break;
            default:
                throw new IllegalArgumentException("MovieType can contains just POPULAR, TOP_RATED or FAVORITE categories");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.order_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return getProperData(item.getItemId(), super.onOptionsItemSelected(item));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        movieTypeID = converMovieTypeToMovieCategoryID(movieType);
        outState.putInt(MOVIE_TYPE, movieTypeID);
        super.onSaveInstanceState(outState);
    }


    private boolean getProperData(int code, boolean defaultReturn) {
        switch (code) {
            case R.id.top_rating:
                movieType = MovieOrder.TOP_RATED;
                setMovieOrder(MovieOrder.TOP_RATED);
                return true;
            case R.id.pupularity:
                movieType = MovieOrder.POPULARITY;
                setMovieOrder(MovieOrder.POPULARITY);
                return true;
            case R.id.favorite:
                movieType = MovieOrder.FAVORITE;
                setMovieOrder(MovieOrder.FAVORITE);
                return true;
            default:
                return defaultReturn;
        }
    }
}
