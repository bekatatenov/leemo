package com.leemo.leemo.controller;

import com.leemo.leemo.entity.Tasks;
import com.leemo.leemo.enums.TaskStatus;
import com.leemo.leemo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TaskController {

    @Autowired
    TaskService tasksService;

    @PostMapping(value = "/created-task")
    public String createdTask(@ModelAttribute(name = "tasks") Tasks task, BindingResult bindingResult) {
        this.tasksService.createTask(task);
        return "redirect:/mainpage";
    }

    @GetMapping(value = "/create-task")
    public String createTask(Model model, @ModelAttribute Tasks tasks, BindingResult bindingResult){
        if (tasks == null) tasks = new Tasks();
        model.addAttribute("tasks", tasks);
        return "create-task";
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



