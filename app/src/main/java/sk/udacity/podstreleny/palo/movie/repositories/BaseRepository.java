package sk.udacity.podstreleny.palo.movie.repositories;

import android.app.Application;

import sk.udacity.podstreleny.palo.movie.db.MovieDatabase;

public abstract class BaseRepository {

    protected MovieDatabase movieDatabase;

    public BaseRepository(Application context){
        this.movieDatabase = MovieDatabase.getDatabaseInstance(context);
    }

}
