package com.leemo.leemo.controller;

import com.leemo.leemo.dtos.GetTaskDto;
import com.leemo.leemo.dtos.TaskTzDto;
import com.leemo.leemo.entity.*;
import com.leemo.leemo.enums.TaskStatus;
import com.leemo.leemo.repo.BalanceRepository;
import com.leemo.leemo.service.BalanceService;
import com.leemo.leemo.service.CandidatesService;
import com.leemo.leemo.service.TaskService;
import com.leemo.leemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
public class TaskController {

    @Autowired
    TaskService tasksService;

    @Autowired
    UserService userService;

    @Autowired
    CandidatesService candidatesService;


//    @PostMapping(value = "/created-task")
//    public String createdTask(@ModelAttribute(name = "tasks") Tasks task, BindingResult bindingResult) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String username = ((UserDetails) principal).getUsername();
//        this.tasksService.createTask(task, username);
//        return "redirect:/mainpage";
//    }

    @GetMapping(value = "/create-task")
    public String createTask(Model model, @ModelAttribute TaskTzDto tasks, BindingResult bindingResult) {
        if (tasks == null) tasks = new TaskTzDto();
        tasks.setGuarantee(false);
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

    @GetMapping(value = "/choose-executor/{id}")
    public String chooseExecutor(@PathVariable Long id) {
        Candidates candidate = candidatesService.getCandidate(id);
        Users users = userService.findByMail(candidate.getExecutor());
        Tasks tasks = tasksService.findTask(candidate.getTaskId());
        this.tasksService.chooseExecutor(tasks.getId(), users.getId());
        this.candidatesService.deleteTaskCandidates(id);
        return "redirect:/mainpage";
    }



    @PostMapping(value = "/done-task")
    public String doneTask(@RequestParam(value = "taskId") Long taskId, @RequestParam(value = "executorId") Long executorId) {
        this.tasksService.doneTask(executorId, taskId);
        return "task finished by user:" + executorId;
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> uploadedFile(@PathVariable String id) {
        UploadedFile uploadedFileToRet = tasksService.downloadFile(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(uploadedFileToRet.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename " + uploadedFileToRet.getFileName())
                .body(new ByteArrayResource(uploadedFileToRet.getFileData()));

    }

    @GetMapping("/downloadFile/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String id) {
        UploadedFile uploadedFileToRet = tasksService.getFileByTaskId(Long.valueOf(id));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(uploadedFileToRet.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename " + uploadedFileToRet.getFileName())
                .body(new ByteArrayResource(uploadedFileToRet.getFileData()));

    }

    @GetMapping("/showTask/{id}")
    public String showTask(@PathVariable Long id, Model model) {

        Tasks task = tasksService.findTask(id);
        UploadedFile uploadedFile = tasksService.getFileByTaskId(task.getId());
        model.addAttribute("TaskDto", new GetTaskDto(task, uploadedFile));
        return "showTask";
    }

//    @PostMapping("/upload/db")
//    public void uploadDb(@RequestParam("file")MultipartFile multipartFile){
//        tasksService.uploadToDb(multipartFile);
//    }

    @PostMapping("/uploadTask")
    public String createdTaskWithTZ(@ModelAttribute(name = "tasks") TaskTzDto task, BindingResult bindingResult) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        Boolean guaranty = task.getGuarantee();
        Tasks newTask = new Tasks(task.getId(),
                task.getCustomerId(),
                task.getHeaderTitle(),
                task.getTitle(),
                task.getStatus(),
                task.getRequirements(),
                task.getStackTech(),
                task.getDeveloperRequirements(),
                task.getCreatedDate(),
                task.getGuarantee(),
                task.getPrice());
        this.tasksService.createTask(newTask, username, guaranty);
        tasksService.uploadToDb(task.getFile(), newTask);
        return "redirect:/mainpage";
    }


    @RequestMapping(value = "/adminTasks", method = RequestMethod.GET)
    public ModelAndView adminTasks() {
        ModelAndView modelAndView = new ModelAndView("tasksAdmin");
        List<Tasks> allNewTasks = tasksService.getAllTaskAdmin();
        modelAndView.addObject("allNewTasks", allNewTasks);
        List<TaskStatus> status = new ArrayList<TaskStatus>(Arrays.asList(TaskStatus.values()));
        modelAndView.addObject("Status", status);


        return modelAndView;
    }


    @PostMapping(value = "/saveTaskesAdmins")
    public String saveTasks(@RequestParam(name = "id") Long id, @RequestParam(name = "TaskStatus") String Status) {
        tasksService.updateTaskStatus(id, Status);
        return "redirect:/adminTasks";
    }

    @RequestMapping(value = "/publishedTasks", method = RequestMethod.GET)
    public ModelAndView userTasks() {
        ModelAndView modelAndView = new ModelAndView("publishedTasks");
        List<Tasks> publishedTasks = tasksService.getAllTasksExecutor();
        modelAndView.addObject("publishTasks", publishedTasks);
        List<TaskStatus> status = new ArrayList<TaskStatus>(Arrays.asList(TaskStatus.values()));
        modelAndView.addObject("Status", status);
        return modelAndView;
    }


    @GetMapping(value = "/getTask/{id}")
    public ModelAndView getTask(@PathVariable Long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ModelAndView modelAndView = new ModelAndView("getTask");
        modelAndView.addObject("taskDto", tasksService.getTask(id));
        return modelAndView;
    }


//    @GetMapping(value = "/getTask/{id}")
//    public ModelAndView getCandidates(@PathVariable Long id) {
//        ModelAndView modelAndView = new ModelAndView("");
//        List<Candidates> candidates = candidatesService.getCandidates(id);
//        return modelAndView;
//    }

    @PostMapping(value = "/click")
    public String candidate(@RequestParam(name = "id") Long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String executor = ((UserDetails) principal).getUsername();
        try {
            candidatesService.respond(id, executor);
            return "redirect:/publishedTasks";
        } catch (Exception e) {
            return "redirect:/ErrorPage";
        }
    }

    @PostMapping(value = "/changeTask")
    public String changeTask(@RequestParam(name = "id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users users = userService.findByMail(authentication.getName());
        Tasks tasks = tasksService.findTask(id);
        tasks.setExecutorId(users.getId());
        tasksService.update(tasks);
        return "redirect:/getTask?id=" + id;
    }
    @RequestMapping(value = "/customerTasks", method = RequestMethod.GET)
    public String customerTask(){
        return "redirect:/getCustomerTasks";
    }


    @GetMapping(value = "/getCustomerTasks")
    public ModelAndView customerTasks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users user = userService.findByMail(authentication.getName());
        ModelAndView modelAndView = new ModelAndView("customerTasks");
        List<Tasks> customerTasks = tasksService.getAllTasks(user.getId());
        modelAndView.addObject("customerTasks", customerTasks);
        return modelAndView;
    }

    @RequestMapping(value = "/tasksCandidates{id}", method = RequestMethod.GET)
    public ModelAndView candidateTasks(@RequestParam(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("taskCandidates");
        List<Candidates> candidates = candidatesService.getCandidates(id);
        modelAndView.addObject("taskCandidates", candidates);
        return modelAndView; }




    @RequestMapping(value = "/mainpage-exit", method = RequestMethod.POST)
    public String Exit2() {
        return "redirect:/mainpage";
    }

    @RequestMapping(value = "/main-page-exit", method = RequestMethod.POST)
    public String Exit() {
        return "redirect:/mainpage";
    }

    @RequestMapping(value = "/ErrorPage", method = RequestMethod.GET)
    public String ErrorPage() {
        return "ErrorPage";
    }

}
