package com.leemo.leemo.controller;

import com.leemo.leemo.entity.Payment;
import com.leemo.leemo.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @PostMapping(value = "/createPayment")
    public String createPayment(@RequestParam("payment") Payment payment, @RequestParam("balanceId")Long balanceId) {
        paymentService.paymentToBalance(payment,balanceId);
        return "success";
    }


}
