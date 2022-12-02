package com.leemo.leemo.controller;

import com.leemo.leemo.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PaymentController {
    @Autowired
    PaymentService paymentService;
}
