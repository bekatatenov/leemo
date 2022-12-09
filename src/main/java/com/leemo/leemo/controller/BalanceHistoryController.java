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

    @GetMapping(value = "/balance/{id}")
    public ModelAndView showBalance( @PathVariable Long id){
        Users user = userService.findUser(id);
        ModelAndView modelAndView = new ModelAndView("balance");
        Balance balance = user.getBalance();
        modelAndView.addObject("balanceId", balance.getId());
        modelAndView.addObject("getBalance",user.getBalance());
        return modelAndView;
    }


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


}

