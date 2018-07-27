package sk.udacity.podstreleny.palo.movie.viewModels;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

import sk.udacity.podstreleny.palo.movie.db.entity.Movie;
import sk.udacity.podstreleny.palo.movie.model.MovieOrder;
import sk.udacity.podstreleny.palo.movie.model.Resource;
import sk.udacity.podstreleny.palo.movie.repositories.DashBoardRepository;
import sk.udacity.podstreleny.palo.movie.screen.dashboard.DashBoardActivity;

import static sk.udacity.podstreleny.palo.movie.model.MovieOrder.POPULARITY;
import static sk.udacity.podstreleny.palo.movie.model.MovieOrder.TOP_RATED;

public class DashBoardViewModel extends ViewModel {

    private static final String LOG = DashBoardActivity.class.toString();
    private MutableLiveData<MovieOrder> isMovieOrderChanged = new MutableLiveData<>();

    private DashBoardRepository mRepository;

    public final LiveData<Resource<List<Movie>>> movies = Transformations.switchMap(isMovieOrderChanged, new Function<MovieOrder, LiveData<Resource<List<Movie>>>>() {
                @Override
                public LiveData<Resource<List<Movie>>> apply(MovieOrder input) {
                    switch (input) {
                        case TOP_RATED:
                            return mRepository.getTopRateMovies();
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



    public DashBoardViewModel(Application context) {
        mRepository = DashBoardRepository.getInstance(context);
    }

    public void setTopRatedMovies() {
        isMovieOrderChanged.setValue(TOP_RATED);
    }

    public void setPopularMovies() {
        isMovieOrderChanged.setValue(POPULARITY);
    }

    public void setFavoriteMovies(){
        isMovieOrderChanged.setValue(MovieOrder.FAVORITE);
    }

}
