package com.upload.image.image_upload.auth;

import lombok.Data;

@Data
public class RegisterRequest {
    private String firstname;
    private String password;
    private String email;
    private String phone;
    private String role;
}
