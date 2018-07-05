package sk.udacity.podstreleny.palo.movie.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import sk.udacity.podstreleny.palo.movie.model.Movie;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Movie> movies);

    @Insert
    void insertMovie(Movie movie);

    @Query("SELECT COUNT(popularity) FROM movies")
    int getMoviesCount();

    @Query("SELECT * FROM movies ORDER BY popularity DESC")
    List<Movie> getPopularMovies();

    @Query("SELECT * FROM movies ORDER BY vote_average DESC ")
    List<Movie> getTopRatedMovies();

    @Update
    void updateMovie(Movie movie);

}
