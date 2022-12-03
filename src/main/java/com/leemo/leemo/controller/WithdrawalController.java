package com.leemo.leemo.controller;

import com.leemo.leemo.entity.Withdrawal;
import com.leemo.leemo.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
public class WithdrawalController {
    @Autowired
    WithdrawalService withdrawalService;
    @PostMapping(name = "/giveMeMoney")
    public String createWithdrawal(@RequestParam(name = "balanceId")Long balanceId,@RequestParam(name = "withdrawal") Withdrawal withdrawal){
        withdrawalService.WithdrawalFromBalance(withdrawal,balanceId);
        return "";
    }
}
