package sk.udacity.podstreleny.palo.movie.screen.dashboard;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import sk.udacity.podstreleny.palo.movie.model.Resource;
import sk.udacity.podstreleny.palo.movie.viewModels.DashBoardViewModel;
import sk.udacity.podstreleny.palo.movie.viewModels.DashBoardViewModelFactory;

public class DashBoardActivity extends AppCompatActivity {

    private final static int MULTIPLE_COLUMN = 2;
    private static final String RESOURCE_ID = "resource_id";
    private int mResourceID;

    private DashBoardViewModel viewModel;

    @BindView(R.id.progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.main_rv) RecyclerView recyclerView;
    @BindView(R.id.actualOrdering) TextView mOrderingTv;
    @BindView(R.id.top_bar) Toolbar toolbar;
    @BindView(R.id.retry_btn) Button mRetryButton;
    @BindView(R.id.problem_internet_tv) TextView mInternetProblemTv;
    @BindView(R.id.problem_unauthorized_tv) TextView mUnAuthorizedProblemTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        final MovieAdapter adapter = new MovieAdapter(this);

        final GridLayoutManager manager = new GridLayoutManager(this,MULTIPLE_COLUMN);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        DashBoardViewModelFactory factory = new DashBoardViewModelFactory(getApplication());
        viewModel = ViewModelProviders.of(this,factory).get(DashBoardViewModel.class);
        showProgressBar();



        viewModel.movies.observe(this, new Observer<Resource<List<Movie>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Movie>> movies) {
                if (movies != null) {
                    switch (movies.getStatus()){
                        //ERROR, LOADING, SUCCESS, UNAUTHORIZED
                        case LOADING:
                            hideRecyclerView();
                            hideInternetConnectionProblem();
                            hideUnAuthorizedError();
                            showProgressBar();
                        case SUCCESS:
                            hideProgressBar();
                            hideInternetConnectionProblem();
                            hideUnAuthorizedError();
                            adapter.swapData(movies.getData());
                            showRecyclerView();
                            break;
                        case ERROR:
                            if(movies.getData() == null || movies.getData().isEmpty()) {
                                hideRecyclerView();
                                hideProgressBar();
                                hideUnAuthorizedError();
                                showInterneConnectionProblem();
                            }else {
                                adapter.swapData(movies.getData());
                            }
                            break;
                        case UNAUTHORIZED:
                            hideRecyclerView();
                            hideProgressBar();
                            hideInternetConnectionProblem();
                            showUnAuthorizedError();
                            break;
                        default:
                            hideRecyclerView();
                            hideInternetConnectionProblem();
                            hideUnAuthorizedError();
                            showProgressBar();
                    }
                    showRecyclerView();
                }
            }
        });

        mRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideInternetConnectionProblem();
                hideRecyclerView();
                showProgressBar();
                retryRequest(mResourceID);
            }
        });

        if (savedInstanceState != null && savedInstanceState.containsKey(RESOURCE_ID)) {
            mResourceID = savedInstanceState.getInt(RESOURCE_ID);
            switch (mResourceID){
                default:
                case R.id.pupularity:
                    setPopularTv();
                    break;
                case R.id.top_rating:
                    setTopRatedTv();
                    break;
                case R.id.favorite:
                    setFavoriteTv();
                    break;
            }
        } else {
            mResourceID = R.id.pupularity;
            setPopularTv();
        }

    }

    private void retryRequest(int resourceID){
        hideRecyclerView();
        hideInternetConnectionProblem();
        hideUnAuthorizedError();
        showProgressBar();
        switch (resourceID) {
            default:
            case R.id.pupularity:
                viewModel.setPopularMovies();
                break;
            case R.id.top_rating:
                viewModel.setTopRatedMovies();
                break;
            case R.id.favorite:
                viewModel.setFavoriteMovies();
                break;
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

    private void setFavoriteTv(){
        final String arg = getString(R.string.main_screen_menu_favorite);
        mOrderingTv.setText(getString(R.string.main_screen_order_text,arg));
        viewModel.setFavoriteMovies();
    }

    private void showRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void hideRecyclerView(){
        recyclerView.setVisibility(View.GONE);
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        mProgressBar.setVisibility(View.GONE);
    }

    private void showUnAuthorizedError(){
        mUnAuthorizedProblemTv.setVisibility(View.VISIBLE);
    }

    private void hideUnAuthorizedError(){
        mUnAuthorizedProblemTv.setVisibility(View.GONE);
    }

    private void showInterneConnectionProblem(){
        mRetryButton.setVisibility(View.VISIBLE);
        mInternetProblemTv.setVisibility(View.VISIBLE);
    }

    private void hideInternetConnectionProblem(){
        mRetryButton.setVisibility(View.GONE);
        mInternetProblemTv.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.order_menu, menu);
        return true;
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
            case R.id.favorite:
                if(mResourceID != R.id.favorite){
                    showProgressBar();
                    setFavoriteTv();
                    mResourceID = R.id.favorite;
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
