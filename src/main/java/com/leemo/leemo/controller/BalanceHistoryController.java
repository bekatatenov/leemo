package com.leemo.leemo.controller;

import com.leemo.leemo.entity.Balance;
import com.leemo.leemo.entity.Payment;

import com.leemo.leemo.entity.Users;
import com.leemo.leemo.entity.Withdrawal;
import com.leemo.leemo.service.BalanceService;
import com.leemo.leemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;

@Controller
public class BalanceHistoryController {
    @Autowired
    BalanceService balanceService;
    @Autowired
    UserService userService;




    @RequestMapping(value = "/paymentsHistory", method = RequestMethod.GET)
    public ModelAndView paymentsHistory(@RequestParam(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("paymentsHistory");
        List<Payment> paymentHistory = balanceService.getPaymentsHistory(id);
        modelAndView.addObject("paymentHistory", paymentHistory);
        return modelAndView;
    }

    @RequestMapping(value = "/withdrawalHistory", method = RequestMethod.GET)
    public ModelAndView withdrawalHistory(@RequestParam(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("withdrawalHistory");
        List<Withdrawal> withdrawalHistory = balanceService.getWithdrawalHistory(id);
        modelAndView.addObject("withdrawalHistory", withdrawalHistory);
        return modelAndView;
    }
    @GetMapping(value = "/getBalance")
    public ModelAndView getBalance(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userService.findByMail(email);
        ModelAndView modelAndView = new ModelAndView("balance");
        modelAndView.addObject("balanceDTO", user.getBalance());
        return modelAndView;
    }

}

