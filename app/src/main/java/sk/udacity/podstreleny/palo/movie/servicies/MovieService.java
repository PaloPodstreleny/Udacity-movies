package sk.udacity.podstreleny.palo.movie.servicies;

import android.arch.lifecycle.LiveData;

import retrofit2.Call;
import retrofit2.http.GET;
import sk.udacity.podstreleny.palo.movie.ApiKey;
import sk.udacity.podstreleny.palo.movie.model.MovieList;
import sk.udacity.podstreleny.palo.movie.model.response.ApiResponse;

public interface MovieService {

    @GET("3/movie/top_rated?api_key="+ ApiKey.API_KEY)
    LiveData<ApiResponse<MovieList>> topRated();

    @GET("3/movie/popular?api_key="+ ApiKey.API_KEY)
    LiveData<ApiResponse<MovieList>> popular();

}
