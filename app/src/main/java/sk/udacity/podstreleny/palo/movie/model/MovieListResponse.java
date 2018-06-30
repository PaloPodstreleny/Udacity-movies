package sk.udacity.podstreleny.palo.movie.model;

import java.util.List;

public class MovieListResponse extends RetrofitResponse<List<Movie>> {
    public MovieListResponse(RetrofitResponseRequest request, List<Movie> data){
        super(request,data);
    }
}
