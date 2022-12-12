package com.leemo.leemo.service;

import com.leemo.leemo.entity.*;
import com.leemo.leemo.enums.PaymentStatus;
import com.leemo.leemo.enums.TaskStatus;
import com.leemo.leemo.repo.BalanceRepository;
import com.leemo.leemo.repo.PaymentRepository;
import com.leemo.leemo.repo.SiteBalanceRepository;
import com.leemo.leemo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private BalanceRepository balanceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SiteBalanceRepository siteBalanceRepository;

    public void paymentToBalance(Payment payment){
        payment.setStatus(PaymentStatus.SUCCESSFUL);
        payment.setCreatedDate(new Date());
        paymentRepository.save(payment);
        balanceRepository.updateBalance(payment.getAmount().intValue(),payment.getBid());
    }



    public void payForWork(Tasks tasks){
       SiteBalance siteBalance = siteBalanceRepository.getSiteBalanceByTaskId(tasks.getId());
        siteBalance.setAmount(siteBalance.getAmount().subtract(tasks.getPrice()));
        siteBalance.setExecutorId(tasks.getExecutorId());
        siteBalanceRepository.save(siteBalance);
        Users users = userRepository.getById(tasks.getExecutorId());
        Payment payment = new Payment();
        payment.setAmount(tasks.getPrice());
        payment.setBid(users.getBalance().getId());
        payment.setRequisite("siteBalance");
        paymentToBalance(payment);

    }

//    public Optional<Payment> findAllByPeriod(Date fromDate, Date toDate){
//        return this.paymentRepository.getAllByPeriod(fromDate, toDate);
//    }

}
