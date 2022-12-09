package com.leemo.leemo.repo;

import com.leemo.leemo.entity.Users;
import com.leemo.leemo.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface UserRepository extends JpaRepository <Users,Long>{
    Users findFirstByEmail(String username);
    Users findByEmail(String mail);
    Users findByEmailAndStatus(String email, Status status);
    Users findByEmailAndBalance(String email, BigDecimal balance);
}
