package sk.udacity.podstreleny.palo.movie.viewModels;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import sk.udacity.podstreleny.palo.movie.model.Movie;
import sk.udacity.podstreleny.palo.movie.repositories.DashBoardRepository;

public class DashBoardViewModel extends ViewModel {

    private MutableLiveData<Boolean> isMovieOrderChanged = new MutableLiveData<>();

    private DashBoardRepository mRepository;
    public final LiveData<List<Movie>> movies = Transformations.
            switchMap(isMovieOrderChanged, new Function<Boolean, LiveData<List<Movie>>>() {
                @Override
                public LiveData<List<Movie>> apply(Boolean input) {
                    if (input) {
                        return mRepository.getTopRatedMovies();
                    } else {
                        return mRepository.getPopularMovies();
                    }

                }
            });

    public DashBoardViewModel() {
        mRepository = new DashBoardRepository();
    }

    public void setTopRatedMovies() {
        isMovieOrderChanged.setValue(true);
    }

    public void setPopularMovies() {
        isMovieOrderChanged.setValue(false);
    }

}
