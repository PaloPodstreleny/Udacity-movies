package sk.udacity.podstreleny.palo.movie.model;

import java.util.List;

import sk.udacity.podstreleny.palo.movie.db.entity.Movie;

public class MovieListResponse extends RetrofitResponse<List<Movie>> {
    public MovieListResponse(RetrofitResponseRequest request, List<Movie> data){
        super(request,data);
    }
}
