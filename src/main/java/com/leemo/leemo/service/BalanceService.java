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
    private BalanceRepository balanceRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private WithdrawalRepository withdrawalRepository;
    @Autowired
    private SiteBalanceRepository siteBalanceRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;

    public Balance getBalance(Long id){
        return balanceRepository.findFirstById(id);
    }

    public List<Withdrawal> getWithdrawalHistory(Long id){
        Users users = userService.findUser(id);
        Balance balance = users.getBalance();
        return withdrawalRepository.findAllByBalance_Id(balance.getId());
    }
    public List<Payment> getPaymentsHistory(Long id){
        Users users = userService.findUser(id);
        Balance balance = users.getBalance();
        return paymentRepository.findAllByBalance_Id(balance.getId());
    }
    public Boolean chekBalance(Long id){
        Balance balance= this.balanceRepository.findByIdAndStatus(id, Status.ACTIVE);
        return balance != null;
    }

    public void payForWork(Long taskId){
        Tasks task = taskService.findTask(taskId);
        Users customer = userService.findUser(task.getCustomerId());
        Users executor = userService.findUser(task.getExecutorId());

    }
}
