package sk.udacity.podstreleny.palo.movie.servicies;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sk.udacity.podstreleny.palo.movie.util.MovieUrlUtil;

public class RetrofitProvider {

    private static Retrofit.Builder sBuilder = new Retrofit.Builder().
            baseUrl(MovieUrlUtil.BASE_MOVIE_URL).
            addConverterFactory(GsonConverterFactory.create());

    private static Retrofit sRetrofit = sBuilder.build();

    public static <T> T getService(Class<T> data) {
        return sRetrofit.create(data);
    }


}
