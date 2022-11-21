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
    public String save(@RequestParam (name = "id") Long id) {
        this.argueService.createArgue(id);
        return "Task now in argue";
    }
    @PostMapping(value = "/resolve")
    public String resolveArgue(@RequestParam(name = "argueId")Long argueId, @RequestParam(name = "role")Roles roles, @RequestParam(name = "taskId") Long taskId){
        this.argueService.resolveArgue(argueId,taskId,roles);
        return "dispute resolved in favor of" + roles;
    }

}
