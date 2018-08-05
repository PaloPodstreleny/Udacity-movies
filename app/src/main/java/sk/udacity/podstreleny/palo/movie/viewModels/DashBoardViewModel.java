package sk.udacity.podstreleny.palo.movie.viewModels;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import sk.udacity.podstreleny.palo.movie.db.entity.Movie;
import sk.udacity.podstreleny.palo.movie.model.MovieOrder;
import sk.udacity.podstreleny.palo.movie.model.Resource;
import sk.udacity.podstreleny.palo.movie.repositories.DashBoardRepository;
import sk.udacity.podstreleny.palo.movie.screen.dashboard.DashBoardActivity;

public class DashBoardViewModel extends AndroidViewModel {

    private static final String LOG = DashBoardActivity.class.toString();
    private MutableLiveData<MovieOrder> isMovieOrderChanged = new MutableLiveData<>();

    private boolean forceLoad = false;

    private LiveData<Resource<List<Movie>>> topRatedMovies;
    private LiveData<Resource<List<Movie>>> popularMovies;
    private LiveData<Resource<List<Movie>>> favoriteMovies;

    private DashBoardRepository mRepository;

    public final LiveData<Resource<List<Movie>>> movies = Transformations.switchMap(isMovieOrderChanged, new Function<MovieOrder, LiveData<Resource<List<Movie>>>>() {
                @Override
                public LiveData<Resource<List<Movie>>> apply(MovieOrder input) {
                    switch (input) {
                        case TOP_RATED:
                            if(forceLoad){
                                return mRepository.getTopRateMovies();
                            }
                            if(topRatedMovies == null) {
                                topRatedMovies =  mRepository.getTopRateMovies();
                            }
                            return topRatedMovies;
                        case POPULARITY:
                            if(forceLoad)
                                return mRepository.getPopularMovies();
                            if(popularMovies == null) {
                                popularMovies = mRepository.getPopularMovies();
                            }
                            return popularMovies;
                        case FAVORITE:
                            if(favoriteMovies == null) {
                                favoriteMovies = mRepository.getFavouriteMovies();
                            }
                            return favoriteMovies;
                        default:
                            Log.e(LOG, "This enum value of MovieOrder is not defined! Possible options: TOP_RATED,POPULARITY,FAVORITE");
                            return null;
                    }
                }
            });

    public DashBoardViewModel(@NonNull Application application) {
        super(application);
        mRepository = DashBoardRepository.getInstance(getApplication());
    }

    /***
     *
     * Method modifies MutableLiveData which triggers fetching data from repository
     *
     * @param movieOrder tells which data fetch from repository
     * @param mustFetch decides if this fetch have to be forced or we can use cached data
     */
    public void setMovieOrder(MovieOrder movieOrder, boolean mustFetch) {
        forceLoad = mustFetch;
        isMovieOrderChanged.setValue(movieOrder);
    }
}
