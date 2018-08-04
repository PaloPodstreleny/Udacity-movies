package sk.udacity.podstreleny.palo.movie.model;

import java.util.List;

import sk.udacity.podstreleny.palo.movie.db.entity.Review;

public class ReviewList {

    private int id;
    private List<Review> results;

    public ReviewList() {

    }

    public List<Review> getResults() {
        return results;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setResults(List<Review> results) {
        this.results = results;
    }
}
