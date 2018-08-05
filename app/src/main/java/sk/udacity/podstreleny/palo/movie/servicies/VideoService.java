package sk.udacity.podstreleny.palo.movie.servicies;

import android.arch.lifecycle.LiveData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import sk.udacity.podstreleny.palo.movie.ApiKey;
import sk.udacity.podstreleny.palo.movie.model.VideoList;
import sk.udacity.podstreleny.palo.movie.model.response.ApiResponse;

public interface VideoService {

    @GET("3/movie/{id}/videos?api_key="+ ApiKey.API_KEY)
    LiveData<ApiResponse<VideoList>> getVideosWithMovieID(@Path("id") int id);

}
