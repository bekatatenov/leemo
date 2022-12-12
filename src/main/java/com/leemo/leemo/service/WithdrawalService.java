package com.leemo.leemo.service;

import com.leemo.leemo.dtos.WithdrawalDto;
import com.leemo.leemo.entity.*;
import com.leemo.leemo.enums.PaymentStatus;
import com.leemo.leemo.repo.BalanceRepository;
import com.leemo.leemo.repo.SiteBalanceRepository;
import com.leemo.leemo.repo.UserRepository;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SiteBalanceRepository siteBalanceRepository;



    public void WithdrawalFromBalance(Withdrawal withdrawal) {
        Balance balance = balanceRepository.findFirstById(withdrawal.getBid());
        if (balance.getAmount().compareTo(withdrawal.getAmount()) >= 0) {
            balanceRepository.updateBalance(withdrawal.getAmount().intValue() * -1, balance.getId());
            withdrawalRepository.save(withdrawal);
        } else {
            String error = "Not enough money on balance";
        }
    }

    public void payToWork(Tasks tasks) throws Exception  {
        Users user = userRepository.getById(tasks.getExecutorId());
            if (user.getBalance().getAmount().compareTo(tasks.getWarranty()) >= 0) {
                SiteBalance siteBalance = siteBalanceRepository.getSiteBalanceByTaskId(tasks.getId());
                siteBalance.setAmount(siteBalance.getAmount().add(tasks.getWarranty()));
                siteBalance.setExecutorId(tasks.getExecutorId());
                siteBalanceRepository.save(siteBalance);
                balanceRepository.updateBalance(tasks.getWarranty().intValue(), user.getBalance().getId());
            }
            else {
                throw new Exception("Error");
            }




    }
//    public List<Withdrawal> getAllByPeriod(Date fromDate, Date toDate){
//        return this.withdrawalRepository.getAllByPeriod(fromDate, toDate);
//    }
}
