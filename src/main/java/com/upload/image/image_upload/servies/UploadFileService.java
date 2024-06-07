package com.upload.image.image_upload.servies;

import com.upload.image.image_upload.dto.FileUploadRespone;

import com.upload.image.image_upload.entity.FileUpload;
import com.upload.image.image_upload.mapper.FileMapper;
import com.upload.image.image_upload.repository.FileUploadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UploadFileService {

    private  final FileUploadRepository fileUploadRepository;

    @Value("${upload.files.path}")
    private String uploadPath;
    @Value("${upload.files.extensions}")
    private String fileExtension;

    public List<FileUploadRespone> uploadFile(MultipartFile[] multipartFiles, Map<String ,String>header) {
        if(multipartFiles== null){
            log.error("Error uploading file");
            throw  new IllegalArgumentException("Invalid file");
        }
        List<FileUpload>files= new ArrayList<>();
        Arrays.stream(multipartFiles).forEach(file->{

            try {
                System.out.println(file.getOriginalFilename());
                String fileName= StringUtils.cleanPath(file.getOriginalFilename());
                String fileType= getFileType(fileName);

                if(!Arrays.asList(fileType).contains(fileType)){
                    throw  new IllegalArgumentException("Invalid file type");
                }
                byte [] bytes = file.getBytes();
                var fileNameUpload = FilenameUtils.removeExtension(fileName+'_'+ Calendar.getInstance().getTimeInMillis()+"."+getFileType(fileName));
                Files.write(Paths.get(uploadPath+file.getOriginalFilename()),bytes);
                FileUpload fileUpload = new FileUpload();
                fileUpload.setStatus(true);
                fileUpload.setFileName(fileNameUpload);
                fileUpload.setFileUploadOriginalName(FilenameUtils.removeExtension(fileName));
                fileUpload.setFilePath(uploadPath+fileName);
                fileUpload.setFileSize(file.getSize());
                fileUpload.setChannelCode(header.get("channel-code"));
                fileUpload.setExtension(fileType);
                files.add(fileUpload);
            }catch (IOException ex){
                log.error("Error uploading file",ex.getLocalizedMessage() );
            }
        });
        fileUploadRepository.saveAll(files);
        return files.stream().map(FileMapper.INSTANCE::toFileUploadRespone).collect(Collectors.toList());
    }

    public FileUploadRespone getFileUploadResponse(Long id) {
        Optional<FileUpload> fileUploadOptional = fileUploadRepository.findById(id);
        if (fileUploadOptional.isPresent()) {
            FileUploadRespone response = FileMapper.INSTANCE.toFileUploadRespone(fileUploadOptional.get());
            String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/files/view/")
                    .path(id.toString())
                    .toUriString();
            response.setFilePath(fileUrl);
            return response;
        } else {
            throw new IllegalArgumentException("File not found");
        }
    }
    public String getFileNameById(Long id) {
        return fileUploadRepository.findById(id)
                .map(FileUpload::getFilePath)
                .orElseThrow(() -> new RuntimeException("File not found"));
    }
    public List<FileUploadRespone>getAll(){
        List<FileUpload>fileUploads=fileUploadRepository.findAll();

        return fileUploads.stream().map(FileMapper.INSTANCE::toFileUploadRespone).collect(Collectors.toList()) ;
    }
    public String getFileType(String fileName) {
        int index = fileName.lastIndexOf(".");
        if(index <0){
            return null;
        }else{
            return fileName.substring(index+1);
        }
    }

}
