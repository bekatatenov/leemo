package com.leemo.leemo.service;

import com.leemo.leemo.entity.Payment;
import com.leemo.leemo.repo.BalanceRepository;
import com.leemo.leemo.repo.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    BalanceRepository balanceRepository;

    public void paymentToBalance(Payment payment,Long balanceId){
        paymentRepository.save(payment);
        balanceRepository.updateBalance(payment.getAmount(),balanceId);
    }
}
