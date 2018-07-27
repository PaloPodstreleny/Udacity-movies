package sk.udacity.podstreleny.palo.movie.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import sk.udacity.podstreleny.palo.movie.db.entity.Review;
import sk.udacity.podstreleny.palo.movie.model.Resource;
import sk.udacity.podstreleny.palo.movie.model.response.ApiResponse;

@Dao
public interface ReviewDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Review> reviews);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Review review);

    @Query("SELECT * FROM reviews WHERE movie_id = :movie_ids")
    LiveData<List<Review>> getAllReviews(int movie_ids);

//    @Query("SELECT * FROM reviews WHERE movie_id = :movie_ids")
//    LiveData<List<Review>>> getAllReviews(int movie_ids);

}
