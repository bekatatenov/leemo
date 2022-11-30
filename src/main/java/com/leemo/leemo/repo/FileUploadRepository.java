package com.leemo.leemo.repo;

import com.leemo.leemo.entity.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FileUploadRepository extends JpaRepository<UploadedFile,Long> {
    UploadedFile findFirstByFileId(String fileId);
}
