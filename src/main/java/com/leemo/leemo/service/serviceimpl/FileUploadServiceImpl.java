package com.leemo.leemo.service.serviceimpl;

import com.leemo.leemo.entity.UploadedFile;
import com.leemo.leemo.repo.FileUploadRepository;
import com.leemo.leemo.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    private final String uploadFolderPath = "C:\\Users\\Aibek\\blame";
    @Autowired
    private FileUploadRepository fileUploadRepository;
    @Override
    public void uploadLocal(MultipartFile file) {

        try {
            byte[] data = file.getBytes();
            Path path = Paths.get(uploadFolderPath + file.getOriginalFilename());
            Files.write(path,data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void uploadToDb(MultipartFile file) {
        UploadedFile uploadedFile = new UploadedFile();
        try {
            uploadedFile.setFileData(file.getBytes());
            uploadedFile.setFileType(file.getContentType());
            uploadedFile.setFileName(file.getOriginalFilename());
            fileUploadRepository.save(uploadedFile);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public UploadedFile downloadFile(String fileId) {
        return fileUploadRepository.findFirstByFileId(fileId);
    }
}