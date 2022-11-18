package com.leemo.leemo.dao;

import com.leemo.leemo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <Users,Long>{
    Users findFirstByEmail(String username);
}
