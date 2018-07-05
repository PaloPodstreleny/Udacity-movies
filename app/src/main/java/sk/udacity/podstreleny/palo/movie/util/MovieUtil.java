package sk.udacity.podstreleny.palo.movie.util;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import java.io.IOException;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sk.udacity.podstreleny.palo.movie.model.MovieList;
import sk.udacity.podstreleny.palo.movie.model.MovieListResponse;
import sk.udacity.podstreleny.palo.movie.model.RetrofitResponseRequest;
import sk.udacity.podstreleny.palo.movie.db.MovieDatabase;

public final class MovieUtil {

    private static final int UNAUTHORIZED = 401;

    public static MutableLiveData<MovieListResponse> getRetrofitData(final MutableLiveData<MovieListResponse> liveData, Call<MovieList> data, final MovieDatabase database, final Executor executor){

        data.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, final Response<MovieList> response) {

                if(response.body().getResults() != null && response.isSuccessful()){

                    executor.execute(new Runnable() {
                                         @Override
                                         public void run() {
                                             database.movieDao().insertAll(response.body().getResults());
                                         }
                                     }
                    );
                    liveData.setValue(new MovieListResponse(RetrofitResponseRequest.SUCCESSFUL,response.body().getResults()));
                }else {
                    if(response.code() == UNAUTHORIZED){
                        liveData.setValue(new MovieListResponse(RetrofitResponseRequest.UNAUTHORIZED,null));
                    }else {
                        liveData.setValue(new MovieListResponse(RetrofitResponseRequest.UNKNOWN,null));
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                if(t instanceof IOException){
                        liveData.setValue(new MovieListResponse(RetrofitResponseRequest.NO_INTERNET_CONNECTION,null));
                    }else {
                        liveData.setValue(new MovieListResponse(RetrofitResponseRequest.UNKNOWN,null));
                    }
            }
        });
        return liveData;
    }

}
