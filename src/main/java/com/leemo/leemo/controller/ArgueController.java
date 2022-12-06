package com.leemo.leemo.controller;

import com.leemo.leemo.enums.Roles;
import com.leemo.leemo.service.ArgueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class ArgueController {
    @Autowired
   private ArgueService argueService;
    @PostMapping(value = "/create-argue")
    public String save(@RequestParam (name = "id") Long taskId) {
        this.argueService.createArgue(taskId);
        return "Task now in argue";
    }
    @PostMapping(value = "/resolve")
    public String resolveArgue(@RequestParam(name = "argueId")Long taskId,@RequestParam(name = "user") Boolean user  ){
        this.argueService.resolveArgue(taskId,user);
        return "dispute resolved in favor of";
    }

}
