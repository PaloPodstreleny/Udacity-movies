package sk.udacity.podstreleny.palo.movie.viewModels;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

import sk.udacity.podstreleny.palo.movie.model.Movie;
import sk.udacity.podstreleny.palo.movie.model.MovieOrder;
import sk.udacity.podstreleny.palo.movie.repositories.DashBoardRepository;
import sk.udacity.podstreleny.palo.movie.screen.dashboard.DashBoardActivity;

public class DashBoardViewModel extends ViewModel {

    private static final String LOG = DashBoardActivity.class.toString();
    private MutableLiveData<MovieOrder> isMovieOrderChanged = new MutableLiveData<>();

    private DashBoardRepository mRepository;
    public final LiveData<List<Movie>> movies = Transformations.
            switchMap(isMovieOrderChanged, new Function<MovieOrder, LiveData<List<Movie>>>() {
                @Override
                public LiveData<List<Movie>> apply(MovieOrder input) {
                    switch (input) {
                        case TOP_RATED:
                            return mRepository.getTopRatedMovies();
                        case POPULARITY:
                            return mRepository.getPopularMovies();
                        case FAVORITE:
                            return mRepository.getFavouriteMovies();
                        default:
                            Log.e(LOG, "This enum value of MovieOrder is not defined!");
                            return null;
                    }
                }
            });


    //TODO use injection
    public DashBoardViewModel() {
        mRepository = new DashBoardRepository();
    }

    public void setTopRatedMovies() {
        isMovieOrderChanged.setValue(MovieOrder.TOP_RATED);
    }

    public void setPopularMovies() {
        isMovieOrderChanged.setValue(MovieOrder.POPULARITY);
    }

    public void setFavoriteMovies(){
        isMovieOrderChanged.setValue(MovieOrder.FAVORITE);
    }

}
