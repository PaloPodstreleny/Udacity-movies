package sk.udacity.podstreleny.palo.movie.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "reviews",
        foreignKeys = @ForeignKey(entity = Movie.class,
                parentColumns = "id",
                childColumns = "movie_id"),

        indices = @Index("movie_id"))

public class Review {

    @NonNull
    @PrimaryKey
    private String id;

    @ColumnInfo(name = "movie_id")
    private int movieID;
    private String author;
    private String content;


    public int getMovieID() {
        return movieID;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
