package com.leemo.leemo.service;

import com.leemo.leemo.dtos.PaymentDto;
import com.leemo.leemo.entity.Balance;
import com.leemo.leemo.entity.Payment;
import com.leemo.leemo.enums.PaymentStatus;
import com.leemo.leemo.repo.BalanceRepository;
import com.leemo.leemo.repo.PaymentRepository;
import com.leemo.leemo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private BalanceRepository balanceRepository;
    @Autowired
    UserRepository userRepository;

    public void paymentToBalance(Payment payment){
        payment.setStatus(PaymentStatus.SUCCESSFUL);
        payment.setCreatedDate(new Date());
        paymentRepository.save(payment);
        balanceRepository.updateBalance(payment.getAmount().intValue(),payment.getBid());
    }


//    public Optional<Payment> findAllByPeriod(Date fromDate, Date toDate){
//        return this.paymentRepository.getAllByPeriod(fromDate, toDate);
//    }

}
