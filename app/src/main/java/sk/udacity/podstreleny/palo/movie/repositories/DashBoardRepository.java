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
import sk.udacity.podstreleny.palo.movie.servicies.MovieService;

public class DashBoardRepository {

    private static final String TAG = DashBoardRepository.class.toString();
    private static final String BASE_MOVIEW_URL = "https://api.themoviedb.org/3/movie";

    private MutableLiveData<List<Movie>> topMovies = new MutableLiveData<>();


    private MovieService movieService;

    public DashBoardRepository() {
        Retrofit.Builder builder = createBuilder();
        Retrofit retrofit = builder.build();
        movieService = retrofit.create(MovieService.class);

    }

    private Retrofit.Builder createBuilder() {
        return new Retrofit.Builder()
                .baseUrl(BASE_MOVIEW_URL)
                .addConverterFactory(GsonConverterFactory.create());
    }

    public LiveData<List<Movie>> getTopMovies() {
        return  null;
    }

    public LiveData<List<Movie>> getPopularMovies() {
        Call<List<Movie>> data = movieService.popular();

        if (topMovies.getValue() != null && topMovies.getValue().isEmpty()) {
            return topMovies;
        } else {

            data.enqueue(new Callback<List<Movie>>() {
                @Override
                public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                    topMovies.setValue(response.body());
                }

                @Override
                public void onFailure(Call<List<Movie>> call, Throwable t) {
                    Log.e(TAG, "Problem with getting data!");
                }
            });

            return topMovies;
        }
    }

}
