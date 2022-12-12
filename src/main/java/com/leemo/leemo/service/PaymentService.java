package com.leemo.leemo.service;

import com.leemo.leemo.entity.*;
import com.leemo.leemo.enums.PaymentStatus;
import com.leemo.leemo.enums.TaskStatus;
import com.leemo.leemo.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    @Autowired
    private TasksRepository tasksRepository;

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
        tasks.setStatus(TaskStatus.CLOSED);
        tasksRepository.save(tasks);
    }



//    public Optional<Payment> findAllByPeriod(Date fromDate, Date toDate){
//        return this.paymentRepository.getAllByPeriod(fromDate, toDate);
//    }

}
