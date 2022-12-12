package com.leemo.leemo.repo;

import com.leemo.leemo.entity.SiteBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SiteBalanceRepository extends JpaRepository<SiteBalance,Long> {

    @Transactional
    @Modifying
    @Query(value = "Update SiteBalance set amount = amount + :payment where taskId = :id", nativeQuery = true)
    void updateBalance(@Param(value = "payment") Integer payment, @Param(value = "id")  Long id);
    SiteBalance getSiteBalanceByTaskId(Long id);
}
