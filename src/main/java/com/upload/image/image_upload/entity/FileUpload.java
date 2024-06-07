package com.upload.image.image_upload.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
public class FileUpload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;
    private String fileName;
    private String extension;
    private Long fileSize;
    private String filePath;
    private Boolean status;
    private String channelCode;
    private String fileUploadOriginalName;
    private LocalDateTime uploadTime=LocalDateTime.now();

}
