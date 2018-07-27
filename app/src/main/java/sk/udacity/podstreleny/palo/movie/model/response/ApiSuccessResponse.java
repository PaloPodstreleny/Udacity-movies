package sk.udacity.podstreleny.palo.movie.model.response;

public class ApiSuccessResponse<T> extends ApiResponse<T> {

    private T body;

    public ApiSuccessResponse(T body){
        this.body = body;
    }

    public T getBody() {
        return body;
    }
}
