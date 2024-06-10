package com.upload.image.image_upload.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoAuth {
    @GetMapping("hello")
    public ResponseEntity<?> login() {
        return ResponseEntity.ok("Hello World!");
    }
    @GetMapping("api/hello")
    public ResponseEntity<?> login1() {
        return ResponseEntity.ok("Hello World token");
    }
}
