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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;

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
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        this.userService.save(user);
        return "login";
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

    @RequestMapping(value = "/getBalance")
    public ModelAndView getBalance(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("balance");
        Users users = userService.findUser(id);
        Balance balance = users.getBalance();
        modelAndView.addObject(balance);
        return modelAndView;
    }

    @RequestMapping(value = "/getUserProfile/{id}")
    public ModelAndView getUserPage(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("userProfile");
        Users users = userService.findUser(id);
        modelAndView.addObject("Profile", users);
        return modelAndView;
    }
    @RequestMapping(name = "/showProfile")
    public String showProfile(){
        return "rediret:/profile";
    }
}
