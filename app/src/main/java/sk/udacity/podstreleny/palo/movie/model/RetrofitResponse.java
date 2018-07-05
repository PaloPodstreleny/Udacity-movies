package sk.udacity.podstreleny.palo.movie.model;

public abstract class RetrofitResponse<T> {

    private final RetrofitResponseRequest response;
    private final T data;

    public RetrofitResponse(RetrofitResponseRequest response, T data) {
        this.response = response;
        this.data = data;
    }

    public RetrofitResponseRequest getResponse() {
        return response;
    }

    public T getData() {
        return data;
    }



}
