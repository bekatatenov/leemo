package com.leemo.leemo.controller;

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

}
