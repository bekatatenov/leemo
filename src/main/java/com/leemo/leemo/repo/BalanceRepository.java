package com.leemo.leemo.repo;

import com.leemo.leemo.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface BalanceRepository extends JpaRepository<Balance,Long> {
    Balance findFirstById(Long id);
    @Modifying
    @Query(value = "Update balance set amount = amount - withdrawal :=? where id =:?", nativeQuery = true)
    Balance withdrawalFromBalance(@Param(value = "withdrawal")BigDecimal withdrawal,@Param(value = "id")  Long id);

    @Modifying
    @Query(value = "Update balance set amount = amount + payment :=? where id =:?", nativeQuery = true)
    Balance paymentFromBalance(@Param(value = "withdrawal")BigDecimal payment,@Param(value = "id")  Long id);


}
