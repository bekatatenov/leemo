package com.leemo.leemo.repo;

import com.leemo.leemo.entity.Payment;
import com.leemo.leemo.entity.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface WithdrawalRepository extends JpaRepository<Withdrawal,Long> {
    List<Withdrawal> findAllByBid(Long balanceId);

//    @Query(value = " select * from withdrawal where createdDate between : fromDate and :toDate", nativeQuery = true)
//    List<Withdrawal> getAllByPeriod(@Param(value = "fromDate") Date fromDate,
//                                 @Param(value = "toDate") Date toDate);
}

