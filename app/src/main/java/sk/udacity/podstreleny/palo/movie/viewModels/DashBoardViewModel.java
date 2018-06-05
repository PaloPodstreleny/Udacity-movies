package sk.udacity.podstreleny.palo.movie.viewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import sk.udacity.podstreleny.palo.movie.model.Movie;
import sk.udacity.podstreleny.palo.movie.repositories.DashBoardRepository;

public class DashBoardViewModel extends ViewModel {

    private DashBoardRepository mRepository;
    private LiveData<List<Movie>> mTopRatingMovies;
    private LiveData<List<Movie>> mMostPupularMovies;


    public DashBoardViewModel(){
        mRepository = new DashBoardRepository();
    }

    public LiveData<List<Movie>> getTopRatingMovies(){
        if(mTopRatingMovies == null){
            mTopRatingMovies =  mRepository.getTopMovies();
        }
        return mTopRatingMovies;
    }

    public LiveData<List<Movie>> getMostPupularMovies() {
        if(mMostPupularMovies == null){
            mMostPupularMovies =  mRepository.getPopularMovies();
        }
        return mMostPupularMovies;
    }
}
