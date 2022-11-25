package com.leemo.leemo.service;

import com.leemo.leemo.entity.Tasks;
import com.leemo.leemo.entity.Users;
import com.leemo.leemo.enums.TaskStatus;
import com.leemo.leemo.repo.TasksRepository;
import com.leemo.leemo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TaskService {
    @Autowired
    TasksRepository tasksRepository;
    @Autowired
    UserRepository repository;
    public void createTask(Tasks task){
        task.setStatus(TaskStatus.ON_REVIEW);
        task.setCreatedDate(new Date());
        tasksRepository.save(task);
    }

    public Tasks findTask(Long id){
    return this.tasksRepository.findById(id).orElse(null);

    }

    public void checkTask(Long id, TaskStatus taskStatus){
        if (findTask(id) != null){
        findTask(id).setStatus(taskStatus);
        }
    }
    public void cancelTask(Long id){
        if (findTask(id) != null){
            findTask(id).setStatus(TaskStatus.CANCELED);
        }
    }
    public Users findUser(Long id){
        return this.repository.findById(id).orElse(null);
    }
    public void chooseExecutor(Long userId, Long taskId){
        if (findUser(userId)!=null){
            if (findTask(taskId)!=null){
                findTask(taskId).setExecutorId(userId);
                findTask(taskId).setStatus(TaskStatus.IN_PROGRESS);
            }
        }
    }
    public void doneTask(Long userId, Long taskId) {
        if (findUser(userId) != null) {
            if (findTask(taskId) != null) {
                findTask(taskId).setStatus(TaskStatus.DONE);
            }
        }
    }
}
