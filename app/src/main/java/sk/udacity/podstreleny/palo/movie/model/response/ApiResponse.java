package sk.udacity.podstreleny.palo.movie.model.response;


import java.io.IOException;
import retrofit2.Response;

public class ApiResponse<T>{

    private static final int EMPTY_RESPONSE = 204;
    private static final int UNAUTHORIZED = 401;

    public  ApiErrorResponse<T>  create(Throwable error){
        String errorMessage = "unknown error";
        if(error.getMessage() != null) {
                errorMessage = error.getMessage();
        }
        return new ApiErrorResponse<>(errorMessage);
    }

    public ApiResponse<T> create(Response<T> response){
        if(response.code() == UNAUTHORIZED){
            return new ApiUnauthorizedResponse<>();
        }

        if(response.isSuccessful()){
            T body = response.body();
            if (body == null || response.code() == EMPTY_RESPONSE){
                return new ApiEmptyResponse<>();
            } else {
                return new ApiSuccessResponse<>(body);
            }
        }else {
            String message = "";
            try {
                message = response.errorBody().string();
                if (message == null || message.isEmpty()){
                    message = response.message();
                }

            }catch (IOException e){
                e.printStackTrace();
            }
            return new ApiErrorResponse<T>(message);

        }
    }



}

