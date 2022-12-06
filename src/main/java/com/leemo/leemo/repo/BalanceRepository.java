package com.leemo.leemo.repo;

import com.leemo.leemo.entity.Balance;
import com.leemo.leemo.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Repository
public interface BalanceRepository extends JpaRepository<Balance,Long> {
    Balance findFirstById(Long id);
    Balance findByIdAndStatus(Long id, Status status);

    @Transactional
    @Modifying
    @Query(value = "Update balance set amount = amount + :withdrawal where id = :id", nativeQuery = true)
    void updateBalance(@Param(value = "withdrawal") Integer withdrawal,@Param(value = "id")  Long id);

}
