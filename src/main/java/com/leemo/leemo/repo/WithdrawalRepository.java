package com.leemo.leemo.repo;

import com.leemo.leemo.entity.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawalRepository extends JpaRepository<Withdrawal,Long> {
}
