package sk.udacity.podstreleny.palo.movie.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "reviews",
        foreignKeys = @ForeignKey(entity = Movie.class,
                parentColumns = "id",
                childColumns = "movie_id"),

        indices = @Index("movie_id"))

public class Review {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "movie_id")
    private int movieID;
    private String author;
    private String content;


    public int getId() {
        return id;
    }

    public int getMovieID() {
        return movieID;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public void setId(int id) {
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
