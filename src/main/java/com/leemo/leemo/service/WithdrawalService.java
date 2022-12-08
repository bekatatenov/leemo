package com.leemo.leemo.service;

import com.leemo.leemo.dtos.WithdrawalDto;
import com.leemo.leemo.entity.Balance;
import com.leemo.leemo.entity.Payment;
import com.leemo.leemo.entity.Withdrawal;
import com.leemo.leemo.enums.PaymentStatus;
import com.leemo.leemo.repo.BalanceRepository;
import com.leemo.leemo.repo.WithdrawalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WithdrawalService {
    @Autowired
    private WithdrawalRepository withdrawalRepository;
    @Autowired
    private BalanceRepository balanceRepository;



    public void WithdrawalFromBalance(Withdrawal withdrawal) {
        Balance balance = balanceRepository.findFirstById(withdrawal.getBid());
        if (balance.getAmount().compareTo(withdrawal.getAmount()) >= 0) {

            balanceRepository.updateBalance(withdrawal.getAmount().intValue() * -1, balance.getId());
        } else {
            String error = "Not enough money on balance";
        }
    }
//    public List<Withdrawal> getAllByPeriod(Date fromDate, Date toDate){
//        return this.withdrawalRepository.getAllByPeriod(fromDate, toDate);
//    }
}
