package com.leemo.leemo.repo;

import com.leemo.leemo.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<Balance,Long> {
}
