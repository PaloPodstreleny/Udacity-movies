package sk.udacity.podstreleny.palo.movie.model;

import java.util.List;

import sk.udacity.podstreleny.palo.movie.db.entity.Movie;

public class MovieList {

    private List<Movie> results;

    public MovieList(){

    }

    public List<Movie> getResults() {
        return results;
    }

    public void setMovies(List<Movie> results) {
        this.results = results;
    }
}
