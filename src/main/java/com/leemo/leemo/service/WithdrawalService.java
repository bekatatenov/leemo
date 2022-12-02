package com.leemo.leemo.service;

import com.leemo.leemo.entity.Balance;
import com.leemo.leemo.entity.Withdrawal;
import com.leemo.leemo.enums.PaymentStatus;
import com.leemo.leemo.repo.BalanceRepository;
import com.leemo.leemo.repo.WithdrawalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WithdrawalService {
    @Autowired
    WithdrawalRepository withdrawalRepository;
    @Autowired
    BalanceRepository balanceRepository;

    public Balance findBalance(Long id){
        return this.balanceRepository.findById(id).orElse(null);
    }

    public Withdrawal saveWithdrawal(Long balanceId, BigDecimal amount){
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setAmount(amount);
        withdrawal.setBalance(findBalance(balanceId));
        withdrawal.setStatus(PaymentStatus.WAITING);
       return this.withdrawalRepository.save(withdrawal);
    }

    public Withdrawal findById(Long id){
       return this.withdrawalRepository.findById(id).orElse(null);
    }
    public void doWithdrawal(Long withdrawalId, Long balanceId){
      if (findById(withdrawalId)!=null){
          balanceRepository.updateBalance(findById(withdrawalId).getAmount(),balanceId);
          withdrawalRepository.updateStatus(PaymentStatus.SUCCESSFUL,withdrawalId);
      }
      else withdrawalRepository.updateStatus(PaymentStatus.WAITING,withdrawalId);
    }
}
