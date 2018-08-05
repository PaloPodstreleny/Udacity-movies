package sk.udacity.podstreleny.palo.movie.viewModels;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import java.util.List;

import sk.udacity.podstreleny.palo.movie.db.entity.Movie;
import sk.udacity.podstreleny.palo.movie.db.entity.Review;
import sk.udacity.podstreleny.palo.movie.db.entity.Video;
import sk.udacity.podstreleny.palo.movie.model.Resource;
import sk.udacity.podstreleny.palo.movie.repositories.MovieDetailRepository;

public class MovieDetailViewModel extends AndroidViewModel {

    private MovieDetailRepository movieDetailRepository;

    private MutableLiveData<Integer> movieIDForReview = new MutableLiveData<>();
    private LiveData<Resource<List<Review>>> reviews = Transformations.switchMap(movieIDForReview, new Function<Integer, LiveData<Resource<List<Review>>>>() {
        @Override
        public LiveData<Resource<List<Review>>> apply(Integer input) {
            return movieDetailRepository.getReviews(input);
        }
    });

    private MutableLiveData<Integer> movieID = new MutableLiveData<>();
    private LiveData<Movie> movie = Transformations.switchMap(movieID, new Function<Integer, LiveData<Movie>>() {
        @Override
        public LiveData<Movie> apply(Integer input) {
            return movieDetailRepository.getMovieById(input);
        }
    });

    private MutableLiveData<Integer> movieIDForVideo = new MutableLiveData<>();
    private LiveData<Resource<List<Video>>> videos = Transformations.switchMap(movieIDForReview, new Function<Integer, LiveData<Resource<List<Video>>>>() {
        @Override
        public LiveData<Resource<List<Video>>> apply(Integer input) {
            return movieDetailRepository.getVideos(input);
        }
    });

    public MovieDetailViewModel(Application application){
        super(application);
        movieDetailRepository = MovieDetailRepository.getInstance(getApplication());
    }

    public void updateMovie(Movie movie){
        movieDetailRepository.updateMovie(movie);
    }


    public void setMovieId(int id){
        if (movie != null) {
            movieID.setValue(id);
            movieIDForReview.setValue(id);
            movieIDForVideo.setValue(id);
        }
    }

    public LiveData<Movie> getMovie(){
        return movie;
    }
    public LiveData<Resource<List<Review>>> getReviews(){return reviews;}
    public LiveData<Resource<List<Video>>> getVideos(){return videos;}
}
