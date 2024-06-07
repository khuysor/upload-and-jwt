package com.upload.image.image_upload.controller;

import com.upload.image.image_upload.dto.FileUploadRespone;
import com.upload.image.image_upload.servies.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/files")
@RequiredArgsConstructor
public class FileController {
    private final UploadFileService uploadFileService;

    @Value("${upload.files.path}")
    private String uploadDirectory;

    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    @PostMapping("upload")
    public ResponseEntity<?> upload(@RequestParam("files") MultipartFile[] files, @RequestHeader Map<String, String> header) {
        try {
            List<FileUploadRespone> response = uploadFileService.uploadFile(files, header);
            response.forEach(file -> {
                String fileUrl = "http://localhost:8080/api/files/" + file.getFileId();
                file.setFilePath(fileUrl);
            });
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("upload error", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }
    @GetMapping()
    public ResponseEntity<?> getFile(){
        List<FileUploadRespone>fileUploadRespones = uploadFileService.getAll();
        return ResponseEntity.ok(fileUploadRespones);
    }
    @GetMapping("{id}")
    public ResponseEntity<?> getFile(@PathVariable("id") Long id) {
        FileUploadRespone fileUploadRespone = uploadFileService.getFileUploadResponse(id);
        return ResponseEntity.ok(fileUploadRespone);
    }
    @GetMapping("view/{id}")
    public ResponseEntity<Resource> viewFile(@PathVariable("id") Long id) {
        try {
            String fileName = uploadFileService.getFileNameById(id);
            Path filePath = Paths.get(uploadDirectory).resolve(fileName).normalize();

            Resource resource = new FileSystemResource(filePath);
            if (resource.exists() && resource.isReadable()) {
                MediaType mediaType = MediaType.parseMediaType(Files.probeContentType(filePath));
                return ResponseEntity.ok()
                        .contentType(mediaType)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Error retrieving file", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
