package com.upload.image.image_upload.dto;

import lombok.Data;


@Data
public class FileUploadRespone {
    private Long fileId;
    private String fileName;
    private String extension;
    private Long fileSize;
    private String filePath;
    private Boolean status;
    private String channelCode;

}
