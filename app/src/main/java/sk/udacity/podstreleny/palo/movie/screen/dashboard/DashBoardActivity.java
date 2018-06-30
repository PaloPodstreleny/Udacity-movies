package sk.udacity.podstreleny.palo.movie.screen.dashboard;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.udacity.podstreleny.palo.movie.R;
import sk.udacity.podstreleny.palo.movie.model.Movie;
import sk.udacity.podstreleny.palo.movie.screen.movieDetail.MovieDetail;
import sk.udacity.podstreleny.palo.movie.util.IntentStrings;
import sk.udacity.podstreleny.palo.movie.viewModels.DashBoardViewModel;

public class DashBoardActivity extends AppCompatActivity implements MovieAdapter.MovieItemClickListener {

    private final static int MULTIPLE_COLUMN = 2;
    private static final String RESOURCE_ID = "resource_id";
    private int mResourceID;

    private DashBoardViewModel viewModel;

    @BindView(R.id.progress_bar)
    FrameLayout mProgressBar;

    @BindView(R.id.main_rv)
    RecyclerView recyclerView;

    @BindView(R.id.actualOrdering)
    TextView mOrderingTv;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        final MovieAdapter adapter = new MovieAdapter(this, this);

        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(MULTIPLE_COLUMN, RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(DashBoardViewModel.class);
        showProgressBar();

        viewModel.movies.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                if (movies != null) {
                    adapter.swapData(movies);
                    showRecyclerView();
                }
            }
        });

        if (savedInstanceState != null && savedInstanceState.containsKey(RESOURCE_ID)) {
            mResourceID = savedInstanceState.getInt(RESOURCE_ID);
            if (mResourceID == R.id.pupularity) {
                setPopularTv();
            } else {
                setTopRatedTv();
            }
        } else {
            mResourceID = R.id.pupularity;
            setPopularTv();
        }

    }

    private void setPopularTv() {
        final String arg = getString(R.string.main_screen_menu_popularity);
        mOrderingTv.setText(getString(R.string.main_screen_order_text, arg));
        viewModel.setPopularMovies();
    }

    private void setTopRatedTv() {
        final String arg = getString(R.string.main_screen_menu_highest_rating);
        mOrderingTv.setText(getString(R.string.main_screen_order_text, arg));
        viewModel.setTopRatedMovies();
    }

    private void showRecyclerView() {
        mProgressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.order_menu, menu);
        return true;
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(this, MovieDetail.class);

        intent.putExtra(IntentStrings.MOVIE_TITLE, movie.getTitle());
        intent.putExtra(IntentStrings.MOVIE_OVERVIEW, movie.getOverview());
        intent.putExtra(IntentStrings.MOVIE_POSTER, movie.getPoster_path());
        intent.putExtra(IntentStrings.MOVIE_RELEASE_DATE, movie.getRelease_date());
        intent.putExtra(IntentStrings.MOVIE_VOTE_AVERAGE, movie.getVote_average());

        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.top_rating:
                if (mResourceID != R.id.top_rating) {
                    showProgressBar();
                    setTopRatedTv();
                    mResourceID = R.id.top_rating;
                }
                return true;
            case R.id.pupularity:
                if (mResourceID != R.id.pupularity) {
                    showProgressBar();
                    setPopularTv();
                    mResourceID = R.id.pupularity;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(RESOURCE_ID, mResourceID);
        super.onSaveInstanceState(outState);
    }
}
