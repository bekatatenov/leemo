package com.leemo.leemo.repo;

import com.leemo.leemo.entity.Withdrawal;
import com.leemo.leemo.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WithdrawalRepository extends JpaRepository<Withdrawal,Long> {
    @Modifying
    @Query(value = "update withdrawal w set w.status =:? where id =:?", nativeQuery = true)
    Withdrawal updateStatus(@Param("status") PaymentStatus status, @Param("id")Long id);
}

