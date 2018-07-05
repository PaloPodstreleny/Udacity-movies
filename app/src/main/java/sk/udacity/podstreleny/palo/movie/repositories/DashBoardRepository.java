package sk.udacity.podstreleny.palo.movie.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import sk.udacity.podstreleny.palo.movie.AppExecutor;
import sk.udacity.podstreleny.palo.movie.model.MovieListResponse;
import sk.udacity.podstreleny.palo.movie.model.RetrofitResponseRequest;
import sk.udacity.podstreleny.palo.movie.servicies.MovieService;
import sk.udacity.podstreleny.palo.movie.servicies.RetrofitProvider;
import sk.udacity.podstreleny.palo.movie.util.MovieUtil;

public class DashBoardRepository extends BaseRepository {

    private static final String LOG = DashBoardRepository.class.toString();

    private MutableLiveData<MovieListResponse> popularMovies = new MutableLiveData<>();
    private MutableLiveData<MovieListResponse> topRatedMovies = new MutableLiveData<>();

    private MediatorLiveData<MovieListResponse> topRated = new MediatorLiveData<>();
    private MediatorLiveData<MovieListResponse> popular = new MediatorLiveData<>();

    private MovieService movieService;
    private AppExecutor appExecutor;

    public DashBoardRepository(Application context) {
        super(context);
        movieService = RetrofitProvider.getService(MovieService.class);
        appExecutor = new AppExecutor();

        topRated.addSource(topRatedMovies, new Observer<MovieListResponse>() {
            @Override
            public void onChanged(@Nullable MovieListResponse movieListResponse) {
                topRated.setValue(movieListResponse);
            }
        });

        popular.addSource(popularMovies, new Observer<MovieListResponse>() {

            @Override
            public void onChanged(@Nullable MovieListResponse movieListResponse) {
                popular.setValue(movieListResponse);
            }
        });
    }

    public LiveData<MovieListResponse> getTopRatedMovies() {
            if (topRatedMovies.getValue() != null && topRatedMovies.getValue().getResponse() == RetrofitResponseRequest.SUCCESSFUL) {
                return topRatedMovies;
            } else {
                appExecutor.diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        if(movieDatabase.movieDao().getMoviesCount() < 30){
                            topRatedMovies = MovieUtil.getRetrofitData(topRatedMovies,movieService.topRated(),movieDatabase,appExecutor.diskIO());
                        }else {
                            //Get data from db
                            topRatedMovies.postValue(new MovieListResponse(RetrofitResponseRequest.SUCCESSFUL,movieDatabase.movieDao().getTopRatedMovies()));
                        }
                    }
                });

                return topRated;
            }
    }

    public LiveData<MovieListResponse> getPopularMovies() {
        if (popularMovies.getValue() != null && popularMovies.getValue().getResponse() == RetrofitResponseRequest.SUCCESSFUL) {
            return popularMovies;
        } else {
            appExecutor.diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    if(movieDatabase.movieDao().getMoviesCount() < 30){
                        popularMovies = MovieUtil.getRetrofitData(popularMovies,movieService.popular(),movieDatabase,appExecutor.diskIO());
                    }else {
                        //Get data from db
                        popularMovies.postValue(new MovieListResponse(RetrofitResponseRequest.SUCCESSFUL,movieDatabase.movieDao().getPopularMovies()));
                    }
                }
            });

            return popular;
        }
    }

    public LiveData<MovieListResponse> getFavouriteMovies(){
        //TODO add functionality
        return  null;
    }

}
