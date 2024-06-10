package com.upload.image.image_upload.utils;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<AuthErrorResponse> handleDuplicateKeyException(DataIntegrityViolationException ex) {
        String errorMessage = "User name already taken " ;
        AuthErrorResponse authErrorResponse = AuthErrorResponse.builder()
                .httpStatus(HttpStatus.UNAUTHORIZED) // 409 Conflict
                .message(errorMessage)
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(authErrorResponse);
    }
}
