package com.hackathon.config;

import com.hackathon.util.api.*;
import com.hackathon.util.exception.*;
import lombok.extern.slf4j.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(CustomException.class)
    public ApiResponse<Object> customException(CustomException e) {

        int code = e.getCode();
        Object data = e.getData();
        String clientShowMsg;

        if (e instanceof ExpectableServerException serverEx) {
            log.warn(
                    "Server exception has been raised : {}",
                    serverEx.getClass().getSimpleName(),
                    serverEx
            );

            clientShowMsg = serverEx.getClientShowMessage();
        } else {
            clientShowMsg = e.getMessage();
        }

        return ApiResponse.fail(code, data, clientShowMsg);
    }
}
