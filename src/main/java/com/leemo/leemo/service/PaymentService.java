package com.leemo.leemo.service;

import com.leemo.leemo.dtos.PaymentDto;
import com.leemo.leemo.entity.Balance;
import com.leemo.leemo.entity.Payment;
import com.leemo.leemo.repo.BalanceRepository;
import com.leemo.leemo.repo.PaymentRepository;
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

    public void paymentToBalance(PaymentDto dto, Long balanceId){
        Payment payment = new Payment();
        Balance balance = balanceRepository.findFirstById(balanceId);
        payment.setBalance(balance);
        payment.setStatus(dto.getStatus());
        payment.setCreatedDate(new Date());
        payment.setAmount(dto.getAmount());
        payment.setRequisite(dto.getRequisite());
        paymentRepository.save(payment);
        balanceRepository.updateBalance(payment.getAmount().intValue(),balanceId);
    }

//    public Optional<Payment> findAllByPeriod(Date fromDate, Date toDate){
//        return this.paymentRepository.getAllByPeriod(fromDate, toDate);
//    }

}
