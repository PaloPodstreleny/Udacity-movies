package sk.udacity.podstreleny.palo.movie.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;


@Entity(tableName = "videos",
        foreignKeys = @ForeignKey(entity = Movie.class,
                parentColumns = "id",
                childColumns = "movie_id"),
        indices = @Index("movie_id"))

public class Video {

    @PrimaryKey
    @NonNull
    private String id;

    private int movie_id;

    @SerializedName("key")
    @ColumnInfo(name = "watch_key")
    private String watchKey;

    @SerializedName("name")
    @ColumnInfo(name = "video_name")
    private String videoName;

    @SerializedName("type")
    @ColumnInfo(name = "video_type")
    private String videoType;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getWatchKey() {
        return watchKey;
    }

    public void setWatchKey(String watchKey) {
        this.watchKey = watchKey;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public String getVideoName() {
        return videoName;
    }
}
