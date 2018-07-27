package sk.udacity.podstreleny.palo.movie.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import sk.udacity.podstreleny.palo.movie.model.MovieType;
import sk.udacity.podstreleny.palo.movie.util.MovieTypeConvertor;

@Entity(tableName = "movies",
        indices = {
                @Index("popularity"),
                @Index("voteAverage")})
public class Movie implements Parcelable {

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
    private MovieType movieType;

    private Float popularity;
    private String overview;
    private boolean favorite = false;

    public Movie(){
    }

    @Ignore
    protected Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        posterPath = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readFloat();
        popularity = in.readFloat();
        overview = in.readString();
        favorite = in.readByte() != 0;
    }

    @Ignore
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }

    @Ignore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(releaseDate);
        dest.writeFloat(voteAverage);
        dest.writeFloat(popularity);
        dest.writeString(overview);
        dest.writeByte((byte) (favorite ? 1 : 0));
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

    public MovieType getMovieType() {
        return movieType;
    }

    public void setMovieType(MovieType movieType) {
        this.movieType = movieType;
    }
}
