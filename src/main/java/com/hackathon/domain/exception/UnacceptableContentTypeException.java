package com.hackathon.domain.exception;

import com.hackathon.util.exception.*;

public class UnacceptableContentTypeException extends BadRequestException {

    public UnacceptableContentTypeException(String message) {
        super(message);
    }
}
