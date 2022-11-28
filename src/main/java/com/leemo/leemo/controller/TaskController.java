package com.leemo.leemo.controller;

import com.leemo.leemo.entity.Tasks;
import com.leemo.leemo.enums.TaskStatus;
import com.leemo.leemo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class TaskController {

    @Autowired
    TaskService tasksService;

    @PostMapping(value = "/create-task")
    public String createTask(@RequestBody Tasks task) {
        this.tasksService.createTask(task);
        return "Task now in waiting for validation";
    }

    @PostMapping(value = "/chek-task")
    public String checkTask(@RequestParam(value = "id") Long id, @RequestParam(value = "status") TaskStatus status) {
        this.tasksService.checkTask(id, status);
        if (status == TaskStatus.RETURNED) {
            return "change your task please";
        } else if (status == TaskStatus.PUBLISHED)
            return "your task confirmed and published";
        else return checkTask(id, status);
    }

    @PostMapping(value = "/cancel-task")
    public String cancelTask(@RequestParam(value = "id") Long id) {
        this.tasksService.cancelTask(id);
        return "task is canceled";

    }

    @PostMapping(value = "/choose-executor")
    public String chooseExecutor(@RequestParam(value = "taskId") Long taskId, @RequestParam(value = "executorId") Long executorId) {
        this.tasksService.chooseExecutor(executorId, taskId);
        return "choosen executors id:" + executorId;
    }

    @PostMapping(value = "/done-task")
    public String doneTask(@RequestParam(value = "taskId") Long taskId, @RequestParam(value = "executorId") Long executorId) {
        this.tasksService.doneTask(executorId, taskId);
        return "task finished by user:" + executorId;
    }

}



