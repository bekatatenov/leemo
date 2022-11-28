package com.leemo.leemo.repo;

import com.leemo.leemo.entity.Token;
import com.leemo.leemo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Object> findByTokenAndUsers(Integer token, Users users);
    Optional<Object> findByToken(Integer token);
}