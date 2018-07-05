package sk.udacity.podstreleny.palo.movie.servicies;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitProvider {

    private static final String BASE_MOVIE_URL = "https://api.themoviedb.org/";

    private static Retrofit.Builder sBuilder = new Retrofit.Builder().
            baseUrl(BASE_MOVIE_URL).
            addConverterFactory(GsonConverterFactory.create());

    private static Retrofit sRetrofit = sBuilder.build();

    public static <T> T getService(Class<T> data) {
        return sRetrofit.create(data);
    }


}
