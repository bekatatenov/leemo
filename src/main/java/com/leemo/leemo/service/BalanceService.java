package com.leemo.leemo.service;

import com.leemo.leemo.entity.Balance;
import com.leemo.leemo.entity.Payment;
import com.leemo.leemo.entity.Withdrawal;
import com.leemo.leemo.repo.BalanceRepository;
import com.leemo.leemo.repo.PaymentRepository;
import com.leemo.leemo.repo.WithdrawalRepository;
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
}
