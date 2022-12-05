package com.leemo.leemo.dtos;



import com.leemo.leemo.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskTzDto {

    BigDecimal price;
    Long id;
    Long customerId;
    String headerTitle;
    String title;
    TaskStatus status;
    String requirements;
    String stackTech;
    String developerRequirements;
    Date createdDate;
    Long executorId;
    MultipartFile file;
    Boolean guarantee;

}
