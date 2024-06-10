package com.upload.image.image_upload.utils;

public class DuplicateKey extends RuntimeException{
    public DuplicateKey(String message){
        super(message);
    }
}
