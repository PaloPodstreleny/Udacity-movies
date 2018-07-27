package sk.udacity.podstreleny.palo.movie.util;


import android.arch.persistence.room.TypeConverter;

import sk.udacity.podstreleny.palo.movie.model.MovieType;


public  class MovieTypeConvertor {

    @TypeConverter
    public int MovieTypeToInt(MovieType type){
        switch (type){
            case  POPULAR :
                return 0;
            case TOP_RATED:
                return 1;
            default:
                throw new IllegalArgumentException("MovieType can not be converted to int!");
        }
    }

    @TypeConverter
    public  MovieType intToMovieType(int movieType){
        switch (movieType){
            case 0:
                return MovieType.POPULAR;
            case 1:
                return MovieType.TOP_RATED;
            default:
                throw new IllegalArgumentException("Int can not be converted to movieType!");
        }
    }

}
