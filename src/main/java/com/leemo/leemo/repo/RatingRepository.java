package com.leemo.leemo.repo;

import com.leemo.leemo.entity.Balance;
import com.leemo.leemo.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface RatingRepository extends JpaRepository<Rating,Long> {
    @Modifying
    @Query(value = "Update rating set rate = rate + rate :=? where id =:?", nativeQuery = true)
    Rating updateRate(@Param(value = "rate") Double rate, @Param(value = "id")  Long id);

    Rating findFirstById(Long id);
}
