package com.leemo.leemo.controller;

import com.leemo.leemo.entity.UploadedFile;
import com.leemo.leemo.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1")
public class UploadFileController {

    @Autowired
    private FileUploadService fileUploadService;
    @PostMapping("/upload/local")
    public void uploadLocal(@RequestParam("file")MultipartFile multipartFile)
    {
        fileUploadService.uploadLocal(multipartFile);

    }

    @PostMapping("/upload/db")
    public void uploadDb(@RequestParam("file")MultipartFile multipartFile){
    fileUploadService.uploadToDb(multipartFile);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> uploadedFile(@PathVariable String id){
        UploadedFile uploadedFileToRet = fileUploadService.downloadFile(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(uploadedFileToRet.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename " +uploadedFileToRet.getFileName())
                .body(new ByteArrayResource(uploadedFileToRet.getFileData()));

    }
}
