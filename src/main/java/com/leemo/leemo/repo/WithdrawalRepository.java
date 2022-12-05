package com.leemo.leemo.repo;

import com.leemo.leemo.entity.Payment;
import com.leemo.leemo.entity.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WithdrawalRepository extends JpaRepository<Withdrawal,Long> {
    List<Withdrawal> findAllByBalance_Id(Long balanceId);
}

