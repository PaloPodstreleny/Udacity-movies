package sk.udacity.podstreleny.palo.movie.util;


import android.arch.persistence.room.TypeConverter;

import sk.udacity.podstreleny.palo.movie.model.MovieOrder;


public class MovieTypeConvertor {

    @TypeConverter
    public int MovieTypeToInt(MovieOrder type) {
        switch (type) {
            case POPULARITY:
                return 0;
            case TOP_RATED:
                return 1;
            default:
                throw new IllegalArgumentException("MovieType can not be converted to int!");
        }
    }

    @TypeConverter
    public MovieOrder intToMovieType(int movieType) {
        switch (movieType) {
            case 0:
                return MovieOrder.POPULARITY;
            case 1:
                return MovieOrder.TOP_RATED;
            default:
                throw new IllegalArgumentException("Int can not be converted to movieType!");
        }
    }

}
