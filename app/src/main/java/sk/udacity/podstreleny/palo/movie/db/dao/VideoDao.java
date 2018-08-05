package sk.udacity.podstreleny.palo.movie.db.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import sk.udacity.podstreleny.palo.movie.db.entity.Video;

@Dao
public interface VideoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Video> videos);

    @Query("SELECT * FROM videos WHERE movie_id = :movie_ids")
    LiveData<List<Video>> getAllVideos(int movie_ids);


}
