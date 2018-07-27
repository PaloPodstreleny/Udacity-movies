package sk.udacity.podstreleny.palo.movie.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import sk.udacity.podstreleny.palo.movie.db.entity.Movie;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Movie> movies);

    @Query("SELECT * FROM movies WHERE movie_type = 0 ORDER BY popularity DESC")
    LiveData<List<Movie>> getPopularMovies();

    @Query("SELECT * FROM movies WHERE movie_type = 1 ORDER BY voteAverage DESC ")
    LiveData<List<Movie>> getTopRatedMovies();

    @Query("SELECT * FROM movies WHERE favorite = 1")
    LiveData<List<Movie>> getFavoriteMovies();

    @Query("SELECT * FROM movies WHERE id = :id")
    LiveData<Movie> getMovieById(int id);

    @Update
    void updateMovie(Movie movie);

}
