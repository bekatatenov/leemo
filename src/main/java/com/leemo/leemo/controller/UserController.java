package com.leemo.leemo.controller;

import com.leemo.leemo.dtos.NewPassUserDto;
import com.leemo.leemo.entity.Balance;
import com.leemo.leemo.entity.Token;
import com.leemo.leemo.entity.Users;
import com.leemo.leemo.service.BalanceService;
import com.leemo.leemo.service.EmailSendlerService;
import com.leemo.leemo.service.TokenService;
import com.leemo.leemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import java.math.BigDecimal;

@Controller
public class UserController {
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private EmailSendlerService emailSendlerService;
    @Autowired
    private BalanceService balanceService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView("registration");
        modelAndView.addObject("user", new Users());
        return modelAndView;
    }
    @PostMapping(value = "/registration")
    public String registration(@ModelAttribute (name = "user")Users user) {
        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            this.userService.save(user);
            return "login";
        }catch (Exception e){
            return "redirect:/register";
        }
    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginPage(@RequestParam(value = "error", required = false) String error,
                                  @RequestParam(value = "logout",	required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Почта или пароль неверны");
            model.setViewName("/login");
        }
        if (logout != null) {
            model.addObject("logout", "Logged out successfully.");
            model.setViewName("/login");
        }
        return model;
    }
    @RequestMapping (value = "/mainpage" , method = RequestMethod.GET)
    public String mainpage(){
        return "mainpage";
    }

    @GetMapping(value = "/forgotPassword")
    public String resetPasswordPage() {
        return "forgotPassword";
    }

    @PostMapping(value = "/passwordRecoveryEmail")
    public ModelAndView getEmailForResetPassword(@RequestParam (name = "email") String mail) throws MessagingException {
        ModelAndView modelAndView = new ModelAndView("newPasswordUser");
        Users saved = userService.findByMail(mail);
        Token token = tokenService.saveToken(saved, tokenService.makeToken());
        emailSendlerService.sendEmail(saved.getEmail(),"Восстановление пароля", "Введите данный токен, чтобы сбросить ваш пароль: " + String.valueOf(token.getToken()));
        NewPassUserDto newPassUser = new NewPassUserDto();
        newPassUser.setEmail(saved.getEmail());
        modelAndView.addObject("reset", newPassUser);
        return modelAndView;
    }

    @PostMapping(value = "/newPasswordUser")
    public String newPassword(@ModelAttribute(name = "reset") NewPassUserDto newPasswordUser) throws Exception {
        Users users = userService.findByMail(newPasswordUser.getEmail());
        Token byUserAndToken = tokenService.findByUserAndToken(users, newPasswordUser.getToken());
        if (newPasswordUser.getPassword().equals(newPasswordUser.getRepeatPassword())) {
            users.setPassword(bCryptPasswordEncoder.encode(newPasswordUser.getPassword()));
            userService.Update(users);
            tokenService.deleteToken(byUserAndToken);
            return "login";
        } else {
            return "changePassword";
        }
    }

    @GetMapping(value = "/getUserProfile")
    public ModelAndView getUserPage(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users users = userService.findByMail(email);
        ModelAndView modelAndView = new ModelAndView("Userprofile");
        modelAndView.addObject("profile", users);
        return modelAndView;
    }
    @RequestMapping(name = "/showProfile")
    public String showProfile(@RequestParam Long id){
        userService.findUser(id);
        return "redirect:/profile";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String updateForm(@PathVariable(value = "id") Long id, Model model) {
        Users users = userService.findUser(id);
        model.addAttribute("users", users);
        return "update";
    }

    @GetMapping("/update")
    public String updateUser(@ModelAttribute("user") Users user) {
        userService.updateUser(user);
        return "redirect:/mainpage";
    }
}
