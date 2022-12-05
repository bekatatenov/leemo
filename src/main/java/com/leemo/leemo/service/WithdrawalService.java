package com.leemo.leemo.service;

import com.leemo.leemo.entity.Balance;
import com.leemo.leemo.entity.Payment;
import com.leemo.leemo.entity.Withdrawal;
import com.leemo.leemo.enums.PaymentStatus;
import com.leemo.leemo.repo.BalanceRepository;
import com.leemo.leemo.repo.WithdrawalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WithdrawalService {
    @Autowired
    WithdrawalRepository withdrawalRepository;
    @Autowired
    BalanceRepository balanceRepository;

    public void WithdrawalFromBalance(Withdrawal withdrawal, Long balanceId) {
        withdrawalRepository.save(withdrawal);
        Balance balance = balanceRepository.findFirstById(balanceId);
        if (balance.getAmount().compareTo(withdrawal.getAmount()) >= 0) {
            balanceRepository.withdrawalFromBalance((withdrawal.getAmount()), balanceId);
        }
        else { String error = "Not enough money on balance";
        }
    }
}
