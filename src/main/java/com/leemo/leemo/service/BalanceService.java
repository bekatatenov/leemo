package com.leemo.leemo.service;

import com.leemo.leemo.entity.*;
import com.leemo.leemo.enums.Status;
import com.leemo.leemo.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BalanceService {
    @Autowired
    BalanceRepository balanceRepository;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    WithdrawalRepository withdrawalRepository;
    @Autowired
    SiteBalanceRepository siteBalanceRepository;
    @Autowired
    UserRepository userRepository;

    public Balance getBalance(Long id){
        return balanceRepository.findFirstById(id);
    }

    public List<Withdrawal> getWithdrawalHistory(Long id){
        Balance balance = getBalance(id);
        return withdrawalRepository.findAllByBalance_Id(balance.getId());
    }
    public List<Payment> getPaymentsHistory(Long id){
        Balance balance = getBalance(id);
        return paymentRepository.findAllByBalance_Id(id);
    }
    public Boolean chekBalance(Long id){
        Balance balance= this.balanceRepository.findByIdAnAndStatus(id, Status.ACTIVE);
        return balance != null;
    }

    public void payForWork(Long customerId, Long executorId){
        Users customer = userRepository.getById(customerId);
        Users executor = userRepository.getById(executorId);

    }
}
