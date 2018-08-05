package sk.udacity.podstreleny.palo.movie.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import sk.udacity.podstreleny.palo.movie.model.MovieOrder;

@Entity(tableName = "movies",
        indices = {
                @Index("popularity"),
                @Index("voteAverage")})
public class Movie {

    @PrimaryKey
    private int id;

    private String title;

    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    private String posterPath;

    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    private String releaseDate;

    @SerializedName("vote_average")
    private Float voteAverage;

    @ColumnInfo(name = "movie_type")
    private MovieOrder movieOrder;

    private Float popularity;
    private String overview;
    private boolean favorite = false;

    public Movie() {
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getPopularity() {
        return popularity;
    }

    public void setPopularity(Float popularity) {
        this.popularity = popularity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public MovieOrder getMovieOrder() {
        return movieOrder;
    }

    public void setMovieOrder(MovieOrder movieOrder) {
        this.movieOrder = movieOrder;
    }
}
