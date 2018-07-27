package sk.udacity.podstreleny.palo.movie.util;

import android.arch.lifecycle.LiveData;
import android.support.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import sk.udacity.podstreleny.palo.movie.model.response.ApiResponse;

public class LiveDataCallAdapterFactory extends CallAdapter.Factory {


    @Nullable
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if(CallAdapter.Factory.getRawType(returnType) != LiveData.class){
            return null;
        }

        Type observableType = CallAdapter.Factory.getParameterUpperBound(0, (ParameterizedType) returnType);
        Object rawObservableType = CallAdapter.Factory.getRawType(observableType);

        if(rawObservableType != ApiResponse.class){
            throw new IllegalArgumentException("type must be a resource");
        }

        if(!(observableType instanceof ParameterizedType)){
            throw new IllegalArgumentException("type must be parameterized");
        }

        Type bodyType = CallAdapter.Factory.getParameterUpperBound(0,(ParameterizedType) observableType);

        return new LiveDataCallAdapter<>(bodyType);

    }



}
