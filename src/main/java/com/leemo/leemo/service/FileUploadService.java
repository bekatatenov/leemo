package com.leemo.leemo.service;

import com.leemo.leemo.entity.UploadedFile;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    public void uploadLocal(MultipartFile file);
    public void uploadToDb(MultipartFile file);
    public UploadedFile downloadFile(String fileId);
}
