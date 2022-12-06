package com.leemo.leemo.service;

import com.leemo.leemo.entity.Argue;
import com.leemo.leemo.entity.SiteBalance;
import com.leemo.leemo.entity.Tasks;
import com.leemo.leemo.enums.ArgueEnums;
import com.leemo.leemo.enums.Roles;
import com.leemo.leemo.enums.TaskStatus;
import com.leemo.leemo.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ArgueService {
    @Autowired
   private ArgueRepository argueRepository;
    @Autowired
   private TasksRepository tasksRepository;
    @Autowired
    private SiteBalanceRepository siteBalanceRepository;
    @Autowired
    private BalanceRepository balanceRepository;

    public Tasks findTask(Long id){
        return this.tasksRepository.findById(id).orElse(null);
    }
    public void createArgue(Long id){
        Argue argue = new Argue();
        Tasks tasks = findTask(id);
        if(tasks != null)
            argue.setArgueEnums(ArgueEnums.IN_PROGRESS);
            argue.setCreatedDate(new Date());
        assert tasks != null;
        tasks.setStatus(TaskStatus.IN_ARGUE);
            tasksRepository.save(tasks);
            argueRepository.save(argue);
    }
   public Argue findArgue(Long id){
        return this.argueRepository.findById(id).orElse(null);
   }

   public void resolveArgue(Long argueId, Boolean customer){
       Argue argue = findArgue(argueId);
       Tasks task = argue.getTask();
        if (task.getStatus().equals(TaskStatus.IN_ARGUE)) {
            if (customer) {
                argue.setUserId(task.getCustomerId());
                siteBalanceRepository.updateBalance(task.getPrice().intValue(), task.getCustomerId());
                balanceRepository.updateBalance(task.getPrice().intValue(), task.getCustomerId());
            }
            else {
                argue.setUserId(task.getExecutorId());
                balanceRepository.updateBalance(task.getPrice().intValue(), task.getExecutorId());
                siteBalanceRepository.updateBalance(task.getPrice().intValue(), task.getCustomerId());

            }
            argue.setResolvedDate(new Date());
            task.setStatus(TaskStatus.RESOLVED);
            tasksRepository.save(task);
            argueRepository.save(argue);
        }
   }


}
