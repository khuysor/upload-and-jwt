package com.upload.image.image_upload.utils;

import org.springframework.http.HttpStatus;

public class ErrorResponse extends ApiException {
    public ErrorResponse(HttpStatus status, String message) {
        super(status, message);
    }
}
