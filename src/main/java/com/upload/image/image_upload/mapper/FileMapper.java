package com.upload.image.image_upload.mapper;

import com.upload.image.image_upload.dto.FileUploadRespone;
import com.upload.image.image_upload.entity.FileUpload;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.web.multipart.MultipartFile;

@Mapper
public interface FileMapper {
    FileMapper INSTANCE= Mappers.getMapper(FileMapper.class);
    FileUploadRespone toFileUploadRespone(FileUpload file);
    FileUpload toFileUpload(FileUploadRespone file);

}
