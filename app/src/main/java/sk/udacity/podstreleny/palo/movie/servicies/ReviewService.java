package sk.udacity.podstreleny.palo.movie.servicies;

import android.arch.lifecycle.LiveData;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import sk.udacity.podstreleny.palo.movie.ApiKey;
import sk.udacity.podstreleny.palo.movie.db.entity.Review;
import sk.udacity.podstreleny.palo.movie.model.response.ApiResponse;

public interface ReviewService {


    @GET("3/movie/{id}/reviews?api_key="+ ApiKey.API_KEY)
    LiveData<ApiResponse<List<Review>>> getReviewsWithMovieID(@Path("id") int id);

}
