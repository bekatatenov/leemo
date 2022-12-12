package com.leemo.leemo.controller;


import com.leemo.leemo.entity.*;
import com.leemo.leemo.service.BalanceService;
import com.leemo.leemo.service.TaskService;
import com.leemo.leemo.service.UserService;
import com.leemo.leemo.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class WithdrawalController {
    @Autowired
    WithdrawalService withdrawalService;
    @Autowired
    UserService userService;
    @Autowired
    BalanceService balanceService;
    @Autowired
    TaskService taskService;





    @RequestMapping(value = "/withdrawal-cash", method = RequestMethod.GET)
    public ModelAndView newWithdrawal() {
        ModelAndView modelAndView = new ModelAndView("withdrawal");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users users = userService.findByMail(authentication.getName());
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setBid(users.getBalance().getId());
        modelAndView.addObject("withdrawal",withdrawal);
        return modelAndView;
    }

    @PostMapping("/withdrawal-money")
    public String payCash(@ModelAttribute("withdrawal") Withdrawal withdrawal){
        withdrawalService.WithdrawalFromBalance(withdrawal);
        return "redirect:/mainpage";
    }

    @RequestMapping(value = "/payToWork/{id}")
    public String payToWork(@PathVariable Long id) {
        Tasks tasks = taskService.findTask(id);
        try {
            withdrawalService.payToWork(tasks);
            return "redirect:/publishedTasks";
        } catch (Exception e) {
            return "redirect:/ErrorPayToWork";
        }

    }
}

//    @GetMapping(value = "/get-withdrawals-by-period")
//    public ModelAndView getPaymentsByPeriod(@RequestParam(name = "fromDate") Date fromDate,
//                                            @RequestParam(name = "toDate") Date toDate){
//        ModelAndView modelAndView = new ModelAndView("payments");
//        modelAndView.addObject("payments", this.withdrawalService.getAllByPeriod(fromDate,toDate));
//        return modelAndView;
//    }

