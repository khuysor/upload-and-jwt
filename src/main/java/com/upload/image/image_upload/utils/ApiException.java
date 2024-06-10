package com.upload.image.image_upload.utils;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
@Data
@RequiredArgsConstructor
public class ApiException extends RuntimeException {
    private final HttpStatus status;
    private final String message;


}
