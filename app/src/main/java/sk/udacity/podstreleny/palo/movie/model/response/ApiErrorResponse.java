package sk.udacity.podstreleny.palo.movie.model.response;

public class ApiErrorResponse<T> extends ApiResponse<T> {

    private final String errorMessage;

    public ApiErrorResponse(String errorMessage){
        this.errorMessage = errorMessage;

    }

    public String getErrorMessage() {
        return errorMessage;
    }

}



