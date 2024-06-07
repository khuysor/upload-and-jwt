package com.upload.image.image_upload.repository;

import com.upload.image.image_upload.entity.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileUploadRepository extends JpaRepository<FileUpload,Long> {
}
