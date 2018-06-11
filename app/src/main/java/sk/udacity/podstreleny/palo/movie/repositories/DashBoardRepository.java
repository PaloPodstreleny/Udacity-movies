package sk.udacity.podstreleny.palo.movie.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sk.udacity.podstreleny.palo.movie.model.Movie;
import sk.udacity.podstreleny.palo.movie.model.MovieList;
import sk.udacity.podstreleny.palo.movie.servicies.MovieService;

public class DashBoardRepository {

    private static final String TAG = DashBoardRepository.class.toString();
    private static final String BASE_MOVIE_URL = "https://api.themoviedb.org/";

    private MutableLiveData<List<Movie>> popularMovies = new MutableLiveData<>();
    private MutableLiveData<List<Movie>> topRatedMovies = new MutableLiveData<>();
    private MovieService movieService;

    public DashBoardRepository() {
        Retrofit.Builder builder = createBuilder();
        Retrofit retrofit = builder.build();
        movieService = retrofit.create(MovieService.class);
    }

    private Retrofit.Builder createBuilder() {
        return new Retrofit.Builder()
                .baseUrl(BASE_MOVIE_URL)
                .addConverterFactory(GsonConverterFactory.create());
    }

    public LiveData<List<Movie>> getTopRatedMovies() {
        Call<MovieList> data = movieService.topRated();

        if (topRatedMovies.getValue() != null && !topRatedMovies.getValue().isEmpty()) {
            return topRatedMovies;
        } else {

            data.enqueue(new Callback<MovieList>() {
                @Override
                public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                    topRatedMovies.setValue(response.body().getResults());
                }

                @Override
                public void onFailure(Call<MovieList> call, Throwable t) {
                    Log.e(TAG, "Problem with getting top rated movies!");
                }
            });

            return topRatedMovies;
        }
    }

    public LiveData<List<Movie>> getPopularMovies() {
        Call<MovieList> data = movieService.popular();

        if (popularMovies.getValue() != null && !popularMovies.getValue().isEmpty()) {
            return popularMovies;
        } else {

            data.enqueue(new Callback<MovieList>() {
                @Override
                public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                    popularMovies.setValue(response.body().getResults());
                }

                @Override
                public void onFailure(Call<MovieList> call, Throwable t) {
                    Log.e(TAG, "Problem with getting popular data!");
                }
            });

            return popularMovies;
        }
    }

}
