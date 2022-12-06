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
    WithdrawalRepository withdrawalRepository;
    @Autowired
    BalanceRepository balanceRepository;



    public void WithdrawalFromBalance(WithdrawalDto dto, Long balanceId) {
        Withdrawal withdrawal = new Withdrawal();
        Balance balance = balanceRepository.findFirstById(balanceId);
        withdrawal.setBalance(balance);
        withdrawal.setStatus(dto.getStatus());
        withdrawal.setCreatedDate(new Date());
        withdrawal.setAmount(dto.getAmount());
        withdrawal.setRequisite(dto.getRequisite());
        withdrawalRepository.save(withdrawal);
        if (balance.getAmount().compareTo(withdrawal.getAmount()) >= 0) {

            balanceRepository.updateBalance(withdrawal.getAmount().intValue() * -1, balanceId);
        } else {
            String error = "Not enough money on balance";
        }
    }
    public List<Withdrawal> getAllByPeriod(Date fromDate, Date toDate){
        return this.withdrawalRepository.getAllByPeriod(fromDate, toDate);
    }
}
