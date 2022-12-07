package com.leemo.leemo.service;

import com.leemo.leemo.dtos.GetTaskDto;
import com.leemo.leemo.dtos.TaskTzDto;
import com.leemo.leemo.entity.*;
import com.leemo.leemo.enums.TaskStatus;
import com.leemo.leemo.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;


@Service
public class TaskService {
    @Autowired
    private TasksRepository tasksRepository;
    @Autowired
    private UserRepository repository;
    @Autowired
    private FileUploadRepository fileUploadRepository;
    @Autowired
    private BalanceRepository balanceRepository;
    @Autowired
    private SiteBalanceRepository siteBalanceRepository;


    public void createTask(Tasks task, String username, Boolean guarantee) {

        Users firstByEmail = repository.findFirstByEmail(username);
        task.setStatus(TaskStatus.ON_REVIEW);
        task.setCustomerId(firstByEmail.getId());
        task.setCreatedDate(new Date());
        Balance balance = firstByEmail.getBalance();
        SiteBalance siteBalance = new SiteBalance();
        siteBalance.setCustomerId(firstByEmail.getId());
        siteBalance.setTaskId(task.getId());
        siteBalance.setAmount(task.getPrice());
        if (guarantee) {
            BigDecimal warranty1 = new BigDecimal("10");
            BigDecimal warranty2 = new BigDecimal("100");
            BigDecimal warranty = (task.getPrice().divide(warranty2).setScale(2,RoundingMode.HALF_UP));
           task.setWarranty(warranty.multiply(warranty1).setScale(2,RoundingMode.HALF_UP)); //получаем 10% от заказа
        }
        else {
            task.setWarranty(new BigDecimal("0"));

        }
        balanceRepository.updateBalance(task.getPrice().intValue() * -1, balance.getId());
        siteBalanceRepository.save(siteBalance);
        tasksRepository.save(task);
    }

    public Tasks findTask(Long id) {
        return this.tasksRepository.findById(id).orElse(null);
    }


    public void checkTask(Long id, TaskStatus taskStatus) {
        Tasks task = findTask(id);
        if (task != null) {
            task.setStatus(taskStatus);
            tasksRepository.save(task);
        }
    }

    public void cancelTask(Long id) {
        Tasks task = findTask(id);
        if (task != null) {
            task.setStatus(TaskStatus.CANCELED);
        }
        tasksRepository.save(task);
    }

    public Users findUser(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    public void chooseExecutor(Long userId, Long taskId) {
        Tasks task = findTask(taskId);
            if (task != null && task.getStatus() == TaskStatus.PUBLISHED) {
                task.setExecutorId(userId);
                task.setStatus(TaskStatus.IN_PROGRESS);
                tasksRepository.save(task);
//                siteBalanceRepository.chooseExecutor(userId,taskId);
            }

        }


    public void doneTask(Long userId, Long taskId) {
            Users user = findUser(userId);
        if (user != null) {
            Tasks task = findTask(taskId);
            if (task != null) {
                task.setStatus(TaskStatus.DONE);
                tasksRepository.save(task);
            }
        }
    }
    public Boolean checkTask(Long id){
        boolean check = false;
        if (findTask(id) != null){
        }
       return check = true;
    }
    public void payForDoneTask(Long taskId,Long executorId){
        Tasks task = findTask(taskId);
        if (task.getStatus().equals(TaskStatus.DONE))
        siteBalanceRepository.updateBalance(task.getPrice().intValue(),taskId);
        balanceRepository.updateBalance(task.getPrice().intValue(), executorId);
    }

    public UploadedFile downloadFile(String fileId) {
        return fileUploadRepository.findFirstByFileId(fileId);
    }

    public void uploadToDb(MultipartFile file, Tasks tasks) {
        UploadedFile uploadedFile = new UploadedFile();
        try {
            uploadedFile.setFileData(file.getBytes());
            uploadedFile.setFileType(file.getContentType());
            uploadedFile.setFileName(file.getOriginalFilename());
            uploadedFile.setTask(tasks);
            fileUploadRepository.save(uploadedFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public UploadedFile getFileByTaskId(Long taskId) {
        return fileUploadRepository.findByTaskId(taskId)
                .orElse(new UploadedFile());
    }


    public List<Tasks> getAllTaskAdmin() {
        return tasksRepository.findAllByStatus(TaskStatus.ON_REVIEW);
    }

    public void updateTaskStatus(Long id, String taskStatus) {
        Tasks tasks = tasksRepository.findAllById(id);
        if (taskStatus.equals("ON_REVIEW")) {
            tasks.setStatus(TaskStatus.ON_REVIEW);
        } else if (taskStatus.equals("CANCELED")) {
            tasks.setStatus(TaskStatus.CANCELED);
        }else if (taskStatus.equals("PUBLISHED")) {
            tasks.setStatus(TaskStatus.PUBLISHED);
        }else if (taskStatus.equals("IN_PROGRESS")) {
            tasks.setStatus(TaskStatus.IN_PROGRESS);
        }else if (taskStatus.equals("DONE")) {
            tasks.setStatus(TaskStatus.DONE);
        }else if (taskStatus.equals("IN_ARGUE")) {
            tasks.setStatus(TaskStatus.IN_ARGUE);
        }else if (taskStatus.equals("RESOLVED")) {
            tasks.setStatus(TaskStatus.RESOLVED);
        }else if (taskStatus.equals("RETURNED")) {
            tasks.setStatus(TaskStatus.RETURNED);
        }
        tasksRepository.save(tasks);

    }

    public List<Tasks> getAllTasksExecutor(){
        return tasksRepository.findAllByStatus(TaskStatus.PUBLISHED);
    }

    public GetTaskDto getTask(Long taskId){
    Tasks tasks = findTask(taskId);
    UploadedFile file = fileUploadRepository.findFirstByTaskId(taskId);
        return new GetTaskDto(tasks,file.getFileId());
    }

}

