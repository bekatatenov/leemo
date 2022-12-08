package com.leemo.leemo.controller;

import com.leemo.leemo.dtos.WithdrawalDto;
import com.leemo.leemo.entity.Balance;
import com.leemo.leemo.entity.Payment;
import com.leemo.leemo.entity.Withdrawal;
import com.leemo.leemo.service.BalanceService;
import com.leemo.leemo.service.UserService;
import com.leemo.leemo.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Date;

@Controller
public class WithdrawalController {
    @Autowired
    WithdrawalService withdrawalService;
    @Autowired
    UserService userService;
    @Autowired
    BalanceService balanceService;



//    @PostMapping(value = "/withdrawalToBankAccount")
//    public ResponseEntity<Boolean> withdrawalToBank(@RequestBody WithdrawalDto dto) {
//        boolean validUser = this.userService.chekUser(dto.getEmail());
//        if (validUser) {
//            Balance b = dto.getBalance();
//            boolean validRequisite = this.balanceService.chekBalance(b.getId());
//            if (validRequisite) {
//                this.withdrawalService.WithdrawalFromBalance(dto, b.getId());
//                return new ResponseEntity<>(true, HttpStatus.OK);
//            } else return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
//        } else return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
//    }

    @PostMapping("/withdrawal-cash")
    public ModelAndView payCash(@RequestBody Withdrawal withdrawal){
        ModelAndView modelAndView = new ModelAndView("withdrawal");
        withdrawalService.WithdrawalFromBalance(withdrawal);
        modelAndView.addObject("withdrawalFromBalance", withdrawal);
        return modelAndView;
    }

//    @GetMapping(value = "/get-withdrawals-by-period")
//    public ModelAndView getPaymentsByPeriod(@RequestParam(name = "fromDate") Date fromDate,
//                                            @RequestParam(name = "toDate") Date toDate){
//        ModelAndView modelAndView = new ModelAndView("payments");
//        modelAndView.addObject("payments", this.withdrawalService.getAllByPeriod(fromDate,toDate));
//        return modelAndView;
//    }
}
