package com.upload.image.image_upload.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthService authService;
    @GetMapping("register")
    public ResponseEntity<?> authenticate() {
        return ResponseEntity.ok("Jep");
    }
    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.authenticate(registerRequest));
    }

}
