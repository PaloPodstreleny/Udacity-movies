package sk.udacity.podstreleny.palo.movie.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.List;
import java.util.concurrent.TimeUnit;
import sk.udacity.podstreleny.palo.movie.AppExecutor;
import sk.udacity.podstreleny.palo.movie.db.MovieDatabase;
import sk.udacity.podstreleny.palo.movie.db.dao.MovieDao;
import sk.udacity.podstreleny.palo.movie.db.entity.Movie;
import sk.udacity.podstreleny.palo.movie.model.MovieList;
import sk.udacity.podstreleny.palo.movie.model.MovieType;
import sk.udacity.podstreleny.palo.movie.model.NetworkBoundResource;
import sk.udacity.podstreleny.palo.movie.model.Resource;
import sk.udacity.podstreleny.palo.movie.model.response.ApiResponse;
import sk.udacity.podstreleny.palo.movie.servicies.MovieService;
import sk.udacity.podstreleny.palo.movie.servicies.RetrofitProvider;
import sk.udacity.podstreleny.palo.movie.util.RateLimiter;

public class DashBoardRepository {

    private static DashBoardRepository INSTANCE;
    private AppExecutor executor;
    private MovieDao movieDao;
    private MovieService movieService;
    private final RateLimiter<String> repoListRateLimit = new RateLimiter<String>(10, TimeUnit.MINUTES);

    private DashBoardRepository(Application context) {
        movieService = RetrofitProvider.getService(MovieService.class);
        executor = new AppExecutor();
        movieDao = MovieDatabase.getDatabaseInstance(context).movieDao();
    }

    public static DashBoardRepository getInstance(Application context){
        if(INSTANCE == null){
            synchronized (DashBoardRepository.class){
                INSTANCE = new DashBoardRepository(context);
            }
        }
        return INSTANCE;
    }

    public LiveData<Resource<List<Movie>>> getFavouriteMovies(){
        return new NetworkBoundResource<List<Movie>,List<Movie>>(executor){
            @Override
            protected void saveCallResult(@NonNull List<Movie> item) {
                //noNeed
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Movie> data) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<List<Movie>> loadFromDb() {
                return movieDao.getFavoriteMovies();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Movie>>> createCall() {
                return null;
            }

        }.getAsLiveData();
    }


    public LiveData<Resource<List<Movie>>> getTopRateMovies(){
        final String topRated = "top_rated";

        return new NetworkBoundResource<List<Movie>,MovieList>(executor){
            @Override
            protected void saveCallResult(@NonNull MovieList item) {
                for (Movie movie : item.getResults()){
                    movie.setMovieType(MovieType.TOP_RATED);
                }
                movieDao.insertAll(item.getResults());
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Movie> data) {
                return (data == null || data.isEmpty() || repoListRateLimit.shouldFetch(topRated));
            }

            @NonNull
            @Override
            protected LiveData<List<Movie>> loadFromDb() {
                return movieDao.getTopRatedMovies();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<MovieList>> createCall() {
                return movieService.topRated();
            }

            @Override
            protected void onFetchFailed() {
                Log.e("DashboardRepository","Problem with fetching popular movies!");
            }

        }.getAsLiveData();
    }

    public LiveData<Resource<List<Movie>>> getPopularMovies(){
        final String popular = "popular";
      return new NetworkBoundResource<List<Movie>,MovieList>(executor){
          @Override
          protected void saveCallResult(@NonNull MovieList item) {
              for (Movie movie : item.getResults()){
                  movie.setMovieType(MovieType.POPULAR);
              }
              movieDao.insertAll(item.getResults());
          }

          @Override
          protected boolean shouldFetch(@Nullable List<Movie> data) {
              return (data == null || data.isEmpty() || repoListRateLimit.shouldFetch(popular) );
          }

          @NonNull
          @Override
          protected LiveData<List<Movie>> loadFromDb() {
              return movieDao.getPopularMovies();
          }

          @NonNull
          @Override
          protected LiveData<ApiResponse<MovieList>> createCall() {
             return movieService.popular();
          }

          @Override
          protected void onFetchFailed() {
              Log.e("DashboardRepository","Problem with fetching popular movies!");
          }

      }.getAsLiveData();

    }


}
