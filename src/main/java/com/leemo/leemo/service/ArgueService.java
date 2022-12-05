package com.leemo.leemo.service;

import com.leemo.leemo.entity.Argue;
import com.leemo.leemo.entity.Tasks;
import com.leemo.leemo.enums.ArgueEnums;
import com.leemo.leemo.enums.Roles;
import com.leemo.leemo.enums.TaskStatus;
import com.leemo.leemo.repo.ArgueRepository;
import com.leemo.leemo.repo.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
        Argue argue = new Argue();
        Tasks tasks = findTask(id);
        if(tasks!= null)
            argue.setArgueEnums(ArgueEnums.IN_PROGRESS);
            argue.setCreatedDate(new Date());
            tasks.setStatus(TaskStatus.IN_ARGUE);
            tasksRepository.save(tasks);
            argueRepository.save(argue);
    }
   public Argue findArgue(Long id){
        return this.argueRepository.findById(id).orElse(null);
   }

   public void resolveArgue(Long argueId, Long taskId, Roles role){
       Argue argue = findArgue(argueId);
        if (argue!= null){
            argue.setDecisionInFavor(role);
            argue.setResolvedDate(new Date());
            Tasks tasks = findTask(taskId);
            tasks.setStatus(TaskStatus.RESOLVED);
            tasksRepository.save(tasks);
            argueRepository.save(argue);

        }
   }
}
