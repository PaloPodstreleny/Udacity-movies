package sk.udacity.podstreleny.palo.movie.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "videos",
        foreignKeys = @ForeignKey(entity = Movie.class,
                parentColumns = "id",
                childColumns = "movie_id"),
indices = @Index("movie_id"))

public class Video {

    @PrimaryKey
    private int id;

    private int movie_id;

    @ColumnInfo(name = "url_adress")
    private String urlAdress;

    @ColumnInfo(name = "video_name")
    private String videoName;

    public void setId(int id) {
        this.id = id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public void setUrlAdress(String urlAdress) {
        this.urlAdress = urlAdress;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public int getId() {
        return id;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public String getUrlAdress() {
        return urlAdress;
    }

    public String getVideoName() {
        return videoName;
    }
}
