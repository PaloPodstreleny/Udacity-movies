package sk.udacity.podstreleny.palo.movie.servicies;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import sk.udacity.podstreleny.palo.movie.ApiKey;
import sk.udacity.podstreleny.palo.movie.model.MovieList;

public interface MovieService {

    @GET("/3/movie/top_rated?api_key="+ ApiKey.API_KEY)
    Call<MovieList> topRated();

    @GET("/3/movie/popular?api_key="+ ApiKey.API_KEY)
    Call<MovieList> popular();

}
