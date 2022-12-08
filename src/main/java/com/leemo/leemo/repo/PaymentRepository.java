package com.leemo.leemo.repo;

import com.leemo.leemo.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByBid(Long balanceId);

//    @Query(value = " select * from payment where createdDate between : fromDate and :toDate", nativeQuery = true)
//    Optional<Payment> getAllByPeriod(@Param(value = "fromDate") Date fromDate,
//                                     @Param(value = "toDate") Date toDate);
}
