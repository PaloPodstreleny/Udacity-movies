package sk.udacity.podstreleny.palo.movie.servicies;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import sk.udacity.podstreleny.palo.movie.ApiKey;
import sk.udacity.podstreleny.palo.movie.model.Movie;

public interface MovieService {

    @GET("/top_rated?"+ ApiKey.API_KEY)
    Call<List<Movie>> topRated();

    @GET("/popular?"+ ApiKey.API_KEY)
    Call<List<Movie>> popular();

}
