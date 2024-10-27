package com.fsa.cursus.handle;

import com.fsa.cursus.exception.InvalidTokenException;
import com.fsa.cursus.model.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class, RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiResponse handleException(Exception ex) {
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.error("Internal Server Error");

        return apiResponse;
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ApiResponse handleBadCredentialsException(BadCredentialsException ex) {
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.error("Bad credentials");

        return apiResponse;
    }

    @ExceptionHandler({InvalidTokenException.class, AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ApiResponse handleInvalidTokenException(InvalidTokenException ex) {
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.error("Forbidden");

        return apiResponse;
    }

}

