package sk.udacity.podstreleny.palo.movie.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sk.udacity.podstreleny.palo.movie.model.MovieList;
import sk.udacity.podstreleny.palo.movie.model.MovieListResponse;
import sk.udacity.podstreleny.palo.movie.model.RetrofitResponseRequest;
import sk.udacity.podstreleny.palo.movie.servicies.MovieService;

public class DashBoardRepository {

    private static final String LOG = DashBoardRepository.class.toString();
    private static final String BASE_MOVIE_URL = "https://api.themoviedb.org/";
    private static final int UNAUTHORIZED = 401;


    private MutableLiveData<MovieListResponse> popularMovies = new MutableLiveData<>();
    private MutableLiveData<MovieListResponse> topRatedMovies = new MutableLiveData<>();


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

    public LiveData<MovieListResponse> getTopRatedMovies() {
        Call<MovieList> data = movieService.topRated();

        if (topRatedMovies.getValue() != null && topRatedMovies.getValue().getResponse() == RetrofitResponseRequest.SUCCESSFUL) {
            return topRatedMovies;
        } else {

            data.enqueue(new Callback<MovieList>() {
                @Override
                public void onResponse(Call<MovieList> call,Response<MovieList> response) {
                    if(response.isSuccessful()){
                        topRatedMovies.setValue(new MovieListResponse(RetrofitResponseRequest.SUCCESSFUL,response.body().getResults()));
                    }else {
                        if(response.code() == UNAUTHORIZED){
                            topRatedMovies.setValue(new MovieListResponse(RetrofitResponseRequest.UNAUTHORIZED,null));
                        }else {
                            topRatedMovies.setValue(new MovieListResponse(RetrofitResponseRequest.UNKNOWN,null));
                        }
                    }
                }

                @Override
                public void onFailure(Call<MovieList> call, Throwable t) {
                    if(t instanceof IOException){
                        if(t instanceof IOException){
                            topRatedMovies.setValue(new MovieListResponse(RetrofitResponseRequest.NO_INTERNET_CONNECTION,null));
                        }else {
                            topRatedMovies.setValue(new MovieListResponse(RetrofitResponseRequest.UNKNOWN,null));
                        }
                    }
                }
            });

            return topRatedMovies;
        }
    }

    public LiveData<MovieListResponse> getPopularMovies() {
        Call<MovieList> data = movieService.popular();

        if (popularMovies.getValue() != null &&  popularMovies.getValue().getResponse() == RetrofitResponseRequest.SUCCESSFUL) {
            return popularMovies;
        } else {

            data.enqueue(new Callback<MovieList>() {
                @Override
                public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                    if(response.isSuccessful()) {
                        popularMovies.setValue(new MovieListResponse(RetrofitResponseRequest.SUCCESSFUL,response.body().getResults()));
                    }else {
                        if(response.code() == UNAUTHORIZED){
                            popularMovies.setValue(new MovieListResponse(RetrofitResponseRequest.UNAUTHORIZED,null));
                        }else {
                            popularMovies.setValue(new MovieListResponse(RetrofitResponseRequest.UNKNOWN,null));
                        }
                    }
                }

                @Override
                public void onFailure(Call<MovieList> call, Throwable t) {
                   if(t instanceof IOException){
                       popularMovies.setValue(new MovieListResponse(RetrofitResponseRequest.NO_INTERNET_CONNECTION,null));
                   }else {
                       popularMovies.setValue(new MovieListResponse(RetrofitResponseRequest.UNKNOWN,null));
                   }
                 }
            });

            return popularMovies;
        }
    }

    public LiveData<MovieListResponse> getFavouriteMovies(){
        //TODO add functionality
        return  null;
    }

}
