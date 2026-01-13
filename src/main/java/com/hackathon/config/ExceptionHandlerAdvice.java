package com.hackathon.config;

import com.hackathon.util.api.*;
import com.hackathon.util.exception.*;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(CustomException.class)
    public ApiResponse<?> customException(CustomException e) {
        return ApiResponse.fail(
                e.getCode(), null, e.getMessage()
        );
    }
}
