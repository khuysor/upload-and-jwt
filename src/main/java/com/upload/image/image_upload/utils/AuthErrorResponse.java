package com.upload.image.image_upload.utils;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthErrorResponse  {
    private HttpStatus httpStatus;
    private String message;

}
