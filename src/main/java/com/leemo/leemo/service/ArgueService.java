package com.leemo.leemo.service;

import com.leemo.leemo.entity.Tasks;
import com.leemo.leemo.enums.TaskStatus;
import com.leemo.leemo.repo.ArgueRepository;
import com.leemo.leemo.repo.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArgueService {
    @Autowired
   private ArgueRepository argueRepository;
    @Autowired
   private TasksRepository tasksRepository;
    public Tasks findTask(Long id){
        return this.tasksRepository.findById(id).orElse(null);
    }
    public void createArgue(Long id){
        if(findTask(id)!= null)
            findTask(id).setStatus(TaskStatus.IN_ARGUE);
    }
}
