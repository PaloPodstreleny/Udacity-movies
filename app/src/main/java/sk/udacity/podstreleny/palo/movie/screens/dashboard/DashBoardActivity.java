package sk.udacity.podstreleny.palo.movie.screens.dashboard;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import sk.udacity.podstreleny.palo.movie.R;
import sk.udacity.podstreleny.palo.movie.model.Movie;
import sk.udacity.podstreleny.palo.movie.viewModels.DashBoardViewModel;

public class DashBoardActivity extends AppCompatActivity {

    private static final int SPAN_COUNT = 2;
    private DashBoardViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MovieAdapter movieAdapter = new MovieAdapter();
        final RecyclerView recyclerView = findViewById(R.id.main_rv);

        recyclerView.setLayoutManager(new GridLayoutManager(this,SPAN_COUNT));
        recyclerView.setAdapter(movieAdapter);

        viewModel = ViewModelProviders.of(this).get(DashBoardViewModel.class);
        viewModel.setTopRatedMovies();


        viewModel.movies.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                if(movies != null){
                    movieAdapter.swapData(movies);
                }
            }
        });
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
                viewModel.setTopRatedMovies();
                return true;
            case R.id.pupularity:
                viewModel.setPopularMovies();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
