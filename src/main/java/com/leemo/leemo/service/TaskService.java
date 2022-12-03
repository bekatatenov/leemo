package com.leemo.leemo.service;

import com.leemo.leemo.entity.Tasks;
import com.leemo.leemo.entity.UploadedFile;
import com.leemo.leemo.entity.Users;
import com.leemo.leemo.enums.TaskStatus;
import com.leemo.leemo.repo.FileUploadRepository;
import com.leemo.leemo.repo.TasksRepository;
import com.leemo.leemo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Date;
import java.util.List;


@Service
public class TaskService {
    @Autowired
    TasksRepository tasksRepository;
    @Autowired
    UserRepository repository;
    @Autowired
    FileUploadRepository fileUploadRepository;

    public void createTask(Tasks task, String username) {
        Users firstByEmail = repository.findFirstByEmail(username);
        task.setStatus(TaskStatus.ON_REVIEW);
        task.setCustomerId(firstByEmail.getId());
        task.setCreatedDate(new Date());
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
    }

    public Users findUser(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    public void chooseExecutor(Long userId, Long taskId) {
        Tasks task = findTask(taskId);
            if (task != null) {
                task.setExecutorId(userId);
                task.setStatus(TaskStatus.IN_PROGRESS);
                tasksRepository.save(task);
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
}

