package com.leemo.leemo.controller;

import com.leemo.leemo.entity.Users;
import com.leemo.leemo.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class UserController {
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView("registration");
        modelAndView.addObject("user", new Users());
        return modelAndView;
    }
    @PostMapping(value = "/registration")
    public String registration(@ModelAttribute (name = "user")Users user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        this.userService.save(user);
        return "login";
    }
}
