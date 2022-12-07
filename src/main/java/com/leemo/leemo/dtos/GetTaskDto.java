package com.leemo.leemo.dtos;


import com.leemo.leemo.entity.Tasks;
import com.leemo.leemo.entity.UploadedFile;
import com.leemo.leemo.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetTaskDto {
    Tasks tasks;
    String fileID;
    UploadedFile uploadedFile;

    public GetTaskDto(Tasks task, UploadedFile uploadedFile) {
    }

    public GetTaskDto(Tasks tasks, String fileId) {
    }
}
